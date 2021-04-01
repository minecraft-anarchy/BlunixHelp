package com.blunix.help.commands;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
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
			plugin.reloadDescriptionData();
			Messager.sendMessage(sender, "&aConfig reloaded.");
			return true;
		}
		String message = plugin.getConfig().getString("additional-top-message") + "\n";
		Iterator<Plugin> iterator = getPluginsToDisplay();
		while (iterator.hasNext()) {
			Plugin plugin = iterator.next();
			if (plugin == null)
				continue;

			message += getPluginHelpMessage(plugin);
			if (iterator.hasNext())
				message += "\n";
		}
		message += "\n" + plugin.getConfig().getString("additional-bottom-message");
		Messager.sendMessage(sender, message);
		return true;
	}

	private String getPluginHelpMessage(Plugin helpPlugin) {
		String format = plugin.getConfig().getString("help-format");
		String pluginName = helpPlugin.getName();
		String pluginDescription = helpPlugin.getDescription().getDescription();
		if (pluginDescription == null && getCustomPluginDescription(helpPlugin) != null
				&& getCustomPluginDescription(helpPlugin).length() > 0)
			pluginDescription = getCustomPluginDescription(helpPlugin);

		else if (pluginDescription == null || pluginDescription.length() == 0) {
			pluginDescription = "Unknown. Add a custom plugin description in the 'plugin_descriptions' file.";
			addInputToData(pluginName);
		}
		return format.replace("{PLUGIN}", pluginName).replace("{DESCRIPTION}", pluginDescription);
	}

	private String getCustomPluginDescription(Plugin helpPlugin) {
		FileConfiguration data = plugin.getDescriptionData();
		ConfigurationSection section = data.getConfigurationSection("plugin-descriptions");
		if (section == null || !section.contains(helpPlugin.getName()))
			return null;

		return section.getString(helpPlugin.getName());
	}

	private Iterator<Plugin> getPluginsToDisplay() {
		ArrayList<Plugin> toDisplay = new ArrayList<Plugin>();
		for (String pluginName : plugin.getConfig().getStringList("plugins-to-display")) {
			Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
			if (plugin == null) {
				Bukkit.getLogger().info("Plugin: \"" + pluginName + "\" not found in the plugins folder.");
				continue;
			}
			toDisplay.add(Bukkit.getPluginManager().getPlugin(pluginName));
		}
		return toDisplay.iterator();
	}

	private void addInputToData(String pluginName) {
		plugin.getDescriptionData().set("plugin-descriptions." + pluginName, "");
		plugin.saveDescriptionData();
	}
}
