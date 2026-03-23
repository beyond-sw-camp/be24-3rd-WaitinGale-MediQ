package org.example.mediqback.orders;


import org.example.mediqback.orders.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository  extends JpaRepository<Orders, Long> {
}
