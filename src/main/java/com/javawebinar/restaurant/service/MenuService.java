package com.javawebinar.restaurant.service;

import com.javawebinar.restaurant.model.Menu;
import com.javawebinar.restaurant.repository.CrudMenuRepository;
import com.javawebinar.restaurant.repository.CrudRestaurantRepository;
import com.javawebinar.restaurant.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuService {
    @Autowired
    private CrudMenuRepository menuRepository;

    @Autowired
    private CrudRestaurantRepository restaurantRepository;

    @Transactional
    public Menu createMenu(int restaurantId, @RequestBody Menu menuRequest) {
        Menu createdMenu = restaurantRepository.findById(restaurantId)
                .map(restaurant -> {
                    menuRequest.setRestaurant(restaurant);
                    return menuRequest;
                }).orElseThrow(() -> new NotFoundException("Restaurant with id " + restaurantId + " not found"));
        return menuRepository.save(createdMenu);
    }

    @Transactional
    public void updateMenu(int menuId, @RequestBody Menu menuRequest) {
        Menu updateMenu = menuRepository.findById(menuId)
                .map(menu -> new Menu(menu.getId(), menuRequest.getName(), menuRequest.getMenuDate(), menuRequest.getRestaurant()))
                .orElseThrow(() -> new NotFoundException("Menu with id " + menuId + " not found"));
        menuRepository.save(updateMenu);
    }

    public List<Menu> getAllMenus(int restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id " + restaurantId + " not found"));
    }

    @Transactional
    public void deleteMenu(int menuId) {
        menuRepository.delete(menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu with id " + menuId + " not found")));
    }

    public Menu getMenu(int menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu with id " + menuId + " not found"));
    }

    public List<Menu> getAllWithRestaurantAndCourse(LocalDate date) {
        return menuRepository.findAllByDateTime(date);
    }
}
