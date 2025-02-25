package com.ecommerce.webapp.service;


import java.util.List;

import com.ecommerce.webapp.model.OrderRequest;
import com.ecommerce.webapp.model.ProductOrder;
import org.springframework.data.domain.Page;


public interface OrderService {

    public void saveOrder(Integer userid, OrderRequest orderRequest) throws Exception;

    public List<ProductOrder> getOrdersByUser(Integer userId);

    public ProductOrder updateOrderStatus(Integer id, String status);

    public List<ProductOrder> getAllOrders();

    public ProductOrder getOrdersByOrderId(String orderId);

    public Page<ProductOrder> getAllOrdersPagination(Integer pageNo,Integer pageSize);
}