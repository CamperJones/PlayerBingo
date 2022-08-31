package me.camper.bingo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.camper.bingo.inventario.ComprarCartela;

public class Commands implements CommandExecutor
{		
	public String cmd1 = "skybingo";	
	
	Plugin plugin = Bingo.getPlugin(Bingo.class); 
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{ 		
		ComprarCartela painel = new ComprarCartela();
		
		if(sender instanceof Player)
		{
			Player player = ((Player) sender);
			if(args.length ==0)
			{
				if(cmd.getName().equalsIgnoreCase(cmd1) && player.hasPermission("bingo.sky")) 		
					painel.newInventory(player);	
			}
		}	
		return true;
	}
}
	
