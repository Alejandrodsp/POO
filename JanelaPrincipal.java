package gui;

import java.applet.Applet;
import java.applet.AudioClip;
import personagens.ReiDaNoite;
import personagens.Ferreiro;
import personagens.Comerciante;
import personagens.Daenerys;
import personagens.Inimigo;
import personagens.JonSnow;
import personagens.Jogador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.net.URL;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jogo.Comando;
import jogo.PalavraDeComando;
import static jogo.Dado.dado;
import jogo.Item;
import jogo.ItemType;
import jogo.Parser;
import jogo.Room;
import main.GameOfThrones;
import personagens.Dragao;

public class JanelaPrincipal extends JFrame implements ActionListener
{
    private Parser parser;
    private static Room currentRoom;
    private static Jogador jogador;
    private static String endGame;
    private JPanel painelTitulo, painelJogar, painelInfoGeral,painelGeral;
    private JPanel painelInfoRoom, painelConsole, painelBotao,painelNome,painelServidor,painelJogarMulti;
    private JLabel labelTitulo, labelNome, labelServidor;
    private Font grande = new Font("Times New Roman", Font.PLAIN, 90);
    private Font normal = new Font("Times New Roman", Font.PLAIN, 20);
    protected PainelRoom painelRoom;
    protected PainelStatusJogador painelStatusJogador;
    protected PainelInventario painelInventario;
    protected PainelLoja painelLoja;
    private ImagemMapa imagemMapa;
    private static JTextArea console;
    private JButton usar, jogar, atacar, equipar, desequipar, comprar;
    private JButton comer, pegar, largar, sair, multiplayer, jogarMultiplayer;
    private JButton norte, sul, oeste, leste;
    private JTextField nome,servidor;
    private final int HEIGHT = 131;
    private final int WIDTH = 200;
    private final int SCALE = 6;

    public JanelaPrincipal()
    {
        setSize(WIDTH*SCALE, HEIGHT*SCALE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.black);
        this.setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        parser = new Parser();

    }

    protected void setupGame(){
        jogador = new JonSnow();
        List<Room> rooms = Room.criaSalas();

        for(Room room : rooms){
            if(room.getRoomName().equals("presasdegelo")){
                room.addEnemy(new ReiDaNoite("Rei da Noite", room, 100, 2000));
                room.lock();
            }
            if(room.getRoomName().equals("portoreal")){
                room.addNpc(new Ferreiro("Ferreiro", room));
                room.addNpc(new Comerciante("Comerciante", room));
            }
            if(room.getRoomName().equals("pedradodragao")){
                room.addNpc(new Daenerys("Daenerys", room));
                room.addEnemy(new Dragao("Dragão", room, 100, 1000));
            }
            if(room.getRoomName().equals("winterfell")){
                room.addNpc(new Ferreiro("Ferreiro", room));
                room.addItem("Espada", new Item(ItemType.ATAQUE, "Espada", "+25 damage", 20.0, 30, 0));
                currentRoom = room;
            }
             if(room.getRoomName().equals("ilhadosursos")){
                room.addEnemy(new Inimigo("Selvagem "+1, room, dado(30), dado(100), new Item(ItemType.ATAQUE, "Lança", "Você pode pegar para lutar", 30.0, dado(25), 0)));
                room.addEnemy(new Inimigo("Selvagem Arqueiro "+2, room, dado(30), dado(100), new Item(ItemType.ATAQUE, "Arco", "Você pode pegar para lutar", 25.0, dado(15), 0)));
                room.addEnemy(new Inimigo("Selvagem "+3, room, dado(30), dado(100), new Item(ItemType.ATAQUE, "lança", "Você pode pegar para lutar", 35.0, dado(25), 0)));
                room.addEnemy(new Inimigo("Gigante", room, dado(100), dado(100), new Item(ItemType.ATAQUE, "Pau", "Você pode pegar para lutar", 50.0, dado(40), 0)));
            }
             if(room.getRoomName().equals("mereen")){
                room.addNpc(new Ferreiro("Ferreiro", room));
                room.addNpc(new Comerciante("Comerciante", room));
            }
        }
    }

