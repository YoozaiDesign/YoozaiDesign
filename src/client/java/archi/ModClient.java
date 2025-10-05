package archi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import archi.ModBlocks;

/**
 * 客户端专用类：用于注册渲染层、贴图、模型等视觉内容。
 * 注意：本类只会在 Minecraft 客户端加载，不会在服务端执行。
 */
public class ModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // 为 Light Block 注册半透明渲染层
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_BLOCK, RenderLayer.getTranslucent());
    }
}
