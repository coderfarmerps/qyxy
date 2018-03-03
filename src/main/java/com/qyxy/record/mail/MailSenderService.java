package com.qyxy.record.mail;

import com.qyxy.record.dataobject.RemindDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luxiaoyong on 2018/3/3.
 */
@Service
public class MailSenderService {

    private static final Logger log = LoggerFactory.getLogger(MailSenderService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.from}")
    private String mailFrom;
    @Value("${mail.to}")
    private String mailTo;
    @Value("${mail.titlePre}")
    private String titlePre;

    public void sendRemindData(RemindDate remindDate){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSentDate(new Date());
            helper.setFrom(mailFrom);
            helper.setTo(mailTo);
            if (StringUtils.isBlank(titlePre)){
                helper.setSubject(remindDate.getTitle());
            }else {
                helper.setSubject(titlePre + ": " + remindDate.getTitle());
            }

            StringBuffer sb = new StringBuffer();
            sb.append("<h1>").append(remindDate.getTitle()).append("</h1>")
                    .append("<h3 style='color:#F00'>事件时间：").append(simpleDateFormat.format(remindDate.getImportantDate())).append("</h3>");
            if (StringUtils.isNotBlank(remindDate.getRemark())){
                sb.append("<h3 style='color:#F00'>事件内容：").append(remindDate.getRemark()).append("</h3>");
            }
            helper.setText(sb.toString(), true);
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("邮件发送失败, remindDate:{}", remindDate, e);
        }
    }
}
