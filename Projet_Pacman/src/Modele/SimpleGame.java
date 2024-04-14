package Modele;
import java.util.ArrayList;
import java.util.List;

public class SimpleGame extends Game implements Observable {
    
    private List<Observateur> observateurs = new ArrayList<>();

    public void enregistrerObservateur(Observateur observateur){
        observateurs.add(observateur);
    }

    public void supprimerObservateur(Observateur observateur){
        observateurs.remove(observateur);
        }

    public void notifierObservateurs() {
        for(int i = 0; i< observateurs.size(); i++) {
             observateurs.get(i).actualiser(this.getTurn());
        }
    }

    public void setTurn(int turn){
        this.setTurn(turn);
        notifierObservateurs();
    }

    public SimpleGame(int maxTurn) {
        super(maxTurn);
    }

    
    public void initializeGame() {
        System.out.println("Le jeu a été initialisé.");
    }

 
    public void takeTurn() {
        System.out.println("Tour " + getTurn() + " du jeu en cours.");
        notifierObservateurs();
    }

   
    public boolean gameContinue() {
        return true; 
    }

  
    public void gameOver() {
        System.out.println("Le jeu est terminé.");
    }

}



