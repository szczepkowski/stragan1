package pl.com.coders.shop2.repository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.domain.Category;
import pl.com.coders.shop2.domain.Product;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public class ProductRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    public ProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional
    public Product add(Product product, Long categoryId) {
        Category category = entityManager.find(Category.class, categoryId);
        if (category == null) {
            throw new EntityNotFoundException("Category not found with id: " + categoryId);
        }
        product.setCategory(category);
        product.setCreated(LocalDateTime.now());
        product.setUpdated(LocalDateTime.now());
        entityManager.persist(product);
        return product;
    }
    public Product getProductById(Long id) {
        return entityManager.find(Product.class, id);
    }
    public List<Product> getProductsByCategory(Category category) {
        String jpql = "SELECT p FROM Product p WHERE p.category = :category";
        return entityManager.createQuery(jpql, Product.class)
                .setParameter("category", category)
                .getResultList();
    }
    @Transactional
    public boolean delete(Long id) {
        int deletedCount = entityManager.createQuery("DELETE FROM Product p WHERE p.id = :productId")
                .setParameter("productId", id)
                .executeUpdate();
        return deletedCount > 0;
    }
    @Transactional
    public Product update(Product product, Long id ) {
        Product old = getProductById(id);
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