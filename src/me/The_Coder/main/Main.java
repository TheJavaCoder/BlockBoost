package me.The_Coder.main;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import me.The_Coder.Listeners.WalkingListener;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin{

	
	
	
	
	public HashMap<Location, String> hashloc = new HashMap<Location, String>();
	public HashMap<String, Location> pre = new HashMap<String, Location>();
	
	Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		
		if(label.equalsIgnoreCase("powerup")) {
			if(args.length == 0) {
				
				sender.sendMessage(ChatColor.RED + "to few args!");
			
			}else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("saveconfig") && sender.isOp()) {
					
					saveConfig();
					MemoryLoad();
				}
				if(args[0].equalsIgnoreCase("reloadconfig") && sender.isOp()) {
					
					reloadConfig();
					MemoryLoad();
					
				}
			}else if (args.length == 2 || args.length == 4) {

				if(args[0].equalsIgnoreCase("presets") || args[0].equalsIgnoreCase("preset")) {
					if(sender.isOp()) {
						
						if(args[1].equalsIgnoreCase("list")) {
							ConfigurationSection sec = getConfig().getConfigurationSection(
									"Settings.presets");
							
							Set<String> names = sec.getKeys(false);
							
							for(String name : names) {
								sender.sendMessage(ChatColor.BLUE + "Name: " + name + " Value: " + getConfig().getString("Settings.presets." + name));
							}
						}
						
						

						if(args[1].equalsIgnoreCase("create")) {
							
							if(args[2] != null && args[3] != null) {
								
								sender.sendMessage(ChatColor.BLUE + "Created preset: " + args[2]);
								getConfig().set("Settings.presets." + args[2], args[3]);
								saveConfig();
							}else {
								sender.sendMessage(ChatColor.RED + "usage: /powerup presets create <name> <jump + speed>");
							}
						}
					}else {
						sender.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
					}


				}
			
			}else if(args.length == 3) {
				if(args[0].equalsIgnoreCase("create")) {
					if(sender instanceof Player) {
						if(sender.isOp()) {
							if(args[1] != null) {
								if(args[2] != null) {
									Player player = (Player) sender;
									String worldname = player.getWorld().getName();
									double x = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getX();
									double y = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getY();
									double z = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getZ();


									getConfig().set("Locations." + args[1] + ".world", worldname);
									getConfig().set("Locations." + args[1] + ".x", x);
									getConfig().set("Locations." + args[1] + ".y", y);
									getConfig().set("Locations." + args[1] + ".z", z);
									getConfig().set("Locations." + args[1] + ".power", args[2]);
									saveConfig();

									hashloc.put(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(), args[2]);
									
								}else {
									sender.sendMessage(ChatColor.RED + "usage: /powerup create <name> <preset>");
								}
							}
						}else {
							sender.sendMessage(ChatColor.RED + "You have to be OP to use this command");
						}
					}else {
						sender.sendMessage("You must be a player to use this command");
					}
				}
				
				
			}
			//end of 3 args nest
			if(args.length == 4) {
				
			}
			
			
		}
		
		
		return false;
	}

	@Override
	public void onDisable() {
	
		saveConfig();
		
	}

	
	
	
	@Override
	public void onEnable() {
		
		
		
		getServer().getPluginManager().registerEvents(new WalkingListener(this), this);
		
		
		if(!getConfig().contains("Settings")) {
			
			List<String> blocks = new ArrayList<String>();
			
			blocks.add("red_wool");
			
			
			saveConfig();
			getConfig().set("Settings.BlockList", blocks);
			getConfig().set("Settings.BlockList.red_wool", "one");
			getConfig().set("Settings.presets.one", "2+2");
			
			saveConfig();
		}else {
			
			MemoryLoad();
			
			log.info("config loaded!");
			
		}
		
	}

	public void MemoryLoad() {
		if(getConfig().contains("Locations")) {
			if(!hashloc.isEmpty()) {
				hashloc.clear();
			}
			ConfigurationSection sec = getConfig().getConfigurationSection(
					"Locations");

			Set<String> names = sec.getKeys(false);

			for(String name : names) {
				World world = getServer().getWorld(getConfig().getString("Locations." + name + ".world"));
				double x = Double.parseDouble(getConfig().getString("Locations." + name + ".x"));
				double y = Double.parseDouble(getConfig().getString("Locations." + name + ".y"));
				double z = Double.parseDouble(getConfig().getString("Locations." + name + ".z"));
				String power = getConfig().getString("Locations." + name + ".power");
				Location location = new Location(world, x, y, z);
				log.info(serializeLoc(location));
				hashloc.put(location, power);
				
			}
		}
	}

	public String serializeLoc(Location l){
		return l.getWorld().getName()+","+l.getBlockX()+","+l.getBlockY()+","+l.getBlockZ();
	}
	
	public Location deserializeLoc(String s){
		String[] st = s.split(",");
		return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]));
	}
	
	
}
