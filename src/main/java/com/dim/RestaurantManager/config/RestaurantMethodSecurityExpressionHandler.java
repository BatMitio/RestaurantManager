package com.dim.RestaurantManager.config;

import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.UserService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class RestaurantMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler{
    private final UserService userService;
    private final OrderService orderService;

    public RestaurantMethodSecurityExpressionHandler(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
                                                                              MethodInvocation invocation) {
        MethodSecurityExpressionRoot root = new MethodSecurityExpressionRoot(authentication);

        root.setUserService(userService);
        root.setOrderService(orderService);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(new AuthenticationTrustResolverImpl());
        root.setRoleHierarchy(getRoleHierarchy());

        return root;
    }
}
