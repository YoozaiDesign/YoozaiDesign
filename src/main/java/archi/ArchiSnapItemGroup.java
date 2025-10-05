package archi;

import archi.ModBlocks; // 引入方块类

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * 定义并注册自定义物品组（Creative Tab）
 * 在创造模式中显示模组物品。
 */
public class ArchiSnapItemGroup {

    // 注册一个新的物品组键值（用于标识）
    public static final RegistryKey<ItemGroup> ARCHISNAP_GROUP = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(),
            new Identifier(ArchiSnap.MOD_ID, "archisnap_group")
    );

    /**
     * 注册物品组内容
     */
    public static void registerItemGroup() {
        System.out.println("Registering ArchiSnap item group...");

        Registry.register(
                Registries.ITEM_GROUP,
                ARCHISNAP_GROUP,
                FabricItemGroup.builder()
                        // 设置物品组图标
                        .icon(() -> new ItemStack(ModBlocks.CONCRETE_BARE))
                        // 设置物品组名称（多语言键）
                        .displayName(Text.translatable("itemGroup.archisnap.archisnap_group"))
                        // 添加物品到该物品组
                        .entries((context, entries) -> {
                            entries.add(ModBlocks.CONCRETE_BARE);
                            entries.add(ModBlocks.CONCRETE_BAREX);
                            entries.add(ModBlocks.LIGHT_BLOCK); // 新增 Light Block
                        })
                        .build()
        );
    }
}
