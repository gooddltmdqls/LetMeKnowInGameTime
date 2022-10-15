package kr.icetang0123.letmeknowingametime.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.concurrent.atomic.AtomicReference;

public class ClothConfig {
    private final ConfigSerializer serializer = new ConfigSerializer();
    public AtomicReference<Boolean> use24h = new AtomicReference<>(false);

    public Screen getConfigScreen(Screen parent) {
        return getConfigBuilder().setParentScreen(parent).build();
    }

    private ConfigBuilder getConfigBuilder() {
        LMKIGTConfig config = serializer.load();
        ConfigBuilder builder = ConfigBuilder.create();

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        use24h.set(config.use24h);

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.lmkigt.general"));

        general.addEntry(
                entryBuilder
                        .startBooleanToggle(Text.translatable("config.lmkigt.use24h"), use24h.get())
                        .setDefaultValue(false)
                        .setSaveConsumer(use24h::set)
                        .build()
        );

        builder.setSavingRunnable(() -> serializer.save(this));

        return builder;
    }
}
