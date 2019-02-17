package com.indiv.neilly.util;

import com.google.common.base.Predicate;
import com.indiv.neilly.objects.items.ItemNui;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nullable;

public interface INewEnchantment {
    EnumEnchantmentType NUI = EnumHelper.addEnchantmentType("NUI", new Predicate<Item>() {
        @Override
        public boolean apply(@Nullable Item input) {
            return input instanceof ItemNui;
        }
    });
}
