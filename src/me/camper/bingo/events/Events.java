package me.camper.bingo.events;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.camper.bingo.Bingo;
import me.camper.bingo.FileConfig;
import me.camper.bingo.inventario.ComprarCartela;
import me.camper.bingo.inventario.Confirmar;
import me.camper.bingo.inventario.Inventario;
import me.camper.bingo.jogador.Cartela;
import net.raidstone.wgevents.WorldGuardEvents;


public class Events implements Listener
{		
	Plugin plugin = Bingo.getPlugin(Bingo.class); 
	
	private Inventario build = new Inventario();
	private Cartela cartela;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private int mutados=0; 
	
	private String dataAtual; 
	
	@EventHandler
	private void ChatEvent(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		if(mutados == 1 && !player.hasPermission("bingo.sky"))
		{
			try {
				
				if(WorldGuardEvents.isPlayerInAnyRegion(player.getUniqueId(), FileConfig.getRegion().getString("bingo..region_name")))	
				{	
					if(!(event.getMessage().equalsIgnoreCase("bingo")))
					{
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eRodada em andamento, &ctodos foram silenciados&e!"));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eCaso vencer, digite: &2bingo"));
						event.setCancelled(true);
					}
				}
			}catch(Exception e){			
			}
		}
	}
	
	private void encerrarBingo(String tipo)
	{
		if(tipo.contains("Ouro"))
			plugin.getConfig().set("Encerra." + ".Ouro", plugin.getConfig().getInt("Bingo." + ".Ouro"));
		if(tipo.contains("Diamante"))
			plugin.getConfig().set("Encerra." + ".Diamante", plugin.getConfig().getInt("Bingo." + ".Diamante"));
		if(tipo.contains("Esmeralda"))
			plugin.getConfig().set("Encerra." + ".Esmeralda", plugin.getConfig().getInt("Bingo." + ".Esmeralda"));
		if(tipo.contains("Netherite"))
			plugin.getConfig().set("Encerra." + ".Netherite", plugin.getConfig().getInt("Bingo." + ".Netherite"));
		
		plugin.saveConfig();
	}
				
	private int verificarID(String tipo, String validade) 
	{			
		dataAtual = plugin.getConfig().getString("Data");
		if(!dataAtual.equals(validade))
		{
			plugin.getConfig().set("Bingo." + ".Ouro", 0);
			plugin.getConfig().set("Bingo." + ".Diamante", 0);
			plugin.getConfig().set("Bingo." + ".Esmeralda", 0);
			plugin.getConfig().set("Bingo." + ".Netherite", 0);
			//-----
			plugin.getConfig().set("Encerra." + ".Ouro", 0);
			plugin.getConfig().set("Encerra." + ".Diamante", 0);
			plugin.getConfig().set("Encerra." + ".Esmeralda", 0);
			plugin.getConfig().set("Encerra." + ".Netherite", 0);
			plugin.getConfig().set("Data", validade);
		}
		
		int tipoID = plugin.getConfig().getInt("Bingo." + "."+ tipo) + 1;
		plugin.getConfig().set("Bingo." + "."+ tipo, tipoID);
		plugin.saveConfig();
		
		return tipoID;
	}
	
	//criar livro--------------------------
	private void criarLivro(String name, Player player)
	{
		ItemStack livro = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta meta = livro.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		String validade = this.formatter.format(new Date());
		
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',name));	
		lore.add(ChatColor.translateAlternateColorCodes('&', "&l&bSky&l&eBingo"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7"+validade));
		meta.setLore(lore);
		livro.setItemMeta(meta);
		
		if(player.getInventory().firstEmpty() != -1)
			player.getInventory().addItem(new ItemStack[] { livro });	
		else
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cSem espaço no inventário"));			
	}
	
	//gerar cartela------------------------
	@SuppressWarnings("deprecation")
	private void gerarCartela(String name, Player player, String tipo)
	{	
		String validade = this.formatter.format(new Date());
		int tipoID = verificarID(tipo, validade);
							
		ItemStack cartela = new ItemStack(Material.PAPER);	
		ItemMeta meta = cartela.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',name + " &7Nº " + tipoID));			
			
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7"+validade));
	
		meta.setLore(lore);
		cartela.setItemMeta(meta);
		
		player.setItemInHand(cartela);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void clickLivroCartela(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		
		try {
			if ((event.getAction() == Action.RIGHT_CLICK_AIR || 
					 event.getAction() == Action.RIGHT_CLICK_BLOCK))
				{	
					  ItemStack handItem = player.getInventory().getItemInMainHand();
					  
					  String ouro = ChatColor.translateAlternateColorCodes('&',"&2Cartela &6&lOuro");
					  String diamante = ChatColor.translateAlternateColorCodes('&',"&2Cartela &b&lDiamante");
					  String esmeralda = ChatColor.translateAlternateColorCodes('&',"&2Cartela &a&lEsmeralda");
					  String netherite = ChatColor.translateAlternateColorCodes('&',"&2Cartela &8&lNetherite");
					
					  if(handItem.getType() == Material.ENCHANTED_BOOK)
					  {	
						  if(handItem.hasItemMeta() && 
						  handItem.getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&',"&2Cartela")))
						  {
							  String infoData = handItem.getItemMeta().getLore().get(1);
							  String validade = ChatColor.translateAlternateColorCodes('&', "&7"+this.formatter.format(new Date()));
							  
							  if(infoData.equals(validade))
							  {
								  if(handItem.getItemMeta().getDisplayName().equals(ouro))
									  gerarCartela(ouro, player, "Ouro");
								  if(handItem.getItemMeta().getDisplayName().equals(diamante))
									  gerarCartela(diamante, player, "Diamante");
								  if(handItem.getItemMeta().getDisplayName().equals(esmeralda))
									  gerarCartela(esmeralda, player, "Esmeralda");
								  if(handItem.getItemMeta().getDisplayName().equals(netherite))
									  gerarCartela(netherite, player, "Netherite");
							  }
							  else
							  {
								  player.setItemInHand(new ItemStack(Material.AIR));
								  player.sendMessage(ChatColor.RED + "Cartela expirada!");
							  }
						  }
					  }	
					
					  if(handItem.getType() == Material.PAPER)
					  {
						  if(handItem.hasItemMeta() && 
							 handItem.getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&',"&2Cartela")))
						  {
							  Inventario inv = new Inventario();
							  String nameCartela = handItem.getItemMeta().getDisplayName();
							  String infoData = handItem.getItemMeta().getLore().get(0);
							  
							  String tipo;
							  String nome;
							  if(handItem.getItemMeta().getDisplayName().contains(ouro)) {
								  tipo = "Ouro";
								  nome = ouro;
							  }
							  else {
								  if(handItem.getItemMeta().getDisplayName().contains(diamante)){
									  tipo = "Diamante";
									  nome = diamante;
							      }
							      else 
							    	  if(handItem.getItemMeta().getDisplayName().contains(esmeralda)){
									  tipo = "Esmeralda";
									  nome = esmeralda;
							          }
							          else {
								       tipo = "Netherite";
								       nome = netherite; 
							           }
						      }
							  			  
							  if(infoData.equals("§7" + this.formatter.format(new Date())) && 
							    (Integer.valueOf(nameCartela.replace(nome + " §7Nº ", "")) > plugin.getConfig().getInt("Encerra." + "."+ tipo)) )
							  {
								  if(handItem.getItemMeta().getDisplayName().contains(ouro))
									  inv.newInventory(player, 8, Integer.valueOf(nameCartela.replace(ouro + " §7Nº ", "")));									 
								  if(handItem.getItemMeta().getDisplayName().contains(diamante))
									  inv.newInventory(player, 10, Integer.valueOf(nameCartela.replace(diamante + " §7Nº ", "")));								  
								  if(handItem.getItemMeta().getDisplayName().contains(esmeralda))
									  inv.newInventory(player, 15, Integer.valueOf(nameCartela.replace(esmeralda + " §7Nº ", "")));								  
								  if(handItem.getItemMeta().getDisplayName().contains(netherite))
									  inv.newInventory(player, 20, Integer.valueOf(nameCartela.replace(netherite + " §7Nº ", "")));
							  }
							  else {								
								  player.setItemInHand(new ItemStack(Material.AIR));
								  player.sendMessage(ChatColor.RED + "Cartela expirada!");								  
							  }
						  }				  
					  }
				} 
		}catch(Exception e){			
		}			
	}
	
	@EventHandler
	public void PainelConfirmar(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();		
		ItemStack item = event.getCurrentItem();	
				
		if(event.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&',"&c&lENCERRAR RODADA: ")))
		{
			event.setCancelled(true);
			if(item == null || !item.hasItemMeta())
				return;	
			else
			{
				if(item.getType().equals(Material.GREEN_CONCRETE)) {
					encerrarBingo(item.getItemMeta().getDisplayName());
					player.sendMessage(ChatColor.RED + "Rodada foi encerrada!");
					player.closeInventory();				
				}
				else
				{
					ComprarCartela painel = new ComprarCartela();
					painel.newInventory(player);
				}				
			}
		}
	}
		
	@EventHandler
	public void PainelComprar(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();		
		ItemStack item = event.getCurrentItem();	
				
		if(event.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&',"&l&bSky&l&eBingo")))
		{
			event.setCancelled(true);
			if(item == null || !item.hasItemMeta())
				return;	
			else
			{
				if(item.getType().equals(Material.CACTUS))
					criarLivro(item.getItemMeta().getDisplayName(), player);
				else 
					if(item.getType().equals(Material.BARRIER))
					{
						player.closeInventory();
						Confirmar conf = new Confirmar();
						conf.newInventory(player, item.getItemMeta().getDisplayName());
					}
					
					if(item.getType().equals(Material.OBSIDIAN)) {
						this.mutados =1;
						player.sendMessage(ChatColor.RED + "A sala foi mutada!");
					}
					if(item.getType().equals(Material.SEA_LANTERN)) {
						this.mutados =0;
						player.sendMessage(ChatColor.GREEN + "Chat da sala foi liberado!");
					}
						
			}
		}
	}
		
	@EventHandler
	public void CartelaClique(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();		
		ItemStack item = event.getCurrentItem();	
				
		if(event.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&',"&0&lCARTELA &8Nº ")))
		{
			event.setCancelled(true);
			if(item == null || !item.hasItemMeta())
				return;	
			else
			{
				if(item.hasItemMeta() &&
				   item.getType().name().contains("CONCRETE"))
				{
					int id = Integer.valueOf(event.getView().getTitle().replace("§0§lCARTELA §8Nº ", ""));	
					int clicado = Integer.valueOf(item.getItemMeta().getDisplayName().replace("§e§l", ""));	
					
					int tipo =0;
					for (ItemStack is : event.getInventory().getContents())
						if(is != null)
							tipo ++;
							
					cartela = Bingo.cartelas.get(tipo).get(id);
					
					if(item.getType().equals(Material.WHITE_CONCRETE))
						cartela.insertClicado(clicado);
					else
						cartela.removeClicado(clicado);
						
					//cartela.insertClicados(auxClicados);
					Bingo.cartelas.get(tipo).remove(id);
					Bingo.cartelas.get(tipo).put(id, cartela);
					
					//this.auxClicados.clear();				
					//player.closeInventory();
					build.newInventory(player, tipo, id);
				}				
			}	
		}
	}
}

