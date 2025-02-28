package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // This setup method is intentionally left empty.
        // The repository is already properly initialized via @InjectMocks.
        // No additional initialization is required for these tests.
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setId("da09f646-9b91-437d-ab0f-d0821dde9096");
        product2.setName("Sampo Cap User");
        product2.setQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getId(), savedProduct.getId());

        savedProduct = productIterator.next();
        assertEquals(product2.getId(), savedProduct.getId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindidExisting() {
        Product product = new Product();
        product.setId("1234");
        product.setName("Test Product");
        product.setQuantity(10);
        productRepository.create(product);

        Product found = productRepository.findid("1234");
        assertNotNull(found);
        assertEquals("Test Product", found.getName());
        assertEquals(10, found.getQuantity());
    }

    @Test
    void testFindidNotFound() {
        Product found = productRepository.findid("non-existent-id");
        assertNull(found);
    }

    @Test
    void testFindidNotFoundInNonEmptyRepository() {
        Product product = new Product();
        product.setId("1");
        product.setName("Product 1");
        product.setQuantity(10);
        productRepository.create(product);

        Product found = productRepository.findid("2");
        assertNull(found);
    }

    @Test
    void testUpdateProductPositive() {
        Product product = new Product();
        product.setId("bdde26d8-6b99-4d4a-ba75-15274a410fc1");
        product.setName("Original Product");
        product.setQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setId("bdde26d8-6b99-4d4a-ba75-15274a410fc1");
        updatedProduct.setName("Updated Product");
        updatedProduct.setQuantity(20);
        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals(20, result.getQuantity());

        Product foundProduct = productRepository.findid("bdde26d8-6b99-4d4a-ba75-15274a410fc1");
        assertNotNull(foundProduct);
        assertEquals("Updated Product", foundProduct.getName());
        assertEquals(20, foundProduct.getQuantity());
    }

    @Test
    void testUpdateProductNegative() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setId("3ebd73ef-33fa-41fa-8c4d-b13d4c087b50");
        nonExistentProduct.setName("Non-existent Product");
        nonExistentProduct.setQuantity(0);
        Product result = productRepository.update(nonExistentProduct);
        assertNull(result);
    }

    @Test
    void testUpdateProductNonexistentInNonEmptyRepo() {
        Product product = new Product();
        product.setId("8568c5db-7728-457f-a249-12dd51af8d76");
        product.setName("Existing Product");
        product.setQuantity(5);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setId("ac693967-7482-4905-b69c-2a33eb567143");
        updatedProduct.setName("Updated Product");
        updatedProduct.setQuantity(10);

        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProductPositive() {
        Product product = new Product();
        product.setId("c27c7975-219a-43ba-90c6-d1d2026a0c07");
        product.setName("Product to Delete");
        product.setQuantity(5);
        productRepository.create(product);

        productRepository.delete("c27c7975-219a-43ba-90c6-d1d2026a0c07");
        Product foundProduct = productRepository.findid("c27c7975-219a-43ba-90c6-d1d2026a0c07");
        assertNull(foundProduct);
    }

    @Test
    void testDeleteProductNegative() {
        Product product = new Product();
        product.setId("e446dadb-898f-405c-8c17-3ab21e0cd863");
        product.setName("Product Not Deleted");
        product.setQuantity(10);
        productRepository.create(product);

        productRepository.delete("e446dadb-898f-405c-8c17-3ab21e0cd863");
        Product foundProduct = productRepository.findid("e446dadb-898f-405c-8c17-3ab21e0cd863");
        assertNull(foundProduct);
    }
}
