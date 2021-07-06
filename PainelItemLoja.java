package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jogo.Item;

public class PainelItemLoja extends JPanel{
    
	private JLabel peso;
	private JLabel poder;
        private JLabel preco;

	public PainelItemLoja() {
            setBackground(Color.black);
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            
            peso = new JLabel("Peso: ");
            peso.setForeground(Color.white);
            poder = new JLabel("Poder: ");
            poder.setForeground(Color.white);
            preco = new JLabel("Preço: ");
            preco.setForeground(Color.white);
            add(peso);
            add(poder);
            add(preco);
	}
	
	public void refresh(Item item) {
            peso.setText("Peso: " + item.getPeso());
            poder.setText("Poder: " + item.getPoder());
            preco.setText("Preço: " + item.getPreco());
	}

	public void limpar() {
            peso.setText("Peso: ");
            poder.setText("Poder: ");
            preco.setText("Preço: ");
        }
}
