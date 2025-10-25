package archi;

import archi.util.VoxelShapeUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.EnumMap;
import java.util.Map;

/**
 * Stair block without side boards but sharing the custom collision shape from DynamicStairBlock.
 */
public class MonostringerStairBlock extends Block {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    private static final VoxelShape BASE_SHAPE_NORTH = VoxelShapeUtils.combineShapes(
            VoxelShapeUtils.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 7.5),
            VoxelShapeUtils.createCuboidShape(0.0, 3.0, 10.5, 16.0, 5.0, 16.0),
            VoxelShapeUtils.createCuboidShape(0.0, 8.5, 5.5, 16.0, 10.5, 13.0)
    );

    private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);

    static {
        SHAPES.put(Direction.NORTH, BASE_SHAPE_NORTH);

        SHAPES.put(Direction.EAST, VoxelShapeUtils.combineShapes(
                VoxelShapeUtils.createCuboidShape(8.5, 14.0, 0.0, 16.0, 16.0, 16.0),
                VoxelShapeUtils.createCuboidShape(0.0, 3.0, 0.0, 5.5, 5.0, 16.0),
                VoxelShapeUtils.createCuboidShape(3.0, 8.5, 0.0, 10.5, 10.5, 16.0)
        ));

        SHAPES.put(Direction.SOUTH, VoxelShapeUtils.combineShapes(
                VoxelShapeUtils.createCuboidShape(0.0, 14.0, 8.5, 16.0, 16.0, 16.0),
                VoxelShapeUtils.createCuboidShape(0.0, 3.0, 0.0, 16.0, 5.0, 5.5),
                VoxelShapeUtils.createCuboidShape(0.0, 8.5, 3.0, 16.0, 10.5, 10.5)
        ));

        SHAPES.put(Direction.WEST, VoxelShapeUtils.combineShapes(
                VoxelShapeUtils.createCuboidShape(0.0, 14.0, 0.0, 7.5, 16.0, 16.0),
                VoxelShapeUtils.createCuboidShape(10.5, 3.0, 0.0, 16.0, 5.0, 16.0),
                VoxelShapeUtils.createCuboidShape(5.5, 8.5, 0.0, 13.0, 10.5, 16.0)
        ));
    }

    public MonostringerStairBlock(FabricBlockSettings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState()
                .with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = Direction.NORTH;
        PlayerEntity player = ctx.getPlayer();
        if (player != null) {
            facing = player.getHorizontalFacing();
        } else if (ctx.getHorizontalPlayerFacing() != null) {
            facing = ctx.getHorizontalPlayerFacing();
        }

        return this.getDefaultState().with(FACING, facing);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return getShape(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return getShape(state);
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return getShape(state);
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return getShape(state);
    }

    private VoxelShape getShape(BlockState state) {
        VoxelShape shape = SHAPES.get(state.get(FACING));
        return shape != null ? shape : BASE_SHAPE_NORTH;
    }
}

