package com.dim.RestaurantManager.web.rest;

import com.dim.RestaurantManager.model.view.CookOrderView;
import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CookOrdersRestController {
    private final OrderService orderService;

    public CookOrdersRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("isCook()")
    @GetMapping("/personnel/cook/orders-rest")
    public ResponseEntity<List<CookOrderView>> getOrders() {
        return ResponseEntity.ok(orderService.getPendingOrders());
    }

    @PreAuthorize("isCook()")
    @GetMapping("/personnel/cook/current/orders-rest")
    public ResponseEntity<List<CookOrderView>> getCurrentCookOrders(@AuthenticationPrincipal RestaurantUser restaurantUser) {
        return ResponseEntity.ok(orderService.getCurrentCookOrders(restaurantUser));
    }

    @PreAuthorize("isCook()")
    @GetMapping("/personnel/cook/order/{orderId}/accept-rest")
    public ResponseEntity acceptOrder(@PathVariable(name = "orderId") String orderId,
            @AuthenticationPrincipal RestaurantUser user) {
        orderService.acceptCookOrder(user, Long.parseLong(orderId));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isCook()")
    @GetMapping("/personnel/cook/order/{orderId}/ready-rest")
    public ResponseEntity readyOrder(@PathVariable(name = "orderId") String orderId) {
        orderService.readyCookOrder(Long.parseLong(orderId));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isCook()")
    @GetMapping("/personnel/cook/order/{orderId}/cancel-rest")
    public ResponseEntity cancelOrder(@PathVariable(name = "orderId") String orderId) {
        orderService.cancelCookOrder(Long.parseLong(orderId));
        return ResponseEntity.ok().build();
    }
}

