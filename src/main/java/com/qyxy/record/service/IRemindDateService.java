package com.qyxy.record.service;

import com.github.pagehelper.PageInfo;
import com.qyxy.record.dataobject.RemindDate;

public interface IRemindDateService {
    RemindDate saveRemindDate(Long importantDate, String remark, String title);

    RemindDate updateRemindDate(Long id, Long importantDate, String remark, String title);

    RemindDate delRemindDate(Long id);

    RemindDate finishRemindDate(Long id);

    PageInfo<RemindDate> getRemindDateList(Long beginTime, Long endTime, int current, int pageSize, Integer isFinished);

    void remindReadyDatas();

    RemindDate getRemindDateById(Long id);
}