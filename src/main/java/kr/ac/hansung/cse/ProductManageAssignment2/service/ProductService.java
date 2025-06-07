package kr.ac.hansung.cse.ProductManageAssignment2.service;

import kr.ac.hansung.cse.ProductManageAssignment2.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> listAll();
    Product get(long id);
    void save(Product product);
    void delete(long id);
}
