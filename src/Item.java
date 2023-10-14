public class Item {
    private String name;
    private String type;
    private int atkDmg;
    private String desc;
    private Room location;
    public Item(String name, String type, String desc, int atkDmg, Room room) {
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.atkDmg = atkDmg;
        this.location = room;
    }
    public String getName() {
        return name;
    }
    public void setLocation(Room room){
        this.setLocation(room);
    }
    public Room getLocation(){
        return this.location;
    }
}
