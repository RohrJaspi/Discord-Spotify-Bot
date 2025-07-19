package dev.rohrjaspi.util;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.rohrjaspi.Save;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonHandler {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public <T> T insertJson(String filePath, T object) {
        String json = gson.toJson(object);
        writeToFile(filePath, json);
        return object;
    }

    private void writeToFile(String filePath, String json) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(json);
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T readJson(String path, Class<T> clazz) {
        File file = new File(path);

        // Create file and parent directory if missing
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // create "data/" if needed
                file.createNewFile();          // create empty file
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("{}");        // write empty JSON object
                }
            }

            try (FileReader reader = new FileReader(file)) {
                return gson.fromJson(reader, clazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
