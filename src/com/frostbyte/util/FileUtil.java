package com.frostbyte.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	public static String dirPath = null;
	public static String path = null;
	
	public FileUtil(){
		try{
			dirPath = System.getProperty("user.dir");
			path = dirPath + "/Frostbyte/Frameshift/";
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static boolean checkFile(String location){
		if(new File(path + location).exists()){
			return true;
		}
		
		return false;
	}
	
	public static void createFile(String name){
		try {
			new File(path + name).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createDirectory(String directory){
		new File(path + directory + "/").mkdirs();
	}
	
	public static boolean isDirectory(String directory){
		return new File(path + directory + "/").mkdirs();
	}
}
