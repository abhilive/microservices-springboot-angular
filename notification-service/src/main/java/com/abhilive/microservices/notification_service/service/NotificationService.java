package com.abhilive.microservices.notification_service.service;

import com.abhilive.microservices.order_service.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void lister(OrderPlacedEvent orderPlacedEvent) {
        log.info("Got Message from order-placed topic {}", orderPlacedEvent);
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("springshop@email.com");
            mimeMessageHelper.setTo(orderPlacedEvent.getEmail().toString());
            mimeMessageHelper.setSubject(String.format("Your order with order number %s is placed successfully", orderPlacedEvent.getOrderNumber()));
            mimeMessageHelper.setText(String.format("""
                    Hi %s,%s
                    
                    Your order with order number %s is placed successfully.
                    
                    Best Regards,
                    Spring Shop
                    """,
                    orderPlacedEvent.getFirstName().toString(),
                    orderPlacedEvent.getLastName().toString(),
                    orderPlacedEvent.getOrderNumber()));
        };
        try {
            javaMailSender.send(mimeMessagePreparator);
            log.info("Order Notification emaill sent.");
        } catch(Exception ex) {
            log.error("Exception occured while sending mail.", ex);
            throw new RuntimeException("Exception occured when sending email to springshop@gmail.com", ex);
        }
    }
}
