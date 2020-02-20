package com.dev.cinema.dto;

import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import java.util.List;

public class ShoppingCartResponseDto {
    private List<Ticket> tickets;
    private User user;

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
}
