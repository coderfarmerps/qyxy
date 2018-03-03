package com.qyxy.record.common;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author luxiaoyong
 */
@Data
public class Result<T> implements Serializable {

	private String message;
	
	private int code;
	
	private T data;
	
	private boolean success;
	
	public Result() {
		
	}

	public Result(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ResultObject [message=" + message + ", code=" + code + ", data=" + data + ", success=" + success + "]";
	}

	public static Result successResult() {
		Result result = new Result();
		result.setData(Maps.newHashMap());
		result.setCode(HttpStatus.OK.value());
		result.setMessage("ok");
		result.setSuccess(true);
		return result;
	}

	public static <E> Result<Map<String, Object>> successResult(E data) {
		Result<Map<String, Object>> result = Result.successResult();
		Map<String, Object> map = Maps.newHashMap();
		map.put("data", data);
		result.setData(map);
		return result;
	}

	public static <E> Result<Map<String, Object>> successResult(List<E> data) {
		Result<Map<String, Object>> result = Result.successResult();
		Map<String, Object> map = Maps.newHashMap();
		map.put("list", data);
		result.setData(map);
		return result;
	}

	public static Result<Map<String, Object>> successResult(Map<String, Object> data) {
		Result<Map<String, Object>> result = Result.successResult();
		result.setData(data);
		return result;
	}

	public static Result badRequestResult(String message) {
		Result result = new Result();
		result.setCode(HttpStatus.BAD_REQUEST.value());
		result.setMessage(message);
		result.setSuccess(false);
		return result;
	}
}
