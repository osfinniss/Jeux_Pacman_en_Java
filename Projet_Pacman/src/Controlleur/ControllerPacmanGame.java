package Controlleur;

import Vue.*;
import Modele.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControllerPacmanGame extends AbstractController implements KeyListener {

  private Maze maze;
  private ViewPacmanGame viewpacmangame;


  public static final int UP = KeyEvent.VK_UP;
  public static final int DOWN = KeyEvent.VK_DOWN;
  public static final int LEFT = KeyEvent.VK_LEFT;
  public static final int RIGHT = KeyEvent.VK_RIGHT;

    public ControllerPacmanGame(PacmanGame g ) {

        super(g);
        ViewCommand vc = new ViewCommand(this);
        this.viewpacmangame= new ViewPacmanGame(g);
        g.enregistrerObservateur(this.viewpacmangame);
        g.enregistrerObservateur(vc);
        viewpacmangame.getViewPacGam().addKeyListener(this);
        viewpacmangame.getViewPacGam().setFocusable(true); // Rendre l'élément focusable

       
    }


    @Override
    public void keyPressed(KeyEvent e) {
        // Implémentation pour gérer l'appui sur une touche du clavier
        int keyCode = e.getKeyCode();

        // System.out.println("touche appuyé : " + keyCode);

        switch (keyCode) {
            case UP:
                 game.changementDirection(maze.NORTH);       
                break;
            case DOWN:
                 game.changementDirection(maze.SOUTH);   
                break;
            case LEFT:
                 game.changementDirection(maze.WEST);   
                break;
            case RIGHT:
                 game.changementDirection(maze.EAST);   
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
     
    }
    public Maze getLabyrinthe() {
        return maze;
    }


}







   


