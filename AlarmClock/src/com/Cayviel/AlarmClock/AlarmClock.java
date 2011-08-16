
package com.Cayviel.AlarmClock;
   
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
   
public class AlarmClock extends JavaPlugin
{
	public static Logger log = Logger.getLogger("Minecraft");
	
    public static PermissionHandler permissionHandler;

	public static String directory = "plugins" + File.separator + "AlarmClock";	
    public static File configFile = new File(directory + File.separator + "config.yml");
    public static File serverproperties = new File("server.properties");
	public static Properties properties = new Properties();
	public static AlarmConfig alarmconfig = new AlarmConfig(configFile,directory);
	
    public BedLeaveListener WakeMeUp = new BedLeaveListener(alarmconfig); 
    public BedEnterListener ImInBed = new BedEnterListener(alarmconfig);
    
	public void onEnable(){
		log.info("[AlarmClock]: Starting...");
		PluginManager pm = getServer().getPluginManager();
		alarmconfig.configCheck();
		pm.registerEvent(Event.Type.PLAYER_BED_LEAVE, WakeMeUp, Event.Priority.High, this);
		pm.registerEvent(Event.Type.PLAYER_BED_ENTER, ImInBed, Event.Priority.Normal, this);
		setupPermissions();

	    try {
			FileInputStream inputfile = new FileInputStream(serverproperties);
			properties.load(inputfile);
			inputfile.close();
		} catch (IOException eceptin) {
			eceptin.printStackTrace();
		}		
		log.info("[AlarmClock]: Started!");
	}
	
       public void onDisable()
       { 
                log.info("[AlarmClock]: Stopped.");
       }
       
       @Override
       public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
    {    if (sender instanceof Player){

    if (args.length >= 1){
		Player player = (Player)sender;
           String commandName = command.getName().toLowerCase();
           if(commandName.equals("alarmclock"))
           {                    
                if (args.length == 1)
                {
                	try{
                       int timesetto = Integer.parseInt(args[0]);
                       alarmconfig.setPlayerClock(player, timesetto);
                   }catch(NumberFormatException notanumber)
                   {
                       player.sendMessage(args[0] + " is not a number!");
                       return false;
                   }
                	player.sendMessage("AlarmClock set to "+ args[0]);
                   return true;
                }
                player.sendMessage(args[0] + " too many arguments");
                return false;
           }else{
        	   if(commandName.equals("getalarmclock")){
                   player.sendMessage("Your clock is set to "+ alarmconfig.getPlayerClock(player));
                   return true;
        	   	}
        	   		return false;
           		}
       		}
    	}
    return false;
    }
       
    private void setupPermissions() { 
		if (permissionHandler != null) { return; }

		Plugin permissionsPlugin = getServer().getPluginManager().getPlugin("Permissions");

		if (permissionsPlugin == null) {
				log.info("Permission system not detected, defaulting to OP");
				return;
		}
		permissionHandler = ((Permissions) permissionsPlugin).getHandler();
		log.info("Found and will use plugin "+((Permissions)permissionsPlugin).getDescription().getFullName());
	}
    
    public static AlarmConfig getAlarmConfig(){
    	return alarmconfig;
    	}
}

