package jogo;

import java.util.HashMap;

public class PalavrasDeComando
{
    private HashMap<String, PalavraDeComando> comandosValidos;

    public PalavrasDeComando()
    {
        comandosValidos = new HashMap<String, PalavraDeComando>();
        for(PalavraDeComando comando : PalavraDeComando.values()) {
            if(comando != PalavraDeComando.UNKNOWN) {
                comandosValidos.put(comando.toString(), comando);
            }
        }
    }

    public PalavraDeComando getPalavraDeComando(String palavraDeComando)
    {
        PalavraDeComando comando = comandosValidos.get(palavraDeComando);
        if(comando != null) {
            return comando;
        }
        else {
            return PalavraDeComando.UNKNOWN;
        }
    }
    
    public boolean isCommand(String aString)
    {
        return comandosValidos.containsKey(aString);
    }
    
    public void mostrarTudo() 
    {
        for(String comando : comandosValidos.keySet()) {
            System.out.print(comando + "  ");
        }
        System.out.println();
    }
}
