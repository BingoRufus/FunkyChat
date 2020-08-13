package me.bingorufus.funkychat.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.chat.ComponentSerializer;


public class ItemStackJson {
	Class<?> nmsItemStack;
	Class<?> craftItemStack;
	Class<?> iChatBaseComponent;
	Class<?> chatSerializer;
	public ItemStackJson() {
		try {
			String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
			nmsItemStack = Class.forName("net.minecraft.server." + version + ".ItemStack");
			craftItemStack = Class
					.forName("org.bukkit.craftbukkit.{v}.inventory.CraftItemStack".replace("{v}", version));
			iChatBaseComponent = Class.forName("net.minecraft.server.{v}.IChatBaseComponent".replace("{v}", version));
			chatSerializer = Class
					.forName("net.minecraft.server.{v}.IChatBaseComponent$ChatSerializer".replace("{v}", version));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ItemStack enabled() {
		ItemStack item = new ItemStack(Material.GREEN_WOOL);
		TextComponent enabledmessage = new TextComponent(new TranslatableComponent("addServer.resourcePack.enabled"));
		enabledmessage.setItalic(false);
		enabledmessage.setBold(true);
		enabledmessage.setColor(ChatColor.GREEN);
		return translateName(item, enabledmessage);

		}


	public ItemStack translateName(ItemStack i, TextComponent tc) {
		try {
			Method asNmsCopy = craftItemStack.getMethod("asNMSCopy", ItemStack.class);
			asNmsCopy.setAccessible(true);
			Object nmsItem = asNmsCopy.invoke(craftItemStack, i);
			Method setName = nmsItem.getClass().getMethod("a", iChatBaseComponent);
			setName.setAccessible(true);
			Method serializeJSON = chatSerializer.getMethod("a", String.class);
			serializeJSON.setAccessible(true);
			Object itemname = serializeJSON.invoke(chatSerializer, ComponentSerializer.toString(tc));

			nmsItem = setName.invoke(nmsItem, itemname);
			Method toBukkit = craftItemStack.getMethod("asBukkitCopy", nmsItemStack);
			toBukkit.setAccessible(true);
			return (ItemStack) toBukkit.invoke(craftItemStack, nmsItem);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return new ItemStack(Material.AIR);
		}

	}


	public ItemStack disabled() {
		ItemStack item = new ItemStack(Material.RED_WOOL);
		TextComponent enabledmessage = new TextComponent(new TranslatableComponent("addServer.resourcePack.disabled"));
		enabledmessage.setItalic(false);
		enabledmessage.setBold(true);
		enabledmessage.setColor(ChatColor.RED);
		return translateName(item, enabledmessage);
	}

	public ItemStack customSkin(String texture, String signature) {
		GameProfile gp = new GameProfile(UUID.randomUUID(), "customhead");



		PropertyMap pm =gp.getProperties();
	    pm.put("textures", new Property("textures", texture, signature));
	    
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		try {

			Field profileField = sm.getClass().getDeclaredField("profile");
	    profileField.setAccessible(true);

			profileField.set(sm, gp);
			skull.setItemMeta(sm);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
		return skull;
	}

	
}
