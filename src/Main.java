import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Player player = new Player("Player 1", 100, 15, 100);
        ArrayList<Monster> monsters = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();
        GameMap game_map = new GameMap();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<?, ?> data = objectMapper.readValue(new File("PurgeTheFacility/game2.json"), Map.class);
            Map<?, ?> playerdata = (Map<?, ?>) data.get("player");
            List<?> monsterdata = (List<?>) data.get("monster");
            List<?> roomdata = (List<?>) data.get("room");
            List<?> itemdata = (List <?>) data.get("item");
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

//            Checking the entire game map in readable way
//            for (Object room : roomdata) {
//                Map<?, ?> one_room_data = (Map<?, ?>) room;
//                String room_name = (String) one_room_data.get("name");
//                System.out.println("Room Name: " + room_name);
//
//                Map<?, ?> room_exits = (Map<?, ?>) one_room_data.get("exits");
//
//                for (Map.Entry<?, ?> entry : room_exits.entrySet()) {
//                    String exitDirection = (String) entry.getKey();
//                    String exitRoomName = (String) entry.getValue();
//                    System.out.println("Exit " + exitDirection + " leads to " + exitRoomName);
//                }
//            }

            player.setName((String) playerdata.get("name"));
            player.setLocation(game_map.getRoomByName((String) playerdata.get("location")));

            for(Object monster : monsterdata){
                Map<?,?> one_monster = (Map <?,?>) monster;
                String temperament = (String) one_monster.get("temperament");
                Room location = game_map.getRoomByName((String) one_monster.get("room"));
                int hp = (Integer) one_monster.get("hp");
                int atk = (Integer) one_monster.get("atk");
                int def = (Integer) one_monster.get("def");
                Monster m = new Monster(temperament, location, hp, def, atk);
                monsters.add(m);
            }

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
        } catch (IOException e) {
            e.printStackTrace();
        }


//        System.out.println(items);
//        check condition
        while(player.getHealth() > 0 && !player.checkWinCondition(monsters)){
            player.act(game_map);
            for(Monster monster : monsters){
                if(monster.vitalityCheck() && player.vitalityCheck()) {
                    monster.act(player, game_map);
                }
            }
        }
        if(player.getHealth() <= 0){
            System.out.println("The monsters have won! You have failed");
        } else if(player.checkWinCondition(monsters)){
            System.out.println("You have done it! The monsters are gone now!");
        }
    }
}
