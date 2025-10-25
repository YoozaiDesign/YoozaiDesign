package archi;

import archi.util.VoxelShapeUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.EnumMap;
import java.util.Map;

/**
 * Stair block with dynamic collision and side connections.
 */
public class DynamicStairBlock extends Block {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty CONNECT_LEFT = BooleanProperty.of("connect_left");
    public static final BooleanProperty CONNECT_RIGHT = BooleanProperty.of("connect_right");

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

    public DynamicStairBlock(FabricBlockSettings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(CONNECT_LEFT, false)
                .with(CONNECT_RIGHT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, CONNECT_LEFT, CONNECT_RIGHT);
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

        BlockState state = this.getDefaultState().with(FACING, facing);
        return calculateConnections(state, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (!world.isClient) {
            BlockState updated = calculateConnections(state, world, pos);
            if (!updated.equals(state)) {
                state = updated;
                world.setBlockState(pos, updated, Block.NOTIFY_LISTENERS | Block.NOTIFY_NEIGHBORS);
            }
            updateAdjacentConnections(world, pos, state);
        }
        super.onPlaced(world, pos, state, placer, stack);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState updated = calculateConnections(state, world, pos);
        if (updated != state) {
            return updated;
        }
        return state;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!world.isClient && state.getBlock() != newState.getBlock()) {
            updateAdjacentConnections(world, pos, state);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
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

    private BlockState calculateConnections(BlockState state, WorldAccess world, BlockPos pos) {
        Direction facing = state.get(FACING);
        Direction leftDir = facing.rotateYCounterclockwise();
        Direction rightDir = facing.rotateYClockwise();

        boolean connectLeft = shouldConnect(state, world.getBlockState(pos.offset(leftDir)));
        boolean connectRight = shouldConnect(state, world.getBlockState(pos.offset(rightDir)));

        return state.with(CONNECT_LEFT, connectLeft).with(CONNECT_RIGHT, connectRight);
    }

    private boolean shouldConnect(BlockState state, BlockState neighbor) {
        if (neighbor.getBlock() != this) {
            return false;
        }
        return neighbor.get(FACING) == state.get(FACING);
    }

    private void updateAdjacentConnections(World world, BlockPos pos, BlockState state) {
        Direction facing = state.get(FACING);
        updateNeighbor(world, pos.offset(facing.rotateYCounterclockwise()));
        updateNeighbor(world, pos.offset(facing.rotateYClockwise()));
    }

    private void updateNeighbor(World world, BlockPos neighborPos) {
        BlockState neighborState = world.getBlockState(neighborPos);
        if (neighborState.getBlock() == this) {
            BlockState updated = calculateConnections(neighborState, world, neighborPos);
            if (!updated.equals(neighborState)) {
                world.setBlockState(neighborPos, updated, Block.NOTIFY_LISTENERS | Block.NOTIFY_NEIGHBORS);
            }
        }
    }
}
