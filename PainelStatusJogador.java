package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import personagens.JonSnow;
import static gui.JanelaPrincipal.getJogador;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("unchecked")
public class PainelStatusJogador extends JPanel{

	private JLabel nome;
	private JLabel vida;
        private JLabel moedas;
        private JPanel painelItemEquipado;
        private JList listaItemEquipado;
        private String itemSelecionado;

	public PainelStatusJogador() {

            setBackground(Color.white);
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            
            nome = new JLabel("Nome: ");
            vida = new JLabel("Vida: ");
            moedas = new JLabel("Moedas: ");
            add(nome);
            add(vida);
            add(moedas);

            painelItemEquipado = new JPanel(new GridLayout(2,0));
            painelItemEquipado.add(new JLabel("Armas equipadas", SwingConstants.CENTER));
            listaItemEquipado = new JList();
            listaItemEquipado.setModel(new DefaultListModel());
            listaItemEquipado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listaItemEquipado.setLayoutOrientation(JList.VERTICAL);
            listaItemEquipado.setVisibleRowCount(-1);
            listaItemEquipado.addListSelectionListener(new ItemEquippedSelectionHandler());
            JScrollPane itemsL = new JScrollPane(listaItemEquipado);
            painelItemEquipado.add(itemsL);

            add(painelItemEquipado);
                
	}
        
        public String pegarItemSelecionado() {
            if(itemSelecionado != null){
                String[] item;
                item = itemSelecionado.split(",");
                return item[0];
            }
            return null;
	}
        
        public void refresh() {
            
            nome.setText("Nome: " + getJogador().getNome());
            vida.setText("Vida: " + getJogador().getVida());
            moedas.setText("Moedas: " + getJogador().getMoedas());

            ((DefaultListModel) listaItemEquipado.getModel()).removeAllElements();
            for (String name : getJogador().getChaveDosEquipados()) 
                    ((DefaultListModel) listaItemEquipado.getModel()).addElement(name+","+getJogador().getItemEquipado(name).getPoder());
	}        
        
        class ItemEquippedSelectionHandler implements ListSelectionListener {
            public void valueChanged(ListSelectionEvent e) { 
                if (listaItemEquipado.isSelectionEmpty()) 
                    itemSelecionado = null;
                else 
                    itemSelecionado = (String) listaItemEquipado.getSelectedValue();
            }
	}
}
