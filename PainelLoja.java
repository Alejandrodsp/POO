package gui;



import personagens.Vendedor;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("unchecked")
public class PainelLoja extends JPanel{

        private JList listaDeItens;
        private JPanel loja;
        private PainelItemLoja infoItens;
        private String itemSelecionado;
        private Vendedor npc;

	public PainelLoja() {

            setBackground(Color.white);
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            loja = new JPanel(new GridLayout(2,0));

            listaDeItens = new JList();
            listaDeItens.setModel(new DefaultListModel());
            listaDeItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listaDeItens.setLayoutOrientation(JList.VERTICAL);
            listaDeItens.setVisibleRowCount(-1);
            listaDeItens.addListSelectionListener(new ShopSelectionHandler());
            JScrollPane itemsL = new JScrollPane(listaDeItens);
            loja.add(itemsL);

            infoItens = new PainelItemLoja();
            loja.add(infoItens);

            add(loja);
                
	}
        
        public void setNpcShop(Vendedor npc){
            this.npc = npc;
        }
        
        public void refresh() {

            ((DefaultListModel) listaDeItens.getModel()).removeAllElements();
            for (String name : npc.getShopItems()) 
                    ((DefaultListModel) listaDeItens.getModel()).addElement(name);
		
	}       
        
        public String pegarItemSelecionado() {
            return itemSelecionado;
	}
        
        public void limpar(){
            ((DefaultListModel) listaDeItens.getModel()).removeAllElements();
        }
        
        public void refreshItem() {
            if (itemSelecionado == null) {
                    infoItens.limpar();
            } else {
                    infoItens.refresh(npc.getItem(itemSelecionado));
            }
	}
        
        class ShopSelectionHandler implements ListSelectionListener {
            public void valueChanged(ListSelectionEvent e) { 
                if (listaDeItens.isSelectionEmpty())
                    itemSelecionado = null;
                else 
                    itemSelecionado = (String) listaDeItens.getSelectedValue();
                        
                refreshItem();
            }
	}
}
