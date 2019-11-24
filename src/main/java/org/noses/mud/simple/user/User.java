package org.noses.mud.simple.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.noses.mud.simple.Giveable;
import org.noses.mud.simple.Item;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class User implements Giveable {

    private String name;

    private String description;

    enum gender  {
            MALE,
            FEMALE,
            FLUID;
    };

    List<Item> inventory = new ArrayList<Item>();

    @Override
    public boolean give(Session session, Item item) {
        inventory.add(item);
        return true;
    }

    public String toString() {
        return getName()+", "+getDescription();
    }
}
