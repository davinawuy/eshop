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
 class ServiceimpelTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
     void testCreate() {
        Product product = new Product();
        product.setId("fa139fd2-82c1-43f9-9826-943b2b8691a1");
        product.setName("Test Product");
        product.setQuantity(100);

        when(productRepository.create(product)).thenReturn(product);

        Product created = productService.create(product);
        verify(productRepository, times(1)).create(product);
        assertEquals(product, created);
    }

    @Test
     void testFindAll() {
        Product product1 = new Product();
        product1.setId("fa139fd2-82c1-43f9-9826-943b2b8691a1");
        product1.setName("Product One");
        product1.setQuantity(10);

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Product Two");
        product2.setQuantity(20);

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
     void testDelete() {
        String productId = "fa139fd2-82c1-43f9-9826-943b2b8691a1";
        productService.delete(productId);
        verify(productRepository, times(1)).delete(productId);
    }

    @Test
     void testFindidFound() {
        String productId = "fa139fd2-82c1-43f9-9826-943b2b8691a1";
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setQuantity(100);

        when(productRepository.findid(productId)).thenReturn(product);

        Product found = productService.findid(productId);
        verify(productRepository, times(1)).findid(productId);
        assertNotNull(found);
        assertEquals("Test Product", found.getName());
        assertEquals(100, found.getQuantity());
    }

    @Test
     void testFindidNotFound() {
        String productId = "non-existent";
        when(productRepository.findid(productId)).thenReturn(null);

        Product found = productService.findid(productId);
        verify(productRepository, times(1)).findid(productId);
        assertNull(found);
    }

    @Test
     void testUpdate() {
        Product original = new Product();
        original.setId("fa139fd2-82c1-43f9-9826-943b2b8691a1");
        original.setName("Old Name");
        original.setQuantity(100);

        Product updated = new Product();
        updated.setId("fa139fd2-82c1-43f9-9826-943b2b8691a1");
        updated.setName("New Name");
        updated.setQuantity(200);

        when(productRepository.update(original)).thenReturn(updated);

        Product result = productService.update(original);
        verify(productRepository, times(1)).update(original);
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals(200, result.getQuantity());
    }
}
