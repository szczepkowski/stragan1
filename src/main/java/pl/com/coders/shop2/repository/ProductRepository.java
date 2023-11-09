package pl.com.coders.shop2.repository;

import org.springframework.stereotype.Repository;
import pl.com.coders.shop2.domain.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Product add(Product product) {
        product.setCreated(LocalDateTime.now());
        product.setUpdated(LocalDateTime.now());
        entityManager.persist(product);
        return product;
    }

    public Product get(Long id) {
        Product product = entityManager.find(Product.class, id);
        return product;
    }

    public boolean delete(Long id) {
        entityManager.remove(id);
        return true;
    }

    public Product update(Product product, Long id) {
        Product old = get(id);
        old.setName(product.getName());
        old.setDescription(product.getDescription());
        old.setPrice(product.getPrice());
        old.setQuantity(product.getQuantity());
        entityManager.merge(old);
        return old;
    }

    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }
}

