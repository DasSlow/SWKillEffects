package me.nucha.swkilleffect;

import java.io.File;
import java.io.IOException;

import me.nucha.swkilleffect.commands.CommandSWKillEffect;
import me.nucha.swkilleffect.gui.GuiKillEffectSelector;
import me.nucha.swkilleffect.listeners.KillListener;
import me.nucha.swkilleffect.manager.KillEffectManager;
import me.nucha.swkilleffect.utils.ConfigUtil;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SWKillEffect extends JavaPlugin {

	private static SWKillEffect instance;
	private KillEffectManager killEffectManager;
	private FileConfiguration dataYml;
	private GuiKillEffectSelector guiKillEffectSelector;

	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		ConfigUtil.init(this);
		loadDataYml();
		killEffectManager = new KillEffectManager(this);
		Bukkit.getPluginManager().registerEvents(new KillListener(this), this);
		Bukkit.getPluginManager().registerEvents(guiKillEffectSelector = new GuiKillEffectSelector(killEffectManager), this);
		getCommand("swkilleffect").setExecutor(new CommandSWKillEffect(this));
	}

	@Override
	public void onDisable() {
		killEffectManager.saveKillEffects();
	}

	public void loadDataYml() {
		saveResource("data.yml", false);
		File dataYmlFile = new File(getDataFolder() + "/data.yml");
		if (!dataYmlFile.exists()) {
			try {
				dataYmlFile.createNewFile();
			} catch (IOException e) {
				log("§cdata.yml の読み込み中にエラーが発生しました");
				e.printStackTrace();
				return;
			}
		}
		dataYml = YamlConfiguration.loadConfiguration(dataYmlFile);
		log("§adata.yml を読み込みました");
	}

	public void saveDataYml() {
		File dataYmlFile = new File(getDataFolder() + "/data.yml");
		if (dataYmlFile.exists()) {
			try {
				dataYml.save(dataYmlFile);
			} catch (IOException e) {
				log("§cdata.yml のセーブ中にエラーが発生しました");
				e.printStackTrace();
			}
		}
		log("§adata.yml をセーブしました");
	}

	public FileConfiguration getDataYml() {
		return dataYml;
	}

	public static SWKillEffect getInstance() {
		return instance;
	}

	public static void log(String text) {
		Bukkit.getConsoleSender().sendMessage("§8[§cSWKillEffect§8] §r" + text);
	}

	public KillEffectManager getKillEffectManager() {
		return killEffectManager;
	}

	public GuiKillEffectSelector getGuiKillEffectSelector() {
		return guiKillEffectSelector;
	}

}
