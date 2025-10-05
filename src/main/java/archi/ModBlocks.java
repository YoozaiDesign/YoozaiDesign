package archi;

import archi.ArchiSnap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

/**
 * 管理与注册模组方块。
 * 所有新方块在这里声明、注册，并与物品绑定。
 */
public class ModBlocks {

    public static final Block CONCRETE_BARE = registerBlock("concrete_bare",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));

    public static final Block CONCRETE_BAREX = registerBlock("concrete_barex",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));

    public static final Block LIGHT_BLOCK = registerBlock("light_block",
            new Block(FabricBlockSettings.create()
                    .luminance(14) // 发光亮度（14 = 火把亮度）
                    .noCollision() // 玩家可穿过
                    .nonOpaque()   // 光线可穿透
                    .strength(0.0f) // 任意工具快速破坏
                    .sounds(BlockSoundGroup.GLASS) // 可选声音：玻璃
            ));

    /**
     * 注册所有方块的渲染层
     * 注意：在 Fabric 中可以直接在服务端初始化中调用此函数（简单模组没问题）。
     * 若未来方块较多，可以分出 Client 类处理。
     */
    public static void setupRenderLayers() {
        //BlockRenderLayerMap.INSTANCE.putBlock(LIGHT_BLOCK, RenderLayer.getTranslucent());
    }

    /**
     * 注册方块并自动绑定物品。
     */
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ArchiSnap.MOD_ID, name), block);
    }

    /**
     * 注册方块物品。
     */
    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(ArchiSnap.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    /**
     * 初始化方块注册。
     */
    public static void registerModBlocks() {
        ArchiSnap.LOGGER.info("Registering mod blocks for {}", ArchiSnap.MOD_ID);
        setupRenderLayers();
    }
}
