package org.noses.mud.simple.npc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.noses.mud.simple.room.Room;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TextDialogNPCLoader {

    private static TextDialogNPCLoader instance;

    private Map<String, NPC> npcs;

    public TextDialogNPCLoader() {
        npcs = new HashMap<String, NPC>();
    }

    public static TextDialogNPCLoader getInstance() {
        if (instance == null) {
            instance = new TextDialogNPCLoader();
            try {
                instance.loadAllTextDialogNPCs();
            } catch (Exception exc) {
                log.warn("Could not load text dialog NPCs", exc);
                instance = null;
            }
        }
        return instance;
    }

    private void loadAllTextDialogNPCs() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        Resource[] resources = resolver.getResources("classpath*:/text_dialog_handlers/*.json") ;
        for (Resource resource: resources){
            log.info(resource.getFilename());
            TextDialogListener textDialogListener = objectMapper.readValue(resource.getInputStream(), TextDialogListener.class);
            log.info("Loaded class {}", textDialogListener.getIdentifier());

            NPC npc = new NPC(textDialogListener);
            npc.setShortName(textDialogListener.getShortName());
            npc.setLongName(textDialogListener.getLongName());
            npc.setDescription(textDialogListener.getDescription());
            npcs.put(textDialogListener.getIdentifier(), npc);
        }
    }

    public NPC getNPCByIdentifier(String identifier) {
        return npcs.get(identifier);
    }
}
