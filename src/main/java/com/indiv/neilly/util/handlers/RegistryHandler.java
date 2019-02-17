package com.indiv.neilly.util.handlers;

import com.indiv.neilly.entity.TileEntityNuiBed;
import com.indiv.neilly.entity.TileEntityWaterRemover;
import com.indiv.neilly.init.BlockInit;
import com.indiv.neilly.init.EnchantInit;
import com.indiv.neilly.init.EntityInit;
import com.indiv.neilly.init.ItemInit;
import com.indiv.neilly.util.IHasModel;
import com.indiv.neilly.util.Reference;
import com.indiv.neilly.world.gen.WorldGenCustomOres;
import com.indiv.neilly.world.gen.WorldGenCustomTrees;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
        BlockInit.initTools();
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event){
        for(Item item : ItemInit.ITEMS){
            if(item instanceof IHasModel){
                ((IHasModel)item).registerModels();
            }
        }

        for(Block block : BlockInit.BLOCKS){
            if(block instanceof IHasModel){
                ((IHasModel)block).registerModels();
            }
        }
    }

    @SubscribeEvent
    public static void onEnchantmentRegister(RegistryEvent.Register<Enchantment> event){
        event.getRegistry().registerAll(EnchantInit.ENCHANTMENTS.toArray(new Enchantment[0]));
    }

    public static void otherRegistries(){
        GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 0);
        GameRegistry.registerWorldGenerator(new WorldGenCustomTrees(), 0);
        GameRegistry.registerTileEntity(TileEntityNuiBed.class, Reference.MODID + ":nuiBed");
        GameRegistry.registerTileEntity(TileEntityWaterRemover.class, Reference.MODID + ":water_remover");
        EntityInit.registerEntities();
        OreDictionaryHandler.registerOreDictionary();
        RenderHandler.registerEntityRenders();
    }
}
