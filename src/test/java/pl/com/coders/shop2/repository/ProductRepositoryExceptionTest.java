package pl.com.coders.shop2.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.com.coders.shop2.domain.Category;
import pl.com.coders.shop2.domain.Product;
import pl.com.coders.shop2.exceptions.ProductWithGivenIdNotExistsException;
import pl.com.coders.shop2.exceptions.ProductWithGivenTitleExistsException;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryExceptionTest {

    @InjectMocks
    private ProductRepository productRepository;

    @Mock
    private EntityManager entityManager;

    @Test
    void add_WithNullProductName_ShouldThrowException() {
        Product product = new Product();
        assertThrows(ProductWithGivenTitleExistsException.class, () -> productRepository.add(product));
        verify(entityManager, never()).merge(any());
    }

    @Test
    void getProductById_WithNonExistingId_ShouldThrowException() {
        Long nonExistingId = 1L;
        when(entityManager.find(eq(Product.class), eq(nonExistingId))).thenReturn(null);
        assertThrows(ProductWithGivenIdNotExistsException.class, () -> productRepository.getProductById(nonExistingId));
        verify(entityManager, times(1)).find(eq(Product.class), eq(nonExistingId));
    }
}
