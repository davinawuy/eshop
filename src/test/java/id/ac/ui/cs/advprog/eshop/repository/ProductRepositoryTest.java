package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("da09f646-9b91-437d-ab0f-d0821dde9096");
        product2.setProductName("Sampo Cap User");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testUpdateProductPositive() {
        Product product = new Product();
        product.setProductId("bdde26d8-6b99-4d4a-ba75-15274a410fc1");
        product.setProductName("Original Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("bdde26d8-6b99-4d4a-ba75-15274a410fc1");
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(20);
        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("Updated Product", result.getProductName());
        assertEquals(20, result.getProductQuantity());

        Product foundProduct = productRepository.findid("bdde26d8-6b99-4d4a-ba75-15274a410fc1");
        assertNotNull(foundProduct);
        assertEquals("Updated Product", foundProduct.getProductName());
        assertEquals(20, foundProduct.getProductQuantity());
    }

    @Test
    void testUpdateProductNegative() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("3ebd73ef-33fa-41fa-8c4d-b13d4c087b50");
        nonExistentProduct.setProductName("Non-existent Product");
        nonExistentProduct.setProductQuantity(0);
        Product result = productRepository.update(nonExistentProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProductPositive() {
        Product product = new Product();
        product.setProductId("c27c7975-219a-43ba-90c6-d1d2026a0c07");
        product.setProductName("Product to Delete");
        product.setProductQuantity(5);
        productRepository.create(product);

        productRepository.delete("c27c7975-219a-43ba-90c6-d1d2026a0c07");

        Product foundProduct = productRepository.findid("c27c7975-219a-43ba-90c6-d1d2026a0c07");
        assertNull(foundProduct);
    }

    @Test
    void testDeleteProductNegative() {
        Product product = new Product();
        product.setProductId("e446dadb-898f-405c-8c17-3ab21e0cd863");
        product.setProductName("Product Not Deleted");
        product.setProductQuantity(10);
        productRepository.create(product);

        productRepository.delete("e446dadb-898f-405c-8c17-3ab21e0cd863");

        Product foundProduct = productRepository.findid("e446dadb-898f-405c-8c17-3ab21e0cd863");
        assertNull(foundProduct);
    }
}
