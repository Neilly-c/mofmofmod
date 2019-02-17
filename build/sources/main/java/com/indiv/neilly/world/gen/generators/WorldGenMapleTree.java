package com.indiv.neilly.world.gen.generators;

import com.indiv.neilly.init.BlockInit;
import com.indiv.neilly.objects.blocks.BlockLeaf;
import com.indiv.neilly.objects.blocks.BlockLogs;
import com.indiv.neilly.objects.blocks.BlockSaplings;
import com.indiv.neilly.util.handlers.EnumWoodHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenMapleTree extends WorldGenAbstractTree
{
    public static final IBlockState LOG = BlockInit.LOGS.getDefaultState().withProperty(BlockLogs.VARIANT, EnumWoodHandler.EnumType.MAPLE);
    public static final IBlockState LEAF = BlockInit.LEAVES.getDefaultState().withProperty(BlockLeaf.VARIANT, EnumWoodHandler.EnumType.MAPLE);

    private final int minHeight;

    public WorldGenMapleTree()
    {
        super(false);
        this.minHeight = 5;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        int height = this.minHeight + rand.nextInt(2);
        boolean flag = true;

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        IBlockState downState = world.getBlockState(pos.down());
        Block downBlockType = downState.getBlock();
        Material downMaterial = downBlockType.getDefaultState().getMaterial();

        if(downMaterial != Material.GROUND && downBlockType != Blocks.GRASS && downBlockType != Blocks.DIRT){
            flag = false;
        }

        IBlockState state = world.getBlockState(pos);
        Block blockType = state.getBlock();
        Material material = blockType.getDefaultState().getMaterial();

        if(material != Material.AIR && blockType != Blocks.SAPLING && blockType != Blocks.LEAVES && blockType != Blocks.LEAVES2){
            flag = false;
        }

        for(int yPos = y; yPos <= y + 1 + height; yPos++) {
            int b0 = 3;
            if(yPos == y) b0 = 1;
            if(yPos >= y + 1 + height - 2) b0 = 3;

            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            for(int xPos = x - b0; xPos <= x + b0 && flag; xPos++) {
                for(int zPos = z - b0; zPos <- z + b0 && flag; zPos++) {
                    if(yPos >= 0 && yPos < world.getHeight()) {
                        if(!this.canGrowInto(world.getBlockState(new BlockPos(xPos, yPos, zPos)).getBlock())) {
                            flag = false;
                        }
                    } else {
                        flag = false;
                    }
                }
            }
        }

        if(!flag) {
            return false;
        } else {
            for(int yAdd = 0; yAdd <= height; yAdd++) {
                int b1;
                switch(yAdd){
                    case 0:
                    case 1:
                        b1 = 0;
                        break;
                    default:
                        b1 = 2;
                }
                if(yAdd == height)b1 = 1;
                for(int xAdd = 0 - b1; xAdd <= b1 ; xAdd++) {
                    for(int zAdd = 0 - b1; zAdd <= b1 ; zAdd++) {
                        BlockPos treePos = pos.add(xAdd, yAdd, zAdd);
                        IBlockState treeState = world.getBlockState(treePos);
                        if(treeState.getBlock().isAir(treeState, world, treePos) && (xAdd != Math.abs(b1) || zAdd != Math.abs(b1))) {
                            this.setBlockAndNotifyAdequately(world, pos.add(xAdd, yAdd, zAdd), LEAF);
                        }
                    }
                }
            }
            for(int logHeight = 0; logHeight < height; logHeight++) {
                this.setBlockAndNotifyAdequately(world, pos.up(logHeight), LOG);
            }
        }

        return true;
    }

    @Override
    protected boolean canGrowInto(Block blockType)
    {
        Material material = blockType.getDefaultState().getMaterial();
        return material == Material.AIR || material == Material.LEAVES || blockType == Blocks.LOG || blockType == Blocks.LOG2 || blockType == Blocks.SAPLING || blockType == Blocks.VINE;

    }
}