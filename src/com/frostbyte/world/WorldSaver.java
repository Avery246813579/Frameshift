package com.frostbyte.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Material;
import com.frostbyte.util.FileUtil;

public class WorldSaver {
	World world;

	public WorldSaver(World world) {
		this.world = world;
	}

	public void createFiles() {
		if (!FileUtil.isDirectory("Worlds")) {
			FileUtil.createDirectory("Worlds");
		}

		if (!FileUtil.isDirectory("Worlds/" + world.getName())) {
			FileUtil.createDirectory("Worlds/" + world.getName());
		}

		if (!FileUtil.checkFile("Worlds/" + world.getName() + "/blocks.dat")) {
			FileUtil.createFile("Worlds/" + world.getName() + "/blocks.dat");
		}

		if (!FileUtil.checkFile("Worlds/" + world.getName() + "/player.dat")) {
			FileUtil.createFile("Worlds/" + world.getName() + "/player.dat");
		}
	}

	public void saveWorld() {
		createFiles();

		saveBlocks();
		savePlayer();
	}

	public void loadWorld() {
		createFiles();

		loadBlocks();
		loadPlayer();
	}

	public void saveBlocks() {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(FileUtil.path + "Worlds/" + world.getName() + "/blocks.dat");
			for (int x = 0; x < world.getBlocks().length; x++) {

				String chunkString = "";
				for (int y = 0; y < world.getBlocks()[0].length; y++) {
					chunkString = chunkString + " " + world.getBlocks()[x][y].getMaterial().toString() + "-" + world.getBlocks()[x][y].getDuration();
				}

				chunkString = chunkString.substring(1, chunkString.length());
				chunkString = chunkString + "\n";
				fileWriter.write(chunkString);
			}

			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void savePlayer() {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(FileUtil.path + "Worlds/" + world.getName() + "/player.dat");
			fileWriter.write("X: " + world.getPlayer().getX() + "\n");
			fileWriter.write("Y: " + world.getPlayer().getX() + "\n");

			String inventoryString = "";
			for (int i = 0; i < world.getPlayer().getInventory().getContent().length; i++) {
				ItemStack itemStack = world.getPlayer().getInventory().getContent()[i];

				if (itemStack != null) {
					inventoryString = inventoryString + " " + itemStack.getMaterial().toString();
				} else {
					inventoryString = inventoryString + " " + "NULL";
				}
			}

			inventoryString = inventoryString.substring(1, inventoryString.length());
			fileWriter.write("Inventory: " + inventoryString + "\n");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadBlocks() {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(new File(FileUtil.path + "Worlds/" + world.getName() + "/blocks.dat")));

			String data;
			int line = 0;
			while ((data = fileReader.readLine()) != null) {
				int y = 0;
				for (String s : data.split(" ")) {
					if (world.getBlocks()[line][y].getMaterial() != Material.AIR) {
						world.getBlocks()[line][y].setMaterial(Material.fromString(s.split("-")[0]));
						world.getBlocks()[line][y].setDuration((Integer.parseInt(s.split("-")[1])));
					}

					y++;
				}

				line++;
			}

			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadPlayer() {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(new File(FileUtil.path + "Worlds/" + world.getName() + "/player.dat")));

			String data;
			while ((data = fileReader.readLine()) != null) {
				if (data.startsWith("X: ")) {
					world.getPlayer().setX(Integer.parseInt(data.split(" ")[1]));
				}

				if (data.startsWith("Y: ")) {
					world.getPlayer().setX(Integer.parseInt(data.split(" ")[1]));
				}

				if (data.startsWith("Inventory:")) {
					int i = -1;

					for (String s : data.split(" ")) {
						if (i != -1) {
							if (!s.equalsIgnoreCase("NULL")) {
								world.getPlayer().getInventory().setItem(i, new ItemStack(Material.fromString(s), 1));
							}
						}

						i++;
					}
				}
			}

			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
