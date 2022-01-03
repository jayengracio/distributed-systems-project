package service.core;

import java.io.Serializable;
import java.util.List;

public class ClientApplicationMessage implements Serializable {
    public long id;
    public Item baseItem;
    public List<GameItem> items;

    public ClientApplicationMessage(long id, Item baseItem, List<GameItem> items) {
        this.id = id;
        this.baseItem = baseItem;
        this.items = items;
    }

    public ClientApplicationMessage() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Item getBaseItem() {
        return baseItem;
    }

    public void setBaseItem(Item baseItem) {
        this.baseItem = baseItem;
    }

    public List<GameItem> getItems() {
        return items;
    }

    public void setItems(List<GameItem> items) {
        this.items = items;
    }
}
