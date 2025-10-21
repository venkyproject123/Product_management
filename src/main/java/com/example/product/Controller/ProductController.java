package com.example.product.Controller;

import com.example.product.Requests.ProductRequest;
import com.example.product.Responses.ProductResponse;
import com.example.product.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request) {
        logger.info("Requesting to add product: {}", request.getProductName());
        ProductResponse response = service.addProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);// 201 Created
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        logger.info("Requesting to fetch all products");
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {
        logger.info("Requesting to fetch product with id {}", id);
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable int id,
                                                         @RequestBody ProductRequest request) {
        logger.info("Requesting to update product with id {}", id);
        return ResponseEntity.ok(service.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        logger.info("Requesting to delete product with id {}", id);
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


}
