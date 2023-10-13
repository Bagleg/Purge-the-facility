import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Room {
    private String name;

    private String description = "";
    private List<Item> items = new ArrayList<Item>();

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    private boolean hasMonster = false;

    public String getDescription() {
        return description;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }
}
