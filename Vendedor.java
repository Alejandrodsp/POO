package personagens;

import static gui.JanelaPrincipal.consoleAppend;
import java.util.HashMap;
import java.util.Set;
import jogo.Comando;
import static gui.JanelaPrincipal.getJogador;
import jogo.Item;
import jogo.ItemType;
import jogo.Room;

public abstract class Vendedor extends Personagem{

    protected HashMap<String, Item> shop;

    public Vendedor(String nome, Room sala) {
        super(nome, sala);
        shop = new HashMap<>();
    }

    public int getPrice(String name){
        return getItem(name).getPreco();
    }

    public Set<String> getShopItems(){
        return shop.keySet();
    }

    public void sale(Comando command){

        String name = command.getSegundaPalavra();

        if(getJogador().getMoedas() >= getPrice(name)){
            if(getJogador().getPesoAtual() + getItem(name).getPeso() <= getJogador().getPesoLimite()){
                getJogador().removeMoedas(getPrice(name));
                getJogador().addItem(name, getItem(name));
                if(name.equals("Ovo de dragão"))
                    getJogador().comprouOvoDeDragao();
                consoleAppend("Você adicionou " + name.toUpperCase() + " em seu inventário.\n");
            }
            else
                consoleAppend("Inventário cheio.\n");
        }
        else
            consoleAppend("Você não possui moedas suficientes!\n");
    }

    public Item getItem(String name){
        for(Item item : shop.values())
            if(item.getNome().equals(name))
                return item;
        return null;
    }
}
