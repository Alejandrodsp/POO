package gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import static gui.JanelaPrincipal.getJogador;

@SuppressWarnings("unchecked")
public class PainelInventario extends JPanel{
    
        private JList listaItens;
        private JPanel inventario;
        private String itemSelecionado;

	public PainelInventario() {

            setBackground(Color.white);
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            inventario = new JPanel(new GridLayout(2,0));
            JLabel inventoryL = new JLabel("Inventário");
            inventoryL.setHorizontalAlignment(JLabel.CENTER);
            inventario.add(inventoryL);

            listaItens = new JList();
            listaItens.setModel(new DefaultListModel());
            listaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listaItens.setLayoutOrientation(JList.VERTICAL);
            listaItens.setVisibleRowCount(-1);
            listaItens.addListSelectionListener(new Manipulador());
            JScrollPane itemsL = new JScrollPane(listaItens);
            inventario.add(itemsL);

            add(inventario);
                
	}
        
        public void refresh() {

            ((DefaultListModel) listaItens.getModel()).removeAllElements();
            for (String name : getJogador().getChaveDoInventario()) 
                    ((DefaultListModel) listaItens.getModel()).addElement(name + ","+ getJogador().getItem(name).getQuantidade());
		
	}        
	
	public String pegarItemSelecionado() {
            if(itemSelecionado != null){
                String[] item;
                item = itemSelecionado.split(",");
                return item[0];
            }
            return null;
	}
        
        class Manipulador implements ListSelectionListener {
            public void valueChanged(ListSelectionEvent e) { 
                if (listaItens.isSelectionEmpty()) 
                    itemSelecionado = null;
                else 
                    itemSelecionado = (String) listaItens.getSelectedValue();
            }
	}
}
