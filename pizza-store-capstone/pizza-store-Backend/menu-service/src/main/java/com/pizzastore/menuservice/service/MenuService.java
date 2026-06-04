package com.pizzastore.menuservice.service;

import com.pizzastore.menuservice.dto.MenuItemRequest;
import com.pizzastore.menuservice.entity.Category;
import com.pizzastore.menuservice.entity.MenuItem;
import com.pizzastore.menuservice.exception.BadRequestException;
import com.pizzastore.menuservice.exception.ResourceNotFoundException;
import com.pizzastore.menuservice.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/** Menu CRUD + browsing helpers + stock management. */
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;

    public List<MenuItem> getAllAvailable() { return menuItemRepository.findAll(); }

    public List<MenuItem> getAll() { return menuItemRepository.findAll(); }

    public MenuItem getById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found: " + id));
    }

    public List<MenuItem> getByCategory(String category) {
        return menuItemRepository.findByCategoryAndAvailableTrue(parseCategory(category));
    }

    public List<MenuItem> getBestsellers() { return menuItemRepository.findByBestsellerTrueAndAvailableTrue(); }

    public List<MenuItem> getNewLaunches() { return menuItemRepository.findByNewLaunchTrueAndAvailableTrue(); }

    public List<MenuItem> search(String keyword) { return menuItemRepository.findByNameContainingIgnoreCase(keyword); }

    public List<String> getCategories() {
        return Arrays.stream(Category.values()).map(Enum::name).toList();
    }

    public MenuItem create(MenuItemRequest req) {
        MenuItem item = MenuItem.builder()
                .name(req.getName()).description(req.getDescription())
                .price(req.getPrice()).category(parseCategory(req.getCategory()))
                .imageUrl(req.getImageUrl()).available(req.isAvailable())
                .bestseller(req.isBestseller()).newLaunch(req.isNewLaunch())
                .stockQuantity(req.getStockQuantity() == null ? 0 : req.getStockQuantity())
                .build();
        return menuItemRepository.save(item);
    }

    public MenuItem update(Long id, MenuItemRequest req) {
        MenuItem item = getById(id);
        item.setName(req.getName());
        item.setDescription(req.getDescription());
        item.setPrice(req.getPrice());
        item.setCategory(parseCategory(req.getCategory()));
        item.setImageUrl(req.getImageUrl());
        item.setAvailable(req.isAvailable());
        item.setBestseller(req.isBestseller());
        item.setNewLaunch(req.isNewLaunch());
        if (req.getStockQuantity() != null) {
            item.setStockQuantity(req.getStockQuantity());
        }
        return menuItemRepository.save(item);
    }

    public void delete(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu item not found: " + id);
        }
        menuItemRepository.deleteById(id);
    }

    /**
     * Decrease stock when an order is placed.
     * Throws BadRequestException if not enough stock.
     */
    @Transactional
    public MenuItem decreaseStock(Long id, Integer quantity) {
        MenuItem item = getById(id);
        if (item.getStockQuantity() < quantity) {
            throw new BadRequestException(
                "Not enough stock for '" + item.getName() + "'. " +
                "Requested: " + quantity + ", Available: " + item.getStockQuantity());
        }
        item.setStockQuantity(item.getStockQuantity() - quantity);
        return menuItemRepository.save(item);
    }
    
    @Transactional
    public MenuItem increaseStock(Long id, Integer quantity) {
        MenuItem item = getById(id);
        item.setStockQuantity(item.getStockQuantity() + quantity);
        item.setAvailable(true);
        return menuItemRepository.save(item);
    }

    private Category parseCategory(String value) {
        try { return Category.valueOf(value.toUpperCase()); }
        catch (Exception e) { throw new BadRequestException("Invalid category: " + value); }
    }
}