package com.blunix.help.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messager {

	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	public static void sendNoPermissionMessage(CommandSender sender) {
		sendMessage(sender, "&cYou do not have permissions to use this command!");
	}
}
