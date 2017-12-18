package me.nucha.swkilleffect.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.nucha.swkilleffect.SWKillEffect;
import me.nucha.swkilleffect.effects.KillEffect;
import me.nucha.swkilleffect.effects.KillEffectBloodExplosion;
import me.nucha.swkilleffect.effects.KillEffectHeartExplosion;
import me.nucha.swkilleffect.utils.ConfigUtil;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class KillEffectManager {

	private List<KillEffect> killEffects;
	private HashMap<String, KillEffect> killEffectMap;
	private SWKillEffect plugin;

	public KillEffectManager(SWKillEffect plugin, String nmsVersion) {
		killEffects = new ArrayList<>();
		registerKillEffect(new KillEffectBloodExplosion());
		registerKillEffect(new KillEffectHeartExplosion());
		SWKillEffect.log("NMS Version: " + nmsVersion);
		switch (nmsVersion) {
		case "v1_8_R1":
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_8_R1.KillEffectHeadRocket());
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_8_R1.KillEffectFinalSmash());
			break;
		case "v1_8_R2":
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_8_R2.KillEffectHeadRocket());
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_8_R2.KillEffectFinalSmash());
			break;
		case "v1_8_R3":
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_8_R3.KillEffectHeadRocket());
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_8_R3.KillEffectFinalSmash());
			break;
		case "v1_9_R1":
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_9_R1.KillEffectHeadRocket());
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_9_R1.KillEffectFinalSmash());
			break;
		case "v1_9_R2":
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_9_R2.KillEffectHeadRocket());
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_9_R2.KillEffectFinalSmash());
			break;
		case "v1_10_R1":
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_10_R1.KillEffectHeadRocket());
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_10_R1.KillEffectFinalSmash());
			break;
		case "v1_11_R1":
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_11_R1.KillEffectHeadRocket());
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_11_R1.KillEffectFinalSmash());
			break;
		case "v1_12_R1":
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_12_R1.KillEffectHeadRocket());
			registerKillEffect(new me.nucha.swkilleffect.effects.v1_12_R1.KillEffectFinalSmash());
			break;
		default:
			break;
		}
		this.plugin = plugin;
		loadKillEffects();
	}

	public void loadKillEffects() {
		killEffectMap = new HashMap<>();
		FileConfiguration dataYml = plugin.getDataYml();
		for (String uuid : dataYml.getKeys(false)) {
			String effectId = dataYml.getString(uuid + ".selected-effect");
			killEffectMap.put(uuid, getKillEffectById(effectId));
		}
	}

	public void saveKillEffects() {
		FileConfiguration dataYml = plugin.getDataYml();
		for (String uuid : killEffectMap.keySet()) {
			dataYml.set(uuid + ".selected-effect", killEffectMap.get(uuid).getId());
		}
		plugin.saveDataYml();
	}

	public KillEffect getKillEffectByName(String name) {
		for (KillEffect killEffect : killEffects) {
			if (killEffect.getName().equalsIgnoreCase(name)) {
				return killEffect;
			}
		}
		return null;
	}

	public void registerKillEffect(KillEffect killEffect) {
		killEffects.add(killEffect);
	}

	public KillEffect getKillEffectById(String id) {
		for (KillEffect killEffect : killEffects) {
			if (killEffect.getId().equalsIgnoreCase(id)) {
				return killEffect;
			}
		}
		return null;
	}

	public KillEffect getKillEffect(Player p) {
		String uuid = p.getUniqueId().toString();
		if (killEffectMap.containsKey(uuid)) {
			return killEffectMap.get(uuid);
		}
		return null;
	}

	public void setKillEffect(Player p, KillEffect effect) {
		String uuid = p.getUniqueId().toString();
		if (killEffectMap.containsKey(uuid)) {
			killEffectMap.remove(uuid);
		}
		if (effect != null) {
			killEffectMap.put(uuid, effect);
		}
	}

	public void setKillEffect(Player p, String effectId) {
		String uuid = p.getUniqueId().toString();
		if (killEffectMap.containsKey(uuid)) {
			killEffectMap.remove(uuid);
		}
		KillEffect effect = getKillEffectById(effectId);
		if (effect != null) {
			killEffectMap.put(uuid, effect);
		}
	}

	public boolean hasSelected(Player p, KillEffect effect) {
		if (hasSelectedOne(p) && effect.equals(getKillEffect(p))) {
			return true;
		}
		return false;
	}

	public boolean hasSelected(Player p, String effectId) {
		if (hasSelectedOne(p) && getKillEffect(p).getId().contentEquals(effectId)) {
			return true;
		}
		return false;
	}

	public boolean isKillSoundEnabled() {
		return ConfigUtil.ENABLE_KILLSOUND;
	}

	public Sound getKillSound() {
		return ConfigUtil.KILLSOUND;
	}

	public float getKillSoundPitch() {
		return ConfigUtil.KILLSOUND_PITCH;
	}

	public boolean isAutoRespawnEnabled() {
		return ConfigUtil.ENABLE_AUTORESPAWN;
	}

	public boolean hasSelectedOne(Player p) {
		return getKillEffect(p) != null;
	}

}
