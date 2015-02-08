package com.frostbyte.world;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.frostbyte.blocks.Block;
import com.frostbyte.display.Location;
import com.frostbyte.util.FileUtil;

public class ChunkHandler {
	public List<WorldChunk> chunks = new ArrayList<WorldChunk>();
	public List<WorldChunk> activeChunks = new ArrayList<WorldChunk>();
	public World world;

	public ChunkHandler(World world) {
		this.world = world;
	}

	public WorldChunk findWorldChunk(int x, int y) {
		for (WorldChunk chunk : chunks) {
			if (chunk.inChunk(new Location(world, x, y))) {
				return chunk;
			}
		}

		return null;
	}

	public void loadChunks() {
		for (File file : FileUtil.getFilesInDirectory(new File(FileUtil.path + "Worlds/" + world.getName() + "/Chunks"))) {
			chunks.add(new WorldChunk(file, world));
		}
	}

	public void createChunks() {
		boolean done = true;
		int maxX = 128;
		int maxY = 128;
		int minX = 0;
		int minY = 0;
		int chunk = 0;

		if (!FileUtil.isDirectory("Worlds")) {
			FileUtil.createDirectory("Worlds");
		}

		if (!FileUtil.isDirectory("Worlds/" + world.getName())) {
			FileUtil.createDirectory("Worlds/" + world.getName());
		}

		if (!FileUtil.checkFile("Worlds/" + world.getName() + "/Chunks")) {
			FileUtil.createDirectory("Worlds/" + world.getName() + "/Chunks");
		}

		while (done) {
			FileUtil.createFile("Worlds/" + world.getName() + "/Chunks/C" + chunk);
			System.out.println(maxX + " " + maxY + " " + minX + " " + minY);

			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(new File(FileUtil.path + "Worlds/" + world.getName() + "/Chunks/C" + chunk));

				fileWriter.write("MIN: " + minX + " " + minY + "\n");
				fileWriter.write("MAX: " + maxX + " " + maxY + "\n");
				for (int x = minX; x < maxX; x++) {
					if (x >= world.getBlocks().length) {
						done = false;
						return;
					}

					String yLine = "";
					for (int y = minY; y < maxY; y++) {
						if (y < world.getBlocks()[0].length) {
							Block block = world.getBlocks()[x][y];

							if (block != null) {
								yLine = yLine + " " + block.getMaterial().getDrawInt() + "-" + block.getDuration();
							}
						}
					}

					try {
						yLine = yLine.substring(1, yLine.length());
					} catch (Exception ex) {
					}

					yLine = yLine + "\n";
					fileWriter.write(yLine);
				}

				fileWriter.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			if (done == false) {
				return;
			}

			chunk++;
			if ((minY + 128) > world.getBlocks()[0].length) {
				minY = 0;
				maxY = 128;

				if ((maxX + 128) > world.getBlocks().length) {
					maxX = world.getBlocks().length - 1;

					if ((minX + 128) > world.getBlocks().length) {
						done = false;
						return;
					} else {
						minX += 128;
					}
				} else {
					maxX += 128;
					minX += 128;
				}
			} else {
				if ((maxY + 128) > world.getBlocks()[0].length) {
					maxY = world.getBlocks()[0].length - 1;
					minY += 128;
				} else {
					maxY += 128;
					minY += 128;
				}
			}
		}
	}

	public void update() {
		if (!activeChunks.isEmpty()) {
			for (WorldChunk worldChunk : activeChunks) {
				if (worldChunk != null) {
					worldChunk.save();
				}
			}
		}

		activeChunks.clear();
	}
}
