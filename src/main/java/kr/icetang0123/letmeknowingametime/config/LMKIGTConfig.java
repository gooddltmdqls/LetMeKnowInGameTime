package kr.icetang0123.letmeknowingametime.config;

import java.util.HashMap;
import java.util.Map;

public abstract class LMKIGTConfig {
    public Boolean use24h;

    public Map<String, Object> serialize() {
        return new HashMap<>();
    }

    public static LMKIGTConfig deserialize(Map<String, Object> serialized) {
        LMKIGTConfig res = new LMKIGTConfigImpl();

        res.use24h = (Boolean) serialized.get("use24h");

        return res;
    }
}

