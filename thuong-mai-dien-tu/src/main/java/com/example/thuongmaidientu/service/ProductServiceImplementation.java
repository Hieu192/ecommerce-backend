package com.example.thuongmaidientu.service;

import com.example.thuongmaidientu.exception.ProductException;
import com.example.thuongmaidientu.model.Category;
import com.example.thuongmaidientu.model.Product;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.repository.CategoryRepository;
import com.example.thuongmaidientu.repository.ProductRepository;
import com.example.thuongmaidientu.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductServiceImplementation implements ProductService{
    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;
    public ProductServiceImplementation(ProductRepository productRepository,
                                        UserService userService,
                                        CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.productRepository = productRepository;
    }
    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topLevel = categoryRepository.findByName(req.getTopLavelCategory());
        if (topLevel== null) {
            Category topLavelCategory = new Category();
            topLavelCategory.setName(req.getTopLavelCategory());
            topLavelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLavelCategory);
        }
        Category secondLevel = categoryRepository.findByNameAndParant(req.getSecondLavelCategory(), topLevel.getName());
        if (secondLevel== null) {
            Category secondLavelCategory = new Category();
            secondLavelCategory.setName(req.getSecondLavelCategory());
            secondLavelCategory.setParentCategory(topLevel);
            secondLavelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLavelCategory);
        }
        Category thirdLevel = categoryRepository.findByNameAndParant(req.getThirdLavelCategory(), secondLevel.getName());
        if (thirdLevel== null) {
            Category thirdLavelCategory = new Category();
            thirdLavelCategory.setName(req.getThirdLavelCategory());
            thirdLavelCategory.setParentCategory(secondLevel);
            thirdLavelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLavelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setBrand(req.getBrand());
        product.setCategory(thirdLevel);
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountedPersent(req.getDiscountPersent());
        product.setImageUrl(req.getImageUrl());
        product.setLocalDateTime(LocalDateTime.now());
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());
        product.setSizes(req.getSize());

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product xóa thành công";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);

        if(req.getQuantity()!= 0) {
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }
    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> opt = productRepository.findById(productId);
        if(opt.isPresent()) {
            return opt.get();
        }
        throw new ProductException("Product không tim thấy bởi id " + productId);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> products = productRepository.fillterProducts(category, minPrice, maxPrice, minDiscount, sort);

        if(!colors.isEmpty()){
            products= products.stream().filter(p -> colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }

        if (stock!= null) {
            if(stock.equals("in_stock")){
                products= products.stream().filter(p -> p.getQuantity()>0).collect(Collectors.toList());
            } else if(stock.equals("out_of_stock")){
                products= products.stream().filter(p -> p.getQuantity()<1).collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) Math.min(startIndex + pageable.getPageSize(), products.size());

        List<Product> pageContent = products.subList(startIndex, endIndex);
        Page<Product> filteredProduct = new PageImpl<>(pageContent, pageable, products.size());
        return filteredProduct;
    }

    @Override
    public List<Product> findAllProduct() {
        List<Product> opt = productRepository.findAll();

        return opt;
    }
}
