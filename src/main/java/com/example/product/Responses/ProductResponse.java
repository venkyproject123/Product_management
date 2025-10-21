package com.example.product.Responses;

import lombok.Data;

@Data
public class ProductResponse {
    private int productId;
    private String productName;
    private double productPrice;
    private boolean stockAvailability;

}
