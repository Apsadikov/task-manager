package ru.itis.taskmanager.service;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.itis.taskmanager.dto.MailDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service(value = "confirmationEmailService")
@EnableAsync
public class ConfirmationEmailServiceImpl implements EmailService {
    private Configuration configuration;
    private JavaMailSender mailSender;

    @Autowired
    public ConfirmationEmailServiceImpl(JavaMailSender mailSender, Configuration configuration) {
        this.mailSender = mailSender;
        this.configuration = configuration;
    }

    @Override
    @Async
    public void send(MailDto mailDto) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mailDto.getSubject());
            mimeMessageHelper.setFrom(mailDto.getFrom());
            mimeMessageHelper.setTo(mailDto.getTo());
            mimeMessageHelper.setText(getContentFromTemplate(mailDto), true);

            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getContentFromTemplate(MailDto mailDto) {
        StringBuilder content = new StringBuilder();
        try {
            content.append(FreeMarkerTemplateUtils
                    .processTemplateIntoString(configuration.getTemplate(mailDto.getTemplate()), mailDto.getMap()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}