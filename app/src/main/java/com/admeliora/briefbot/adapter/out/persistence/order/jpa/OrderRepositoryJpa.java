package com.admeliora.briefbot.adapter.out.persistence.order.jpa;

import com.admeliora.briefbot.application.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryJpa extends JpaRepository<Order, Long> {
}

