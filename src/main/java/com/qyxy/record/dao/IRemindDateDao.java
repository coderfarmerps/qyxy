package com.qyxy.record.dao;

import com.qyxy.record.dataobject.RemindDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IRemindDateDao {

    public Long insert(RemindDate remindDate);

    RemindDate selectById(Long id);

    void update(RemindDate oldRemindDate);

    void deleteById(Long id);

    List<RemindDate> getRemindDateList(@Param("beginTime") Long beginTime, @Param("endTime") Long endTime, @Param("isFinished") Integer isFinished);

    List<RemindDate> getReadyDataList();


    void finishRemindDate(Long id);
}