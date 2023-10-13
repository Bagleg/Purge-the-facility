import java.util.ArrayList;
import java.util.List;
public class Action {

    public static void move(Player player, GameMap game_map, String direction){

        Room room = game_map.getExit(player.getLocation(), direction);
        
        if(room != null){
            player.setLocation(room);
            System.out.println("You have entered "+ player.getLocation().getName() + "\n" + player.getLocation().getDescription());
        }
    }

    public static void checkWinCondition(ArrayList<Monster> monsters){
        int count = 0;
        for(Monster monster : monsters){
            if(!monster.vitalityCheck()){
                count++;
            }
        }
    }
//    not necessary?
    public static void addAll(Player player, Room room){
        List<Item> itemList = room.getItems();
        for(int i = 0; i < itemList.size(); i++){
            player.addItem(itemList.get(i));
            room.removeItem(itemList.get(i));
        }
    }
//
    public static void pickUp(Player player, Room room, Item item){
        List<Item> itemList = room.getItems();
        for(int i = 0; i < itemList.size(); i++){
            if(item == itemList.get(i)){
                player.addItem(itemList.get(i));
                room.removeItem(itemList.get(i));
            }
        }
    }

    public static void drop(Player player, Room room, Item item){
        for(int i = 0; i< player.getInventory().size(); i++) {
            if(item == player.getInventory().get(i)) {
                player.removeItem(item);
                room.addItem(item);
            }
        }
    }
}
