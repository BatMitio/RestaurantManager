package com.dim.RestaurantManager.model.entity;


import com.dim.RestaurantManager.model.entity.base.BaseEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne(optional = false)
    private Item item;
    @ManyToOne
    private Bill bill;
    @ManyToOne
    private ArchivedBill archivedBill;
    @ManyToOne(optional = false)
    private OrderStatus status;
    @Column(nullable = false)
    private LocalDateTime placed;
    @ManyToOne
    private User cook;
    @ManyToOne
    private User waiter;
    private String notes;
    @ManyToOne
    private User payer;

    public Item getItem() {
        return item;
    }

    public Order setItem(Item item) {
        this.item = item;
        return this;
    }

    public Bill getBill() {
        return bill;
    }

    public Order setBill(Bill bill) {
        this.bill = bill;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Order setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getPlaced() {
        return placed;
    }

    public Order setPlaced(LocalDateTime placed) {
        this.placed = placed;
        return this;
    }

    public User getCook() {
        return cook;
    }

    public Order setCook(User cook) {
        this.cook = cook;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Order setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public User getWaiter() {
        return waiter;
    }

    public Order setWaiter(User waiter) {
        this.waiter = waiter;
        return this;
    }

    public User getPayer() {
        return payer;
    }

    public Order setPayer(User payer) {
        this.payer = payer;
        return this;
    }

    public ArchivedBill getArchivedBill() {
        return archivedBill;
    }

    public Order setArchivedBill(ArchivedBill archivedBill) {
        this.archivedBill = archivedBill;
        return this;
    }
}
