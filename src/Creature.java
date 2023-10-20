import java.util.Random;

public class Creature {
    private String name;
    private int atk;
    private int hp;
    private int def;
    private boolean isAlive = true;
    private Room location;
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAtk(int atk){
        this.atk = atk;
    }
    public int getAtk(){
        return this.atk;
    }
    public Room getLocation(){
        return location;
    }
    public void setLocation(Room room){
        location = room;
    }
    public int getDef(){
        return this.def;
    }
    public void setDef(int def){
        this.def = def;
    }
    public int getHealth() {
        return hp;
    }
    public void setHealth(int hp){
        this.hp = hp;
    }
    public void takeDamage(int dmg){
        this.hp -= dmg;
    }
    public void heal(int heal){
        this.hp += heal;
    }
    public void die(){
        this.isAlive = false;
    }
    public boolean vitalityCheck(){
        return isAlive;
    }
}
