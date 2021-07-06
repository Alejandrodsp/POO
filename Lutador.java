package personagens;

import jogo.Room;

public abstract class Lutador extends Personagem{

    private Integer vida;
    private Integer moedas;

    public Lutador(String nome, Room sala, Integer vida, Integer moedas) {
        super(nome, sala);
        this.vida = vida;
        this.moedas = moedas;
    }
    public boolean isDead() {
        if(vida <= 0){
            return true;
        }
        return false;
    }

    public Integer getMoedas(){
        return moedas;
    }

    public void setMoedas(Integer moedas) {
        this.moedas = moedas;
    }

    public void adicionarMoedas(int moedas) {
        this.moedas  += moedas;
    }

    public void removeMoedas(int moedas){
        this.moedas -= moedas;
    }

    public Integer getVida() {
        return vida;
    }

    public void restauraVida(int quantaVida) {
        if(vida + quantaVida <= 100){
            vida = vida + quantaVida;
        }
    }

    public void tiraVida(Lutador quemTirou, int quantaVida) {
        vida -= quantaVida;
        if (isDead() && quemTirou != null) {
            morreuPara(quemTirou);
        }
    }

    protected void morreuPara(Lutador quemMatou) {

    }

  public abstract Integer getAtaque();

}
