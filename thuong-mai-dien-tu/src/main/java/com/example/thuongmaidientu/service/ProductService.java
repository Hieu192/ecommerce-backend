package com.example.thuongmaidientu.service;

import com.example.thuongmaidientu.exception.ProductException;
import com.example.thuongmaidientu.model.Product;
import com.example.thuongmaidientu.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest req);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product req) throws ProductException;

    public Product findProductById(Long productId) throws ProductException;

    public List<Product> findProductByCategory(String category);

    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

    public List<Product> findAllProduct();

    Page<Product> getAllProduct2(Integer pageNumber, Integer pageSize);
}
