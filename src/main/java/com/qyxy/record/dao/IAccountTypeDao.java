package com.qyxy.record.dao;

import com.qyxy.record.dataobject.AccountType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAccountTypeDao {

    public Long insert(AccountType accountType);

    public AccountType selectById(Long id);

    List<AccountType> getAccountTypeList();

    AccountType getByCode(@Param("code") String code, @Param("excludeId") Long id);

    void updateById(AccountType oldAccountType);
}