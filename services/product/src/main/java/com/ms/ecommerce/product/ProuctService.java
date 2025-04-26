package com.ms.ecommerce.product;

import com.ms.ecommerce.exception.ProductPurchaseExecption;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProuctService {
    private final  ProductRepository repository;
    private final ProductMapper mapper;

    public ProuctService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Integer createProduct(@Valid ProductRequest request) {
        var product = mapper.toProduct( request);
        return repository.save(product).getId();
    }


    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        //verify that all demanded product exist
        var storedProducts = repository.findAllByIdInOrderById(productIds);
        //if sizes are different, so there is some missing product not all demanded products are available
        if(productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseExecption("One or more product does not exist");
        }
        var storedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for(int i = 0; i < storedRequest.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);

            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseExecption("insufficient stock quantity for product with ID:: "+productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }

        return purchasedProducts;


    }

    public ProductResponse findById(Integer proudctId) {
        return repository.findById(proudctId).map(mapper::toProductResponse)
                .orElseThrow(()-> new EntityNotFoundException(("Product not found with ID:: "+proudctId)));

    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
