package com.ms.ecommerce.payment;

import com.ms.ecommerce.notification.NotificationProducer;
import com.ms.ecommerce.notification.PaymentNotificationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(@Valid PaymentRequest request) {
        var payment = repository.save(mapper.toPayment(request));
        notificationProducer.send(
                new PaymentNotificationRequest(
                    request.orderReference(),
                    request.amount(),
                    request.paymentMethod(),
                    request.customer().firstName(),
                    request.customer().lastName(),
                    request.customer().email()
                )
        );
        return payment.getId();
    }
}
