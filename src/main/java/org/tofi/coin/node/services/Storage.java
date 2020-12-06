package org.tofi.coin.node.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.tofi.coin.node.configuration.State;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Storage {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public State loadState() {
//        try {
//            return objectMapper.readValue(new File("state.json"), State.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return null;
    }

    public void saveState(final State state) {

    }
}
