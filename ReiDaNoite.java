package personagens;

import jogo.Item;
import jogo.ItemType;
import jogo.Room;


public class ReiDaNoite extends Inimigo {

    public ReiDaNoite(String nome, Room sala, Integer vida, Integer moedas) {
        super(nome, sala, vida, moedas, new Item(ItemType.ATAQUE, "Lança do Rei da noite", "Lança do Rei da noite", 0, 45, 0));
    }
    
}
