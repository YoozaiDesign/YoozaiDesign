package archi;

import archi.ModBlocks;

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

    // 原有物品组
    public static final RegistryKey<ItemGroup> ARCHISNAP_GROUP = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(),
            new Identifier(ArchiSnap.MOD_ID, "archisnap_group")
    );

    /**
     * 注册物品组内容
     */
    public static void registerItemGroup() {
        System.out.println("Registering ArchiSnap item groups...");

        // 原有物品组注册
        Registry.register(
                Registries.ITEM_GROUP,
                ARCHISNAP_GROUP,
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(ModBlocks.CONCRETE_BARE))
                        .displayName(Text.translatable("itemGroup.archisnap.archisnap_group"))
                        .entries((context, entries) -> {
                            entries.add(ModBlocks.CONCRETE_BARE);
                            entries.add(ModBlocks.CONCRETE_BAREX);
                            entries.add(ModBlocks.LIGHT_BLOCK);
                        })
                        .build()
        );

        // 新增的 Cocricot 楼梯物品组
        Registry.register(
                Registries.ITEM_GROUP,
                new Identifier(ArchiSnap.MOD_ID, "cocricotstairs"),
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(ModBlocks.STAIR_CLOSEDSTRINGER_BROWN))
                        .displayName(Text.translatable("itemGroup.archisnap.cocricotstairs"))
                        .entries((context, entries) -> {
                            // Closedstringer stairs
                            entries.add(ModBlocks.STAIR_CLOSEDSTRINGER_BROWN);
                            entries.add(ModBlocks.STAIR_CLOSEDSTRINGER_BROWN_IRON);
                            entries.add(ModBlocks.STAIR_CLOSEDSTRINGER_CLEAR);
                            entries.add(ModBlocks.STAIR_CLOSEDSTRINGER_DARK);
                            entries.add(ModBlocks.STAIR_CLOSEDSTRINGER_IRON);
                            entries.add(ModBlocks.STAIR_CLOSEDSTRINGER_NATURAL);
                            entries.add(ModBlocks.STAIR_CLOSEDSTRINGER_SEADRIFT);
                            entries.add(ModBlocks.STAIR_CLOSEDSTRINGER_WHITE);

                            // Monostringer stairs
                            entries.add(ModBlocks.STAIR_MONOSTRINGER_BROWN);
                            entries.add(ModBlocks.STAIR_MONOSTRINGER_BROWN_IRON);
                            entries.add(ModBlocks.STAIR_MONOSTRINGER_CLEAR);
                            entries.add(ModBlocks.STAIR_MONOSTRINGER_DARK);
                            entries.add(ModBlocks.STAIR_MONOSTRINGER_IRON);
                            entries.add(ModBlocks.STAIR_MONOSTRINGER_NATURAL);
                            entries.add(ModBlocks.STAIR_MONOSTRINGER_SEADRIFT);
                            entries.add(ModBlocks.STAIR_MONOSTRINGER_WHITE);
                        })
                        .build()
        );
    }
}
