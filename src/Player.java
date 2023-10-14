import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Player extends Creature{
    private List<Item> inventory = new ArrayList<Item>();
    private int monstersSlain = 0;
    public Player(String name, int hp, int def, int atk) {
        this.setName(name);
        this.setHealth(hp);
        this.setDef(def);
        this.setAtk(atk);
    }
    public void addItem(Item item) {
        inventory.add(item);
    }
    public void removeItem(Item item) {
        inventory.remove(item);
    }
    public List<Item> getInventory() {
        return inventory;
    }
    public void slay(){
        this.monstersSlain++;
    }
    public int getMonstersSlain(){
        return this.monstersSlain;
    }
    public boolean checkWinCondition(ArrayList<Monster> monsters){
        if(this.getMonstersSlain() == monsters.size()){
            return true;
        } else{
            return false;
        }
    }
    public void act(GameMap game_map){
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter an action: ");
        String input = stdin.nextLine();

        if(input.equalsIgnoreCase("move")){
            System.out.print("Please pick a direction: ");
            String direction = stdin.nextLine();
            Action.move(this, game_map, direction);
        } else{

        }
    }

    public void attack(Monster monster){
        Random random = new Random();
        int atkdmg = random.nextInt(this.getAtk());
        monster.takeDamage(atkdmg);
        System.out.println("You have struck the monster for " + atkdmg + "!");
        if(monster.getHealth() <= 0){
           monster.die();
        }
    }

    public void battle(Monster monster){
        while(this.getHealth() > 0 && monster.getHealth() > 0){
            System.out.println("\n -=-=-=-=-=-=-=-=-=-=-=-=-=-=- \n");
            this.attack(monster);
            if(monster.getHealth() > 0) {
                monster.attack(this);
                System.out.println("\n -=-=-=-=-=-=-=-=-=-=-=-=-=-=- \n");

            } else{
                System.out.println("\n -=-=-=-=-=-=-=-=-=-=-=-=-=-=- \n");
            }
            System.out.println("Player's health: " + this.getHealth() + "\n" + "Monster's health: " + monster.getHealth());
            System.out.println("\n -=-=-=-=-=-=-=-=-=-=-=-=-=-=- \n");
        }
        if(this.getHealth() <= 0){
            this.die();
        } else if (monster.getHealth() <= 0){
            this.slay();
        }
    }

}
