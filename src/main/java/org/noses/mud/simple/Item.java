package org.noses.mud.simple;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Item {

    List<String> names = new ArrayList<String>();
    String description;

    public String nameMatchesLine(String line) {
        for (String name: names) {
            if (line.toLowerCase().startsWith(name)) {
                return name;
            }
        }

        return null;
    }

    public String getName() {
        if ((names == null) || (names.size()==0)) {
            return "unknown";
        }
        return names.get(0);
    }
}
