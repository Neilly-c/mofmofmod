package com.indiv.neilly.objects.tools;

import com.google.common.collect.Sets;
import com.indiv.neilly.Main;
import com.indiv.neilly.init.ItemInit;
import com.indiv.neilly.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class ToolBreaker extends ItemPickaxe implements IHasModel {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.DIRT, Blocks.GRASS, Blocks.GRAVEL, Blocks.SAND, Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);

    public ToolBreaker(String name, ToolMaterial material) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.MOFTAB);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels(){
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        EnumFacing facing = EnumFacing.getDirectionFromEntityLiving(pos, entityLiving);
        if(EFFECTIVE_ON.contains(state.getBlock())) {
            switch (facing) {
                case UP:
                case DOWN:
                    for (int x = -1; x <= 1; x++) {
                        for (int z = -1; z <= 1; z++) {
                            if (x == 0 && z == 0)
                                continue;
                            BlockPos pos1 = pos.add(x, 0, z);
                            Material material = worldIn.getBlockState(pos).getBlock().getDefaultState().getMaterial();
                            Material material1 = worldIn.getBlockState(pos1).getBlock().getDefaultState().getMaterial();
                            if (material == material1) {
                                worldIn.destroyBlock(pos1, true);
                            }
                        }
                    }
                    break;
                case EAST:
                case WEST:
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            if (y == 0 && z == 0)
                                continue;
                            BlockPos pos1 = pos.add(0, y, z);
                            Material material = worldIn.getBlockState(pos).getBlock().getDefaultState().getMaterial();
                            Material material1 = worldIn.getBlockState(pos1).getBlock().getDefaultState().getMaterial();
                            if (material == material1) {
                                worldIn.destroyBlock(pos1, true);
                            }
                        }
                    }
                    break;
                case NORTH:
                case SOUTH:
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            if (x == 0 && y == 0)
                                continue;
                            BlockPos pos1 = pos.add(x, y, 0);
                            Material material = worldIn.getBlockState(pos).getBlock().getDefaultState().getMaterial();
                            Material material1 = worldIn.getBlockState(pos1).getBlock().getDefaultState().getMaterial();
                            if (material == material1) {
                                worldIn.destroyBlock(pos1, true);
                            }
                        }
                    }
                    break;
            }
        }
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }
}
