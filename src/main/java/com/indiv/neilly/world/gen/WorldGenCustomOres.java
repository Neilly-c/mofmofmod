package com.indiv.neilly.world.gen;

import com.indiv.neilly.init.BlockInit;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenCustomOres implements IWorldGenerator {
    private WorldGenerator ore_copper, ore_tin, ore_aluminium;

    public WorldGenCustomOres() {
        ore_copper = new WorldGenMinable(BlockInit.ORE_COPPER.getDefaultState(), 9, BlockMatcher.forBlock(Blocks.STONE));
        ore_tin = new WorldGenMinable(BlockInit.ORE_TIN.getDefaultState(), 9, BlockMatcher.forBlock(Blocks.STONE));
        ore_aluminium = new WorldGenMinable(BlockInit.ORE_ALUMINIUM.getDefaultState(), 9, BlockMatcher.forBlock(Blocks.STONE));
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
        switch (world.provider.getDimension()){
            case -1:
                break;
            case 0:
                runGenerator(ore_copper, world, random, chunkX, chunkZ, 50, 0 ,100);
                runGenerator(ore_tin, world, random, chunkX, chunkZ, 50, 0 ,100);
                runGenerator(ore_aluminium, world, random, chunkX, chunkZ, 50, 0 ,100);
                break;
            case 1:
                break;
        }
    }

    private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight){
        if(minHeight > maxHeight || minHeight < 0 || maxHeight > 256) throw new IllegalArgumentException("Ore generated out of bounds");

        int heightDiff = maxHeight - minHeight + 1;
        for(int i=0;i<chance;i++){
            int x = chunkX * 16 + rand.nextInt(16);
            int y = minHeight * 16 + rand.nextInt(heightDiff);
            int z = chunkZ * 16 + rand.nextInt(16);

            gen.generate(world, rand, new BlockPos(x,y,z));
        }
    }

}
