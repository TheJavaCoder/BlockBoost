package me.The_Coder.Listeners;

import me.The_Coder.main.Main;


import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.Wool;


import org.bukkit.util.Vector;




public class WalkingListener implements Listener{
	
	
	Main plugin;
	
	
	public WalkingListener (Main main) {
		
		plugin = main;
		
	}

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		if(e.isCancelled() || e.getFrom().getBlock().getLocation() == e.getTo().getBlock().getLocation()) 
			return;
		
		Player player= e.getPlayer();
		Block blockbelow = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		
		if(playerLocation(player, player.getLocation()) == false)
			return;
			
		
		
		if(checkPlayer(player) == true) {
			
			setBlockEffect(blockbelow.getLocation(), player);
			return;
			
		}
		
		
		if(blockbelow.getType().equals(Material.WOOL) && checkPlayer(player) == false) {
			
			
			//Wool wool = new Wool(blockbelow.getType(), blockbelow.getData());
			Wool wool = new Wool(blockbelow.getType(), blockbelow.getData());
			
			DyeColor dye = wool.getColor();
			switch (dye) {
			case RED:
				
					SetEffectWool(player, "red");
				
				break;

			case WHITE:
				
					SetEffectWool(player, "white");
				
				break;
			
			case BLACK:
				
					SetEffectWool(player, "black");
				
				break;
			
			case BROWN:
					
					SetEffectWool(player, "brown");
				
				break;
				
			case BLUE:
				
					SetEffectWool(player, "blue");
				
				break;
				
			case CYAN:
				
					SetEffectWool(player, "cyan");
				
				break;
				
			case GRAY:
				
					SetEffectWool(player, "grey");
				
				break;
				
			case GREEN:
				
					SetEffectWool(player, "green");
				
				break;
				
			case LIGHT_BLUE:
				
					SetEffectWool(player, "light_blue");
				
				break;
				
			case LIME:
				
					SetEffectWool(player, "lime");
				
				break;
				
			case MAGENTA:
				
					SetEffectWool(player, "magenta");
				
				break;
				
			case ORANGE:
				
					SetEffectWool(player, "orange");
				
				break;
				
			case PINK:
				
					SetEffectWool(player, "pink");
				
				break;
				
			case PURPLE:
				
					SetEffectWool(player, "purple");
				
				break;
				
			case SILVER:
				
					SetEffectWool(player, "silver");
				
				break;
				
			case YELLOW:
				
					SetEffectWool(player, "yellow");
				
				break;
			default:
				break;
				
				
			}
			

			
		}
		
		
	}
	
	public void SetEffectWool(Player player, String color) {

		Vector pv = player.getVelocity();

		if(plugin.getConfig().contains("Settings.BlockList." + color + "_wool")) {
			String num = plugin.getConfig().getString("Settings.BlockList." + color + "_wool");
			String preset = plugin.getConfig().getString("Settings.presets." + num);
			String[] strings = preset.split("\\+");
			double jump = Double.parseDouble(strings[0]);
			double speed = Double.parseDouble(strings[1]);

			
				
			
				
				Vector vet = pv.add(new Vector(player.getLocation().getDirection().getX() * speed / 2, jump / 10, player.getLocation().getDirection().getZ() * speed / 2));
				player.setVelocity(vet);
				
			

				
		}

	} 

	public boolean playerLocation(Player player, Location loc) {
		if(!plugin.pre.containsKey(player.getName())) {
			plugin.pre.put(player.getName(), loc);
		}
	
		if(plugin.pre.get(player.getName()) == loc) {
			return false;
		}else {
			plugin.pre.put(player.getName(), loc); 
			return true;
		}
	}
	
	
	
	public boolean checkPlayer(Player player) {
		Location ploc = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();
		
		if(plugin.hashloc.containsKey(ploc)) {
			return true;
		}
		return false;
	}
	
	public void setBlockEffect(Location loc, Player player) {
		
		String power = plugin.getConfig().getString("Settings.presets." + plugin.hashloc.get(loc));
		
		String[] strings = power.split("\\+");
		double jump = Double.parseDouble(strings[0]);
		double speed = Double.parseDouble(strings[1]);
		
			
		
			Vector vet = player.getVelocity().add(new Vector(player.getLocation().getDirection().getX() * speed / 2, jump / 10, player.getLocation().getDirection().getZ() * speed / 2));
			player.setVelocity(vet);
		
		
	}
}
