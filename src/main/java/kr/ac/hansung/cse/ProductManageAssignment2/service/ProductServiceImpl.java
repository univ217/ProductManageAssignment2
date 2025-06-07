package kr.ac.hansung.cse.ProductManageAssignment2.service;

import kr.ac.hansung.cse.ProductManageAssignment2.entity.Product;
import kr.ac.hansung.cse.ProductManageAssignment2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    public List<Product> listAll() {
        return productRepository.findAll();
    }
    public Product get(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
    }
    public void save(Product product) {
        productRepository.save(product);
    }
    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
