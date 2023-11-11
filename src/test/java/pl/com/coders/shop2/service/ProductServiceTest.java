package pl.com.coders.shop2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.com.coders.shop2.domain.Product;
import pl.com.coders.shop2.repository.ProductRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        // Given
        Product inputProduct = createSampleProduct();
        when(productRepository.add(inputProduct)).thenReturn(inputProduct);

        // When
        Product createdProduct = productService.create(inputProduct);

        // Then
        assertNotNull(createdProduct);
        assertSame(inputProduct, createdProduct);
        verify(productRepository, times(1)).add(inputProduct);
    }

    @Test
    void get() {
        // Given
        Product product = createSampleProduct();
        when(productRepository.add(product)).thenReturn(product);
        Product addedProduct = productRepository.add(product);
        when(productRepository.get(addedProduct.getId())).thenReturn(product);

        // When
        Product resultProduct = productService.get(addedProduct.getId());

        // Then
        assertNotNull(resultProduct);
        assertSame(addedProduct, resultProduct);
        verify(productRepository, times(1)).add(addedProduct);
    }

    @Test
    void delete() {
        // Given
        Product product = createSampleProduct();
        when(productRepository.add(product)).thenReturn(product);
        Product addedProduct = productRepository.add(product);
        when(productRepository.get(addedProduct.getId())).thenReturn(addedProduct);
        when(productRepository.delete(product.getId())).thenReturn(true);

        //When
        boolean resultProduct = productService.delete(addedProduct.getId());

        //Then
        assertTrue(resultProduct);
        verify(productRepository, times(1)).add(product);
        verify(productRepository, times(1)).delete(addedProduct.getId());
    }

    @Test
    void update() {
        Product inputProduct = createSampleProduct();
        when(productRepository.add(inputProduct)).thenReturn(inputProduct);

        // When
        Product createdProduct = productService.create(inputProduct);

        // Then
        assertNotNull(createdProduct);
        assertSame(inputProduct, createdProduct);
        verify(productRepository, times(1)).add(inputProduct);
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