package Vue;
import javax.swing.*;
import Modele.Fantome;
import Modele.Pacman;
import Modele.Observateur;
import Modele.PacmanGame;
import Modele.PositionAgent;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;


public class ViewPacmanGame implements Observateur  {

    private JFrame viewPacGam;
    private PanelPacmanGame panel;
    private PacmanGame game;
    private JLabel scoreLabel; // Ajout du label pour le score

    public ViewPacmanGame(PacmanGame g) {
        this.game = g;
        this.viewPacGam = new JFrame("Pacman Game");
        this.viewPacGam.setSize(800, 600);
        this.viewPacGam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création et ajout du label Score
        this.scoreLabel = new JLabel("Score: 0");
        this.scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.viewPacGam.getContentPane().add(scoreLabel, BorderLayout.NORTH);

        this.panel = new PanelPacmanGame(game.getMaze());
        this.viewPacGam.add(panel);
        this.viewPacGam.setVisible(true);
    }

    @Override
    public void actualiser(int turn) {

        List<Fantome> fantomes = game.getFantomes();
        List<Pacman> pacmans = game.getPacman();

        ArrayList<PositionAgent> posFantomes = new ArrayList<>();
        ArrayList<PositionAgent> posPacman = new ArrayList<>();

        //***********************************-----GESTION FANTOME------***********************************
        for (Fantome a : fantomes){
                posFantomes.add(a.getPosition()); 
        }
        //************************************-----GESTION PACMAN------***********************************
        for(Pacman pac : pacmans){

            posPacman.add(pac.getPosition());   

        }
    
    
        panel.updateAgentPositions(posPacman, posFantomes);

        
        if (game.getCapsuleActive()) {

             panel.setGhostsScarred(true); // Activer l'état effrayé des fantômes dans la vue
      
         } else {

             panel.setGhostsScarred(false); // Désactiver l'état effrayé des fantômes dans la vue
            
        }

        //Actualise le score:
        scoreLabel.setText("Score: " + game.getScore());

        panel.setMaze(game.getMaze());

    }

    

    public PacmanGame getGame(){
        return game;
    }

    public JFrame getViewPacGam() {
        return viewPacGam;
    }

    
    
}
