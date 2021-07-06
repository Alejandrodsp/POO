package jogo;

public enum PalavraDeComando
{
    USE("use"), BUY("buy"), EQUIP("equip"), UNEQUIP("unequip"), ATTACK("attack"), EAT("eat"), PICK("pick"),DROP("drop"), GO("go"), QUIT("quit"), UNKNOWN("@");
    
    private String stringComando;
    
    PalavraDeComando(String stringComando)
    {
        this.stringComando = stringComando;
    }
    
    public String toString()
    {
        return stringComando;
    }
}
