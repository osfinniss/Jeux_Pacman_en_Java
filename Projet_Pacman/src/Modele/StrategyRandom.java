package Modele;

import java.util.Random;

public class StrategyRandom implements Strategie {

    PacmanGame pacmangame;

    public StrategyRandom(PacmanGame g){
        this.pacmangame = g;
    }

    public AgentAction typeAction(){

          Random random = new Random();
         
          int[] directions = { Maze.NORTH, Maze.SOUTH, Maze.EAST, Maze.WEST };
                
          int randomDirectionIndex = random.nextInt(directions.length);
          int randomDirection = directions[randomDirectionIndex];

          AgentAction actionRandom = new AgentAction(randomDirection);

          
          return actionRandom;
          
      
          

    }
    
}
