package ru.karmazin.manager.repository;

import org.springframework.stereotype.Repository;
import ru.karmazin.manager.entity.Product;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = Collections.synchronizedList(new LinkedList<>());

    public InMemoryProductRepository() {
        IntStream.range(1, 4)
            .forEach(i -> this.products.add(new Product(i, "Товар №%d".formatted(i),
                "Описание товара №%d".formatted(i))));
    }

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(this.products);
    }

    @Override
    public Product save(Product product) {
        product.setId(this.products.stream()
            .max(Comparator.comparingInt(Product::getId))
            .map(Product::getId)
            .orElse(0) + 1);
        this.products.add(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        return this.products.stream()
            .filter(product -> Objects.equals(product.getId(), productId)).findFirst();
    }

    @Override
    public void deleteById(Integer id) {
        this.products.removeIf(product -> Objects.equals(id, product.getId()));
    }
}
