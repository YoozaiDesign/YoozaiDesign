package archi.client;

import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class StairDynamicModel implements BakedModel, FabricBakedModel {

    private final BakedModel base;

    public StairDynamicModel(BakedModel base) {
        this.base = base;
    }

    @Override
    public void emitBlockQuads(BlockRenderView world, BlockState state, BlockPos pos,
                               Supplier<Random> rng, RenderContext ctx) {
        var renderer = RendererAccess.INSTANCE.getRenderer();
        if (renderer == null || base == null) return;

        MeshBuilder mb = renderer.meshBuilder();
        var emitter = mb.getEmitter();
        List<BakedQuad> quads = new ArrayList<>();
        quads.addAll(base.getQuads(state, null, rng.get()));
        for (Direction face : Direction.values()) {
            quads.addAll(base.getQuads(state, face, rng.get()));
        }

        Direction facing = state.get(net.minecraft.state.property.Properties.HORIZONTAL_FACING);
        Direction leftDir  = facing.rotateYCounterclockwise();
        Direction rightDir = facing.rotateYClockwise();

        Block leftBlock  = world.getBlockState(pos.offset(leftDir)).getBlock();
        Block rightBlock = world.getBlockState(pos.offset(rightDir)).getBlock();
        Block self = state.getBlock();

        for (BakedQuad q : quads) {
            Sprite sp = q.getSprite();
            String id = sp.getContents().getId().toString();

            boolean hide = false;
            if (id.endsWith("_left")  || id.contains("_left.")) {
                if (leftBlock == self) hide = true;
            } else if (id.endsWith("_right") || id.contains("_right.")) {
                if (rightBlock == self) hide = true;
            }

            if (!hide) {
                emitter.fromVanilla(q, renderer.materialFinder().find(), null);
                emitter.emit();
            }
        }

        Mesh mesh = mb.build();
        ctx.meshConsumer().accept(mesh);
    }

    @Override public boolean isVanillaAdapter() { return false; }
    @Override public List<BakedQuad> getQuads(BlockState s, Direction f, Random r){ return base.getQuads(s,f,r); }
    @Override public boolean useAmbientOcclusion(){ return base.useAmbientOcclusion(); }
    @Override public boolean hasDepth(){ return base.hasDepth(); }
    @Override public boolean isSideLit(){ return base.isSideLit(); }
    @Override public boolean isBuiltin(){ return base.isBuiltin(); }
    @Override public net.minecraft.client.render.model.json.ModelTransformation getTransformation(){ return base.getTransformation(); }
    @Override public net.minecraft.client.render.model.json.ModelOverrideList getOverrides(){ return base.getOverrides(); }
    @Override public net.minecraft.client.texture.Sprite getParticleSprite(){ return base.getParticleSprite(); }
}