    protected void setRoomAtual(Room sala) {
        currentRoom = sala;
    }

    protected void printInicio(){
        consoleAppend("Você é Jon Snow o filho bastardo de Ned Stark, Lorde de Winrterfell");
        consoleAppend("Vendo que os 7 reinos estao ameaçados pelo temível Rei da Noite e seu exercíto de mortos vivos");
        consoleAppend("Você precisa conseguir o lendário Ovo de Dragão para poder encontra-ló");
        consoleAppend("Mas para isso terá que derrotar diversos inimigos.");
        consoleAppend(currentRoom.getLongDescription());
        painelRoom.refresh();
        painelStatusJogador.refresh();
    }

    public static Room getRoomAtual() {
        return currentRoom;
    }

    public static Jogador getJogador() {
        return jogador;
    }

    public static void setJogador(Jogador novoJogador) {
        jogador = novoJogador;
    }

    public static void setEndGame(String end){
        endGame = end;
    }

    public static void consoleAppend(String texto){
        console.append(texto);
        console.append("\n");
    }

    private void goRoom(Comando comando) {
        String direction = comando.getSegundaPalavra();

        Room nextRoom = currentRoom.getRoomExits(direction);

        if(currentRoom.getRoomName().equals("ilhadosursos") && direction.toLowerCase().equals("norte")){
            if(nextRoom.isLocked()){
                consoleAppend("Você precisa do ovo de dragão para passar!");
                return;
            }
        }
         if(currentRoom.getRoomName().equals("durolar") && direction.toLowerCase().equals("oeste")){
            if(nextRoom.isLocked()){
                consoleAppend("Você precisa do ovo de dragão para passar!");
                return;
            }
        }

        if (nextRoom == null) {
            console.append("Não é possivel ir para " + direction + "\n");
        } else {
            enviaServidor(direction);
            setRoomAtual(nextRoom);
            criaNpcsAleatorios();
            console.append(currentRoom.getLongDescription());
            painelRoom.changeRoom(currentRoom);
        }
    }

    protected void criaNpcsAleatorios() {
        if((currentRoom.getRoomName().equals("presasdegelo")) || (currentRoom.getRoomName().equals("ilhadosursos")) || (currentRoom.getRoomName().equals("casteloblack"))|| (currentRoom.getRoomName().equals("durolar"))){
            currentRoom.geraCaminhantes();
        } else if((currentRoom.getRoomName().equals("volantis")) || (currentRoom.getRoomName().equals("mereen")) || (currentRoom.getRoomName().equals("dorne"))|| (currentRoom.getRoomName().equals("pedradodragao"))){
            currentRoom.geraImaculados();
        } else {
            currentRoom.geraSelvagens();
        }
    }

    public void janelaMenu(){
        GameOfThrones musica = new GameOfThrones();
        musica.getSom().loop();
        painelTitulo = new JPanel();
        painelTitulo.setOpaque(false);
        painelTitulo.setBounds(90, 200, 1000, 100);
        labelTitulo = new JLabel("GAME OF THRONES");
        labelTitulo.setForeground(Color.white);
        labelTitulo.setFont(grande);

        painelJogar = new JPanel();
        painelJogar.setBounds(480, 400, 200, 100);
        painelJogar.setOpaque(false);

        jogar = new JButton("       JOGAR       ");
        jogar.setBackground(Color.black);
        jogar.setForeground(Color.white);
        jogar.setFont(normal);
        jogar.addActionListener(this);
        jogar.setFocusPainted(false);

        multiplayer = new JButton("MULTIPLAYER");
        multiplayer.setBackground(Color.black);
        multiplayer.setForeground(Color.white);
        multiplayer.setFont(normal);
        multiplayer.addActionListener(this);
        multiplayer.setFocusPainted(false);

        painelTitulo.add(labelTitulo);
        painelJogar.add(jogar);
        painelJogar.add(multiplayer);

        add(painelTitulo);
        add(painelJogar);
    }

