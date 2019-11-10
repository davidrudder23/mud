package org.noses.mud.simple.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class RoomLoader {

    private static RoomLoader instance;
    HashMap<String, Room> rooms;

    private RoomLoader() {
        rooms = new HashMap<String, Room>();
    }

    private void loadAllRooms() throws IOException  {

        ObjectMapper objectMapper = new ObjectMapper();

        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        Resource[] resources = resolver.getResources("classpath*:/rooms/*.json") ;
        for (Resource resource: resources){
            log.info(resource.getFilename());
            Room room = objectMapper.readValue(resource.getInputStream(), Room.class);
            log.info("Loaded class {}", room.getIdentifier());
            rooms.put(room.getIdentifier(), room);
        }
    }

    public static RoomLoader getInstance() {
        if (instance == null) {
            instance = new RoomLoader();
            try {
                instance.loadAllRooms();
            } catch (Exception exc) {
                log.error("Could not load all rooms", exc);
                return null;
            }
        }

        return instance;
    }

    public Room getDefaultRoom() {
        for (Room room: rooms.values()) {
            if (room.isDefault()) {
                return room;
            }
        }

        return null;
    }

    public Room getRoomByIdentifier(String identifier) {
        return rooms.get(identifier);
    }
}
