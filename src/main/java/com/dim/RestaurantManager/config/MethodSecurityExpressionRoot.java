package com.dim.RestaurantManager.config;

import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.service.exceptions.common.CommonErrorMessages;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class MethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {
    private Object filterObject, returnObject;
    private UserService userService;
    private OrderService orderService;

    public boolean isAdmin(){
        RestaurantUser user = currentUser();
        if(user != null){
            return userService.isAdmin(user.getUsername());
        }
        return false;
    }

    public boolean isManager(){
        RestaurantUser user = currentUser();
        if(user != null){
            return userService.isManager(user.getUsername());
        }
        return false;
    }

    public boolean isCook(){
        RestaurantUser user = currentUser();
        if(user != null){
            return userService.isCook(user.getUsername());
        }
        return false;
    }

    public boolean isWaiter(){
        RestaurantUser user = currentUser();
        if(user != null){
            return userService.isWaiter(user.getUsername());
        }
        return false;
    }

    public boolean isOwner(String username){
        RestaurantUser user = currentUser();
        if(user == null)
            return false;
        return user.getUsername().equals(username);
    }

    public boolean isOwner(Long orderId){
        RestaurantUser user = currentUser();
        if(user != null){
            return orderService.isOwner(orderId, user);
        }
        return false;
    }

    public boolean canOrder() {
        RestaurantUser restaurantUser = currentUser();
        if(restaurantUser == null)
            throw CommonErrorMessages.notLogged();
        User user = userService.getByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));
        return user.getBill() != null && user.getBill().getTable() != null;
    }

    public boolean hasNotOccupied(String username) {
        return userService.hasNotOccupied(username);
    }

    private RestaurantUser currentUser(){
        Authentication authentication = getAuthentication();
        if(authentication.getPrincipal() instanceof UserDetails)
            return ((RestaurantUser)authentication.getPrincipal());
        return null;
    }

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public MethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    public MethodSecurityExpressionRoot setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    public MethodSecurityExpressionRoot setOrderService(OrderService orderService) {
        this.orderService = orderService;
        return this;
    }
}
