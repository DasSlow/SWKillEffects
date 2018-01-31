package me.nucha.swkilleffect.commands;

import me.nucha.swkilleffect.SWKillEffect;
import me.nucha.swkilleffect.utils.ConfigUtil;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSWKillEffect implements CommandExecutor {

	private SWKillEffect plugin;
	public String prefix = "§8[§cSWKillEffect§8] §r";

	public CommandSWKillEffect(SWKillEffect plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("gui")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player p = (Player) sender;
				plugin.getGuiKillEffectSelector().open(p);
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				plugin.getKillEffectManager().saveKillEffects();
				plugin.reloadConfig();
				ConfigUtil.init(plugin);
				sender.sendMessage(prefix + "§aconfig.yml reloaded!");
				return true;
			}
		}
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("gui")) {
				Player t = Bukkit.getPlayer(args[1]);
				if (t != null) {
					plugin.getGuiKillEffectSelector().open(t);
				}
				return true;
			}
		}
		sender.sendMessage(prefix + "§a------------ §cSWKillEffect §aby §eNucha §a------------");
		sender.sendMessage(prefix + "§a/swke gui [player] §2--- §bOpen Kill Effect selector");
		return true;
	}

}
