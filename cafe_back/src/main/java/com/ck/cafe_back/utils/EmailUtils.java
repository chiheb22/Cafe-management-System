package com.ck.cafe_back.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendSimpleMessage(String to, String subject, String text, List<String> list){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("chihebkhmiri122@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        if (list!=null && !list.isEmpty()){
            mailMessage.setCc(getCcArray(list));
            javaMailSender.send(mailMessage);
        }



    }
    private String[] getCcArray(List<String> ccList){
        String[] cc = new String[ccList.size()];
        for (int i = 0; i < ccList.size(); i++) {
            cc[i] = ccList.get(i);

        }
        return cc;
    }

}
