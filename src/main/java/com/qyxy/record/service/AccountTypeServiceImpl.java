package com.qyxy.record.service;

import com.qyxy.record.common.ServiceException;
import com.qyxy.record.dao.IAccountTypeDao;
import com.qyxy.record.dataobject.AccountType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountTypeServiceImpl implements IAccountTypeService {

    @Autowired
    private IAccountTypeDao accountTypeDao;

    @Override
    public List<AccountType> getAccountTypeList() {
        return accountTypeDao.getAccountTypeList();
    }

    @Override
    public AccountType saveAccountType(String name, String code) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(code)) {
            throw new ServiceException("参数不全");
        }

        AccountType oldAccountType = accountTypeDao.getByCode(code, null);
        if (Objects.nonNull(oldAccountType)){
            throw new ServiceException("code已经存在");
        }

        AccountType accountType = new AccountType();
        accountType.setName(name);
        accountType.setCode(code);
        accountTypeDao.insert(accountType);

        return accountType;
    }

    @Override
    public AccountType updateAccountType(Long id, String name, String code) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(code) || Objects.isNull(id)) {
            throw new ServiceException("参数不全");
        }

        AccountType oldAccountType = accountTypeDao.selectById(id);
        if (Objects.isNull(oldAccountType)){
            throw new ServiceException("数据不存在");
        }

        AccountType whichAccountType = accountTypeDao.getByCode(code, id);
        if (Objects.nonNull(whichAccountType)){
            throw new ServiceException("code已经存在");
        }

        oldAccountType.setName(name);
        oldAccountType.setCode(code);
        accountTypeDao.updateById(oldAccountType);

        return oldAccountType;
    }
}