import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorldBuilding {
    public static void buildMap(GameMap game_map, List<?> roomdata){
        for(Object room: roomdata) {
            Map<?, ?> one_room_data = (Map<?, ?>) room;
            String room_name = (String) one_room_data.get("name");
            String room_desc = (String) one_room_data.get("description");
            Room r = new Room(room_name, room_desc);
            game_map.addRoom(r);
        }

        for(Object room: roomdata){
            Map<?, ?> one_room_data = (Map<?, ?>) room;
            String room_name = (String) one_room_data.get("name");
            Map<?, ?> room_exits = (Map<?, ?>) one_room_data.get("exits");

            for(Map.Entry<?,?> entry: room_exits.entrySet()) {
                game_map.addExit(game_map.getRoomByName(room_name),
                        (String) entry.getKey(),
                        game_map.getRoomByName((String) entry.getValue())
                );
            }
        }
    }

    public static void viewMap(List<?> roomdata){
        for (Object room : roomdata) {
            Map<?, ?> one_room_data = (Map<?, ?>) room;
            String room_name = (String) one_room_data.get("name");
            System.out.println("Room Name: " + room_name);

            Map<?, ?> room_exits = (Map<?, ?>) one_room_data.get("exits");

            for (Map.Entry<?, ?> entry : room_exits.entrySet()) {
                String exitDirection = (String) entry.getKey();
                String exitRoomName = (String) entry.getValue();
                System.out.println("Exit " + exitDirection + " leads to " + exitRoomName);
            }
        }

    }
    public static void createPlayer(Player player, Map<?,?> playerdata, GameMap game_map){
        player.setName((String) playerdata.get("name"));
        player.setLocation(game_map.getRoomByName((String) playerdata.get("location")));
    }

    public static void createMonsters(List<?> monsterdata, GameMap game_map, ArrayList<Monster> monsters){
        for(Object monster : monsterdata){
            Map<?,?> one_monster = (Map <?,?>) monster;
            String name = (String) one_monster.get("name");
            String temperament = (String) one_monster.get("temperament");
            Room location = game_map.getRoomByName((String) one_monster.get("room"));
            int hp = (Integer) one_monster.get("hp");
            int atk = (Integer) one_monster.get("atk");
            int def = (Integer) one_monster.get("def");
            Monster m = new Monster(name, temperament, location, hp, def, atk);
            monsters.add(m);
        }
    }
    public static void createItems(List<?> itemdata, GameMap game_map, ArrayList<Item> items){
        for(Object item : itemdata){
            Map<?,?> one_item = (Map<?, ?>) item;
            String name = (String) one_item.get("name");
            String type = (String) one_item.get("type");
            String desc = (String) one_item.get("description");
            int atkDmg = (Integer) one_item.get("attackDamage");
            Room location = game_map.getRoomByName((String) one_item.get("location"));
            Item i = new Item(name, type, desc, atkDmg, location);
            location.addItem(i);
            items.add(i);
        }
    }
    public static void introduction(Player player){
//        include a description of the building with a name, maybe. Add to json?
        System.out.println("You have been tasked to find all of the monsters in this abandoned place and defeat them");
        System.out.println("You enter the building.");
        System.out.println(player.getLocation().getDescription());
    }
}
