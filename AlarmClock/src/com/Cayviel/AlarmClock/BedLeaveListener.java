package com.Cayviel.AlarmClock;

import java.util.Properties;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerListener;

public class BedLeaveListener extends PlayerListener{

	public BedLeaveListener(AlarmConfig passedconfig){
//		AlarmConfig clockconfig = passedconfig;
	};//unnecessary now?
	
	private Properties properties = AlarmClock.properties;
	
	public void onPlayerBedLeave (PlayerBedLeaveEvent PlayerLeavingBed)
	{
      	String world = properties.getProperty("level-name");
		Player player = PlayerLeavingBed.getPlayer();
		int wakeuptime = BedEnterListener.firstplayersalarm;
		long time = player.getServer().getWorld(world).getFullTime();
		BedEnterListener.playersinbed--;
		
        if((time >= 0 && time < 6000 )||(time > 23250 ))
                { if((!BedEnterListener.snoozenow)&&(BedEnterListener.playersinbed==0)) //if not within Snooze and is last person out 
                        player.getServer().getWorld(world).setFullTime(wakeuptime);//then set the time to the players sleep time
                		player.getServer().getWorld(world).setTime(wakeuptime);//then set the time to the players sleep time
						BedEnterListener.snoozenow = false;
                }
	}
}