package me.camper.bingo;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class FileConfig {

	public static YamlConfiguration getRegion() {
		
  		File playerdata;
  		playerdata = new File("plugins//PlayerBingo//region.yml");
		YamlConfiguration Data = YamlConfiguration.loadConfiguration((File) playerdata);

		return Data;
	}
}
