package com.qyxy.record.aop;

import com.alibaba.fastjson.JSON;
import com.qyxy.record.common.Result;
import com.qyxy.record.common.ServiceException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by stefan on 16-1-25.
 */
@Component
@Aspect
@Lazy(value = false)
public class ControllerAspect {
    Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Around("execution(* com.qyxy.record.controller.*.*(..)) && !@annotation(NotAspect)")
    public Object doAround(ProceedingJoinPoint pjp) {
        long startAt = System.currentTimeMillis();
        try {
            Object target = pjp.getTarget();
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();

            ParamCanNull methodParamCanNull = method.getAnnotation(ParamCanNull.class);
            Object[] params = pjp.getArgs();
            List<Object> serializableParams = new LinkedList<>();

            Annotation[][] parameterAnnotations = method.getParameterAnnotations();

            for (int i = 0; i<params.length; i++) {
                Object param = params[i];
                if (!(param instanceof ServletRequest || param instanceof ServletResponse || param instanceof MultipartFile)) { //避免再次调用getInputStream(), getReader()等抛异常
                    serializableParams.add(param);
                }
                if (Objects.isNull(methodParamCanNull)) {
                    Annotation[] annotations = parameterAnnotations[i];
                    if (ArrayUtils.isNotEmpty(annotations)){
                        boolean isNotCanNull = Stream.of(annotations).noneMatch(annotation -> annotation.annotationType().equals(ParamCanNull.class));
                        if (isNotCanNull && (Objects.isNull(param) || StringUtils.isBlank(param.toString()))){
                            Result modelResult = new Result(HttpStatus.BAD_REQUEST.value());
                            modelResult.setMessage("参数不全");
                            return modelResult;
                        }
                    }
                }
            }
            logger.info("ControllerAspect call: class:[" + target.getClass().getName() + "] method:[" + method.getName() + "]  params:" + JSON.toJSONString(serializableParams) + "");
            Object returnValue = pjp.proceed();
            logger.info("ControllerAspect call: class:[" + target.getClass().getName() + "] method:[" + method.getName() + "]  params:" + JSON.toJSONString(serializableParams) + "" + " cost:" + (System.currentTimeMillis() - startAt) + "ms");
            return returnValue;
        } catch (Throwable throwable) {
            Result modelResult = new Result(HttpStatus.INTERNAL_SERVER_ERROR.value());
            if (throwable instanceof ServiceException) {
                ServiceException exception = (ServiceException) throwable;
                logger.info("ControllerAspect exception:[" + pjp.getTarget().getClass().getName() + "------->" + exception.getCode() + ":[" + exception.getMessage() + "]]" + " cost:" + (System.currentTimeMillis() - startAt) + "ms");
                modelResult.setCode(exception.getCode());
                modelResult.setMessage(exception.getMessage());
            } else {
                logger.error("ControllerAspect exception 服务器异常：" + throwable.getMessage(), throwable);
                modelResult.setMessage("服务器异常");
            }
            return modelResult;
        }
    }
}
