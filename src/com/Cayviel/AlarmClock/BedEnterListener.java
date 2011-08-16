package com.Cayviel.AlarmClock;


import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerListener;

public class BedEnterListener extends PlayerListener{

	AlarmConfig clockconfig;
	public BedEnterListener(AlarmConfig passedconfig){
		clockconfig=passedconfig;
	};// unnecessary?

	public static int playersinbed = 0;
	public static int firstplayersalarm = 22500; //default value... it shouldn't be used- here to avoid null pointers
	public static boolean snoozenow = false;
	
	public void onPlayerBedEnter (PlayerBedEnterEvent PlayerEnteringBed)
	{
		if (clockconfig == null){ clockconfig = AlarmClock.getAlarmConfig(); }
		if (!(PlayerEnteringBed.isCancelled())){
			playersinbed++;
			Player player = PlayerEnteringBed.getPlayer();
			if (playersinbed == 1){
				firstplayersalarm = clockconfig.getPlayerClock(player);
				
				String world = AlarmClock.properties.getProperty("level-name");
				long time = player.getServer().getWorld(world).getFullTime();
				long timeElapsed = time - firstplayersalarm;
				int snoozebuffer = clockconfig.getSnooze();
				
				if (timeElapsed <= snoozebuffer)
					snoozenow = true;
				}
		}
	}
}