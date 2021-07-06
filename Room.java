package jogo;

import personagens.Inimigo;
import personagens.Lutador;
import personagens.Personagem;
import personagens.Vendedor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import static jogo.Dado.dado;

public class Room
{
    private String roomName;
    private String roomDescription;
    private HashMap<String, Room> roomExits;
    private HashMap<String, Personagem> npcs;
    private HashMap<String, Item> roomItems;
    private boolean isLocked;

    public Room(String name, String description)
    {
        this.roomName = name;
        this.roomDescription = description;
        roomExits = new HashMap<>();
        roomItems = new HashMap<>();
        npcs = new HashMap<>();
        isLocked = false;
    }

    public void setExit(String direction, Room neighbor)
    {
        roomExits.put(direction, neighbor);
    }

    public String getShortDescription()
    {
        return roomDescription;
    }

    public String getLongDescription()
    {
        return "Você está " + roomDescription + ".\n" + getExitString();
    }

    private String getExitString()
    {
        String returnString = "Saídas:";
        Set<String> keys = roomExits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        returnString += "\n";
        return returnString;
    }

    public Room getRoomExits(String direction)
    {
        return roomExits.get(direction);
    }

    public String getRoomName() {
        return roomName;
    }

    public HashMap<String, Item> getRoomItems() {
        return roomItems;
    }

    public Item getRoomItem(String name){
        return roomItems.get(name);
    }

    public List<String> getCharacters(){
        List<String> names = new ArrayList<String>();
        for(String name : npcs.keySet())
            names.add(name);
        return names;
    }

    public Lutador getRoomEnemy(String name){
        return (Lutador)npcs.get(name);
    }

    public Personagem getRoomNpc(String name){
        return npcs.get(name);
    }

    public Vendedor getVendedor(String name){
        return (Vendedor)npcs.get(name);
    }

    public boolean isNpc(String name){
        return npcs.containsKey(name);
    }

    public Boolean eVendedor(String nome) {
        Personagem p = npcs.get(nome);
        if (p != null) {
            return p instanceof Vendedor;
        }
        return false;
    }

    public void addNpc(Personagem p){
        npcs.put(p.getNome(), p);
    }

    public void geraSelvagens(){
        int qnt = dado(3);
        for(int i = 0; i < qnt; i++)
            addEnemy(new Inimigo("Selvagem "+i, this, dado(30), dado(202), new Item(ItemType.ATAQUE, "Lança", "Você pode pegar para lutar", 35.0, dado(25), 0)));
    }
    public void geraCaminhantes(){
        int qnt = dado(5);
        for(int i = 0; i < qnt; i++)
            addEnemy(new Inimigo("Caminhante Branco "+i, this, dado(20), dado(80), new Item(ItemType.ATAQUE, "Espada", "Você pode pegar para lutar", 35.0, dado(20), 0)));
    }
      public void geraImaculados(){
        int qnt = dado(5);
        for(int i = 0; i < qnt; i++)
            addEnemy(new Inimigo("Imaculado "+i, this, dado(20), dado(100), new Item(ItemType.ATAQUE, "Lança", "Você pode pegar para lutar", 35.0, dado(25), 0)));
    }

    public void addItem(String name, Item item){
        roomItems.put(name, item);
    }

    public Item removeItem(String name){
        return roomItems.remove(name);
    }

    public void addEnemy(Lutador inimigo){
        if (getRoomEnemy(inimigo.getNome()) == null)
            addNpc(inimigo);
    }

    public void removeNpc(String name){
        npcs.remove(name);
    }

    public void lock(){
        isLocked = true;
    }

    public void unlock(){
        isLocked = false;
    }

    public boolean isLocked(){
        return isLocked;
    }

    public static List <Room> criaSalas() {
        List<Room> rooms = new ArrayList<Room>();

        try(BufferedReader br = new BufferedReader(new FileReader("createRooms.txt"))){

            String line = br.readLine();
            while(line != null){
                String[] fields = line.split("/");
                rooms.add(new Room(fields[0], fields[1]));
                line = br.readLine();
            }
        }
        catch (IOException e){
            System.out.println("Erro: " + e.getMessage());
        }

        try(BufferedReader br = new BufferedReader(new FileReader("roomExits.txt"))){

            String line = br.readLine();
            while(line != null){
                String[] fields = line.split("/");
                for(Room room : rooms){
                    if(fields[0].equals(room.getRoomName()))
                        for(Room exit : rooms)
                            if(fields[2].equals(exit.getRoomName()))
                                room.setExit(fields[1], exit);
                }
                line = br.readLine();
            }
        }
        catch (IOException e){
            System.out.println("Erro: " + e.getMessage());
        }
        return rooms;
    }

}
