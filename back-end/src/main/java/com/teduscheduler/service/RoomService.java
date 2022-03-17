package com.teduscheduler.service;

import com.teduscheduler.model.Room;
import com.teduscheduler.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;


    public Room save(Room room){
        Room dbRoom = roomRepository.findRoomByRoomName(room.getRoomName());

        if (dbRoom != null) {
            return dbRoom;
        }

        return roomRepository.save(room);
    }
}
