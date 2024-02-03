package com.ck.cafe_back.DAO;

import com.ck.cafe_back.POJO.Product;
import com.ck.cafe_back.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product,Integer> {

    List<ProductWrapper> getAllProduct();
}
