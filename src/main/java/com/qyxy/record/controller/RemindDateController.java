package com.qyxy.record.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.qyxy.record.aop.ParamCanNull;
import com.qyxy.record.common.Result;
import com.qyxy.record.dataobject.RemindDate;
import com.qyxy.record.service.IRemindDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by luxiaoyong on 2018/3/3.
 */

@RestController
@RequestMapping("/admin/remindDate")
public class RemindDateController {

    @Autowired
    protected IRemindDateService remindDateService;

    @RequestMapping(value = "saveRemindDate", method = RequestMethod.POST)
    public Result saveRemindDate(Long importantDate, @ParamCanNull String remark, String title){
        return Result.successResult(remindDateService.saveRemindDate(importantDate, remark, title));
    }

    @RequestMapping(value = "updateRemindDate", method = RequestMethod.POST)
    public Result updateRemindDate(Long id, Long importantDate, @ParamCanNull String remark, String title){
        return Result.successResult(remindDateService.updateRemindDate(id, importantDate, remark, title));
    }

    @RequestMapping(value = "delRemindDate", method = RequestMethod.POST)
    public Result delRemindDate(Long id){
        return Result.successResult(remindDateService.delRemindDate(id));
    }

    @RequestMapping(value = "getRemindDateById", method = RequestMethod.GET)
    public Result getRemindDateById(Long id){
        return Result.successResult(remindDateService.getRemindDateById(id));
    }

    @RequestMapping(value = "finishRemindDate", method = RequestMethod.POST)
    public Result finishRemindDate(Long id){
        return Result.successResult(remindDateService.finishRemindDate(id));
    }

    @RequestMapping(value = "getRemindDateList", method = RequestMethod.GET)
    @ParamCanNull
    public Result<Map<String, Object>> getRemindDateList(Long beginTime, Long endTime, int current, int pageSize, Integer isFinished){
        PageInfo<RemindDate> remindDatePageInfo = remindDateService.getRemindDateList(beginTime, endTime, current, pageSize, isFinished);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("list", remindDatePageInfo.getList());
        resultMap.put("total", remindDatePageInfo.getTotal());
        return Result.successResult(resultMap);
    }
}
