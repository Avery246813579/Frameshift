package com.frostbyte.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.frostbyte.main.GameManager;

public class CommandManager {
	List<Command> commands = new ArrayList<Command>();
	public GameManager gameManager;
	Scanner scanner;

	public CommandManager(GameManager gameManager) {
		this.gameManager = gameManager;
		scanner = new Scanner(System.in);
		
		commands.add(new CommandClearInventory(gameManager));
		commands.add(new TeleportCommand(gameManager));
		checkCommand();
	}

	public void checkCommand() {
		String commandString = scanner.nextLine();

		for (Command command : commands) {
			if (commandString.split(" ")[0].equalsIgnoreCase(command.getCommand())) {
				command.onCommand(commandString.split(" "));
				checkCommand();
				return;
			}
		}
		
		System.out.println("Command not found!");
		checkCommand();
	}
}
