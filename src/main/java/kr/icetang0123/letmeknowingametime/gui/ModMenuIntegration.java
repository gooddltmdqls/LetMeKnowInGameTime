package kr.icetang0123.letmeknowingametime.gui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import kr.icetang0123.letmeknowingametime.config.ClothConfig;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new ClothConfig().getConfigScreen(parent);
    }
}
