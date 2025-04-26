package com.ms.ecommerce.product;


import java.math.BigDecimal;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        Double availableQuantity,
        BigDecimal price,
        Integer category_id,
        String categoryName,
        String categoryDescription

) {
}
