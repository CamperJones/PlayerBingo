package me.camper.bingo.inventario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.camper.bingo.Bingo;
import me.camper.bingo.jogador.Cartela;
//40--- //50 ----//60
public class Inventario 
{	
	private Cartela cartela;
	
	Plugin plugin = Bingo.getPlugin(Bingo.class); 
	
	public void newInventory(Player player, int tipo, int id) 
	{							
		List<Integer> numeros = new ArrayList<Integer>();
		List<Integer> clicados = new ArrayList<Integer>();	
		Inventory inv ;

		if(Bingo.cartelas.containsKey(tipo)) //existe o tipo cadastrado 
		{ 
			if(Bingo.cartelas.get(tipo).containsKey(id)) //existe id da cartelas cadastrado?
				cartela = Bingo.cartelas.get(tipo).get(id);	//pegar a cartela				
			else
			{		
				cartela = new Cartela(tipo, id);	
				Bingo.cartelas.get(tipo).put(id, cartela); //cadastrar a cartela, no tipo;
			}
		}
		else
		{		
			HashMap<Integer, Cartela> aux = new HashMap<Integer, Cartela>();
			cartela = new Cartela(tipo, id); //gerar nova cartela		
			aux.put(id,cartela); 
			Bingo.cartelas.put(tipo, aux); //cadastrar novo tipo e nova cartela
		}
		
		clicados = cartela.getClicados();
		numeros = cartela.getLista();
			
		if(tipo >= 20)
			inv = plugin.getServer().createInventory(null, 27, ChatColor.translateAlternateColorCodes('&',"&0&lCARTELA &8Nº " + id));
		else
			inv = plugin.getServer().createInventory(null, 18, ChatColor.translateAlternateColorCodes('&',"&0&lCARTELA &8Nº " + id));
		
		for(int i = 0; i<numeros.size(); i++)
		{			
			ItemStack bloco = new ItemStack(Material.WHITE_CONCRETE);
			
			for(int x = 0; x<clicados.size(); x++)
				if(clicados.get(x).equals(numeros.get(i))){
					bloco = new ItemStack(Material.LIGHT_BLUE_CONCRETE);
					break;
				}
			
			ItemMeta meta = bloco.getItemMeta();		
			meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + numeros.get(i));	
			bloco.setItemMeta(meta);
			
			inv.setItem(i, bloco);
		}	
		
		player.openInventory(inv);
	}
}