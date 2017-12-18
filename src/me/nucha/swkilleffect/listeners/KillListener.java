package me.nucha.swkilleffect.listeners;

import me.nucha.swkilleffect.SWKillEffect;
import me.nucha.swkilleffect.effects.KillEffect;
import me.nucha.swkilleffect.manager.KillEffectManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener {

	private SWKillEffect plugin;
	private String nmsVersion;

	public KillListener(SWKillEffect plugin) {
		this.plugin = plugin;
		this.nmsVersion = plugin.getNmsVersion();
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		KillEffectManager killEffectManager = plugin.getKillEffectManager();
		if (p.getKiller() != null) {
			Player k = p.getKiller();
			KillEffect killEffect = killEffectManager.getKillEffect(k);
			if (killEffect != null) {
				killEffect.play(p);
			}
			if (killEffectManager.isKillSoundEnabled()) {
				k.playSound(k.getLocation(), killEffectManager.getKillSound(), 1, killEffectManager.getKillSoundPitch());
			}
		}
		if (killEffectManager.isAutoRespawnEnabled()) {
			Bukkit.getScheduler().runTask(
					plugin,
					() -> {
						switch (nmsVersion) {
						case "v1_8_R1":
							((org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer) p).getHandle().playerConnection
									.a(new net.minecraft.server.v1_8_R1.PacketPlayInClientCommand(
											net.minecraft.server.v1_8_R1.EnumClientCommand.PERFORM_RESPAWN));
							break;
						case "v1_8_R2":
							((org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer) p).getHandle().playerConnection
									.a(new net.minecraft.server.v1_8_R2.PacketPlayInClientCommand(
											net.minecraft.server.v1_8_R2.PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
							break;
						case "v1_8_R3":
							((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) p).getHandle().playerConnection
									.a(new net.minecraft.server.v1_8_R3.PacketPlayInClientCommand(
											net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
							break;
						case "v1_9_R1":
							((org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer) p).getHandle().playerConnection
									.a(new net.minecraft.server.v1_9_R1.PacketPlayInClientCommand(
											net.minecraft.server.v1_9_R1.PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
							break;
						case "v1_9_R2":
							((org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer) p).getHandle().playerConnection
									.a(new net.minecraft.server.v1_9_R2.PacketPlayInClientCommand(
											net.minecraft.server.v1_9_R2.PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
							break;
						case "v1_10_R1":
							((org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer) p).getHandle().playerConnection
									.a(new net.minecraft.server.v1_10_R1.PacketPlayInClientCommand(
											net.minecraft.server.v1_10_R1.PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
							break;
						case "v1_11_R1":
							((org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer) p).getHandle().playerConnection
									.a(new net.minecraft.server.v1_11_R1.PacketPlayInClientCommand(
											net.minecraft.server.v1_11_R1.PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
							break;
						case "v1_12_R1":
							((org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer) p).getHandle().playerConnection
									.a(new net.minecraft.server.v1_12_R1.PacketPlayInClientCommand(
											net.minecraft.server.v1_12_R1.PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
							break;
						default:
							break;
						}
					});
		}
	}

}
