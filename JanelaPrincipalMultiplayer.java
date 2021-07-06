package gui;

import java.awt.EventQueue;
import java.util.List;
import jogo.Dado;
import jogo.Room;
import multiplayer.GuiCliente;
import multiplayer.InterfaceServidor;
import personagens.Jogador;
import personagens.Lutador;
import personagens.Personagem;

public class JanelaPrincipalMultiplayer extends JanelaPrincipal implements InterfaceServidor {
    private GuiCliente cliente;
    private String endereco;
    private String nome;

    public JanelaPrincipalMultiplayer(String endereco, String nome) {
        super();
        this.endereco = endereco;
        this.nome = nome;
    }

    @Override
    public void janelaJogo()
        {
        super.janelaJogo();
        cliente = new GuiCliente(this, endereco);
    }

    @Override
    protected void printInicio() {
        consoleAppend(getRoomAtual().getLongDescription());
        painelRoom.refresh();
        painelStatusJogador.refresh();
    }

    @Override
    protected void setupGame() {
        setJogador(new Jogador(nome));
        List<Room> rooms = Room.criaSalas();
        for (Room room: rooms) {
            if (room.getRoomName().equals("winterfell")) {
                setRoomAtual(room);
            }
        }
    }

    @Override
    protected void enviaServidor(String linha) {
        cliente.envia(linha);
    }

    public void conectou() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                consoleAppend("Conectado ao servidor.");
            }
        });
        enviaServidor("nome/" + nome);
    }

    public void erroAoConectar() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                consoleAppend("Não foi possível conectar no servidor.");
            }
        });
    }

    public void desconectou() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                consoleAppend("Desconectado do servidor.");
            }
        });
    }

    public void novoJogador(String nome) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                int vida = Integer.parseInt(nome.split("/")[1]);
                String nome2 = nome.split("/")[0];
                if (!nome2.equals(getJogador().getNome())) {
                    Jogador j = new Jogador(nome2);
                    j.tiraVida(null, 100 - vida);
                    getRoomAtual().addNpc(j);
                    painelRoom.refresh();
                }
            }
        });
    }

    private void processaComando(Personagem personagem, String comando) {
        String cmd = comando.toLowerCase();
        Room sala = getRoomAtual();
        if (!personagem.getNome().equals(getJogador().getNome()) && (cmd.equals("norte") || cmd.equals("sul") || cmd.equals("leste") || cmd.equals("oeste") || cmd.equals("saiu"))) {
            sala.removeNpc(personagem.getNome());
            painelRoom.refresh();
            if (!cmd.equals("saiu")) {
                consoleAppend(personagem.getNome() + " foi para o " + cmd + ".");
            } else {
                consoleAppend(personagem.getNome() + " saiu do servidor.");
            }
        } else if (comando.startsWith("podeAtacar/")) {
            Lutador p;
            String []mensagemDividida = comando.split("/", 3);
            String quemAtaca = mensagemDividida[1];
            long semente = Long.parseLong(mensagemDividida[2]);
            if ((p = (Lutador)pegaPersonagem(quemAtaca)) != null) {
                Dado.alimenta(semente);
                realmenteAtaca((Jogador)p, personagem.getNome());
                if (p == getJogador() || personagem == getJogador()) {
                    enviaServidor("vida/" + getJogador().getVida());
                }
            }
        } else if (comando.startsWith("atacar/")) {
            String p = comando.split("/", 2)[1];
            if (p != null && p.equals(getJogador().getNome())) {
                long semente = System.currentTimeMillis() / 1000;
                enviaServidor("podeAtacar/" + personagem.getNome() + "/" + semente);
            }
        }
    }

    private Personagem pegaPersonagem(String nome) {
        if (nome.equals(getJogador().getNome())) {
            return getJogador();
        }
        return getRoomAtual().getRoomNpc(nome);
    }

    public void recebeuComando(String nome, String comando) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Personagem p = pegaPersonagem(nome);
                if (p != null) {
                    processaComando(p, comando);
                } else if (comando.startsWith("chegou") && !nome.equals(getJogador().getNome())) {
                    int vida = Integer.parseInt(comando.split("/")[1]);
                    Jogador j = new Jogador(nome);
                    j.tiraVida(null, 100 - vida);
                    getRoomAtual().addNpc(j);
                    painelRoom.refresh();
                    consoleAppend(nome + " chegou.");
                }
            }
        });
    }

    @Override
    protected void ataca(String nomeDoPersonagem) {
        enviaServidor("atacar/" + nomeDoPersonagem);
    }

    @Override
    protected void criaNpcsAleatorios() {
    }

}
