package com.ms.ecommerce.customer;

import lombok.*;
        import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Validated
public class Address {

    @Id
    private String street;
    private String houseNumber;
    private String zipCode;

}
