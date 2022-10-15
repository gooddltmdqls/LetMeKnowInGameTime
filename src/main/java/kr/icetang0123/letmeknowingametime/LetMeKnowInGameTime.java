package kr.icetang0123.letmeknowingametime;

import kr.icetang0123.letmeknowingametime.config.ConfigSerializer;
import kr.icetang0123.letmeknowingametime.config.LMKIGTConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.JTextComponent;

@Environment(EnvType.CLIENT)
public class LetMeKnowInGameTime implements ClientModInitializer {
    private static final ConfigSerializer serializer = new ConfigSerializer();
    public static final Logger LOGGER = LoggerFactory.getLogger(LetMeKnowInGameTime.class);
    public static final KeyBinding showTimeKeybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.lmkigt.showTime",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            "category.lmkigt.general"
    ));

    @Override
    public void onInitializeClient() {
        LOGGER.info("LetMeKnowInGameTime have been enabled");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            LMKIGTConfig config = serializer.load();

            while (showTimeKeybinding.wasPressed()) {
                assert client.player != null;
                assert client.world != null;

                int tick = (int) (client.world.getTimeOfDay() % 24000L);
                double allMinutes = tick * 0.06;
                boolean use24h = config.use24h;
                int hours = (int) (allMinutes / 60);
                int minutes = (int) (allMinutes % 60);
                String pmText = "";

                hours -= minutes / 60;

                hours += 6;

                boolean isPM = hours > 12 && hours < 24;

                if (!use24h && isPM) hours -= 12;
                if (hours >= 24) hours -= 24;
                if (!use24h && hours == 0) hours += 12;

                if (!use24h && isPM) pmText = "PM ";
                else if (!use24h) pmText = "AM ";

                String hourString = String.valueOf(hours);
                String minuteString = String.valueOf(minutes);

                if (hourString.length() == 1) hourString = "0".concat(hourString);
                if (minuteString.length() == 1) minuteString = "0".concat(minuteString);

                client.player.sendMessage(Text.literal(pmText).append(Text.of(hourString).copy().formatted(Formatting.YELLOW).append(Text.literal(":").formatted(Formatting.GOLD).append(Text.of(minuteString).copy().formatted(Formatting.YELLOW)))), true);
            }
        });
    }
}
