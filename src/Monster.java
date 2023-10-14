import java.util.Map;
import java.util.Random;

public class Monster extends Creature{
    private String type;
    private String temperament;
    private String description;

    public Monster(String temperament, Room room, int hp, int def, int atk){
        this.temperament = temperament;
        this.setLocation(room);
        this.setHealth(hp);
        this.setDef(def);
        this.setAtk(atk);

    }
    public void setTemperament(String temperament){
        this.temperament = temperament;
    }
    public void act(Player player, GameMap game_map){
        if(this.temperament.equalsIgnoreCase("aggressive")){
            if(player.getLocation() == this.getLocation()){
                System.out.println("Monster attacks");
                this.battle(player);
            } else if(canHuntPlayer(game_map, player)){
                this.setLocation(player.getLocation());
                System.out.println("Monster has moved to " + this.getLocation().getName());
                this.battle(player);
            }
        }
    }
    public void attack(Player player){
        Random random = new Random();
        int atkdmg = random.nextInt(this.getAtk());
        player.setHealth(atkdmg);
        System.out.println("The monster has struck you for " + atkdmg + "!");
        if(player.getHealth() <= 0){
            player.die();
        }
    }
    public void battle(Player player){
        while(this.getHealth() > 0 && player.getHealth() > 0){
            System.out.println("\n -=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
            this.attack(player);
            if(player.vitalityCheck()){
                player.attack(this);
                System.out.println("\n -=-=-=-=-=-=-=-=-=-=-=-=-=-=- \n");
            } else {
                System.out.println("\n -=-=-=-=-=-=-=-=-=-=-=-=-=-=- \n");
            }
            System.out.println("Player's health: " +player.getHealth() + "\n" + "Monster's health: " + this.getHealth());
            System.out.println("\n -=-=-=-=-=-=-=-=-=-=-=-=-=-=- \n");

        }
        if(!this.vitalityCheck()){
            player.slay();
        }
    }
    public boolean canHuntPlayer(GameMap gameMap, Player player){
        for(Map.Entry<String, Room> entry : gameMap.getExits(player.getLocation()).entrySet()) {
            if (this.getLocation() == entry.getValue()) {
                return true;
            }
        }
        return false;
    }

}
