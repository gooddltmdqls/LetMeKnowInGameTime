package kr.icetang0123.letmeknowingametime.config;

import java.util.HashMap;
import java.util.Map;

public class LMKIGTConfigImpl extends LMKIGTConfig {
    public Boolean use24h;

    public LMKIGTConfigImpl() {
        this.use24h = false;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("use24h", use24h);

        return map;
    }
}
