package com.qyxy.record.dao;

import com.qyxy.record.dataobject.Account;
import com.qyxy.record.modal.AccountDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAccountDao {

    public Long insert(Account account);

    public Account selectById(Long id);

    Account getAcccount(@Param("account") String account, @Param("accountTypeId") Long accountType, @Param("excludeId") Long excludeId);

    void updateById(Account oldAccount);

    List<AccountDO> getAccountList(@Param("account") String account, @Param("accountTypeId") Long accountType);
}