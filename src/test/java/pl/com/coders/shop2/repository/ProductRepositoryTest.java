package pl.com.coders.shop2.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.com.coders.shop2.domain.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddProduct() {
        // Given
        Product product = createSampleProduct();
        when(productRepository.add(product)).thenReturn(product);

        // When
        Product addedProduct = productRepository.add(product);

        // Then
        assertNotNull(addedProduct);
        assertEquals(product, addedProduct);
        verify(productRepository, times(1)).add(product);
    }

    @Test
    void shouldGetProduct() {
        // Given
        Product product = createSampleProduct();
        when(productRepository.add(product)).thenReturn(product);
        Product addedProduct = productRepository.add(product);
        when(productRepository.get(addedProduct.getId())).thenReturn(product);

        // When
        Product foundProduct = productRepository.get(addedProduct.getId());

        // Then
        assertNotNull(foundProduct, "Found product should not be null");
        assertEquals(addedProduct.getId(), foundProduct.getId(), "Product IDs should match");
        assertEquals(product.getName(), foundProduct.getName(), "Product names should match");
        verify(productRepository, times(1)).add(product);
        verify(productRepository, times(1)).get(addedProduct.getId());
    }

    @Test
    void shouldDeleteProduct() {
        Product product = createSampleProduct();
        when(productRepository.add(product)).thenReturn(product);
        Product addedProduct = productRepository.add(product);
        when(productRepository.get(addedProduct.getId())).thenReturn(addedProduct);
        when(productRepository.delete(addedProduct.getId())).thenReturn(true);

        // When
        boolean delete = productRepository.delete(addedProduct.getId());

        // Then
        assertTrue(delete);
        verify(productRepository, times(1)).add(product);
        verify(productRepository, times(1)).delete(addedProduct.getId());
    }

    @Test
    void shouldUpdateProduct() {
        // Given
        Product product = createSampleProduct();
        when(productRepository.add(product)).thenReturn(product);
        Product addedProduct = productRepository.add(product);
        when(productRepository.get(addedProduct.getId())).thenReturn(addedProduct);


        // When
        addedProduct.setName("Updated Product");
        addedProduct.setPrice(BigDecimal.valueOf(29.99));
        Product updatedProduct = productRepository.add(addedProduct);

        // Then
        assertEquals(addedProduct, updatedProduct);
        verify(productRepository, times(2)).add(product);
        verify(productRepository, times(2)).add(addedProduct);
    }

    @Test
    void shouldFindAllProducts() {
        // Given
        Product product1 = createSampleProduct();
        when(productRepository.add(product1)).thenReturn(product1);
        Product product2 = createSampleProduct();
        when(productRepository.add(product2)).thenReturn(product2);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));


        // When
        productRepository.add(product1);
        productRepository.add(product2);
        List<Product> allProducts = productRepository.findAll();

        // Then
        assertEquals(2, allProducts.size());
        verify(productRepository, times(2)).add(product1);
        verify(productRepository, times(2)).add(product2);
        verify(productRepository, times(1)).findAll();
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
