package com.leonid.LotteryBot.service;

import com.leonid.LotteryBot.db.RoomRepository;
import com.leonid.LotteryBot.domain.Room;
import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.exception.AlreadyOnRoomException;
import com.leonid.LotteryBot.exception.FullRoomException;
import com.leonid.LotteryBot.exception.NotOpenRoomException;
import com.leonid.LotteryBot.exception.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void createAndSave(User user, double bet) {
        roomRepository.save(Room.createRoom(user, bet));
    }

    public List<Room> getOpenRoom() {
        Iterable<Room> rooms = roomRepository.findAll();
        List<Room> openRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getState().equals(Room.OPEN)) {
                openRooms.add(room);
            }
        }
        return openRooms;
    }

    public List<Room> getUserRooms(User user) {
        Iterable<Room> rooms = roomRepository.findAll();
        List<Room> userRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getUsers().contains(user)) {
                userRooms.add(room);
            }
        }
        return userRooms;
    }

    public void addUserToRoomById(User user, Long roomId) throws FullRoomException,
            RoomNotFoundException,
            NotOpenRoomException,
            AlreadyOnRoomException {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (!roomOptional.isPresent()) {
            throw new RoomNotFoundException();
        }
        Room room = roomOptional.get();
        if (isRoomNotOpen(room)) {
            throw new NotOpenRoomException();
        }
        Set<User> users = room.getUsers();
        if (users.contains(user)) {
            throw new AlreadyOnRoomException();
        }
        checkRoomFull(users);
        users.add(user);
        if (users.size() == 6) {
            room.setState(Room.CLOSED);
            //todo make lottery
        }
        roomRepository.save(room);
    }

    private boolean isRoomNotOpen(Room room) {
        return !room.getState().equals(Room.OPEN);
    }

    private void checkRoomFull(Set<User> users) throws FullRoomException {
        if (users.size() > 6) {
            throw new FullRoomException();
        }
    }

    public void removeUserFromRoomByIf(User user, Long roomId) throws RoomNotFoundException {
        List<Room> userRooms = getUserRooms(user);
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (!roomOptional.isPresent()) {
            throw new RoomNotFoundException();
        }
        Room room = roomOptional.get();
        if (!userRooms.contains(room)) {
            //todo error
            return;
        }
        room.getUsers().remove(user);
        if (room.getUsers().size() == 0) {
            room.setState(Room.CLOSED);
        }
        roomRepository.save(room);
    }
}
