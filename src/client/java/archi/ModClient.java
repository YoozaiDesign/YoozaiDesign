package archi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import archi.client.StairDynamicModel;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.util.Identifier;

/**
 * Client entrypoint for render layer setup and dynamic stair models.
 */
public class ModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_BLOCK, RenderLayer.getTranslucent());

        ModelLoadingPlugin.register(pluginContext -> pluginContext.modifyModelAfterBake().register((originalModel, context) -> {
            Identifier modelId = context.id();
            if ("archisnap".equals(modelId.getNamespace())
                    && modelId.getPath().startsWith("block/stair_")
                    && !modelId.getPath().contains("stair_closedstringer_")) {
                return new StairDynamicModel(originalModel);
            }
            return originalModel;
        }));

        System.out.println("[ArchiClient] Render layers and dynamic stair models configured.");
    }
}

