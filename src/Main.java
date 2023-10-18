import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Player player = new Player("Player 1", 100, 15, 30);
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

            WorldBuilding.buildMap(game_map, roomdata);
            WorldBuilding.createPlayer(player, playerdata, game_map);
            WorldBuilding.createMonsters(monsterdata, game_map, monsters);
            WorldBuilding.createItems(itemdata, game_map, items);
        } catch (IOException e) {
            e.printStackTrace();
        }

        WorldBuilding.introduction(player);

        while(player.getHealth() > 0 && !player.checkWinCondition(monsters)){
            player.act(game_map);
            for(Monster monster : monsters){
                if(monster.vitalityCheck() && player.vitalityCheck() && !monster.isStunned()) {
                    monster.act(player, game_map);
                }
                if(monster.isStunned()){
                    monster.stunProgress();
                }
            }
        }
        if(player.getHealth() <= 0){
            System.out.println("The monsters have won... You have failed");
        } else if(player.checkWinCondition(monsters)){
            System.out.println("You have done it! The monsters have been cleared");
        }
    }
}
