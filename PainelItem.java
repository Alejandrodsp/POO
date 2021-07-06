package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jogo.Item;

public class PainelItem extends JPanel{
	private JLabel peso;
	private JLabel poder;

	public PainelItem() {
            setBackground(Color.black);
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            
            peso = new JLabel("Peso: ");
            peso.setForeground(Color.white);
            poder = new JLabel("Poder: ");
            poder.setForeground(Color.white);
            add(peso);
            add(poder);
	}
	
	public void refresh(Item item) {
            peso.setText("Peso: " + item.getPeso());
            poder.setText("Poder: " + item.getPoder());
	}

	public void limpa() {
            peso.setText("Peso: ");
            poder.setText("Poder: ");
	}
}
