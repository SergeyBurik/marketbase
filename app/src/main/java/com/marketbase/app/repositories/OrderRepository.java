package com.marketbase.app.repositories;

import com.marketbase.app.models.Order;
import com.marketbase.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findAllByUser(User u);
}
