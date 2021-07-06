package multiplayer;

public class Mensagem
    {
    private Cliente cliente;
    private String mensagem;

    public Mensagem(Cliente cliente, String mensagem) {
        this.cliente = cliente;
        this.mensagem = mensagem;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getMensagem() {
        return mensagem;
    }
}
