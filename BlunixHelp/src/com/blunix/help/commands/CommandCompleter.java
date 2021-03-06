package com.blunix.help.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<String> arguments = new ArrayList<String>();
		if (arguments.isEmpty())
			arguments.add("reload");

		if (args.length >= 0 && args.length < 2)
			return getCompletion(arguments, args, 0);

		arguments.clear();
		return arguments;
	}

	private ArrayList<String> getCompletion(ArrayList<String> arguments, String[] args, int index) {
		ArrayList<String> results = new ArrayList<String>();
		for (String argument : arguments) {
			if (!argument.toLowerCase().startsWith(args[index].toLowerCase()))
				continue;

			results.add(argument);
		}
		return results;
	}
}
