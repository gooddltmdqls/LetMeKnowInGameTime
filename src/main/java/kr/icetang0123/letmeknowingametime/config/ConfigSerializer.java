package kr.icetang0123.letmeknowingametime.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ConfigSerializer {
    public LMKIGTConfig load() {
        try {
            File configFile = new File(Paths.get(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), "config", "lmkigt.json").toUri());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            if (!configFile.exists()) {
                if (configFile.createNewFile())
                    Files.writeString(configFile.toPath(), gson.toJson(getDefaultConfig().serialize()));
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8));
            StringBuilder file = new StringBuilder();

            String line = null;
            do {
                if (line == null) continue;
                file.append("\n");
                file.append(line);
            } while ((line = br.readLine()) != null);

            br.close();

            Map<String, Object> map = gson.fromJson(file.toString(), Map.class);

            return LMKIGTConfig.deserialize(map);
        } catch (IOException ignored) {}

        return getDefaultConfig();
    }

    public void save(ClothConfig values) {
        Map<String, Object> file = new HashMap<>();
        GsonBuilder gson = new GsonBuilder()
                .setPrettyPrinting();

        file.put("use24h", values.use24h.get());

        try {
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(Paths.get(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), "config", "lmkigt.json").toUri())), StandardCharsets.UTF_8));

            br.write(gson.create().toJson(file).toCharArray());

            br.close();
        } catch (IOException ignored) {}
    }

    private LMKIGTConfig getDefaultConfig() {
        return new LMKIGTConfigImpl();
    }
}
