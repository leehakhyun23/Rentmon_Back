package com.himedia.rentmon_back.util;

import com.himedia.rentmon_back.dto.UserDTO;
import com.himedia.rentmon_back.entity.Member;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
@RequiredArgsConstructor
public class MailSend {
    private final JavaMailSender mailSender;
    private int authNumber;

    public String setEmail(String email){
        makeRandomNumber();
        String setFrom =  "rmfoal1996@gmail.com";
        String toMail = email;
        String title= "회원가입 인증 이메일 입니다.";
        String content="로그인사이트를 방문해주셔서 감사합니다. " +
                "<br><br>" +
                "인증 번호는" + authNumber+"입니다."+"<br>"+
                "인증번호를 입력해주세요.";
        mailSender(setFrom,toMail,title,content);

        return Integer.toString(authNumber);
    }

    private void mailSender(String setFrom, String toMail, String title, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true , "UTF-8");
            mimeMessageHelper.setFrom(setFrom);
            mimeMessageHelper.setTo(toMail);
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(content, true);
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //임의의 6자리 양수를 반환
    public void makeRandomNumber(){
        Random r = new Random();
        String randomNumber= "";
        for(int i =0; i<6; i++){
            randomNumber += Integer.toString(r.nextInt(10));
        }
        authNumber = Integer.parseInt(randomNumber);
    }
}
