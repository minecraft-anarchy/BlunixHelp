package com.blunix.help.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.blunix.help.BlunixHelp;
import com.blunix.help.util.Messager;

public class CommandRunner implements CommandExecutor {
	private BlunixHelp plugin;

	public CommandRunner(BlunixHelp plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("help"))
			return true;
		if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
			plugin.reloadConfig();
			Messager.sendMessage(sender, "&aConfig reloaded.");
			return true;
		}

		String message = "";
		Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
		for (int i = 0; i < plugins.length; i++) {
			Plugin plugin = plugins[i];
			if (plugin == null)
				continue;

			message += getPluginHelpMessage(plugin);
			if (i < plugins.length)
				message += "\n";
		}
		message += plugin.getConfig().getString("additional-message");
		Messager.sendMessage(sender, message);
		return true;
	}

	private String getPluginHelpMessage(Plugin helpPlugin) {
		String format = plugin.getConfig().getString("help-format");
		String pluginName = helpPlugin.getName();
		String pluginDescription = helpPlugin.getDescription().getDescription();
		if (pluginDescription == null && plugin.getConfig().getString("plugin-descriptions." + pluginName) != null)
			pluginDescription = plugin.getConfig().getString("plugin-descriptions." + pluginName);
		
		else if (pluginDescription == null) {
			pluginDescription = "Unknown. Check config file to add description message.";
			addInputToConfig(pluginName);
		}
		
		return format.replace("{PLUGIN}", pluginName).replace("{DESCRIPTION}", pluginDescription);
	}

	private void addInputToConfig(String pluginName) {
		plugin.getConfig().set("plugin-descriptions." + pluginName, "");
		plugin.saveConfig();
	}
}
