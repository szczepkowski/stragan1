package pl.com.coders.shop2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.com.coders.shop2.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldAddProduct() {
        // Given
        Product product = createSampleProduct();
        // When
        Product addedProduct = productRepository.add(product);

        // Then
        assertNotNull(addedProduct);
        assertEquals(product, addedProduct);
    }

    @Test
    void shouldGetProduct() {
        // Given
        Product product = createSampleProduct();
        Product addedProduct = productRepository.add(product);

        // When
        Product foundProduct = productRepository.get(addedProduct.getId());

        // Then
        assertNotNull(foundProduct, "Found product should not be null");
        assertEquals(addedProduct.getId(), foundProduct.getId(), "Product IDs should match");
        assertEquals(product.getName(), foundProduct.getName(), "Product names should match");
    }

    @Test
    void shouldDeleteProduct() {
        //Given
        Product product = createSampleProduct();
        Product addedProduct = productRepository.add(product);

        // When
        boolean delete = productRepository.delete(addedProduct.getId());

        // Then
        assertTrue(delete);
    }

    @Test
    void shouldUpdateProduct() {
        // Given
        Product product = createSampleProduct();
        Product addedProduct = productRepository.add(product);

        LocalDateTime expectedCreated = addedProduct.getCreated().withNano(0);
        LocalDateTime expectedUpdated = LocalDateTime.now().withNano(0);

        // When
        addedProduct.setName("Updated Product");
        addedProduct.setPrice(BigDecimal.valueOf(29.99));
        Product updatedProduct = productRepository.update(addedProduct, addedProduct.getId());

        LocalDateTime actualCreated = updatedProduct.getCreated().withNano(0);
        LocalDateTime actualUpdated = updatedProduct.getUpdated().withNano(0);

        // Then
        assertEquals(expectedCreated, actualCreated);
        assertEquals(expectedUpdated, actualUpdated);
    }

    @Test
    void shouldFindAllProducts() {
        // Given
        Product product1 = createSampleProduct();
        Product product2 = createSampleProduct();


        // When
        productRepository.add(product1);
        productRepository.add(product2);
        List<Product> allProducts = productRepository.findAll();

        // Then
        assertEquals(2, allProducts.size());
    }

    private Product createSampleProduct() {
        return Product.builder()
                .name("Sample Product")
                .description("Sample Description")
                .price(BigDecimal.valueOf(19.99))
                .quantity(10)
                .build();
    }
}
