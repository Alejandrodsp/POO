package personagens;

import jogo.Item;
import jogo.Room;

public class Inimigo extends Lutador {

    private Item arma;

    public Inimigo(String nome, Room sala, Integer vida, Integer moedas, Item arma) {
        super(nome, sala, vida, moedas);
        this.arma = arma;
    }

    public Item getArma() {
        return arma;
    }

    @Override
    protected void morreuPara(Lutador quemMatou) {
        quemMatou.adicionarMoedas(getMoedas());
        getSala().addItem(getArma().getNome(), getArma());
        getSala().removeNpc(getNome());
    }

    public Integer getAtaque() {
        return getArma().getPoder();
    }
}
