package com.server.ecomm.cart;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart,String> {
//    Optional<Object> findByEmail(String email);
}