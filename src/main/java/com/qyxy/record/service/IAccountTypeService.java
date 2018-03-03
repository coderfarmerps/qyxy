package com.qyxy.record.service;

import com.qyxy.record.dataobject.AccountType;

import java.util.List;

public interface IAccountTypeService {
    List<AccountType> getAccountTypeList();

    AccountType saveAccountType(String name, String code);

    AccountType updateAccountType(Long id, String name, String code);
}