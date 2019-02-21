package com.indiv.neilly.objects.items.recipe;

import com.google.common.collect.Maps;
import com.indiv.neilly.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class CrushRecipes {
    private static final CrushRecipes CRUSHING_BASE = new CrushRecipes();
    private final Map<ItemStack, ItemStack> crushingList = Maps.<ItemStack, ItemStack>newHashMap();
    private final Map<ItemStack, Float> expList = Maps.<ItemStack, Float>newHashMap();

    public static CrushRecipes instance(){
        return CRUSHING_BASE;
    }

    private CrushRecipes(){
        //this.addCrushingRecipeForBlock(Blocks.IRON_ORE, new ItemStack(ItemInit.IRON_DUST), 0.7F);
        //this.addCrushingRecipeForBlock(Blocks.GOLD_ORE, new ItemStack(ItemInit.GOLD_DUST), 1.0F);
        this.addCrushingRecipeForBlock(Blocks.STONE, new ItemStack(Blocks.SAND, 1), 0.1F);
    }

    public void addCrushingRecipeForBlock(Block input, ItemStack stack, float experience) {
        this.addCrushing(Item.getItemFromBlock(input), stack, experience);
    }

    public void addCrushing(Item input, ItemStack stack, float experience) {
        this.addCrushingRecipe(new ItemStack(input, 1, 32767), stack, experience);
    }

    public void addCrushingRecipe(ItemStack input, ItemStack stack, float experience) {
        if (getCrushingResult(input) != ItemStack.EMPTY) { net.minecraftforge.fml.common.FMLLog.log.info("Ignored crushing recipe with conflicting input: {} = {}", input, stack); return; }
        this.crushingList.put(input, stack);
        this.expList.put(stack, experience);
    }

    public ItemStack getCrushingResult(ItemStack stack) {
        for (Map.Entry<ItemStack, ItemStack> entry : this.crushingList.entrySet()) {
            if (this.compareItemStacks(stack, entry.getKey())) {
                return entry.getValue();
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public Map<ItemStack, ItemStack> getCrushingList()
    {
        return this.crushingList;
    }

    public float getCrushingExperience(ItemStack stack) {
        for (Map.Entry<ItemStack, Float> entry : this.expList.entrySet()) {
            if (this.compareItemStacks(stack, entry.getKey())) {
                return entry.getValue();
            }
        }
        return 0.0F;
    }
}
