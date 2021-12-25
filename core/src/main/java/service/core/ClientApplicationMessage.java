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
}
