package Modele;

import java.util.ArrayList;
import java.util.List;


public class StrategyKillPacman implements Strategie {

    private PacmanGame pacmangame;

    public StrategyKillPacman(PacmanGame g) {
        this.pacmangame = g;
    }

    @Override
    public AgentAction typeAction() {
        List<Fantome> fantomes = pacmangame.getFantomes();
        List<Pacman> pacmans = pacmangame.getPacman();

        if (!fantomes.isEmpty() && !pacmans.isEmpty()) {
            // Choix du premier fantôme et du premier pacman 
            Fantome fantome = fantomes.get(0);
            Pacman pacman = pacmans.get(0);

            // Recherche du chemin le plus court du fantôme au pacman
            ArrayList<PositionAgent> shortestPath = pacmangame.plusCourtChemin(
                    fantome.getPosition(), pacman.getPosition(), pacmangame.getMaze());

            if (!shortestPath.isEmpty()) {
                // La première étape du chemin détermine la prochaine action
                PositionAgent nextStep = shortestPath.get(1); // 0 étant la position actuelle

                // Calcul de la direction vers la prochaine étape
                int dx = nextStep.getX() - fantome.getPosition().getX();
                int dy = nextStep.getY() - fantome.getPosition().getY();

                int direction = Maze.STOP; // Direction par défaut si aucune action n'est trouvée

                if (dx == 0 && dy == -1) {
                    direction = Maze.NORTH;
                } else if (dx == 0 && dy == 1) {
                    direction = Maze.SOUTH;
                } else if (dx == 1 && dy == 0) {
                    direction = Maze.EAST;
                } else if (dx == -1 && dy == 0) {
                    direction = Maze.WEST;
                }

                return new AgentAction(direction);
            }
        }

        // On retourne une action par défaut si aucune action n'est trouvée
        return new AgentAction(Maze.STOP);
    }
}
