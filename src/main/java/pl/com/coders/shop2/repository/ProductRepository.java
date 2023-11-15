package pl.com.coders.shop2.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.domain.Category;
import pl.com.coders.shop2.domain.Product;
import pl.com.coders.shop2.exceptions.ProductWithGivenIdNotExistsException;
import pl.com.coders.shop2.exceptions.ProductWithGivenTitleExistsException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> products;
    @PersistenceContext
    private EntityManager entityManager;

    public ProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Product add(Product product) throws ProductWithGivenTitleExistsException {
        if (product.getName() == null) {
            throw new ProductWithGivenTitleExistsException("Product with the given title already exists.");
        }
        return entityManager.merge(product);
    }


    public Product getProductById(Long id) throws ProductWithGivenIdNotExistsException {
        Product product = entityManager.find(Product.class, id);
        if (product == null) {
            throw new ProductWithGivenIdNotExistsException("Product with the given Id " + id + " doesn't exist");
        }
        return product;
    }


    public List<Product> getProductsByCategory(Category category) {
        String jpql = "SELECT p FROM Product p WHERE p.category = :category";
        return entityManager.createQuery(jpql, Product.class)
                .setParameter("category", category)
                .getResultList();
    }


    @Transactional
    public boolean delete(Long id) {
        Product product = entityManager.find(Product.class, id);
        if (product != null) {
            entityManager.remove(product);
            return true;
        }
        return false;
    }


    @Transactional
    public Product update(Product product) throws ProductWithGivenIdNotExistsException {
        if (product.getId() == null || entityManager.find(Product.class, product.getId()) == null) {
            throw new ProductWithGivenIdNotExistsException("Product with the given ID does not exist.");
        }
        return entityManager.merge(product);
    }

    @Transactional
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();
    }

    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Product").executeUpdate();
    }
}