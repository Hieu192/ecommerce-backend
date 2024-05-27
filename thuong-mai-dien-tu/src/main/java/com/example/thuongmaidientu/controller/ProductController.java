package com.example.thuongmaidientu.controller;

import com.example.thuongmaidientu.exception.ProductException;
import com.example.thuongmaidientu.model.Product;
import com.example.thuongmaidientu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category,
                                                                      @RequestParam List<String>color, @RequestParam List<String> size, @RequestParam Integer minPrice,
                                                                      @RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort,
                                                                      @RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Page<Product> res = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);

        System.out.println("complete product");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/allpage")
    public ResponseEntity<Page<Product>> findAllProductHandler( @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Page<Product> res = productService.getAllProduct2(pageNumber, pageSize);

        System.out.println("complete product");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/category")
    public ResponseEntity<List<Product>> findProductByCategory(@RequestParam String category) {
        List<Product> res = productService.findProductByCategory(category);

        System.out.println("complete product");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/all")
    public ResponseEntity<List<Product>> findAllProduct() {
        List<Product> res = productService.findAllProduct();

        System.out.println("get all product");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException{
        Product product = productService.findProductById(productId);
        return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
    }

//    @GetMapping("/product/search")
//    public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q) throws ProductException{
//        List<Product> products = productService.searchProduct(q);
//        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
//    }
}
