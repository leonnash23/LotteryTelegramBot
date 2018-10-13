package com.leonid.LotteryBot.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Room extends AbstractEntity {

    public static final String OPEN = "open";
    public static final String CLOSED = "closed";


    public static Room createRoom(User user, double bet) {
        Room room = new Room();
        room.users = new HashSet<>();
        room.users.add(user);
        room.bet = bet;
        room.state = OPEN;
        return room;
    }

    private Room() {
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_in_room",
            inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Set<User> users;

    @Column
    private double bet;

    @Column
    private String state;


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
