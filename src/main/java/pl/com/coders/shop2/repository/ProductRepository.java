package pl.com.coders.shop2.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.domain.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Product add(Product product) {
        product.setCreated(LocalDateTime.now());
        product.setUpdated(LocalDateTime.now());
        entityManager.persist(product);
        return product;
    }

    public Product get(Long id) {
        return entityManager.find(Product.class, id);
    }

    @Transactional
    public boolean delete(Long id) {
        Product toDeleteProduct = get(id);
        entityManager.remove(toDeleteProduct);
        return true;
    }

    @Transactional
    public Product update(Product product, Long id) {
        Product old = get(id);

        old.setName(product.getName());
        old.setDescription(product.getDescription());
        old.setPrice(product.getPrice());
        old.setQuantity(product.getQuantity());
        old.setUpdated(LocalDateTime.now());
        entityManager.merge(old);

        return old;
    }

    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }
}

