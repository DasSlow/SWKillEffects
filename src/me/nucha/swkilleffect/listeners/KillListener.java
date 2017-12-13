package me.nucha.swkilleffect.listeners;

import me.nucha.swkilleffect.SWKillEffect;
import me.nucha.swkilleffect.effects.KillEffect;
import me.nucha.swkilleffect.manager.KillEffectManager;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener {

	private SWKillEffect plugin;
	private PacketPlayInClientCommand respawnPacket;

	public KillListener(SWKillEffect plugin) {
		this.plugin = plugin;
		this.respawnPacket = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		KillEffectManager killEffectManager = plugin.getKillEffectManager();
		if (p.getKiller() != null) {
			Player k = p.getKiller();
			KillEffect killEffect = plugin.getKillEffectManager().getKillEffect(k);
			if (killEffect != null) {
				killEffect.play(p);
			}
			if (killEffectManager.isKillSoundEnabled()) {
				k.playSound(k.getLocation(), killEffectManager.getKillSound(), 1, killEffectManager.getKillSoundPitch());
			}
		}
		if (killEffectManager.isAutoRespawnEnabled()) {
			Bukkit.getScheduler().runTask(plugin, () -> {
				((CraftPlayer) p).getHandle().playerConnection.a(respawnPacket);
			});
		}
	}

}
