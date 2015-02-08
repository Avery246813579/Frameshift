package com.frostbyte.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class WorldChunk {
	private Location minLoc, maxLoc;
	private File chunkFile;
	World world;

	public WorldChunk(File chunkFile, World world) {
		this.chunkFile = chunkFile;
		this.world = world;
		load();
	}

	public void load() {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(chunkFile));

			String data;
			int line = 0;
			while ((data = fileReader.readLine()) != null) {
				if(data.trim().equals("")){
					break;
				}
				
				int y = 0;

				if (data.startsWith("MIN:")) {
					String[] split = data.split(" ");
					minLoc = new Location(world, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
					line = minLoc.getX();
					y = minLoc.getY();
				} else if (data.startsWith("MAX:")) {
					String[] split = data.split(" ");
					maxLoc = new Location(world, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
				} else {
					y = minLoc.getY();
					
					for (String s : data.split(" ")) {
						if (line < world.getBlocks().length && y < world.getBlocks()[0].length) {
							world.getBlocks()[line][y].setMaterial(Material.fromInt(Integer.parseInt(s.split("-")[0])));
							world.getBlocks()[line][y].setDuration((Integer.parseInt(s.split("-")[1])));
						}

						y++;
					}

					line++;
				}
			}

			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void save() {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(chunkFile);

			fileWriter.write("MIN: " + minLoc.getX() + " " + minLoc.getY() + "\n");
			fileWriter.write("MAX: " + maxLoc.getX() + " " + maxLoc.getY() + "\n");
			for (int x = 0; x < world.getBlocks().length; x++) {

				String chunkString = "";
				for (int y = 0; y < world.getBlocks()[0].length; y++) {
					chunkString = chunkString + " " + world.getBlocks()[x][y].getMaterial().getDrawInt() + "-" + world.getBlocks()[x][y].getDuration();
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

	public boolean inChunk(Location location) {
		if (minLoc.getX() < location.getX() && location.getX() < maxLoc.getX()) {
			if (minLoc.getY() < location.getY() && location.getY() < maxLoc.getY()) {
				return true;
			}
		}

		return false;
	}

	public File getChunkFile() {
		return chunkFile;
	}

	public void setChunkFile(File chunkFile) {
		this.chunkFile = chunkFile;
	}
}
