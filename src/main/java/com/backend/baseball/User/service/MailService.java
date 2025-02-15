package com.backend.baseball.User.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "umcbaseballdiary@gmail.com"; // 반드시 JavaMailSender의 username과 일치

    // 랜덤 6자리 숫자 생성
    public String createNumber() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 6; i++) { // 6자리 숫자 생성
            key.append(random.nextInt(10)); // 0~9 숫자만 생성
        }
        return key.toString();
    }

    public MimeMessage createMail(String mail, String number) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(MimeMessage.RecipientType.TO, mail);
        message.setSubject("이메일 인증 코드");

        String body = "<h3>요청하신 인증 번호입니다.</h3>"
                + "<h1 style='color:blue;'>" + number + "</h1>"
                + "<p>인증번호를 입력해 주세요.</p>";

        message.setContent(body, "text/html; charset=UTF-8"); // Content-Type 설정

        return message;
    }

    public String sendSimpleMessage(String sendEmail) throws MessagingException {
        String number = createNumber(); // 6자리 숫자 생성

        MimeMessage message = createMail(sendEmail, number);
        try {
            javaMailSender.send(message);
            log.info("메일 전송 성공: {}", sendEmail);
        } catch (MailException e) {
            log.error("메일 전송 실패: {}", e.getMessage());
            throw new IllegalArgumentException("메일 발송 중 오류가 발생했습니다.");
        }

        return number; // 생성된 인증번호 반환
    }

}
