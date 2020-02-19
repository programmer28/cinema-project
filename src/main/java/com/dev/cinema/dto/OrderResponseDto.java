package com.dev.cinema.dto;

import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDto {
    private List<Ticket> tickets;
    private User user;
    private LocalDateTime orderDate;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
