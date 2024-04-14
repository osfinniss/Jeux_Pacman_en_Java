package Modele;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class PacmanGame extends Game implements Observable {

    private Maze maze;
    private List<Pacman> pacmans;
    private List<Fantome> fantomes;


    private ArrayList<PositionAgent> posFood = new ArrayList<PositionAgent>();
    private ArrayList<PositionAgent> posCapsule = new ArrayList<PositionAgent>();

    private List<Observateur> observateurs = new ArrayList<>();
    private int direction;
    private boolean capsulesActive = false;
    private int score = 0;
    private Maze sav_maze;
 
    
    //****************************-------------Observateur:-----------------*********************************************************

    public void enregistrerObservateur(Observateur observateur){                                    
        observateurs.add(observateur);
    }

    public void supprimerObservateur(Observateur observateur){
        observateurs.remove(observateur);
        }

    public void notifierObservateurs() {
        for (Observateur observateur : observateurs) {
            observateur.actualiser(this.getTurn());
        }
    }

    public void setTurn(int turn){
        this.setTurn(turn);
        notifierObservateurs();
    }

    //*****************************---------CONSTRUCTEUR PACMAN GAME--------************************************************************
    

    public PacmanGame(int turn) {
        super(turn);
      

    try {
        // Menu déroulant:
        String[] options = { "Charger labyrinthe", "Quitter" };

        ImageIcon icon = new ImageIcon("src/icons/images.jpeg"); 

        // Affichage du menu déroulant 
        int choice = JOptionPane.showOptionDialog(null, "", "PACMAN",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);

        if (choice == 0) { // Si "Charger labyrinthe" est sélectionné
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Layout files", "lay");
            fileChooser.setFileFilter(filter);

            // Afficher la boîte de dialogue pour choisir le fichier
            int result = fileChooser.showOpenDialog(null);

            // Si l'utilisateur a sélectionné un fichier
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                String fileString = filePath.toString();

                this.maze = new Maze(fileString);
                this.pacmans = new ArrayList<>();
                this.fantomes = new ArrayList<>();
            }
        } else if (choice == 1) {
            System.exit(0); // Quitter le jeu
        }

        //**************************************Position des Foods**********************************
        for (int i = 0; i < this.maze.getSizeX(); i++) {
            for (int j = 0; j < this.maze.getSizeY(); j++) {
                if (this.maze.isFood(i,j)){
                    posFood.add(new PositionAgent(i, j, 0)); 
                }
            }
        }

        //**************************************Position des Capsules**********************************

        for (int i = 0; i < this.maze.getSizeX(); i++) {
            for (int j = 0; j < this.maze.getSizeY(); j++) {
                if (this.maze.isCapsule(i,j)){
                    posCapsule.add(new PositionAgent(i, j, 0)); 
                }
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public boolean isLegalMove(Agent agent, AgentAction action, Maze maze) {
        PositionAgent currentPosition = agent.getPosition();
        int newX = currentPosition.getX();
        int newY = currentPosition.getY();
    
        // Selon l'action, calculer les nouvelles coordonnées potentielles
        if (action.get_direction() == Maze.NORTH) {
            newY--;
        } else if (action.get_direction() == Maze.SOUTH) {
            newY++;
        } else if (action.get_direction() == Maze.EAST) {
            newX++;
        } else if (action.get_direction() == Maze.WEST) {
            newX--;
        } else {
            return false; // Action invalide
        }
        
        // Vérifier si les nouvelles coordonnées sont valides dans le labyrinthe
        return (newX >= 0 && newX < maze.getSizeX() && 
        newY >= 0 && newY < maze.getSizeY() && !maze.isWall(newX, newY));
        
        
    }

    public void moveAgent(Agent agent, AgentAction action, Maze maze) {
        PositionAgent currentPosition = agent.getPosition();
        int newX = currentPosition.getX();
        int newY = currentPosition.getY();

        // Selon l'action, calculer les nouvelles coordonnées potentielles
        if (action.get_direction() == Maze.NORTH) {
            newY--;
        } else if (action.get_direction()== Maze.SOUTH) {
            newY++;
        } else if (action.get_direction() == Maze.EAST) {
            newX++;
        } else if (action.get_direction() == Maze.WEST) {
            newX--;
        } 

   
        // Mettre à jour la position de l'agent si le déplacement est valide
        

         if(isLegalMove(agent, action, maze)){

            currentPosition.setX(newX);
            currentPosition.setY(newY);
            agent.setPosition(currentPosition);
            agent.getPosition().setDir(action.get_direction());;

        }
     
    }

    public void changementDirection(int dir){
        direction = dir;
          
    }

    
    //*****************************************************************************************************************************

    @Override
    public void initializeGame() {

        pacmans.clear();
       fantomes.clear();

        //Position initial Pacman:
        for(PositionAgent pos: this.maze.getPacman_start()){
            Pacman pacman = new Pacman(new PositionAgent(pos.getX(), pos.getY(), pos.getDir()), null);
            pacmans.add(pacman);

        }

        //Position des Fantomes
        ArrayList<PositionAgent> positionInit = new ArrayList<PositionAgent>(this.maze.getGhosts_start());

        for(int i=0; i < positionInit.size(); i++){

            Fantome fantome = new Fantome(new PositionAgent(positionInit.get(i).getX(), positionInit.get(i).getY(), positionInit.get(i).getDir()), new StrategyRandom(this));
            fantomes.add(fantome);

        }




        //***********************************FOOD----et---CAPSULE**************************************************************


        for(PositionAgent pos : posFood){
            this.maze.setFood(pos.getX(),pos.getY(),true);
        }

        for(PositionAgent pos : posCapsule){
              this.maze.setCapsule(pos.getX(),pos.getY(),true);
        }


        notifierObservateurs();


    }

    @Override
    public void takeTurn() {

    
       for (Fantome fantome : fantomes) {
              moveAgent(fantome, fantome.getStrategie().typeAction(), this.maze);     
       }

       for (Pacman pac : pacmans) {
            moveAgent(pac, new AgentAction(direction), this.maze);
       }

       //**********************------------GESTION DE LA MORT D'UN PACMAN OU D'UN FANTOME------************************************
           
        
        ArrayList<Pacman> PacmanMorts = new ArrayList<>();
        ArrayList<Fantome> FantomeMorts = new ArrayList<>();


        if(capsulesActive){


            for (Fantome f: fantomes) {

            
                 for (Pacman pac : pacmans) {

                 //*************************************----FOOD----******************************************
                    if(this.getMaze().isFood(pac.getPosition().getX(), pac.getPosition().getY())){
                            this.getMaze().setFood(pac.getPosition().getX(), pac.getPosition().getY(), false);

                            score++;
                    }

                //************************************----CAPSULE----***************************************
                     if(this.getMaze().isCapsule(pac.getPosition().getX(), pac.getPosition().getY())){
                             this.getMaze().setCapsule(pac.getPosition().getX(), pac.getPosition().getY(), false);

                             activateCapsulesTimer();
                     }

                    
                    if (pac.getPosition().equals(f.getPosition())) {
                         FantomeMorts.add(f); 

                     }

                }
            
            }


        } else {


            for (Fantome f: fantomes) {

                 for (Pacman pac : pacmans) {

                 //*************************************----FOOD----******************************************
                    if(this.getMaze().isFood(pac.getPosition().getX(), pac.getPosition().getY())){
                            this.getMaze().setFood(pac.getPosition().getX(), pac.getPosition().getY(), false);

                            score++;    
                    }

                //************************************----CAPSULE----***************************************
                     if(this.getMaze().isCapsule(pac.getPosition().getX(), pac.getPosition().getY())){
                             this.getMaze().setCapsule(pac.getPosition().getX(), pac.getPosition().getY(), false);

                            activateCapsulesTimer();
                     }

                    
                    if (pac.getPosition().equals(f.getPosition())) {
                         System.out.println("Pacman Mort"); 
                         PacmanMorts.add(pac); 

                     }

                }
            
            }


        }
           

        for (Fantome fan : FantomeMorts) {
                fantomes.remove(fan);
        }

        for (Pacman pac : PacmanMorts) {
                pacmans.remove(pac);
        }
                
       notifierObservateurs();
    }


    public void activateCapsulesTimer() {
        capsulesActive = true;
        TimerTask capsulesTask = new TimerTask() {
            public void run() {
                capsulesActive = false;
            }
        };
    
        Timer capsulesTimer = new Timer();
        capsulesTimer.schedule(capsulesTask, 5 * 1000); // Là c'est des périodes de 5 mais si on remplace 5 
        // par 20  on aura des périodes de 20  (20 * 1000 ms) 
    }


    @Override
    public boolean gameContinue() {
          return (!pacmans.isEmpty() && !fantomes.isEmpty());
    }

    public void gameOver() {

        if (pacmans.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Game Over", "Partie Terminée", JOptionPane.WARNING_MESSAGE);

        } else if(posFood.isEmpty()){

             JOptionPane.showMessageDialog(null, "Vous avez gagné", "Partie Terminée", JOptionPane.WARNING_MESSAGE);

        }
    }

    public ArrayList<PositionAgent> plusCourtChemin(PositionAgent start, PositionAgent goal, Maze maze) {
        Queue<PositionAgent> queue = new LinkedList<>();
        ArrayList<PositionAgent> visited = new ArrayList<>();
        HashMap<PositionAgent, PositionAgent> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            PositionAgent current = queue.poll();

            if (current.equals(goal)) {
                // Retrouver le chemin en remontant les parents depuis le but
                ArrayList<PositionAgent> path = new ArrayList<>();
                PositionAgent node = current;

                while (node != null) {
                    path.add(node);
                    node = parent.get(node);
                }

                Collections.reverse(path);
                return path;
            }

            // Recherche dans les directions voisines
            int[] dx = {0, 0, 1, -1};
            int[] dy = {1, -1, 0, 0};

            for (int i = 0; i < 4; i++) {
                int newX = current.getX() + dx[i];
                int newY = current.getY() + dy[i];

                if (newX >= 0 && newX < maze.getSizeX() && newY >= 0 && newY < maze.getSizeY()
                        && !maze.isWall(newX, newY)) {
                    PositionAgent next = new PositionAgent(newX, newY, 0);

                    if (!visited.contains(next)) {
                        queue.add(next);
                        visited.add(next);
                        parent.put(next, current);
                    }
                }
            }
        }

        return new ArrayList<>(); // Aucun chemin trouvé
    }

    public int getScore() {
        return score;
    }

    public Maze getMaze() {
        return maze;
    }

    public List<Fantome> getFantomes(){
        return fantomes;
    }

    public List<Pacman> getPacman(){
        return pacmans;
    }


    public boolean getCapsuleActive(){
        return capsulesActive;
    }


    
}