package personagens;
import jogo.Item;
import jogo.ItemType;
import jogo.Room;

public class Daenerys extends Vendedor {
    public Daenerys(String nome, Room sala) {
        super(nome, sala);
        shop.put("Ovo de dragão", new Item(ItemType.ATAQUE, "Ovo de dragão", "O precioso ovo de dragão!", 5.0, 0, 10000));
    }
}
