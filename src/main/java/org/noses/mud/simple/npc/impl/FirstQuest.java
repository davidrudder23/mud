package org.noses.mud.simple.npc.impl;

import org.noses.mud.simple.Item;
import org.noses.mud.simple.npc.GiveHandler;
import org.noses.mud.simple.npc.NPC;
import org.noses.mud.simple.npc.TextDialogListener;
import org.noses.mud.simple.user.Session;
import org.springframework.util.StringUtils;

import java.util.AbstractMap;
import java.util.Map;

public class FirstQuest extends NPC {
    public FirstQuest() {
        super();

        setShortName("Guide");
        setLongName("The Doorstep Guide");
        setDescription("A wizened old man, holding a glowing scroll.  It looks like he wants something from you.  Try saying \"say to guide quest\"");

        TextDialogListener textDialogListener = new TextDialogListener();
        textDialogListener.setShortName(getShortName());

        textDialogListener.setWhisper(
                Map.ofEntries(
                        new AbstractMap.SimpleEntry<String, String>("quest", "I seem to have lost my pipe.  Bring it to me and I'll open the kingdom to you."),
                        new AbstractMap.SimpleEntry<String, String>("help", "I have lost something important to me.  To find out more about this quest, please enter \"say to guide quest\""),
                        new AbstractMap.SimpleEntry<String, String>("", "I have lost something important to me.  To find out more about this quest, please enter \"whisper to guide quest\"")
                )
        );
        textDialogListener.setTalk(
                Map.ofEntries(
                        new AbstractMap.SimpleEntry<String, String>("quest", "I seem to have lost my pipe.  Bring it to me and I'll open the kingdom to you."),
                        new AbstractMap.SimpleEntry<String, String>("help", "I have lost something important to me.  To find out more about this quest, please enter \"whisper to guide quest\"")
                )
        );

        getDialogListeners().add(textDialogListener);

        GiveHandler giveHandler = new GiveHandler() {
            @Override
            public boolean handleGive(Session session, Item item) {
                if (!StringUtils.isEmpty(item.nameMatchesLine("pipe"))) {
                    session.getIoHandler().sendMessage("The Doorstep Guide says \"thank you for your generosity.  But, before I open the kingdom to you, please ..\"TODO");
                }
                return false;
            }
        };

        getGiveHandlers().add(giveHandler);
    }

}
