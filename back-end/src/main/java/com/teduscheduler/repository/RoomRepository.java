package com.teduscheduler.repository;

import com.teduscheduler.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

    Room findRoomByRoomName(String roomName);

}
