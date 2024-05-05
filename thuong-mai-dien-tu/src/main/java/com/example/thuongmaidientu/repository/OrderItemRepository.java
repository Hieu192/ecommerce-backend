package com.example.thuongmaidientu.repository;

import com.example.thuongmaidientu.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
