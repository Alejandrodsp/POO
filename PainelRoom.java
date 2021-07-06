package gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.SwingConstants;

import jogo.Room;
@SuppressWarnings("unchecked")
public class PainelRoom extends JPanel {
    
        private Room room;
        private String selectedCharacter;
        private String selectedItem;
        private JLabel description;
        private JList charactersList;
        private JList itemsList;
        private PainelStatusInimigo enemyStatus;
        private PainelItem itemInfo;
        private PainelLoja shop;
        
        public PainelRoom(Room room, PainelLoja shop) {
            this.shop = shop;
            this.room = room;
            selectedCharacter = null;

            setBackground(Color.white);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            description = new JLabel("You are ...", SwingConstants.CENTER);
            description.setHorizontalAlignment(JLabel.CENTER);
            add(description);

            JPanel listPanel = new JPanel();
            listPanel.setLayout(new GridLayout(2, 2));

            listPanel.add(new JLabel("Personagens", SwingConstants.CENTER));
            listPanel.add(new JLabel("Itens", SwingConstants.CENTER));

            charactersList = new JList();
            charactersList.setModel(new DefaultListModel());
            charactersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            charactersList.setLayoutOrientation(JList.VERTICAL);
            charactersList.setVisibleRowCount(-1);
            charactersList.addListSelectionListener(new EnemySelectionHandler());
            JScrollPane enemiesL = new JScrollPane(charactersList);
            listPanel.add(enemiesL);

            itemsList = new JList();
            itemsList.setModel(new DefaultListModel());
            itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            itemsList.setLayoutOrientation(JList.VERTICAL);
            itemsList.setVisibleRowCount(-1);
            itemsList.addListSelectionListener(new ItemSelectionHandler());
            JScrollPane itemsL = new JScrollPane(itemsList);
            listPanel.add(itemsL);

            add(listPanel);

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new GridLayout(0,2));

            enemyStatus = new PainelStatusInimigo();
            infoPanel.add(enemyStatus);

            itemInfo = new PainelItem();
            infoPanel.add(itemInfo);

            add(infoPanel);
                
        }
        
        public void changeRoom(Room newRoom) {
            room = newRoom;
            refresh();
        }
        
        public void refresh() {
            description.setText("Você está " + room.getShortDescription());

            ((DefaultListModel) charactersList.getModel()).removeAllElements();
            for (String name : room.getCharacters()) 
                    ((DefaultListModel) charactersList.getModel()).addElement(name);


            ((DefaultListModel) itemsList.getModel()).removeAllElements();
            for (String name : room.getRoomItems().keySet()) 
                    ((DefaultListModel) itemsList.getModel()).addElement(name);

        }
      
        public void refreshEnemy() {
            if (selectedCharacter == null) {
                    enemyStatus.limpar();
            } else {
                    enemyStatus.refresh(room.getRoomEnemy(selectedCharacter));
            }
        }
        
        public String takeSelectedCharacter() {
                return selectedCharacter;
        }
        
        public void refreshItem() {
            if (selectedItem == null) {
                    itemInfo.limpa();
            } else {
                    itemInfo.refresh(room.getRoomItem(selectedItem));
            }
        }
        
        public String takeSelectedItem() {
            return selectedItem;
        }

        class EnemySelectionHandler implements ListSelectionListener {
                public void valueChanged(ListSelectionEvent e) {
                        if (charactersList.isSelectionEmpty()) {
                                selectedCharacter = null;
                                shop.limpar();
                                enemyStatus.limpar();
                        } else {
                                selectedCharacter = (String) charactersList.getSelectedValue();
                                if(room.eVendedor(selectedCharacter)){
                                    shop.setNpcShop(room.getVendedor((String) charactersList.getSelectedValue()));
                                    shop.refresh();
                                    enemyStatus.limpar();
                                }
                                else{
                                    refreshEnemy();
                                    shop.limpar();
                                }
                        }
                }
        }
        
        class ItemSelectionHandler implements ListSelectionListener {
            public void valueChanged(ListSelectionEvent e) { 
                if (itemsList.isSelectionEmpty()) 
                    selectedItem = null;
                else
                    selectedItem = (String) itemsList.getSelectedValue();
                refreshItem();
            }
        }
}
