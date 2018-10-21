package com.leonid.LotteryBot.service;

import com.leonid.LotteryBot.db.RoomRepository;
import com.leonid.LotteryBot.domain.Room;
import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.exception.AlreadyOnRoomException;
import com.leonid.LotteryBot.exception.FullRoomException;
import com.leonid.LotteryBot.exception.NotOpenRoomException;
import com.leonid.LotteryBot.exception.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class RoomService extends AbstractService {

    @Value("${roomCapacity}")
    private int roomCapacity;
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, EntityManagerFactory factory) {
        super(factory);
        this.roomRepository = roomRepository;
    }

    public void createAndSave(User user, double bet) {
        roomRepository.save(Room.createRoom(user, bet));
    }

    public List<Room> getOpenRoom() {
        return roomRepository.findOpenRoomEager(Room.OPEN);
    }

    public List<Room> getUserRooms(User user) {
        return roomRepository.findAllForUser(user.getId());
    }

    public void addUserToRoomById(User user, Long roomId) throws FullRoomException,
            RoomNotFoundException,
            NotOpenRoomException,
            AlreadyOnRoomException {
        Optional<Room> roomOptional = roomRepository.findByIdEager(roomId);
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
        if (users.size() == roomCapacity) {
            room.setState(Room.CLOSED);
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
        Optional<Room> roomOptional = roomRepository.findByIdEager(roomId);
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
