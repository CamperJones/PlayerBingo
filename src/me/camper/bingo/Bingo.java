package me.camper.bingo;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.camper.bingo.events.Events;
import me.camper.bingo.jogador.Cartela;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class Bingo extends JavaPlugin implements Listener {
	
	public static HashMap<Integer, HashMap<Integer, Cartela>> cartelas = new HashMap<Integer, HashMap<Integer, Cartela>>();
	private static File region;
	
	public void onEnable() {   	 
	
		Commands commands = new Commands(); 		   
		getCommand(commands.cmd1).setExecutor(commands);
		 
		getServer().getPluginManager().registerEvents((Listener)new Events(), this);
		 
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Bingo foi habilitado com sucesso!");	   	
		 
		 
		region = new File(getDataFolder() + File.separator + "region");
		if (!region.exists())
			region.mkdirs();
		
		 
		
		
		File playerfile;
		playerfile = new File("plugins//PlayerBingo//region.yml");
		if (!playerfile.exists()) {
			
		try {
			
			playerfile.createNewFile();
			FileWriter Data = new FileWriter(playerfile);
			
		 	Data.write("bingo:\n"
		 			+ "  region_name: 'salabingo'");
		 	
		 	Data.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	YamlConfiguration.loadConfiguration(playerfile);
		}		

		
		
		
		loadConfig();    
	}
	
	public void loadConfig() {
		
		 getConfig().options().copyDefaults(true);
	     saveConfig();
	}	
	
	public void onDisable() {
		
	    getServer().getConsoleSender().sendMessage(ChatColor.RED + "Bingo foi desabilitado!");
	}	
}
