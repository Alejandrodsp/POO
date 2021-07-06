package multiplayer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import jogo.Room;
import personagens.JogadorMultiplayer;
import personagens.Personagem;

public class Cliente {
    private Servidor servidor;
    private Socket socketCliente;
    private PrintStream saida;
    private JogadorMultiplayer jogador;

    public Cliente(Servidor servidor, Socket socketCliente) throws IOException {
        this.servidor = servidor;
        this.socketCliente = socketCliente;
        jogador = null;
        Scanner scanner = new Scanner(socketCliente.getInputStream());
        saida = new PrintStream(socketCliente.getOutputStream());
        Thread t = new Thread() {
            @Override
            public void run() {
                while (scanner.hasNextLine()) {
                    try {
                        String linha = scanner.nextLine();
                        servidor.adicionaMensagem(Cliente.this, linha);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                if (jogador != null) {
                    jogador.getSala().removeNpc(jogador.getNome());
                    enviaSala(jogador.getNome() + "/saiu");
                    jogador = null;
                }
            }
        };
        t.setDaemon(true);
        t.start();
    }

    public void envia(String mensagem) {
        saida.println(mensagem);
    }

    public void enviaSala(String mensagem) {
        for (String nome: jogador.getSala().getCharacters()) {
            JogadorMultiplayer jm = (JogadorMultiplayer)jogador.getSala().getRoomNpc(nome);
            jm.getCliente().envia(mensagem);
        }
    }

    private void enviaDescricaoDaSala() {
        envia("sala/" + jogador.getSala().getRoomName());
        for (String nome: jogador.getSala().getCharacters()) {
            JogadorMultiplayer p = (JogadorMultiplayer)jogador.getSala().getRoomNpc(nome);
            if (!nome.equals(jogador.getNome())) {
                envia("jogador/" + nome + "/" + p.getVida());
            }
        }
    }

    private void movimenta(String saida) {
        Room novaSala = jogador.getSala().getRoomExits(saida);
        if (novaSala != null) {
            jogador.setSala(novaSala);
            enviaDescricaoDaSala();
            enviaSala(jogador.getNome() + "/chegou/" + jogador.getVida());
        }
    }

    public void processa(Mensagem m) {
        String []mensagemDividida = m.getMensagem().split("/");
        String comando = mensagemDividida[0].toLowerCase();
        if (comando.equals("nome") && jogador == null) {
            String nome = mensagemDividida[1];
            Room salaInicial = servidor.getSala("winterfell");
            jogador = new JogadorMultiplayer(nome, salaInicial, this);
            salaInicial.addNpc(jogador);
            enviaDescricaoDaSala();
            enviaSala(jogador.getNome() + "/chegou/" + jogador.getVida());
        } else if (jogador != null) {
            if (comando.equals("vida")) {
                jogador.setVida(Integer.parseInt(mensagemDividida[1]));
            } else {
                enviaSala(jogador.getNome() + "/" + m.getMensagem());
                if (comando.equals("norte") || comando.equals("sul") || comando.equals("leste") || comando.equals("oeste")) {
                    movimenta(comando);
                }
            }
        }
    }
}
