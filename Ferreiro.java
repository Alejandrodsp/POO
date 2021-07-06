package personagens;
import jogo.Item;
import jogo.ItemType;
import jogo.Room;

public class Ferreiro extends Vendedor {
    public Ferreiro(String nome, Room sala) {
        super(nome, sala);
        shop.put("Espada", new Item(ItemType.ATAQUE, "Espada", "+30 de dano", 60.0, 30, 1000));
        shop.put("Espada de Aço Valeriano", new Item(ItemType.ATAQUE, "Espada de Aço Valeriano", "+50 de dano", 60.0, 50, 5000));
        shop.put("Arco", new Item(ItemType.ATAQUE, "Arco", "+20 de dano", 40.0, 20, 800));
        shop.put("Lança", new Item(ItemType.ATAQUE, "Lança", "+25 de dano", 35.0, 25, 900));
        shop.put("Machado", new Item(ItemType.ATAQUE, "Machado", "+35 de dano", 60.0, 35, 1500));
        shop.put("Escudo", new Item(ItemType.DEFESA, "Escudo", "+15 de proteção", 70.0, 15, 2000));
        shop.put("Armadura", new Item(ItemType.DEFESA, "Armadura", "+30 de proteção", 70.0, 30, 4000));
    }
}
