package com.qyxy.record.dataobject;


public class AccountType{
    
    public AccountType() {
    }

    
    /**
    * 
    */
    private Long id;
    
    /**
    * 账号类型名称 qq 微信 知乎 12306 微博
    */
    private String name;
    
    /**
    * 账号类型code  qq weixin zhihu 12306 weibo
    */
    private String code;
    
    
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
    
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }
    
}