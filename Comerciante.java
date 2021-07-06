package personagens;
import jogo.Item;
import jogo.ItemType;
import jogo.Room;

public class Comerciante extends Vendedor {
    public Comerciante(String nome, Room sala) {
        super(nome, sala);
        shop.put("Coração Cru de Cavalo", new Item(ItemType.COMIDA, "Coração Cru de Cavalo", "+30 de vida", 10.0, 30, 400));
        shop.put("Carne", new Item(ItemType.COMIDA, "Carne", "+15 de vida", 9.0, 15, 300));
        shop.put("Peixe", new Item(ItemType.COMIDA, "Peixe", "+10 de vida", 7.0, 10, 200));
        shop.put("Pão", new Item(ItemType.COMIDA, "Pão", "+5 de vida", 5.0, 5, 100));
        shop.put("Torta de Pombo", new Item(ItemType.COMIDA, "Torta de Pombo", "+15 de vida", 10.0, 15, 280));
        shop.put("Sopa", new Item(ItemType.COMIDA, "Sopa", "+8 de vida", 5.0, 8, 80));
        shop.put("Guisado de Coelho", new Item(ItemType.COMIDA, "Guisado de Coelho", "+20 de vida", 8.0, 20, 350));
    }
}
