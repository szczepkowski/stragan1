package pl.com.coders.shop2.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.com.coders.shop2.domain.Category;
import pl.com.coders.shop2.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class ProductRepositoryTest {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private Product product;
    private Category category;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @BeforeEach
    void setUp() {
        category = createSampleCategory();
        categoryRepository.save(category);
        product = createSampleProduct(category);
        productRepository.add(product, category.getId());
    }

    @Test
    void shouldAddProductToRepository() {
        assertThat(product).isNotNull();
        assertThat(product.getId()).isNotNull();
    }

    @Test
    void shouldGetProductFromRepository() {
        Product foundProduct = productRepository.getProductById(product.getId());
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(product.getId());
        assertThat(foundProduct.getName()).isEqualTo(product.getName());
    }
    @Test
    void shouldDeleteProductFromRepository() {
        boolean delete = productRepository.delete(product.getId());
        assertThat(delete).isTrue();
        Product deletedProduct = productRepository.getProductById(product.getId());
        assertThat(deletedProduct).isNull();
    }
    @Test
    void shouldUpdateProductInRepository() {
        Category category = createSampleCategory();
        categoryRepository.save(category);
        Product product = createSampleProduct(category);
        product.setCategory(category);
        Product addedProduct = productRepository.add(product, category.getId());
        LocalDateTime expectedCreated = addedProduct.getCreated().withNano(0);
        addedProduct.setName("Updated Product");
        addedProduct.setPrice(BigDecimal.valueOf(29.99));
        Product updatedProduct = productRepository.update(addedProduct, product.getId());
        LocalDateTime actualCreated = updatedProduct.getCreated().withNano(0);
        assertThat(actualCreated).isEqualTo(expectedCreated);
    }
    @Test
    void shouldFindAllProductsInRepository() {
        Product product2 = createSampleProduct(category);
        Product addedProduct2 = productRepository.add(product2, category.getId());
        List<Product> allProducts = productRepository.findAll();
        assertEquals(6, allProducts.size());
    }
    private Product createSampleProduct(Category category) {
        return Product.builder()
                .name("Sample Product")
                .description("Sample Description")
                .price(BigDecimal.valueOf(19.99))
                .quantity(10)
                .category(category)
                .build();
    }
    private Category createSampleCategory() {
        return Category.builder()
                .name("Electronics")
                .build();
    }


}