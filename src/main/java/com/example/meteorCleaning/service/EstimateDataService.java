package com.example.meteorCleaning.service;

import com.example.meteorCleaning.model.EstimateOrder;
import com.example.meteorCleaning.repository.DataJpaOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EstimateDataService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    DataJpaOrderRepository repository;

    public List<EstimateOrder> getAll() {
        return repository.getAll();
    }


    public void sendEmail(String[] to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("info@meteorcleaning.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        mailSender.send(message);
    }
}
