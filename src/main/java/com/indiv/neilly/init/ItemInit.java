package com.indiv.neilly.init;

import java.util.*;

import com.indiv.neilly.entity.EntityWaterMill;
import com.indiv.neilly.objects.items.ItemBase;
import com.indiv.neilly.objects.items.ItemMill;
import com.indiv.neilly.objects.items.ItemNui;
import com.indiv.neilly.objects.tools.*;
import com.indiv.neilly.objects.armor.ArmorBase;
import com.indiv.neilly.util.Reference;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ItemInit {
    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item NUI = new ItemNui("nui");

    public static final Item WATER_MILL = new ItemMill(EntityWaterMill.class, "water_mill");

    public static final Item BREAKER_IRON = new ToolBreaker("breaker_iron", ToolMaterial.IRON);
    public static final Item PLACER = new ToolPlacer("placer");
    public static final Item REPLACER = new ToolReplacer("replacer");

    public static final ToolMaterial TOOL_COPPER = EnumHelper.addToolMaterial("tool_copper",1, 180, 5.0F, 1.5F, 18);
    public static final ArmorMaterial ARMOR_COPPER = EnumHelper.addArmorMaterial("armor_copper", Reference.MODID + ":copper", 12, new int[]{1, 4, 5, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
    public static final ToolMaterial TOOL_TIN = EnumHelper.addToolMaterial("tool_tin",1, 180, 5.0F, 1.5F, 18);
    public static final ArmorMaterial ARMOR_TIN = EnumHelper.addArmorMaterial("armor_tin", Reference.MODID + ":tin", 12, new int[]{1, 4, 5, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
    public static final ToolMaterial TOOL_ALUMINIUM = EnumHelper.addToolMaterial("tool_aluminium", 1, 180, 5.0F, 1.5F, 12);
    public static final ArmorMaterial ARMOR_ALUMINIUM = EnumHelper.addArmorMaterial("armor_aluminium", Reference.MODID + ":aluminium", 12, new int[]{2, 5, 6, 2}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
    public static final ToolMaterial TOOL_LEAD = EnumHelper.addToolMaterial("tool_lead", 2, 250, 6.0F, 2.5F, 12);
    public static final ArmorMaterial ARMOR_LEAD = EnumHelper.addArmorMaterial("armor_lead", Reference.MODID + ":lead", 18, new int[]{2, 5, 6, 2}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
    public static final ToolMaterial TOOL_SILVER = EnumHelper.addToolMaterial("tool_silver", 2, 600, 6.0F, 2.0F, 25);
    public static final ArmorMaterial ARMOR_SILVER = EnumHelper.addArmorMaterial("armor_silver", Reference.MODID + ":silver", 30, new int[]{2, 6, 7, 2}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
    public static final ToolMaterial TOOL_IRIDIUM = EnumHelper.addToolMaterial("tool_iridium", 3, 1280, 10.0F, 2.0F, 30);
    public static final ArmorMaterial ARMOR_IRIDIUM = EnumHelper.addArmorMaterial("armor_iridium", Reference.MODID + ":iridium", 40, new int[]{3, 6, 8, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
    public static final ToolMaterial TOOL_BRONZE = EnumHelper.addToolMaterial("tool_bronze",2, 250, 6.0F, 2.0F, 14);
    public static final ArmorMaterial ARMOR_BRONZE = EnumHelper.addArmorMaterial("armor_bronze", Reference.MODID + ":bronze", 15, new int[]{2, 6, 7, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
    public static final ToolMaterial TOOL_STEEL = EnumHelper.addToolMaterial("tool_steel", 3, 960, 7.5F, 2.5F, 15);
    public static final ArmorMaterial ARMOR_STEEL = EnumHelper.addArmorMaterial("armor_steel", Reference.MODID + ":iridium", 30, new int[]{3, 6, 8, 3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);

    public static final Item INGOT_COPPER = new ItemBase("ingot_copper");
    public static final Item INGOT_TIN = new ItemBase("ingot_tin");
    public static final Item INGOT_ALUMINIUM = new ItemBase("ingot_aluminium");
    public static final Item INGOT_LEAD = new ItemBase("ingot_lead");
    public static final Item INGOT_SILVER = new ItemBase("ingot_silver");
    public static final Item INGOT_IRIDIUM = new ItemBase("ingot_iridium");
    public static final Item INGOT_BRONZE = new ItemBase("ingot_bronze");
    public static final Item INGOT_STEEL = new ItemBase("ingot_steel");
    public static final Item INGOT_PLATINUM = new ItemBase("ingot_platinum");

    public static final Item DUST_IRON = new ItemBase("dust_iron");
    public static final Item DUST_GOLD = new ItemBase("dust_gold");
    public static final Item DUST_COPPER = new ItemBase("dust_copper");
    public static final Item DUST_TIN = new ItemBase("dust_tin");
    public static final Item DUST_ALUMINIUM = new ItemBase("dust_aluminium");
    public static final Item DUST_LEAD = new ItemBase("dust_lead");
    public static final Item DUST_SILVER = new ItemBase("dust_silver");
    public static final Item DUST_IRIDIUM = new ItemBase("dust_iridium");
    public static final Item DUST_PLATINUM = new ItemBase("dust_platinum");
    public static final Item DUST_BRONZE = new ItemBase("dust_bronze");
    public static final Item DUST_STEEL = new ItemBase("dust_steel");

    public static final Item AXE_COPPER = new ToolAxe("axe_copper", TOOL_COPPER);
    public static final Item HOE_COPPER = new ToolHoe("hoe_copper", TOOL_COPPER);
    public static final Item PICKAXE_COPPER = new ToolPickaxe("pickaxe_copper", TOOL_COPPER);
    public static final Item SHOVEL_COPPER = new ToolSpade("spade_copper", TOOL_COPPER);
    public static final Item SWORD_COPPER = new ToolSword("sword_copper", TOOL_COPPER);
    public static final Item AXE_TIN = new ToolAxe("axe_tin", TOOL_TIN);
    public static final Item HOE_TIN = new ToolHoe("hoe_tin", TOOL_TIN);
    public static final Item PICKAXE_TIN = new ToolPickaxe("pickaxe_tin", TOOL_TIN);
    public static final Item SHOVEL_TIN = new ToolSpade("spade_tin", TOOL_TIN);
    public static final Item SWORD_TIN = new ToolSword("sword_tin", TOOL_TIN);

    public static final Item HELMET_COPPER = new ArmorBase("helmet_copper", ARMOR_COPPER, 1, EntityEquipmentSlot.HEAD);
    public static final Item CHESTPLATE_COPPER = new ArmorBase("chestplate_copper", ARMOR_COPPER, 1, EntityEquipmentSlot.CHEST);
    public static final Item LEGGINGS_COPPER = new ArmorBase("leggings_copper", ARMOR_COPPER, 2, EntityEquipmentSlot.LEGS);
    public static final Item BOOTS_COPPER = new ArmorBase("boots_copper", ARMOR_COPPER, 1, EntityEquipmentSlot.FEET);
    public static final Item HELMET_TIN = new ArmorBase("helmet_tin", ARMOR_TIN, 1, EntityEquipmentSlot.HEAD);
    public static final Item CHESTPLATE_TIN = new ArmorBase("chestplate_tin", ARMOR_TIN, 1, EntityEquipmentSlot.CHEST);
    public static final Item LEGGINGS_TIN = new ArmorBase("leggings_tin", ARMOR_TIN, 2, EntityEquipmentSlot.LEGS);
    public static final Item BOOTS_TIN = new ArmorBase("boots_tin", ARMOR_TIN, 1, EntityEquipmentSlot.FEET);
}
