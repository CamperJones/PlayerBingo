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

public class ComprarCartela 
{	
	Plugin plugin = Bingo.getPlugin(Bingo.class); 
	
	public void newInventory(Player player)
	{
		Inventory inv = plugin.getServer().createInventory(null, 45, ChatColor.translateAlternateColorCodes('&',"&l&bSky&l&eBingo"));
		ArrayList<String> lore = new ArrayList<String>();
						
		for(int i = 10; i < 18; i+=2)
		{
			ItemStack bloco = new ItemStack(Material.CACTUS);		
			ItemMeta meta = bloco.getItemMeta();
			
			lore.clear();		
			if(i==10) {
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&2Cartela &6&lOuro"));
				lore.add(ChatColor.translateAlternateColorCodes('&', "&78 números"));
				lore.add(ChatColor.translateAlternateColorCodes('&', "&fPegar livro"));
			}
			if(i==12) {
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&2Cartela &b&lDiamante"));
				lore.add(ChatColor.translateAlternateColorCodes('&', "&710 números"));
				lore.add(ChatColor.translateAlternateColorCodes('&', "&fPegar livro"));
			}
			if(i==14) {
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&2Cartela &a&lEsmeralda"));
				lore.add(ChatColor.translateAlternateColorCodes('&', "&715 números"));
				lore.add(ChatColor.translateAlternateColorCodes('&', "&fPegar livro"));
			}	
			if(i==16) {
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&2Cartela &8&lNetherite"));
				lore.add(ChatColor.translateAlternateColorCodes('&', "&720 números"));
				lore.add(ChatColor.translateAlternateColorCodes('&', "&fPegar livro"));
			}	
			meta.setLore(lore);
			bloco.setItemMeta(meta);			
			inv.setItem(i, bloco);
		}	
		
		for(int i = 19; i < 27; i+=2)
		{
			ItemStack bloco = new ItemStack(Material.BARRIER);		
			ItemMeta meta = bloco.getItemMeta();
			
			lore.clear();		
			lore.add(ChatColor.translateAlternateColorCodes('&', "&cEncerrar!"));
			if(i==19)
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6Ouro"));			
			if(i==21) 
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&bDiamante"));			
			if(i==23) 
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&aEsmeralda"));
			if(i==25) 
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&8Netherite"));
							
			meta.setLore(lore);
			bloco.setItemMeta(meta);			
			inv.setItem(i, bloco);
		}		
		
		for(int i = 1; i < 9; i+=2)
		{
			ItemStack bloco = new ItemStack(Material.PAPER);		
			ItemMeta meta = bloco.getItemMeta();
			
			lore.clear();		
			lore.add(ChatColor.translateAlternateColorCodes('&', "&7Próxima"));
			lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cartela"));
			if(i==1) {
				int tipoID =  plugin.getConfig().getInt("Bingo." + ".Ouro") +1; 
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6Nº " + tipoID));
			}
			if(i==3) { 
				int tipoID =  plugin.getConfig().getInt("Bingo." + ".Diamante") +1;
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&bNº " + tipoID));
			}
			if(i==5) {
				int tipoID =  plugin.getConfig().getInt("Bingo." + ".Esmeralda") +1;
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&8Nº " + tipoID));
			}
			if(i==7) {
				int tipoID =  plugin.getConfig().getInt("Bingo." + ".Netherite") +1;
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&8Nº " + tipoID));
			}
							
			meta.setLore(lore);
			bloco.setItemMeta(meta);			
			inv.setItem(i, bloco);
		}	
		
		for(int i = 38; i < 44; i+=4)
		{	
			ItemStack bloco =null;
			ItemMeta meta=null;
			if(i==38) {
				bloco = new ItemStack(Material.OBSIDIAN);	
				meta = bloco.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&cMutar Sala"));
			}
			if(i==42) { 
				bloco = new ItemStack(Material.SEA_LANTERN);	
				meta = bloco.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&aLiberar Sala"));
			}
			bloco.setItemMeta(meta);			
			inv.setItem(i, bloco);
		}
		
		player.openInventory(inv);
	}
}
