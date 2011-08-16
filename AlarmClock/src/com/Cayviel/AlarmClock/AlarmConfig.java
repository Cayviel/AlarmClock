package com.Cayviel.AlarmClock;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

public class AlarmConfig{

    public String directory;
    public File configFile;
    
	AlarmConfig(File conf, String dir){
		directory = dir;
		configFile = conf;
	}

	public void configCheck(){
	        new File(directory).mkdir();
	        if(!configFile.exists()){
	            try {
	            	configFile.createNewFile();
	                addDefaults();

	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        } else {

	            loadkeys();
	        }
	    }
	
   public void write(String root, Object x){
       Configuration config = load();
       config.setProperty(root, x);
       config.save();
   }
   
    private int readInt(String root)
    {
       Configuration config = load();
       return config.getInt(root,getDefaultClock());
    }


   private Configuration load(){

       try {
           Configuration config = new Configuration(configFile);
           config.load();
           return config;

       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
   }
   
   private void addDefaults(){
    AlarmClock.log.info("Generating Config File...");
    write("Default Alarm",22500);
    write("Snooze Time",6000);
    loadkeys();
   }
   
   private void loadkeys(){
       AlarmClock.log.info("Loading Config File...");
   }
   
   public void setPlayerClock(Player player, int time){
	   write("Players."+player.getName(),time);
   }

   public int getPlayerClock(Player player){
	   return readInt("Players."+player.getName());
   }
   
   public int getDefaultClock(){
       Configuration config = load();
       return config.getInt("Default",22500);
   }
   
   public int getSnooze(){
       Configuration config = load();
       return config.getInt("Snooze Time",6000);
   }
}
