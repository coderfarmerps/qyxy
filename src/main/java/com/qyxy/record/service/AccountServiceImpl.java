package com.qyxy.record.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qyxy.record.common.ServiceException;
import com.qyxy.record.dao.IAccountDao;
import com.qyxy.record.dao.IAccountTypeDao;
import com.qyxy.record.dataobject.Account;
import com.qyxy.record.dataobject.AccountType;
import com.qyxy.record.modal.AccountDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountDao accountDao;

    @Autowired
    private IAccountTypeDao accountTypeDao;

    @Override
    public Account saveAccount(String account, String password, Long accountType, String remark) {
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || Objects.isNull(accountType)) {
            throw new ServiceException("参数不全");
        }

        AccountType oldAccountType = accountTypeDao.selectById(accountType);
        if (Objects.isNull(oldAccountType)) {
            throw new ServiceException("账号类型不存在");
        }

        Account oldAccount = accountDao.getAcccount(account, accountType, null);
        if (Objects.nonNull(oldAccount)) {
            throw new ServiceException("账号已经存在");
        }

        Account newAccount = new Account();
        newAccount.setAccount(account);
        newAccount.setPassword(password);
        newAccount.setAccountTypeId(accountType);
        newAccount.setRemark(remark);
        accountDao.insert(newAccount);

        return newAccount;
    }

    @Override
    public Account updateAccount(Long id, String account, String password, Long accountType, String remark) {
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || Objects.isNull(accountType) || Objects.isNull(id)) {
            throw new ServiceException("参数不全");
        }

        Account oldAccount = accountDao.selectById(id);
        if (Objects.isNull(oldAccount)) {
            throw new ServiceException("账号不存在");
        }

        Account whichAccount = accountDao.getAcccount(account, accountType, id);
        if (Objects.nonNull(whichAccount)) {
            throw new ServiceException("账号已经存在");
        }

        oldAccount.setAccount(account);
        oldAccount.setPassword(password);
        oldAccount.setAccountTypeId(accountType);
        oldAccount.setRemark(remark);
        accountDao.updateById(oldAccount);

        return oldAccount;
    }

    @Override
    public PageInfo<AccountDO> getAccountList(Long accountType, String account, int current, int pageSize) {
        return PageHelper.startPage(current, pageSize).doSelectPageInfo(() ->
                accountDao.getAccountList(account, accountType));
    }

    @Override
    public Account selectById(Long id) {
        if (Objects.isNull(id)) {
            throw new ServiceException("参数不全");
        }
        return accountDao.selectById(id);
    }
}