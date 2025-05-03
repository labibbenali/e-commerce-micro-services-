package com.ms.ecommerce.kafka;

import com.ms.ecommerce.email.EmailService;
import com.ms.ecommerce.kafka.order.OrderConfirmation;
import com.ms.ecommerce.kafka.payment.PaymentConfirmation;
import com.ms.ecommerce.notification.Notification;
import com.ms.ecommerce.notification.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.ms.ecommerce.notification.NotificationType.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailservice;


    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consumming the message from payment Topic:: <{}>", paymentConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );
       var customerName = paymentConfirmation.customerFirstname()+" "+paymentConfirmation.customerLastname();
       emailservice.sendPaymentSuccessEmail(
               paymentConfirmation.customerEmail(),
               customerName,
               paymentConfirmation.amount(),
               paymentConfirmation.orderReference()
       );
    }
    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consumming the message from order-topic Topic:: <{}>", orderConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );
        var customerName = orderConfirmation.customer().firstname()+" "+orderConfirmation.customer().lastname();
        emailservice.senOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }



}
