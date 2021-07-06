package multiplayer;

public interface InterfaceServidor {
    void conectou();
    void erroAoConectar();
    void desconectou();
    void novoJogador(String nome);
void recebeuComando(String jogador, String comando);
}
