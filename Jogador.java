package personagens;

import static gui.JanelaPrincipal.consoleAppend;
import java.util.HashMap;
import java.util.Set;
import jogo.SimuladorDeBatalha;
import jogo.Comando;
import static gui.JanelaPrincipal.getRoomAtual;
import gui.JanelaPrincipal;
import jogo.Item;
import jogo.ItemType;
import jogo.Room;

public class Jogador extends Lutador {

    private double pesoLimite;
    private HashMap<String, Item> inventario;
    private HashMap<String, Item> equipado;
    private Integer ataque;
    private Integer defesa;
    private boolean ovoDeDragao;

    public Jogador(String nome) {
        super(nome, null, 100, 0);
        this.pesoLimite = 500.00;
        inventario = new HashMap<>();
        equipado = new HashMap<>();
        defesa = 0;
        ataque = 5;
        ovoDeDragao = false;
    }

    public double getPesoLimite() {
        return pesoLimite;
    }

    public Integer getAtaque() {
        return ataque;
    }

    public Integer getDefesa() {
        return defesa;
    }

    public double getPesoAtual(){
        double peso = 0.0;
        for(String item : inventario.keySet())
            peso += inventario.get(item).getPeso();
        return peso;
    }

    public Set<String> getChaveDoInventario(){
        return inventario.keySet();
    }

    public Set<String> getChaveDosEquipados(){
        return equipado.keySet();
    }

    public Item getItemEquipado(String nome){
        return equipado.get(nome);
    }

    public Item getItemPeloTipo(ItemType tipo){
        for(Item item : equipado.values())
            if(item.getTipo().equals(tipo))
                return item;
        return null;
    }

    public Item getItem(String name){
        return inventario.get(name);
    }

    public void setAtaque() {
        if(temItemEquipado(ItemType.ATAQUE))
            ataque = getAtaque() + getItemPeloTipo(ItemType.ATAQUE).getPoder();
        else
            ataque = 5;
    }

    public void setDefesa() {
        if(temItemEquipado(ItemType.DEFESA))
            defesa = getDefesa() + getItemPeloTipo(ItemType.DEFESA).getPoder();
        else
            defesa = 5;
    }
    
    public boolean podeEquipar(double peso){
        return peso + getPesoAtual() <= pesoLimite;
    }
     
    public boolean temItemEquipado(ItemType tipo){
        for(Item item : equipado.values())
            if(item.getTipo().equals(tipo))
                return true;
        return false;
    }
     
    public void equipaItem(String nome, Item item){
        equipado.put(nome, item);
    }
     
    public Item unequipItem(String nome){
        return equipado.remove(nome);
    }
    
    public boolean addItem(String nome, Item item){
        if(podeEquipar(item.getPeso())){
            item.incrementaQuantidade();
            inventario.put(nome, item);
            return true;
        }
        else{
            consoleAppend("Inventario cheio!");
            return false;
        }
    }

    public Item removeItem(String nome){
        inventario.get(nome).decrementaQuantidade();
        if(inventario.get(nome).getQuantidade() == 0)
            return inventario.remove(nome);
        else
            return inventario.get(nome);
    }
    
    public boolean temMoedas(){
        if(getMoedas() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void comprouOvoDeDragao(){
        ovoDeDragao = true;
    }
    
    public boolean temOvoDeDragao(){
        return ovoDeDragao;
    }

    public void comer(Comando comando){

        if(comando.getSegundaPalavra() == null) {
            consoleAppend("Selecione uma comida!");
            return;
        }

        String comida = comando.getSegundaPalavra();

        if(getItem(comida).getTipo() == ItemType.COMIDA){
            restauraVida(getItem(comida).getPoder());
            removeItem(comida);
        }
        else
            consoleAppend("Você não pode comer isso!\n");
    }

    public void pegar(Comando comando){
        
        if(comando.getSegundaPalavra() == null) {
            consoleAppend("Selecione um item!");
            return;
        }
        
        String item = comando.getSegundaPalavra();
        
        if(podeEquipar(getRoomAtual().getRoomItem(item).getPeso())){
            addItem(item, getRoomAtual().removeItem(item));
            consoleAppend("Você adicionou " + item.toUpperCase() + " no seu inventário.");
        }
       else
            consoleAppend("Inventário cheio!Impossível pegar o item.");
        
    }
    
    public void largar(Comando command){
        
        if(command.getSegundaPalavra() == null) {
            consoleAppend("Selecione um item!");
            return;
        }
        
        String itemName = command.getSegundaPalavra();
 
        getRoomAtual().addItem(itemName, removeItem(itemName));
        consoleAppend("Você largou " + itemName.toUpperCase() + " do seu inventário.");

    }
    
    public boolean atacar(Comando comando){
        if(comando.getSegundaPalavra() == null) {
            consoleAppend("Selecione um inimigo");
            return false;
        }

        String inimigo = comando.getSegundaPalavra();
         Lutador i = getRoomAtual().getRoomEnemy(inimigo);
        if (i == null && inimigo.equals(JanelaPrincipal.getJogador().getNome())) {
            i = JanelaPrincipal.getJogador();
        }

        if(i == null) {
            consoleAppend("Não ataque seus amigos!");
            return false;
        }

        SimuladorDeBatalha battle = new SimuladorDeBatalha(this, i);
        return battle.luta();
    }

    public void equipar(Comando comando){
        
        if(comando.getSegundaPalavra() == null) {
            consoleAppend("Selecione um item!");
            return;
        }
        
        String item = comando.getSegundaPalavra();
        
        ItemType type = getItem(item).getTipo();
        if(type.equals(ItemType.COMIDA)){
            consoleAppend("Você não pode equipar uma comida!");
            return;
        }
        else if(temItemEquipado(type))
            consoleAppend("Você precisa desequipar o item atual primeiro!");
        else{
            equipaItem(item, removeItem(item));
            if(type.equals(ItemType.DEFESA)){
                setDefesa();
                consoleAppend("Você equipou " + item.toUpperCase() + " como sua defesa!");
            }
            if(type.equals(ItemType.ATAQUE)){
                setAtaque();
                consoleAppend("Você equipou " + item.toUpperCase() + " como seu ataque!");
            }
        }
    }
    
    public void desequipar(Comando comando){
        
        if(comando.getSegundaPalavra() == null) {
            consoleAppend("Selecione um item");
            return;
        }
        
        String item = comando.getSegundaPalavra();
        
        addItem(item, unequipItem(item));
        if(getItemPeloTipo(ItemType.DEFESA) == null)
            setDefesa();
        if(getItemPeloTipo(ItemType.ATAQUE) == null)
            setAtaque();

        consoleAppend("Você desequipou " + item.toUpperCase());
    }
    
    public void usar(){
        if(temOvoDeDragao()){
            if(getRoomAtual().getRoomName().equals("ilhadosursos")){               
                Room nextRoom = getRoomAtual().getRoomExits("north");
                consoleAppend("Mate o Rei da noite!");
                nextRoom.unlock();
            }
            if(getRoomAtual().getRoomName().equals("durolar")){               
                Room nextRoom = getRoomAtual().getRoomExits("west");
                consoleAppend("Mate o Rei da noite!");
                nextRoom.unlock();
            }
            else
                consoleAppend("Este não é o lugar para entregar o ovo de dragão!");
        }
        else
            consoleAppend("Você não possui o ovo de dragão!");
    }

}
