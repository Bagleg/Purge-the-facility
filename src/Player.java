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
        if(monster.getHealth() <= 0){
           monster.die();
        }
    }

    public void battle(Monster monster){
        while(this.getHealth() > 0 && monster.getHealth() > 0){
            this.attack(monster);
            if(monster.getHealth() > 0) {
                monster.attack(this);
            }
            System.out.println("Player's health: " + this.getHealth() + "\n" + "Monster's health: " + monster.getHealth());

        }
        if(this.getHealth() <= 0){
            this.die();
//            prolly some more stuff
        } else if (monster.getHealth() <= 0){
            this.slay();
        }
    }

}
