package personagens;

import multiplayer.Cliente;
import jogo.Room;
// Classe usada para armazenar os personagens no servidor

public class JogadorMultiplayer extends Personagem {
    private Cliente cliente;
    private int vida;

    public JogadorMultiplayer(String nome, Room sala, Cliente cliente) {
        super(nome, sala);
        this.cliente = cliente;
        vida = 100;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
}