    public void janelaJogo(){
        if (painelTitulo != null) {
            painelTitulo.setVisible(false);
            painelJogar.setVisible(false);
            remove(painelTitulo);
            remove(painelJogar);
        }

        setupGame();
        setLayout(new BorderLayout());
        imagemMapa = new ImagemMapa();

        painelGeral = new JPanel();
        painelGeral.setLayout(new GridLayout(4,0));
        painelInfoGeral = new JPanel();
        painelInfoRoom = new JPanel();
        painelConsole = new JPanel();
        painelBotao = new JPanel();

        painelGeral.add(painelInfoGeral);
        painelGeral.add(painelInfoRoom);
        painelGeral.add(painelConsole);
        painelGeral.add(painelBotao);

        painelInfoGeral.setBackground(Color.white);
        painelInfoRoom.setOpaque(false);
        painelConsole.setOpaque(false);
        painelBotao.setOpaque(false);
        painelBotao.setBackground(Color.white);
        painelInfoGeral.setVisible(true);
        painelInfoRoom.setVisible(true);
        painelConsole.setVisible(true);
        painelBotao.setVisible(true);

        painelBotao.setLayout(new GridLayout(0,2));
        painelBotao.setOpaque(false);

        JPanel commands = new JPanel();
        commands.setOpaque(false);
        commands.setLayout(new GridLayout(3,3));
        atacar = new JButton("ATACAR");
        atacar.addActionListener(this);
        atacar.setBackground(Color.black);
        atacar.setForeground(Color.white);
        atacar.setFont(normal);
        atacar.setFocusPainted(false);
        commands.add(atacar);
        pegar = new JButton("PEGAR");
        pegar.addActionListener(this);
        pegar.setBackground(Color.black);
        pegar.setForeground(Color.white);
        pegar.setFont(normal);
        pegar.setFocusPainted(false);
        commands.add(pegar);
        largar = new JButton("LARGAR");
        largar.addActionListener(this);
        largar.setBackground(Color.black);
        largar.setForeground(Color.white);
        largar.setFont(normal);
        largar.setFocusPainted(false);
        commands.add(largar);
        equipar = new JButton("EQUIPAR");
        equipar.addActionListener(this);
        equipar.setBackground(Color.black);
        equipar.setForeground(Color.white);
        equipar.setFont(normal);
        equipar.setFocusPainted(false);
        commands.add(equipar);
        desequipar = new JButton("DESEQUIPAR");
        desequipar.addActionListener(this);
        desequipar.setBackground(Color.black);
        desequipar.setForeground(Color.white);
        desequipar.setFont(normal);
        desequipar.setFocusPainted(false);
        commands.add(desequipar);
        usar = new JButton("USAR");
        usar.addActionListener(this);
        usar.setBackground(Color.black);
        usar.setForeground(Color.white);
        usar.setFont(normal);
        usar.setFocusPainted(false);
        commands.add(usar);
        comer = new JButton("COMER");
        comer.addActionListener(this);
        comer.setBackground(Color.black);
        comer.setForeground(Color.white);
        comer.setFont(normal);
        comer.setFocusPainted(false);
        commands.add(comer);
        comprar = new JButton("COMPRAR");
        comprar.addActionListener(this);
        comprar.setBackground(Color.black);
        comprar.setForeground(Color.white);
        comprar.setFont(normal);
        comprar.setFocusPainted(false);
        commands.add(comprar);
        sair = new JButton("SAIR");
        sair.addActionListener(this);
        sair.setBackground(Color.black);
        sair.setForeground(Color.white);
        sair.setFont(normal);
        sair.setFocusPainted(false);
        commands.add(sair);
        painelBotao.add(commands);


        JPanel movement = new JPanel();
        movement.setLayout(new BorderLayout());
        movement.setOpaque(false);
        norte = new JButton("NORTE");
        norte.addActionListener(this);
        norte.setBackground(Color.black);
        norte.setForeground(Color.white);
        norte.setFont(normal);
        norte.setFocusPainted(false);
        movement.add(norte, BorderLayout.NORTH);
        sul = new JButton("SUL");
        sul.addActionListener(this);
        sul.setBackground(Color.black);
        sul.setForeground(Color.white);
        sul.setFont(normal);
        sul.setFocusPainted(false);
        movement.add(sul, BorderLayout.SOUTH);
        oeste = new JButton("OESTE");
        oeste.addActionListener(this);
        oeste.setBackground(Color.black);
        oeste.setForeground(Color.white);
        oeste.setFont(normal);
        oeste.setFocusPainted(false);
        movement.add(oeste, BorderLayout.WEST);
        leste = new JButton("LESTE");
        leste.addActionListener(this);
        leste.setBackground(Color.black);
        leste.setForeground(Color.white);
        leste.setFont(normal);
        leste.setFocusPainted(false);
        movement.add(leste, BorderLayout.EAST);
        painelBotao.add(movement);
        console = new JTextArea();
        console.setEditable(false);
        painelConsole.setLayout( new BorderLayout() );
        painelConsole.add(new JScrollPane(console), BorderLayout.CENTER);
        painelInfoGeral.setLayout(new GridLayout(0,3));
        painelStatusJogador = new PainelStatusJogador();
        painelInfoGeral.add(painelStatusJogador);
        painelInventario = new PainelInventario();
        painelInfoGeral.add(painelInventario);
        painelLoja = new PainelLoja();
        painelInfoGeral.add(painelLoja);
        painelRoom = new PainelRoom(currentRoom, painelLoja);
        painelInfoRoom.setLayout(new BorderLayout());
        painelInfoRoom.add(painelRoom, BorderLayout.CENTER);
        add(imagemMapa,BorderLayout.CENTER);
        add(painelGeral,BorderLayout.WEST);
        printInicio();
    }

