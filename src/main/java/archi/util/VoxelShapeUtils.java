package archi.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

/**
 * Helper for building and rotating simple voxel shapes (0-16 coordinate grid, 90 degree Y rotations only).
 */
public class VoxelShapeUtils {

    public static VoxelShape createCuboidShape(double x1, double y1, double z1,
                                               double x2, double y2, double z2) {
        x1 = clamp(x1);
        y1 = clamp(y1);
        z1 = clamp(z1);
        x2 = clamp(x2);
        y2 = clamp(y2);
        z2 = clamp(z2);
        return Block.createCuboidShape(x1, y1, z1, x2, y2, z2);
    }

    private static double clamp(double v) {
        if (v < 0.0) return 0.0;
        if (v > 16.0) return 16.0;
        return v;
    }

    public static VoxelShape combineShapes(VoxelShape... shapes) {
        VoxelShape result = VoxelShapes.empty();
        for (VoxelShape shape : shapes) {
            result = VoxelShapes.union(result, shape);
        }
        return result;
    }

    /** Treats the input shape as facing north and rotates to the requested direction. */
    public static VoxelShape rotateShape(VoxelShape shape, Direction dir) {
        return switch (dir) {
            case EAST  -> rotateY(shape, 90);
            case SOUTH -> rotateY(shape, 180);
            case WEST  -> rotateY(shape, 270);
            default    -> shape; // NORTH
        };
    }

    /** Rotates the shape around the Y axis by the provided angle (multiples of 90). */
    public static VoxelShape rotateShapeY(VoxelShape shape, int angle) {
        return rotateY(shape, angle);
    }

    private static VoxelShape rotateY(VoxelShape shape, int angle) {
        VoxelShape[] buffer = new VoxelShape[]{shape, VoxelShapes.empty()};
        int turns = (angle % 360 + 360) % 360 / 90;

        for (int i = 0; i < turns; i++) {
            VoxelShape source = buffer[0];
            final VoxelShape[] accumulator = new VoxelShape[]{VoxelShapes.empty()};
            source.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
                accumulator[0] = VoxelShapes.union(
                        accumulator[0],
                        Block.createCuboidShape(
                                16 - maxZ, minY, minX,
                                16 - minZ, maxY, maxX
                        )
                );
            });
            buffer[0] = accumulator[0];
        }

        return buffer[0];
    }
}
