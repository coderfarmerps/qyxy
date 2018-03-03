package com.qyxy.record.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qyxy.record.dao.IRemindDateDao;
import com.qyxy.record.dataobject.RemindDate;
import com.qyxy.record.mail.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luxiaoyong on 2018/3/3.
 */
@Service
public class RemindDateServiceImpl implements IRemindDateService {

    private Logger logger = LoggerFactory.getLogger(RemindDateServiceImpl.class);

    @Autowired
    private IRemindDateDao remindDateDao;

    @Autowired
    private MailSenderService mailSender;

    @Override
    public RemindDate saveRemindDate(Long importantDate, String remark, String title) {

        RemindDate remindDate = new RemindDate();
        remindDate.setImportantDate(importantDate);
        remindDate.setRemark(remark);
        remindDate.setTitle(title);

        calculateNextRemindTime(remindDate);
        remindDateDao.insert(remindDate);
        return remindDate;
    }

    @Override
    public RemindDate updateRemindDate(Long id, Long importantDate, String remark, String title) {

        RemindDate oldRemindDate = remindDateDao.selectById(id);
        oldRemindDate.setImportantDate(importantDate);
        oldRemindDate.setRemark(remark);
        oldRemindDate.setTitle(title);

        oldRemindDate.setIsFinished(0);
        calculateNextRemindTime(oldRemindDate);
        remindDateDao.update(oldRemindDate);

        return oldRemindDate;
    }

    @Override
    public RemindDate delRemindDate(Long id) {
        RemindDate oldRemindDate = remindDateDao.selectById(id);
        remindDateDao.deleteById(id);
        return oldRemindDate;
    }

    @Override
    public RemindDate finishRemindDate(Long id) {
        RemindDate oldRemindDate = remindDateDao.selectById(id);
        remindDateDao.finishRemindDate(id);
        return oldRemindDate;
    }

    @Override
    public PageInfo<RemindDate> getRemindDateList(Long beginTime, Long endTime, int current, int pageSize, Integer isFinished) {
        return PageHelper.startPage(current, pageSize).doSelectPageInfo(() ->
                remindDateDao.getRemindDateList(beginTime, endTime, isFinished));
    }

    @Override
    public void remindReadyDatas() {
        List<RemindDate> readyDatas =  remindDateDao.getReadyDataList();
        logger.info("调度任务开始执行，本次待提醒数据总数：{}", readyDatas.size());
        readyDatas.forEach(remindDate -> {
            // 1. 发送邮件
            mailSender.sendRemindData(remindDate);
            // 2. 更新数据库
            remindDate.setRemindTimes(remindDate.getRemindTimes() + 1);
            calculateNextRemindTime(remindDate);
            remindDateDao.update(remindDate);
        });
        logger.info("调度任务结束执行，本次总共提醒数据总数：{}", readyDatas.size());
    }

    @Override
    public RemindDate getRemindDateById(Long id) {
        return remindDateDao.selectById(id);
    }

    private void calculateNextRemindTime(RemindDate remindDate){

        Long importantDate = remindDate.getNextRemindTime() == null ? remindDate.getImportantDate() : remindDate.getNextRemindTime();
        Long now = System.currentTimeMillis();

        Long surplus = importantDate - now;

        // 剩余时间超过两天
        if (surplus > 2 * 24 * 60 * 60 * 1000){
            remindDate.setNextRemindTime(importantDate - 2 * 24 * 60 * 60 * 1000);
            return;
        }

        // 剩余时间超过1天
        if (surplus > 24 * 60 * 60 * 1000){
            remindDate.setNextRemindTime(importantDate - 24 * 60 * 60 * 1000);
            return;
        }

        // 剩余时间超过12小时
        if (surplus > 12 * 60 * 60 * 1000){
            remindDate.setNextRemindTime(importantDate - 12 * 60 * 60 * 1000);
            return;
        }

        // 剩余时间超过6小时
        if (surplus > 6 * 60 * 60 * 1000){
            remindDate.setNextRemindTime(importantDate - 6 * 60 * 60 * 1000);
            return;
        }

        // 剩余时间超过3小时
        if (surplus > 3 * 60 * 60 * 1000){
            remindDate.setNextRemindTime(importantDate - 3 * 60 * 60 * 1000);
            return;
        }

        // 剩余时间超过1小时
        if (surplus > 60 * 60 * 1000){
            remindDate.setNextRemindTime(importantDate - 60 * 60 * 1000);
            return;
        }

        // 剩余时间超过半小时
        if (surplus > 30 * 60 * 1000){
            remindDate.setNextRemindTime(importantDate - 30 * 60 * 1000);
            return;
        }

        // 剩余时间超过10分钟
        if (surplus > 10 * 60 * 1000){
            remindDate.setNextRemindTime(importantDate - 10 * 60 * 1000);
            return;
        }

        // 剩余时间小于10分钟，结束
        remindDate.setIsFinished(1);
    }
}
