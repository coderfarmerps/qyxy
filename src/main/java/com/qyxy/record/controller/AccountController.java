package com.qyxy.record.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.qyxy.record.common.Result;
import com.qyxy.record.modal.AccountDO;
import com.qyxy.record.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/account")
public class AccountController{

    @Autowired
    protected IAccountService accountService;

    @RequestMapping(value = "saveAccount", method = RequestMethod.POST)
    public Result saveAccount(String account, String password, Long accountTypeId, String remark){
        return Result.successResult(accountService.saveAccount(account, password, accountTypeId, remark));
    }

    //selectById
    @RequestMapping(value = "selectById", method = RequestMethod.GET)
    public Result<Map<String, Object>> selectById(Long id){
        return Result.successResult(accountService.selectById(id));
    }

    @RequestMapping(value = "updateAccount", method = RequestMethod.POST)
    public Result updateAccount(Long id, String account, String password, Long accountTypeId, String remark){
        return Result.successResult(accountService.updateAccount(id, account, password, accountTypeId, remark));
    }

    @RequestMapping(value = "getAccountList", method = RequestMethod.GET)
    public Result<Map<String, Object>> getAccountList(Long accountTypeId, String account, int current, int pageSize){
        PageInfo<AccountDO> accountPageInfo = accountService.getAccountList(accountTypeId, account, current, pageSize);

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("list", accountPageInfo.getList());
        resultMap.put("total", accountPageInfo.getTotal());

        return Result.successResult(resultMap);
    }
}