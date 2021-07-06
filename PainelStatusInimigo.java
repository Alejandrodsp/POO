package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import personagens.Lutador;

public class PainelStatusInimigo extends JPanel{

        private JLabel nome;
        private JLabel vida;

        public PainelStatusInimigo() {
            setBackground(Color.black);
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            nome = new JLabel("Nome: ");
            nome.setForeground(Color.white);
            vida = new JLabel("Vida: ");
            vida.setForeground(Color.white);
            add(nome);
            add(vida);
        }

        public void refresh(Lutador inimigo) {
            nome.setText("Nome: " + inimigo.getNome());
            vida.setText("Vida: " + inimigo.getVida());
        }

        public void limpar() {
            nome.setText("Nome: ");
            vida.setText("Vida: ");
        }
}
