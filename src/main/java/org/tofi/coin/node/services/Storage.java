package org.tofi.coin.node.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.autoconfigure.web.servlet.JspTemplateAvailabilityProvider;
import org.tofi.coin.node.configuration.State;
import org.tofi.coin.node.models.Block;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public State loadState() {
        try {
            return objectMapper.readValue(new File("state.json"), State.class);
        } catch (FileNotFoundException e) {
            State state = new State();
            state.setBlocksProceeded(0);
            state.setProceededBlocks(new ArrayList<>());
            saveState(state);
            return state;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveState(final State state) {
        try {
            objectMapper.writeValue(new File("state.json"), state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
