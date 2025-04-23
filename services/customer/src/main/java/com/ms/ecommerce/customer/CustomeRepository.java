package com.ms.ecommerce.customer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomeRepository extends MongoRepository<Customer, String> {
}
