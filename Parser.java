package jogo;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Parser 
{
    private PalavrasDeComando commands;  
    private Scanner reader;         

    public Parser() 
    {
        commands = new PalavrasDeComando();
        reader = new Scanner(System.in);
    }

    public Comando getCommand() 
    {
        String inputLine;   
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();    
            }
        }

        return new Comando(commands.getPalavraDeComando(word1), word2);
    }
    
    public void showCommands()
    {
        commands.mostrarTudo();
    }
}
