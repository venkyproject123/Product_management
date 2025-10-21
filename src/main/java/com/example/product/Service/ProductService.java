package com.example.product.Service;

import com.example.product.Dao.ProductRepository;
import com.example.product.Exceptions.ProductNotFoundException;
import com.example.product.Model.Product;
import com.example.product.Requests.ProductRequest;
import com.example.product.Responses.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponse addProduct(ProductRequest request) {
        logger.info("Adding new product: {}", request.getProductName());

        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setProductPrice(request.getProductPrice());
        product.setStockAvailability(request.isStockAvailability());

        Product saved = repository.save(product);
        logger.debug(" The product saved successfully......The saved product is: {}", saved);

        return mapToResponse(saved);
    }

    public List<ProductResponse> getAllProducts() {
        logger.info("Fetching all products");
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(int id) {
        logger.info("Fetching product with id {}", id);

        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));
        logger.info("Fetched product successfully with id: {}", id);

        return mapToResponse(product);
    }

    public ProductResponse updateProduct(int id, ProductRequest request) {
        logger.trace("Entered updateProduct method with id: {} and request: {}", id, request);

        Product existing = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));

        existing.setProductName(request.getProductName());
        existing.setProductPrice(request.getProductPrice());
        existing.setStockAvailability(request.isStockAvailability());

        Product updated = repository.save(existing);
        logger.debug("The product details updated successfully......The updated product is: {}", updated);

        return mapToResponse(updated);
    }

    public void deleteProduct(int id) {
        logger.trace("Entered deleteProduct method with id: {}", id);

        Product existing = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));

        repository.delete(existing);
        logger.info("Product deleted successfully with id: {}", id);
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setProductName(product.getProductName());
        response.setProductPrice(product.getProductPrice());
        response.setStockAvailability(product.isStockAvailability());
        return response;
    }
}
