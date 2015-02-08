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

		savePlayer();
		saveEntities();
	}

	public void loadWorld() {
		createFiles();

		loadPlayer();
	}

	public void savePlayer() {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(FileUtil.path + "Worlds/" + world.getName() + "/player.dat");
			fileWriter.write("X: " + world.getPlayer().getX() + "\n");
			fileWriter.write("Y: " + world.getPlayer().getY() + "\n");
			fileWriter.write("Right: " + world.getPlayer().isFacingRight());
			fileWriter.write("Health: " + world.getPlayer().getHealth() + " " + world.getPlayer().getMaxHealth());
			fileWriter.write("Hunger: " + world.getPlayer().getHunger() + " " + world.getPlayer().getMaxHunger());
			fileWriter.write("Stamina: " + world.getPlayer().getStamina() + " " + world.getPlayer().getMaxStamina());
			
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
	
	public void saveEntities(){
		FileWriter fileWriter;
		try{
			fileWriter = new FileWriter(FileUtil.path + "Worlds/" + world.getName() + "/entities.dat");
			fileWriter.write("");
			fileWriter.close();
		}catch(IOException ex){
			ex.printStackTrace();
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
					world.getPlayer().setY(Integer.parseInt(data.split(" ")[1]));
				}
				
				if(data.startsWith("Right")){
					world.getPlayer().setFacingRight(Boolean.parseBoolean(data.split(" ")[1]));
				}
				
				if(data.startsWith("Health: ")){
					world.getPlayer().setHealth(Integer.parseInt(data.split(" ")[1]));
					world.getPlayer().setMaxHealth(Integer.parseInt(data.split(" ")[2]));
				}
				
				if(data.startsWith("Hunger: ")){
					world.getPlayer().setHunger(Integer.parseInt(data.split(" ")[1]));
					world.getPlayer().setMaxHunger(Integer.parseInt(data.split(" ")[2]));
				}
				
				if(data.startsWith("Stamina: ")){
					world.getPlayer().setStamina(Double.parseDouble(data.split(" ")[1]));
					world.getPlayer().setMaxStamina(Double.parseDouble(data.split(" ")[2]));
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
