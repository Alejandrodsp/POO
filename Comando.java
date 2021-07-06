package jogo;

public class Comando
{
    private PalavraDeComando palavraDeComando;
    private String segundaPalavra;

    public Comando(PalavraDeComando palavraDeComando, String segundaPalavra)
    {
        this.palavraDeComando = palavraDeComando;
        this.segundaPalavra = segundaPalavra;
    }

    public PalavraDeComando getPalavraDeComando()
    {
        return palavraDeComando;
    }
    
    public String getSegundaPalavra()
    {
        return segundaPalavra;
    }

    public boolean desconhecido()
    {
        return (palavraDeComando == PalavraDeComando.UNKNOWN);
    }

    public boolean temSegundaPalavra()
    {
        return (segundaPalavra != null);
    }
}

