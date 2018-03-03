package com.qyxy.record.dataobject;


import lombok.Data;

@Data
public class RemindDate {

    public RemindDate() {
    }

    
    private Long id;

    private Long importantDate;

    private String remark;
    
    private int remindTimes;

    private int isFinished;
    private int isDel;

    private Long nextRemindTime;

    private String title;
}