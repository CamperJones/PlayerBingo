package me.camper.bingo.jogador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cartela 
{
	//variaveis
	private int id;
	private int tipo;
	private List<Integer> lista = new ArrayList<Integer>();
	private List<Integer> clicados = new ArrayList<Integer>(); 
	
	//construtor
	public Cartela(int tipo, int id)
	{				
		this.setId(id);
		this.setTipo(tipo);
		this.setLista(gerarNumeros(tipo));
		this.setClicados(Collections.emptyList());
	}
	
	//constructor
	public Cartela(int id, int qnt, List<Integer> lista,  List<Integer> clicados){
		this.setId(id);
		this.setTipo(qnt);
		this.setLista(lista);
		this.setClicados(clicados);
	}
	
	@SuppressWarnings("removal")
	public void removeClicado(int num)
	{	
		List<Integer> aux = new ArrayList<Integer>(); 
		
		aux = this.getClicados();
		
		aux.remove(new Integer(num));	
		
		this.setClicados(aux);	
	}
	
	public void insertClicado(int num)
	{			
		List<Integer> aux = new ArrayList<Integer>(); 
		
		if(!this.getClicados().isEmpty())
			aux = this.getClicados();
	
		aux.add(num);	
		this.setClicados(aux);
	}

	private List<Integer> gerarNumeros(int qnt) 
	{	
		int max;
		switch(qnt)
		{
			case 8:
				max=30;
			break;
			case 10:
				max=40;
			break;
			case 15:
				max=50;
			break;
			default:
				max=60;
		}	
		
		List<Integer> aux = new ArrayList<Integer>();
		aux.add((int)(Math.random() * (max - 1) + 1));
					
		//até 20,15,10,8 numeros
		int i=1;
		while(i<qnt)
		{	
			//gerar um numero
			int num =  (int)(Math.random() * (max - 1) + 1);	
			
			//verificar se numero é igual da lista
			for(int x=0; x < aux.size(); x++){				
				if(aux.get(x) == num)	//se for igual parar;
					break;
				else //se for diferente
					if((x+1) == aux.size()) //Se final da lista, adiciona numero;
					{ 
						aux.add(num);
						i++; //chamar o proximo até 10 numeros;
					}
			}			
		}				
		Collections.sort(aux);  
		return aux;
	}

	//sets
	private void setLista(List<Integer> lista){
		this.lista = lista;}
	private void setId(int id){
		this.id = id;}
	private void setTipo(int tipo){
		this.tipo = tipo;}
	private void setClicados(List<Integer> clicados){
		this.clicados = clicados;}

	//gets
	public int getId(){
		return id;}
	public int getTipo(){
		return tipo;}
	public List<Integer> getLista(){
		return lista;}
	public List<Integer> getClicados(){
		return clicados;}
}
