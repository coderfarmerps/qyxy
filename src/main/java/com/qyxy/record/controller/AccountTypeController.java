package com.qyxy.record.controller;

import com.qyxy.record.common.Result;
import com.qyxy.record.service.IAccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/accountType")
public class AccountTypeController{

    @Autowired
    protected IAccountTypeService accountTypeService;

    @RequestMapping(value = "saveAccountType", method = RequestMethod.POST)
    public Result saveAccountType(String name, String code){
        return Result.successResult(accountTypeService.saveAccountType(name, code));
    }

    @RequestMapping(value = "updateAccountType", method = RequestMethod.POST)
    public Result updateAccountType(Long id, String name, String code){
        return Result.successResult(accountTypeService.updateAccountType(id, name, code));
    }

    @RequestMapping(value = "getAccountTypeList", method = RequestMethod.GET)
    public Result<Map<String, Object>> getAccountTypeList(){
        return Result.successResult(accountTypeService.getAccountTypeList());
    }

}