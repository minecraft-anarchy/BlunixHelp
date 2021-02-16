package com.blunix.help;

import org.bukkit.plugin.java.JavaPlugin;

import com.blunix.help.commands.CommandRunner;

public class BlunixHelp extends JavaPlugin {

	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		getCommand("help").setExecutor(new CommandRunner(this));
		getCommand("help").setTabCompleter(new CommandCompleter());
	}

	@Override
	public void onDisable() {

	}
}