    public void  janelaMultiplayer(){
        painelTitulo.setVisible(false);
        painelJogar.setVisible(false);
        remove(painelTitulo);
        remove(painelJogar);
        setLayout(null);
        setVisible(true);
        painelNome = new JPanel();
        painelNome.setOpaque(false);
        painelNome.setBounds(80, 200, 1000, 100);
        labelNome = new JLabel("NOME");
        labelNome.setForeground(Color.white);
        labelNome.setFont(normal);
        nome = new JTextField(10);
        nome.setFont(normal);
        painelNome.add(labelNome);
        painelNome.add(nome);
        painelServidor = new JPanel();
        painelServidor.setOpaque(false);
        painelServidor.setBounds(60, 300, 1000, 150);
        labelServidor = new JLabel("SERVIDOR");
        labelServidor.setForeground(Color.white);
        labelServidor.setFont(normal);
        servidor = new JTextField(10);
        servidor.setFont(normal);
        painelServidor.add(labelServidor);
        painelServidor.add(servidor);
        painelJogarMulti = new JPanel();
        painelJogarMulti.setOpaque(false);
        painelJogarMulti.setBounds(110, 400, 1000, 200);
        jogarMultiplayer = new JButton("     JOGAR     ");
        jogarMultiplayer.setBackground(Color.black);
        jogarMultiplayer.setForeground(Color.white);
        jogarMultiplayer.setFont(normal);
        jogarMultiplayer.addActionListener(this);
        jogarMultiplayer.setFocusPainted(false);
        painelJogarMulti.add(jogarMultiplayer);
          add(painelNome);
          add(painelServidor);
          add(painelJogarMulti);
    }

    public void janelaGameOver(){

        painelGeral.setVisible(false);
        imagemMapa.setVisible(false);
        remove(painelGeral);
        remove(imagemMapa);
        setLayout(null);

        JPanel endPanel = new JPanel();
        endPanel.setBounds(330, 200, 500, 200);
        endPanel.setOpaque(false);

        JLabel end = new JLabel(endGame);
        end.setFont(grande);
        end.setForeground(Color.white);

        endPanel.add(end);
        add(endPanel);

    }

    protected void tocaSom(String nomeDoSom)
        {
        URL url = GameOfThrones.class.getResource(nomeDoSom + ".wav");
        AudioClip som = Applet.newAudioClip(url);
        som.play();
    }

