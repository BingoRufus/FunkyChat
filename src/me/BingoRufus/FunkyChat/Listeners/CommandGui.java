package me.bingorufus.funkychat.listeners;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import me.bingorufus.funkychat.FunkyChat;
import me.bingorufus.funkychat.Settings;
import me.bingorufus.funkychat.utils.ItemStackJson;
import net.md_5.bungee.api.ChatColor;

public class CommandGui implements CommandExecutor {
	private FunkyChat m;
	private ItemStack enabled;
	private ItemStack disabled;
	private ItemStackJson isj;
	public CommandGui(FunkyChat m) {
		isj = new ItemStackJson();
		this.enabled = isj.enabled();
		this.disabled = isj.disabled();
		this.m = m;
	}

	public boolean canChange(CommandSender sender) {
		String[] perms = (String[]) Arrays.asList("funkychat.self.reverse", "funkychat.self.upsidedown").toArray();

		for (String perm : perms) {
			if(sender.hasPermission(perm)) return true;
		}
		return false;
	}

	public boolean canChangeOther(CommandSender sender) {
		String[] perms = (String[]) Arrays.asList("funkychat.other.reverse", "funkychat.other.upsidedown").toArray();

		for (String perm : perms) {
			if (sender.hasPermission(perm))
				return true;
		}
		return false;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can do this!");
			return true;
		}
		Player p = (Player) sender;
		Player editing = p;

		if (!canChange(sender)) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
			return true;
		}
		if (args.length > 0 && Bukkit.getPlayer(args[0]) != null && canChangeOther(sender)) {
			editing = Bukkit.getPlayer(args[0]);
		}
		if (m.settingInventory.containsKey(editing)) {
			p.openInventory(m.settingInventory.get(editing));
			return true;
		}
		p.openInventory(createInventory(editing));

		return true;
		
	}



	public Inventory createInventory(Player p) {
		Inventory inv = Bukkit.createInventory(p, 27, ChatColor.GREEN + "" + ChatColor.BOLD + "FunkyChat");

		ItemStack player = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta sm = (SkullMeta) player.getItemMeta();
		sm.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
		sm.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "FunkyChat - " + p.getName());
		player.setItemMeta(sm);

		inv.setItem(4, player);

		Settings setting = m.getSetting(p);
		inv.setItem(20, setting.reverse() ? enabled : disabled);
		inv.setItem(24, setting.upsidedown() ? enabled : disabled);
		ItemStack reverse = isj.customSkin(
				"ewogICJ0aW1lc3RhbXAiIDogMTU5NjA4NzI0MTAyMywKICAicHJvZmlsZUlkIiA6ICJhOGJjOWEzNTM4NjE0OTEwYThiZGVmMTg3NTk5Yjk3ZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJCaW5nb1J1ZnVzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzI3NDNhNzkxNDU0MTQwMDgwOWJkN2VkZWNkNzc4ZDQ0YjE3ZDliNWRkNDlkNThjODJmMjJkMDM4ZGZhN2MxY2QiCiAgICB9CiAgfQp9",
				"UTkwFOn51beqGz/y/xOxhhOscNO3sfOOQoMLFKjJb9OWMJPkolv9piP2WVAZ8dnuwpdtaorRp9237XQ0MFiyR/zbxctJsgfSkFVPgm4/v0YYo8D8XBe8DE2NJd8lAqc68Rk9n2B+r87rQ7DXxxwvA37i+PM2ZGFqUCLIBgzBJqDyprMe1MrsIG/4LgNQ+76lRWjO5D/mCldgTI6Au6Nzv5iQgYugCA+5zuO6gGL89yKRIkNSVOV6eQGc4pOWDNnXy+mKAna4pU6dHrAsJEtiaNZhOYVPWCngspYnmeJBYktp318B8W8T7qTy/h0nYkxCYkqQQTVkqs6mPSM5LGbybhvrvWZW/yIMlP6b17pqSD0pwpTkriCRN7IFhISqp91sz9tK3HUrmyt5TZsFWIC2Bt0ob1f6IvP+ExCNX7O2+Mqfy39SvwFy6K65pXyPhzNfN4ilQNESi7g6lcCfw3ylnbrshIg4Ej4RhCZv4xVgKQaPDRZFhM6CNXlUNZ7GUOZkPLOqMkvnIrXub7Gty62fEvvFSYldJ6UzoAmUyKPG0SEte1pzBvMtoz/uoyMqRd+TM2lvKMlpl6NXV/ruoNPjg0DQPBrpzTuWKowh57L/hOzgHJn1cfjg6707HhjTdC9IjyYpVMKCZ/2XQ8TELO1PacXJzPdpp6j96gFhzdp0JEw=");
		sm = (SkullMeta) reverse.getItemMeta();
		sm.setDisplayName(ChatColor.YELLOW + "esreveR");
		reverse.setItemMeta(sm);
		inv.setItem(11, reverse);

		ItemStack upsidedown = isj.customSkin(
				"ewogICJ0aW1lc3RhbXAiIDogMTU5NjA4NjU2MjQ4NywKICAicHJvZmlsZUlkIiA6ICJhOGJjOWEzNTM4NjE0OTEwYThiZGVmMTg3NTk5Yjk3ZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJCaW5nb1J1ZnVzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2E0NzU4ZDI1NDRkZTA5MjE0ZDYwODRhZTE3OTZlNGRmN2M0M2U0ODAwNDIyODA2NjE4MTE4NzRjOWVkNDUxNTYiCiAgICB9CiAgfQp9",
				"U/CEDBMhIdk2lFgnlbGZ4SREPWhVKLlF3usphCrdi92hCAlZjVAk6XPJ6gorglqhHm35PsFDg21CWagu0O/RNbL/slf58ufDnd53RuAInzgSX3cwUuIPMcnc0meKz4APskfeJeB8xviQva6JBPpT6ob6+xg3Ay5aun82dhaYwL5WB1MIrJW3Y9gIiY0caVyX1nHg5Ip6OU0S5T7q2krYCnVn4lKenqjxhn4mcaMEKlP8mh9gk4BEQOecp5ggQTl0K4OJ0Tm/aiYf9Gb7quQOqa3PkM6mErZD1k7czi7wiePHoFio1V6kQG6fUt0IIMSHBbSLONX8TBkPis94SJvypmasC3qjcLVaDmO1y2siUgYNph/SNkvfIWa4xeOSCrKx4KnlzmEMEwcFbm9sDiVy/ZvwjFh+k5cGGP61cu10vdSDRd3gAcr3lyeMrDyCWgkbJQhU6hElQ6mfTz2gSL/TdgmJRDYjf+2rtPZCuyw4tQngL52g+r9tpRt820SUA1dJ1Cx9YVPFnjdstpbFSk5Qph2vnTAjUfVA1cnkmlG5Cxek/yjsyvO2M9stedzuATt7+/soczMijXhPhILXsE+1+8M/FulKOm1AXNz0XaNi9nDeB340v+e8R5WlHnv9A/NhEhIW06jQDonipNmxgN3akBZJJzRXA2X4E/wWVPP6AEM=");
		sm = (SkullMeta) upsidedown.getItemMeta();
		sm.setDisplayName(ChatColor.YELLOW + "uʍop ǝpᴉsd∩");
		upsidedown.setItemMeta(sm);
		inv.setItem(15, upsidedown);
		m.settingInventory.put(p, inv);
		return inv;
	}
	


}
