package com.example.product.Requests;

import lombok.Data;

@Data
public class ProductRequest {
    private String productName;
    private double productPrice;
    private boolean stockAvailability;

}
