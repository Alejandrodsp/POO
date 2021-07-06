package multiplayer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GuiCliente {
    private InterfaceServidor interfaceServidor;
    PrintStream saida;
    private Socket cliente;
    private String endereco;

    public GuiCliente(InterfaceServidor interfaceServidor, String endereco) {
        this.endereco = endereco;
        this.interfaceServidor = interfaceServidor;
        Thread t = new Thread() {
            @Override
            public void run() {
                processaCliente();
            }
        };
        t.setDaemon(true);
        t.start();
    }

    public void processaMensagem(String linha) {
        String []mensagemDividida = linha.split("/", 2);
        String nome = mensagemDividida[0];
        String comando = mensagemDividida[1];
        if (nome.equals("jogador")) { // Mensagem para criar um jogador na interface
            interfaceServidor.novoJogador(comando);
        }
        else { // Comando comum do jogador
            interfaceServidor.recebeuComando(nome, comando);
        }
    }

    public void processaCliente() {
        Scanner scanner;
        try {
            cliente = new Socket(endereco, 1234);
            scanner = new Scanner(cliente.getInputStream());
            saida = new PrintStream(cliente.getOutputStream());
        interfaceServidor.conectou();
        }
        catch (IOException e) {
            interfaceServidor.erroAoConectar();
            return;
        }
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            if (linha != null) {
                processaMensagem(linha);
            }
        }
    }

    public void envia(String linha)
        {
        saida.println(linha);
    }
}
