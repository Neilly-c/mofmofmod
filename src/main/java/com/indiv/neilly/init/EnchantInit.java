package com.indiv.neilly.init;

import com.indiv.neilly.enchantment.*;
import com.indiv.neilly.util.INewEnchantment;
import com.indiv.neilly.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class EnchantInit {
    public static final List<Enchantment> ENCHANTMENTS = new ArrayList<Enchantment>();
    public static final Enchantment ENCHANT_GLOWING = new EnchantGlowing("glowing", Enchantment.Rarity.COMMON, INewEnchantment.NUI, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
    public static final Enchantment ENCHANT_WANDER = new EnchantWander("wander", Enchantment.Rarity.VERY_RARE, INewEnchantment.NUI, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
    public static final Enchantment ENCHANT_SWEET_DREAM = new EnchantSweetDream("sweet_dream", Enchantment.Rarity.COMMON, INewEnchantment.NUI, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
    public static final Enchantment ENCHANT_HAWKEYE = new EnchantHawkeye("hawkeye", Enchantment.Rarity.COMMON, INewEnchantment.NUI, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
    public static final Enchantment ENCHANT_ABSOLUTE = new EnchantAbsolute("absolute", Enchantment.Rarity.VERY_RARE, INewEnchantment.NUI, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});

    @SubscribeEvent
    public static void enchantWanderFunctions(LivingEvent.LivingUpdateEvent event){

    }

    @SubscribeEvent
    public static void enchantGlowingFunctions(LivingEvent.LivingUpdateEvent event){

    }

    @SubscribeEvent
    public static void enchantSweetDreamFunctions(LivingEvent.LivingUpdateEvent event){

    }

    @SubscribeEvent
    public static void enchantHawkeyeFunctions(LivingEvent.LivingUpdateEvent event){

    }

    @SubscribeEvent
    public static void enchantAbsoluteFunctions(LivingEvent.LivingUpdateEvent event){

    }
}