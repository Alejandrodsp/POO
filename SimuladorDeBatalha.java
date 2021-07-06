package jogo;

import personagens.Jogador;
import personagens.Lutador;
import static gui.JanelaPrincipal.consoleAppend;
import static jogo.Dado.dado;
import static gui.JanelaPrincipal.getRoomAtual;
import static gui.JanelaPrincipal.setEndGame;

public class SimuladorDeBatalha {

    private Jogador jogador;
    private Lutador inimigo;
    private int precisao;
    private int dano;

    public SimuladorDeBatalha(Jogador jogador, Lutador inimigo) {
        this.jogador = jogador;
        this.inimigo = inimigo;
    }

    public boolean luta() {

        precisao = dado(10);
        dano = jogador.getAtaque();
        ataque(jogador, inimigo);

        if (inimigo.isDead()) {
            consoleAppend(inimigo.getNome() + " morto");
            if(inimigo.getNome().equals("Rei da Noite")){
                setEndGame("Você venceu!");
                return true;
            }
            return false;
        }

        precisao = dado(10);
        dano = inimigo.getAtaque() - jogador.getDefesa();
        ataque(inimigo, jogador);

        if (jogador.isDead()) {
            setEndGame("Você morreu!");
            return true;
        }

        return false;
    }

    public void ataque(Lutador atacante, Lutador defensor) {
        if (precisao <= 3)
            consoleAppend(atacante.getNome() + " Errou o ataque");
        else if (precisao <= 6) {
            consoleAppend(atacante.getNome() + " Causou " + dano/2 + " de dano.");
            defensor.tiraVida(atacante, dano / 2);
        }
        else{
            consoleAppend(atacante.getNome() + " Causou " + dano + " de dano.");
            defensor.tiraVida(atacante, dano);
        }
    }

}
