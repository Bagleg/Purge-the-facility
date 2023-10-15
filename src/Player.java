import java.util.*;

public class Player extends Creature{
    private List<Item> inventory = new ArrayList<Item>();
    private List<Item> equipment = new ArrayList<>();
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
public void pickUp(Room room) {
    List<Item> items = room.getItems();
    if (items != null) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            this.addItem(item);
            System.out.println("You have found a " + item.getName() + "!");
            iterator.remove();  // Remove the item using the iterator
        }
    } else {
        System.out.println("You spend your time looking, but find no items.");
    }
}

    public void equip(){
        Scanner stdin = new Scanner(System.in);
        boolean success = false;
        String input = "";
        while(!success && !input.equalsIgnoreCase("Stop")) {
            System.out.println("What item would you like to equip? See your items below: \n");
            for(Item item : this.getInventory()){
                System.out.println(item.getName());
            }
            System.out.print("\nPlease select an item: ");
            input = stdin.nextLine();
            Iterator<Item> iterator = inventory.iterator();
            while(iterator.hasNext()){
                Item item = iterator.next();
                if (input.equalsIgnoreCase(item.getName()) && item.getType().equalsIgnoreCase("weapon")) {
                    this.setAtk(item.getAtkDmg());
                    success = true;
                    equipment.add(item);
                    iterator.remove();
                }
            }
            if(!success) {
                System.out.println("Item not found. Say stop to give up this turn.");
            }
        }
    }
    public void act(GameMap game_map){
        Scanner stdin = new Scanner(System.in);
        boolean acted = false;
        while(acted == false) {
            System.out.print("Enter an action: ");
            String input = stdin.nextLine();
            if (input.equalsIgnoreCase("move")) {
                System.out.print("Please pick a direction: ");
                String direction = stdin.nextLine();
                Action.move(this, game_map, direction);
                acted = true;
            } else if (input.equalsIgnoreCase("loot")) {
                this.pickUp(this.getLocation());
                acted = true;
            } else if(input.equalsIgnoreCase("equip")){
                this.equip();
            }else if(input.equalsIgnoreCase("idle")){
                System.out.println("You waited around for a while...");
                acted = true;
            }
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
