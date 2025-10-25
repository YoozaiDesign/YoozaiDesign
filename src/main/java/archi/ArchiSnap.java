package archi;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchiSnap implements ModInitializer {

    public static final String MOD_ID = "archisnap";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing ArchiSnap Mod...");

        // 注册方块等内容。注意：ModBlocks 与 ArchiSnapItemGroup 应与本文件同包（package archi）
        ModBlocks.registerModBlocks();
        ArchiSnapItemGroup.registerItemGroup();

        LOGGER.info("ArchiSnap initialization complete.");
    }
}
