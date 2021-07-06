package multiplayer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jogo.Room;
import java.util.HashMap;
import java.util.Map;

public class Servidor {
    private ServerSocket socketServidor;
    private BlockingQueue <Mensagem> filaDeMensagens;
    private Map <String, Room> salas;

    public Servidor() throws IOException {
        salas = new HashMap <String, Room>();
        for (Room sala: Room.criaSalas()) {
            salas.put(sala.getRoomName(), sala);
        }
        socketServidor = new ServerSocket(1234);
        filaDeMensagens = new LinkedBlockingQueue <Mensagem>();
        Thread t = new Thread() {
            @Override
            public void run() {
                aceitaConexao();
            }
        };
        t.setDaemon(true);
        t.start();
        System.out.println("Servidor iniciado no endereço " + InetAddress.getLocalHost());
    }

    public void processa(Mensagem m) {
        if (m.getCliente() != null) {
            m.getCliente().processa(m);
        }
    }

    public void processaMensagens() throws InterruptedException {
        Mensagem m;
        while ((m = filaDeMensagens.take()) != null) {
            processa(m);
        }
    }

    public void aceitaConexao()
        {
        while (true)
            {
            try {
                Socket socketCliente = socketServidor.accept();
                new Cliente(this, socketCliente);
            } catch (IOException e) {
                break;
            }
        }
    }

    public void adicionaMensagem(Cliente cliente, String mensagem) throws InterruptedException {
        filaDeMensagens.put(new Mensagem(cliente, mensagem));
    }

    public static void main(String []args) throws Exception {
        Servidor s = new Servidor();
        s.processaMensagens();
    }

    public Room getSala(String nome) {
        return salas.get(nome);
    }
}
