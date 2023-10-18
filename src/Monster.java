import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Monster extends Creature{
    private String type;
    private String temperament;
    private String description;
    private boolean stunned = false;
    private int stunCount = 0;


    public Monster(String name, String temperament, Room room, int hp, int def, int atk){
        this.setName(name);
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
//        make switch statement when more temperaments are added
        if(this.temperament.equalsIgnoreCase("aggressive")){
            if(player.getLocation() == this.getLocation()){
                System.out.println("Monster attacks");
                this.battle(player);
            } else if(canHuntPlayer(game_map, player)){
                this.setLocation(player.getLocation());
                System.out.println(this.getName() + " has moved to " + this.getLocation().getName());
                this.battle(player);
            }
        }
    }
    public boolean isStunned(){
        return this.stunned;
    }
    public void stunProgress(){
        stunCount++;
        if(stunCount == 2){
            stunCount = 0;
            stunned = false;
        }
    }
    public void attack(Player player){
        Random random = new Random();
        int atkdmg = random.nextInt(this.getAtk());
        player.takeDamage(atkdmg);
        System.out.println(this.getName() + " has struck you for " + atkdmg + "!");
        if(player.getHealth() <= 0){
            player.die();
        }
    }
    public void battle(Player player){
        Scanner stdin = new Scanner(System.in);
        Random random = new Random();
        boolean ran = false;
        System.out.println("You are faced with " + this.getName());
        while(this.getHealth() > 0 && player.getHealth() > 0 && !ran){
            System.out.println("\n -=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
            this.attack(player);
            if(player.vitalityCheck()){
                System.out.print("Please choose an action: ");
                String action = stdin.nextLine();
                if(action.equalsIgnoreCase("attack")) {
                    player.attack(this);
                } else if(action.equalsIgnoreCase("run")){
                    int chance = random.nextInt(2) + 1;
                    switch(chance){
                        case 1:
                            System.out.println("You try to drop a smoke bomb, but it bounces on the floor and knocks you down without exploding");
                            break;
                        case 2:
                            System.out.println("You have dropped a smoke bomb, and the monster is stunned");
                            ran = true;
                            this.stunned = true;
                            break;
                    }
                } else if(action.equalsIgnoreCase("item")){
                    // set up health potions and other item types.
                }
                System.out.println("\n -=-=-=-=-=-=-=-=-=-=-=-=-=-=- \n");
            } else {
                System.out.println("\n -=-=-=-=-=-=-=-=-=-=-=-=-=-=- \n");
            }
            System.out.println("Player's health: " +player.getHealth() + "\n" + this.getName() + "'s health: " + this.getHealth());
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
