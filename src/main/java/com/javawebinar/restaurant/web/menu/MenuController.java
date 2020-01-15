package com.javawebinar.restaurant.web.menu;

import com.javawebinar.restaurant.model.Menu;
import com.javawebinar.restaurant.service.MenuService;
import com.javawebinar.restaurant.web.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)

public class MenuController {

    static final String REST_URL = "/rest";

    @Autowired
    MenuService menuService;

    @PostMapping(value = "/restaurants/{restaurantId}/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createMenu(@Valid @RequestBody Menu menuRequest,
                                           @PathVariable(value = "restaurantId") int restaurantId) {
        Menu created = menuService.createMenu(restaurantId, menuRequest);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/menus/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateMenu(@Valid @RequestBody Menu menuRequest,
                           @PathVariable(value = "menuId") int menuId
    ) {
        System.out.println("auth " + SecurityUtil.get().getAuthorities());
        menuService.updateMenu(menuId, menuRequest);
    }

    @GetMapping("/restaurants/{restaurantId}/menus")
    public List<Menu> getAllMenusForRestaurant(@PathVariable(value = "restaurantId") int restaurantId) {
        return menuService.getAllMenus(restaurantId);
    }

    @DeleteMapping("/menus/{menuId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable(value = "menuId") int menuId) {
        menuService.deleteMenu(menuId);
    }

    @GetMapping("/menus/{menuId}")
    public Menu getMenu(@PathVariable(value = "menuId") int menuId) {
        return menuService.getMenu(menuId);
    }

}
