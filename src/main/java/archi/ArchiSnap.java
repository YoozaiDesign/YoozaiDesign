package archi;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 引入自定义方块注册类与物品组类
import archi.ModBlocks;
import archi.ArchiSnapItemGroup;

/**
 * 主 Mod 类
 * 负责在 Minecraft 启动时注册所有内容。
 */
public class ArchiSnap implements ModInitializer {

    // 模组 ID（在 fabric.mod.json 中保持一致）
    public static final String MOD_ID = "archisnap";

    // 日志记录器
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // 注册方块
        ModBlocks.registerModBlocks();

        // 注册物品组（如创造模式分类）
        ArchiSnapItemGroup.registerItemGroup();

        // 启动日志
        LOGGER.info("ArchiSnap mod has been initialized!");
    }
}

