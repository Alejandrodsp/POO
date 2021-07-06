package personagens;

import jogo.Room;

public abstract class Personagem {
    private Room sala;
    private String nome;

    public Personagem(String nome, Room sala) {
        this.nome = nome;
        this.sala = sala;
    }

    public String getNome() {
        return nome;
    }

    public Room getSala()
        {
        return sala;
    }

    public void setSala(Room sala) {
        if (this.sala != null) {
            this.sala.removeNpc(this.getNome());
        }
        this.sala = sala;
        sala.addNpc(this);
    }
}
