package me.camper.bingo.inventario;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.camper.bingo.Bingo;

public class Confirmar {
	Plugin plugin = Bingo.getPlugin(Bingo.class); 
	
	public void newInventory(Player player, String tipo)
	{
		Inventory inv = plugin.getServer().createInventory(null, 27, ChatColor.translateAlternateColorCodes('&',"&c&lENCERRAR RODADA: "));
		ArrayList<String> lore = new ArrayList<String>();
		
		if(tipo.contains("Ouro"))
			tipo = "&6Ouro";
		if(tipo.contains("Diamante"))
			tipo = "&bDiamante";
		if(tipo.contains("Esmeralda"))
			tipo = "&aEsmeralda";
		if(tipo.contains("Netherite"))
			tipo = "&8Netherite";
    				
		for(int i = 11 ; i < 19; i+=4)
		{				
			ItemStack bloco = new ItemStack(Material.GREEN_CONCRETE);
			ItemMeta meta = bloco.getItemMeta();;
			
			lore.clear();	
			lore.add(ChatColor.translateAlternateColorCodes('&', "&7Clique para confirmar"));
			if(i==11) {
				bloco = new ItemStack(Material.GREEN_CONCRETE);		
				meta = bloco.getItemMeta();				
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', tipo + " &e&lEncerrar!"));
			}
			if(i==15) {
				bloco = new ItemStack(Material.RED_CONCRETE);		
				meta = bloco.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&cCancelar"));
			}
	
			meta.setLore(lore);
			bloco.setItemMeta(meta);			
			inv.setItem(i, bloco);
		}
		player.openInventory(inv);
	}
}
