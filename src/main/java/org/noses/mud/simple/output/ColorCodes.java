package org.noses.mud.simple.output;

import org.springframework.util.StringUtils;

import java.util.AbstractMap;
import java.util.Map;

public class ColorCodes {

    private static Map<String, String> colors = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, String>("red", "\u001b[31;1m"),
            new AbstractMap.SimpleEntry<String, String>("orange", "9"),
            new AbstractMap.SimpleEntry<String, String>("yellow", "3"),
            new AbstractMap.SimpleEntry<String, String>("green", "2"),
            new AbstractMap.SimpleEntry<String, String>("blue", "4"),
            new AbstractMap.SimpleEntry<String, String>("indigo", "12"),
            new AbstractMap.SimpleEntry<String, String>("violet", "5"),
            new AbstractMap.SimpleEntry<String, String>("white", "\u001b[37;1m"),
            new AbstractMap.SimpleEntry<String, String>("black", "\u001b[30;1m"),
            new AbstractMap.SimpleEntry<String, String>("light_grey", "\u001b[37;m"),
            new AbstractMap.SimpleEntry<String, String>("dark_grey", "\u001b[30;m")
    );

    public static String color(String color) {
        String value = colors.get(color.toLowerCase());
        if (StringUtils.isEmpty(value)) {
            value = colors.get("white");
        }

        return value;
    }
}
