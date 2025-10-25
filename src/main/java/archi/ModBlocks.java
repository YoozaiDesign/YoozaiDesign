package archi;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

/**
 * 管理与注册模组方块。
 * 所有新方块在这里声明、注册，并与物品绑定。
 */
public class ModBlocks {

    // ===== 原有方块 =====
    public static final Block CONCRETE_BARE = registerBlock("concrete_bare",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));

    public static final Block CONCRETE_BAREX = registerBlock("concrete_barex",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));

    public static final Block LIGHT_BLOCK = registerBlock("light_block",
            new Block(FabricBlockSettings.create()
                    .luminance(14) // 发光亮度（14 = 火把亮度）
                    .noCollision() // 玩家可穿过
                    .nonOpaque()   // 光线可穿透
                    .strength(0.0f)
                    .sounds(BlockSoundGroup.GLASS)
            ));

    // ===== 新增楼梯方块 =====

    // Closedstringer
    public static final Block STAIR_CLOSEDSTRINGER_BROWN =
            registerBlock("stair_closedstringer_brown",
                    new DynamicStairBlock(FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)));
    public static final Block STAIR_CLOSEDSTRINGER_BROWN_IRON =
            registerBlock("stair_closedstringer_brown_iron",
            		new DynamicStairBlock(FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)));

    public static final Block STAIR_CLOSEDSTRINGER_CLEAR =
            registerBlock("stair_closedstringer_clear",
                    new DynamicStairBlock(FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)));
    public static final Block STAIR_CLOSEDSTRINGER_DARK =
            registerBlock("stair_closedstringer_dark",
                    new DynamicStairBlock(FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)));
    public static final Block STAIR_CLOSEDSTRINGER_IRON =
            registerBlock("stair_closedstringer_iron",
                    new DynamicStairBlock(FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)));
    public static final Block STAIR_CLOSEDSTRINGER_NATURAL =
            registerBlock("stair_closedstringer_natural",
                    new DynamicStairBlock(FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)));
    public static final Block STAIR_CLOSEDSTRINGER_SEADRIFT =
            registerBlock("stair_closedstringer_seadrift",
                    new DynamicStairBlock(FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)));
    public static final Block STAIR_CLOSEDSTRINGER_WHITE =
            registerBlock("stair_closedstringer_white",
                    new DynamicStairBlock(FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)));

    // Monostringer
    public static final Block STAIR_MONOSTRINGER_BROWN = registerMonostringer("stair_monostringer_brown");
    public static final Block STAIR_MONOSTRINGER_BROWN_IRON = registerMonostringer("stair_monostringer_brown_iron");
    public static final Block STAIR_MONOSTRINGER_CLEAR = registerMonostringer("stair_monostringer_clear");
    public static final Block STAIR_MONOSTRINGER_DARK = registerMonostringer("stair_monostringer_dark");
    public static final Block STAIR_MONOSTRINGER_IRON = registerMonostringer("stair_monostringer_iron");
    public static final Block STAIR_MONOSTRINGER_NATURAL = registerMonostringer("stair_monostringer_natural");
    public static final Block STAIR_MONOSTRINGER_SEADRIFT = registerMonostringer("stair_monostringer_seadrift");
    public static final Block STAIR_MONOSTRINGER_WHITE = registerMonostringer("stair_monostringer_white");


    /**
     * 注册普通方块（自动生成 BlockItem）
     */
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ArchiSnap.MOD_ID, name), block);
    }

    /**
     * Registers monostringer stairs with the custom collision shape.
     */
    private static Block registerMonostringer(String name) {
        return registerBlock(name, new MonostringerStairBlock(FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)));
    }

    /**
     * 注册方块物品
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

    /**
     * 渲染层设置（保留原逻辑）
     */
    public static void setupRenderLayers() {
        //BlockRenderLayerMap.INSTANCE.putBlock(LIGHT_BLOCK, RenderLayer.getTranslucent());
    }
}
