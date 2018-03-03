package com.qyxy.record.dataobject;


import lombok.Data;

@Data
public class Account{
    
    public Account() {
    }

    
    /**
    * 
    */
    private Long id;
    
    /**
    * 账号
    */
    private String account;
    
    /**
    * 密码，未加密
    */
    private String password;
    
    /**
    * 账号类型 
    */
    private Long accountTypeId;

    /**
     * 备注
     */
    private String remark;
}