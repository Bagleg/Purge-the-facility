import java.util.HashMap;
import java.util.Map;

public class GameMap {
    private Map<Room, Map<String, Room>> game_map = new HashMap<Room, Map<String, Room>>();

    public  Map<Room, Map<String, Room>> getGame_map(){
        return game_map;
    }

    public Room getRoomByName(String room){
        for(Map.Entry<Room,Map<String, Room>> entry : game_map.entrySet()){
            if (room.equals(entry.getKey().getName())) {
                return entry.getKey();
            }
        }
        System.out.println("Invalid Search. Please try again. Room(s) are null!");
        return null;
    }
    public void addRoom(Room room) {
        game_map.put(room, new HashMap<String, Room>());
    }

    public void addExit(Room room, String direction, Room exit) {
        game_map.get(room).put(direction, exit);
    }

    public Room getExit(Room room, String direction) {
        return game_map.get(room).get(direction);
    }

    public Map<String, Room> getExits(Room room){
        return game_map.get(room);
    }
}

