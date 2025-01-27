package com.backend.baseball.Login.User.emailauth;

import com.backend.baseball.Login.entity.User;
import com.backend.baseball.Login.User.dto.UserEmailDto;
import com.backend.baseball.Login.User.exception.CertificationNumberMismatchException;
import com.backend.baseball.Login.User.exception.EmailNotResistedException;
import com.backend.baseball.Login.User.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailCertification mailCertification;
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String senderEmail;

    private String createRandomNumber(){
        Random rand = new Random();
        String randomNum="";
        for(int i = 0;i<6;i++){
            String random=Integer.toString(rand.nextInt(10));
            randomNum+=random;
        }
        return randomNum;
    }

    @Override
    public MailCertificationResponse sendMailPasswordReset(String email) {
        if(userRepository.findByEmail(email).isEmpty()){
            throw new EmailNotResistedException();
        }
        return this.sendMailJoin(email);
    }

    @Override
    public MailCertificationResponse sendMailJoin(String email) {
        MimeMessage message=javaMailSender.createMimeMessage();
        String randomNum=createRandomNumber();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + randomNum + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
        mailCertification.createMailCertification(email,randomNum);
        return new MailCertificationResponse(email);
    }

    @Override
    public Boolean verifyEmail(MailCertificationDto requestDto) {
        if(isVerify(requestDto)){
            throw new CertificationNumberMismatchException();
        }
        mailCertification.deleteMailCertification(requestDto.getEmail());

        return true;
    }
    private Boolean isVerify(MailCertificationDto requestDto){
        return !(mailCertification.hasKey(requestDto.getEmail())
        &&mailCertification.getMailCertification(requestDto.getEmail())
                .equals(requestDto.getAuthNumber()));
    }

    @Override
    public Boolean confirmDupEmail(UserEmailDto userEmailDto) {
        Optional<User> user = userRepository.findByEmail(userEmailDto.getEmail());
        if(user.isEmpty()){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }
}
