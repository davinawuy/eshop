package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceimpelTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testCreate() {
        Product product = new Product();
        product.setProductId("fa139fd2-82c1-43f9-9826-943b2b8691a1");
        product.setProductName("Test Product");
        product.setProductQuantity(100);

        when(productRepository.create(product)).thenReturn(product);

        Product created = productService.create(product);
        verify(productRepository, times(1)).create(product);
        assertEquals(product, created);
    }

    @Test
    public void testFindAll() {
        Product product1 = new Product();
        product1.setProductId("fa139fd2-82c1-43f9-9826-943b2b8691a1");
        product1.setProductName("Product One");
        product1.setProductQuantity(10);

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Product Two");
        product2.setProductQuantity(20);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productService.findAll();
        verify(productRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));
    }

    @Test
    public void testDelete() {
        String productId = "fa139fd2-82c1-43f9-9826-943b2b8691a1";
        productService.delete(productId);
        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    public void testFindidFound() {
        String productId = "fa139fd2-82c1-43f9-9826-943b2b8691a1";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Test Product");
        product.setProductQuantity(100);

        when(productRepository.findid(productId)).thenReturn(product);

        Product found = productService.findid(productId);
        verify(productRepository, times(1)).findid(productId);
        assertNotNull(found);
        assertEquals("Test Product", found.getProductName());
        assertEquals(100, found.getProductQuantity());
    }

    @Test
    public void testFindidNotFound() {
        String productId = "non-existent";
        when(productRepository.findid(productId)).thenReturn(null);

        Product found = productService.findid(productId);
        verify(productRepository, times(1)).findid(productId);
        assertNull(found);
    }

    @Test
    public void testUpdate() {
        Product original = new Product();
        original.setProductId("fa139fd2-82c1-43f9-9826-943b2b8691a1");
        original.setProductName("Old Name");
        original.setProductQuantity(100);

        Product updated = new Product();
        updated.setProductId("fa139fd2-82c1-43f9-9826-943b2b8691a1");
        updated.setProductName("New Name");
        updated.setProductQuantity(200);

        when(productRepository.update(original)).thenReturn(updated);

        Product result = productService.update(original);
        verify(productRepository, times(1)).update(original);
        assertNotNull(result);
        assertEquals("New Name", result.getProductName());
        assertEquals(200, result.getProductQuantity());
    }
}
