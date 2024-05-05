package com.example.thuongmaidientu.request;

public class ReviewRequest {
    private Long productId;
    private String review;

    public ReviewRequest(Long id, String review) {
        this.productId = id;
        this.review = review;
    }

    public Long getProductId() {
        return productId;
    }

    public void setId(Long id) {
        this.productId = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
