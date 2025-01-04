package com.ecommerce.webapp.repository;


import java.util.List;

import com.ecommerce.webapp.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {

    List<ProductOrder> findByUserId(Integer userId);

    ProductOrder findByOrderId(String orderId);

}
