package com.server.ecomm.order.repository;

import com.server.ecomm.order.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
