package com.dim.RestaurantManager.web.rest.responses;

public class UsernameAvailabilityResponse {
    private boolean available;

    public boolean isAvailable() {
        return available;
    }

    public UsernameAvailabilityResponse setAvailable(boolean available) {
        this.available = available;
        return this;
    }
}
