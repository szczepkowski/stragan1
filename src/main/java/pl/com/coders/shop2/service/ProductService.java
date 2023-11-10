package pl.com.coders.shop2.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.coders.shop2.domain.Product;
import pl.com.coders.shop2.repository.ProductRepository;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public Product create(Product product) {
        return productRepository.add(product);
    }

    public Product get(Long id) {
        return productRepository.get(id);
    }

    public boolean delete(Long id) {
        return productRepository.delete(id);
    }

    public Product update(Product product, Long id) {
        return productRepository.update(product,id);
    }
}
