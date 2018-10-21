package com.leonid.LotteryBot.db;

import com.leonid.LotteryBot.domain.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    @Query("select distinct r from Room r, in(r.users) users join fetch r.users where users.id = (:id)")
    List<Room> findAllForUser(@Param("id") Long userId);

    @Query("select r from Room r join fetch r.users where r.id = (:id)")
    Optional<Room> findByIdEager(@Param("id") Long roomId);

    @Query("select r from Room r join fetch r.users where r.state = (:state)")
    List<Room> findOpenRoomEager(@Param("state") String state);
}
