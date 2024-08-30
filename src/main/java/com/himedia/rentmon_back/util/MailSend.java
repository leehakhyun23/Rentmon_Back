package com.himedia.rentmon_back.util;

import com.himedia.rentmon_back.dto.UserDTO;
import com.himedia.rentmon_back.entity.Member;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Random;

@Repository
@RequiredArgsConstructor
public class MailSend {
    private final JavaMailSender mailSender;
    private int authNumber;

    public String sendEmail(String email ,String resetPasswordUrl , String content){
        makeRandomNumber();
        String setFrom =  "rmfoal1996@gmail.com";
        String toMail = email;
        String title = "비밀번호 재설정 요청 이메일입니다.";

        mailSender(setFrom,toMail,title,content);

        return Integer.toString(authNumber);
    }



    public String setEmail(String email){
        makeRandomNumber();
        String setFrom =  "rmfoal1996@gmail.com";
        String toMail = email;
        String title= "회원가입 인증 이메일 입니다.";
        String content = "<div style='line-height: 1.6; padding: 20px; max-width: 600px; margin: auto;'>" +
                "<p style='font-size: 16px; color: #555;'>로그인 사이트를 방문해 주셔서 감사합니다.</p>" +
                "<p style='font-size: 16px; color: #555;'>인증 번호는 <strong style='font-size: 18px; color: #007bff;'>" + authNumber + "</strong>입니다.</p>" +
                "<p style='font-size: 16px; color: #555;'>인증번호를 입력해주세요.</p>" +
                "</div>";
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
