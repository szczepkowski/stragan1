package pl.com.coders.shop2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.com.coders.shop2.domain.Product;
import pl.com.coders.shop2.domain.ProductDto;
import pl.com.coders.shop2.repository.ProductRepository;
import pl.com.coders.shop2.service.ProductService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void create() throws Exception {
        // Given
        Product product = createSampleProduct();
        when(productService.create(any(Product.class))).thenReturn(product);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        Product responseProduct = objectMapper.readValue(responseContent, Product.class);

        assertEquals(product, responseProduct);
    }

    @Test
    void get() throws Exception {
        // Given
        Product product = createSampleProduct();
        when(productService.get(anyLong())).thenReturn(product);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Then
        String json = result.getResponse().getContentAsString();
        Product responseProduct = objectMapper.readValue(json, Product.class);
        assertEquals(product, responseProduct);
    }

    @Test
    void delete() throws Exception {
        // Given
        Product product = createSampleProduct();
        when(productService.get(anyLong())).thenReturn(product);
        long productId = 1L;
        when(productService.delete(productId)).thenReturn(true);

        // When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/product")
                        .param("id", String.valueOf(productId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseContent = mvcResult.getResponse().getContentAsString();
        assertNotNull(mvcResult);
    }

    @Test
    void update() throws Exception {
        // Given
        Product existingProduct = createSampleProduct();
        when(productService.update(any(Product.class), anyLong())).thenReturn(existingProduct);

        // When
        ProductDto updatedProductDto = createSampleProductDto();
        String json = objectMapper.writeValueAsString(updatedProductDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        Product responseProduct = objectMapper.readValue(responseContent, Product.class);
        assertEquals(existingProduct, responseProduct);
    }


    private Product createSampleProduct() {
        return Product.builder()
                .name("Sample Product")
                .description("Sample Description")
                .price(BigDecimal.valueOf(19.99))
                .quantity(10)
                .build();
    }

    private ProductDto createSampleProductDto() {
        return ProductDto.builder()
                .name("Sample Product")
                .description("Sample Description")
                .price(BigDecimal.valueOf(19.99))
                .quantity(10)
                .build();
    }
}