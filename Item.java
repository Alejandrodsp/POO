package jogo;

public class Item {
    
    private String nome;
    private String descricao;
    private double peso;
    private int poder;
    private ItemType tipo;
    private int preco;
    private int quantidade;

    public Item(ItemType tipo, String nome, String descricao, double peso, int poder, int preco) {
        this.tipo = tipo;
        this.nome = nome;
        this.descricao = descricao;
        this.peso = peso;
        this.poder = poder;
        this.preco = preco;
        quantidade = 0;
    }

    public int getPreco() {
        return preco;
    }
    
    public ItemType getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }
    
    public int getQuantidade(){
        return quantidade;
    }
    
    public void incrementaQuantidade(){
        quantidade++; 
    }
    
    public void decrementaQuantidade(){
        quantidade--;
    }

    public double getPeso() {
        return peso;
    }

    public int getPoder() {
        return poder;
    }

}