    protected void enviaServidor(String comando) {
    }

    protected void realmenteAtaca(Jogador quemAtaca, String nomeDoPersonagem) {
        tocaSom("ataqueEspada");
        Comando command = new Comando(PalavraDeComando.ATTACK, nomeDoPersonagem);
        if(quemAtaca.atacar(command) && quemAtaca == jogador) {
            janelaGameOver();
            return;
        }
        painelStatusJogador.refresh();
        painelRoom.refresh();
    }

    protected void ataca(String nomeDoPersonagem) { // no modo multiplayer os personagens tem que ter uma confirma��o se pode atacar
        realmenteAtaca(jogador, nomeDoPersonagem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jogar)
            janelaJogo();
        else if(e.getSource() == multiplayer)
            janelaMultiplayer();
        else if(e.getSource() == jogarMultiplayer){
            String nomePlayer = nome.getText();
            String nomeServidor = servidor.getText();
            painelNome.setVisible(false);
            painelServidor.setVisible(false);
            painelJogarMulti.setVisible(false);
            remove(painelNome);
            remove(painelServidor);
            remove(painelJogarMulti);
            setVisible(false);
            dispose();
            JanelaPrincipalMultiplayer jmp = new JanelaPrincipalMultiplayer(nomeServidor, nomePlayer);
            jmp.janelaJogo();
            jmp.show();
        }
        else if (e.getSource() == norte) {
            Comando comando = new Comando(PalavraDeComando.GO, "norte");
            goRoom(comando);
        }
        else if (e.getSource() == sul) {
            Comando comando = new Comando(PalavraDeComando.GO, "sul");
            goRoom(comando);
        }
        else if (e.getSource() == leste) {
            Comando comando = new Comando(PalavraDeComando.GO, "leste");
            goRoom(comando);
        }
        else if (e.getSource() == oeste) {
            Comando comando = new Comando(PalavraDeComando.GO, "oeste");
            goRoom(comando);
        }
        else if (e.getSource() == atacar) {
            ataca(painelRoom.takeSelectedCharacter());
        }
        else if(e.getSource() == pegar){
            Comando command = new Comando(PalavraDeComando.PICK, painelRoom.takeSelectedItem());
            jogador.pegar(command);
            painelRoom.refresh();
            painelInventario.refresh();
        }
        else if(e.getSource() == largar){
            Comando command = new Comando(PalavraDeComando.DROP, painelInventario.pegarItemSelecionado());
            jogador.largar(command);
            painelRoom.refresh();
            painelInventario.refresh();
        }
        else if(e.getSource() == comer){
            Comando command = new Comando(PalavraDeComando.EAT, painelInventario.pegarItemSelecionado());
            jogador.comer(command);
            tocaSom("comer");
            painelInventario.refresh();
            painelStatusJogador.refresh();
        }
        else if(e.getSource() == equipar){
            Comando command = new Comando(PalavraDeComando.EQUIP, painelInventario.pegarItemSelecionado());
            jogador.equipar(command);
            painelStatusJogador.refresh();
            painelInventario.refresh();
        }
        else if(e.getSource() == desequipar){
            Comando command = new Comando(PalavraDeComando.UNEQUIP, painelStatusJogador.pegarItemSelecionado());
            jogador.desequipar(command);
            painelStatusJogador.refresh();
            painelInventario.refresh();
        }
        else if(e.getSource() == comprar){
            if(painelLoja.pegarItemSelecionado() != null && painelRoom.takeSelectedCharacter() != null){
                Comando comando = new Comando(PalavraDeComando.BUY, painelLoja.pegarItemSelecionado());
                getRoomAtual().getVendedor(painelRoom.takeSelectedCharacter()).sale(comando);
                painelLoja.refresh();
                painelInventario.refresh();
                painelStatusJogador.refresh();
            }
            else
                consoleAppend("Você precisa selecionar um item");
        }
        else if(e.getSource() == usar){
            jogador.usar();
        }
        else if(e.getSource() == sair){
            System.exit(0);
        }

    }

}
