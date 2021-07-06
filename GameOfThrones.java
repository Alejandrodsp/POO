package main;

import gui.JanelaPrincipal;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.EventQueue;
import java.net.URL;

public class GameOfThrones
{
   private URL som = GameOfThrones.class.getResource("musica.wav");
   private AudioClip Som = Applet.newAudioClip(som);

   public AudioClip getSom(){
       return Som;
   }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JanelaPrincipal mw = new JanelaPrincipal();
                mw.janelaMenu();
                mw.setVisible(true);
            }
        });
    }

}
