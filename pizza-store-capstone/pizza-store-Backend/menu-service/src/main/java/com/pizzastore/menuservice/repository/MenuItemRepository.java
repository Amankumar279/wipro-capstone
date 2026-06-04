package com.pizzastore.menuservice.repository;

import com.pizzastore.menuservice.entity.Category;
import com.pizzastore.menuservice.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByAvailableTrue();
    List<MenuItem> findByCategoryAndAvailableTrue(Category category);
    List<MenuItem> findByBestsellerTrueAndAvailableTrue();
    List<MenuItem> findByNewLaunchTrueAndAvailableTrue();
    List<MenuItem> findByNameContainingIgnoreCase(String keyword);
}
