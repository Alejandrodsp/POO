package personagens;

import jogo.Item;
import jogo.ItemType;
import jogo.Room;

public class Dragao extends Inimigo{
    
     public Dragao(String nome, Room sala, Integer vida, Integer moedas) {
        super(nome, sala, vida, moedas, new Item(ItemType.COMIDA, "Carne de Dragão", "Recupere sua vida!", 40.0, 50, 0));
    }
}
