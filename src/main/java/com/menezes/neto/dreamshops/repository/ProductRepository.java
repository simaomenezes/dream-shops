package com.menezes.neto.dreamshops.repository;

import com.menezes.neto.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
