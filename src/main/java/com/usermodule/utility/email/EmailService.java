package com.usermodule.utility.email;

import com.usermodule.utility.exception.CustomException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger LOGGER= LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String email) {

        try{
            MimeMessage mimeMessage= mailSender.createMimeMessage();
            MimeMessageHelper helper =
                new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject("Confirm Your Mail");
            helper.setFrom("is463856@gmail.com");
            mailSender.send(mimeMessage);
        }
        catch(MessagingException e){
                LOGGER.error("Fail to Send Email",e);
                throw new CustomException("Failed to send Email", HttpStatus.BAD_GATEWAY);
        }
    }
}
