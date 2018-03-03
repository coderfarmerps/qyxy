package com.qyxy.record.service;

import com.github.pagehelper.PageInfo;
import com.qyxy.record.dataobject.Account;
import com.qyxy.record.modal.AccountDO;

public interface IAccountService {
    Account saveAccount(String account, String password, Long accountType, String remark);

    Account updateAccount(Long id, String account, String password, Long accountType, String remark);

    PageInfo<AccountDO> getAccountList(Long accountType, String account, int current, int pageSize);

    Account selectById(Long id);
}