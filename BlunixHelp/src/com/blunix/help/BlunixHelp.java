package com.blunix.help;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.blunix.help.commands.CommandCompleter;
import com.blunix.help.commands.CommandRunner;
import com.blunix.help.files.DescriptionsFile;

public class BlunixHelp extends JavaPlugin {
	private DescriptionsFile descriptionData;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		descriptionData = new DescriptionsFile(this);
		
		getCommand("help").setExecutor(new CommandRunner(this));
		getCommand("help").setTabCompleter(new CommandCompleter());
	}

	@Override
	public void onDisable() {

	}
	
	public FileConfiguration getDescriptionData() {
		return descriptionData.getConfig();
	}
	
	public void saveDescriptionData() {
		descriptionData.saveConfig();
	}
	
	public void reloadDescriptionData() {
		descriptionData.reloadConfig();
	}
}
