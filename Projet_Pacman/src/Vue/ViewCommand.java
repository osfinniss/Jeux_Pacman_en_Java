package Vue;
import Controlleur.*;
import Modele.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;


public class ViewCommand implements Observateur {

    private JFrame viewCom;
    private JLabel labturn;
     private EtatBouton etatBouton;

    
    
    public ViewCommand(AbstractController abs){



        viewCom = new JFrame("View Command");
        viewCom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewCom.setSize(800,400); 
        Dimension windowSize = viewCom.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 - 350;
        viewCom.setLocation(dx, dy);
      
 

        JPanel Panel = new JPanel(new GridLayout(2, 1));

     
        Icon restart = new ImageIcon("src/icons/icon_restart.png");
        Icon pause = new ImageIcon("src/icons/icon_pause.png");
        Icon run = new ImageIcon("src/icons/icon_run.png");
        Icon step = new ImageIcon("src/icons/icon_step.png");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton buttonRestart = new JButton(restart);
        JButton buttonRun = new JButton(run);
        JButton buttonStep = new JButton(step);
        JButton buttonPause = new JButton(pause);
        buttonPanel.add(buttonRestart);
        buttonPanel.add(buttonRun);
        buttonPanel.add(buttonStep);
        buttonPanel.add(buttonPause);

        buttonRestart.setEnabled(false);
        buttonPause.setEnabled(false);
       
        JPanel PanelBas = new JPanel(new GridLayout(1, 2));

        JPanel sliderPanel = new JPanel(new GridLayout(2,1));

        JLabel Label1 = new JLabel("Number of turns per second");
        Label1.setHorizontalAlignment(SwingConstants.CENTER);
        sliderPanel.add(Label1);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        slider.setMajorTickSpacing(1); 
        slider.setPaintTicks(true); 
        slider.setPaintLabels(true); 
        slider.setSnapToTicks(true); 
        sliderPanel.add(slider);
        
        
        PanelBas.add(sliderPanel);
        
        labturn = new JLabel("Turn: 0");
        labturn.setHorizontalAlignment(SwingConstants.CENTER);
        PanelBas.add(labturn);

  
        Panel.add(buttonPanel);
        Panel.add(PanelBas);


        viewCom.add(Panel);

        viewCom.setVisible(true);

        etatBouton = new EtatEnCours();

        buttonRestart.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                etatBouton.gererRecommencer(abs, buttonRun, buttonPause, buttonStep, buttonRestart);

               
            }
        });

        buttonRun.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                etatBouton.gererLancer(abs, buttonRun, buttonPause, buttonStep, buttonRestart);

            }

             });

        buttonStep.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                etatBouton.gererPas(abs, buttonRun, buttonPause, buttonStep, buttonRestart);

            }
        });

        buttonPause.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                etatBouton.gererPause(abs, buttonRun, buttonPause, buttonStep, buttonRestart);
            }
        });

         slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent c){
             abs.setSpeed(slider.getValue());
         }

 
 });

        

    }

    public void actualiser(int turn) {
        labturn.setText("Turn: "+turn);
    }
    

    
}
