package com.example.shopapp.repositories;

import com.example.shopapp.models.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository repo;
    @Test
    public void testSearchProducts() {
        String keyword = "Durable";
        int pageNumber = 1;
        int pageSize = 9;
        Long id = 1L;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = repo.searchProducts(id,keyword, pageable);

        List<Product> products = page.getContent();
        products.forEach(products1 -> System.out.println(products1.getName()));

        Assertions.assertThat(products.size()).isGreaterThan(0);
    }
}