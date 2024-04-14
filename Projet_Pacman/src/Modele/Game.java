package Modele;
import java.lang.Runnable;
import java.lang.Thread;

public abstract class Game implements Runnable {

    private int turn;
    private int maxTurn;
    private boolean isRunning;
    private Thread thread;
    private long time;

   

    public void launch(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
      
    }

    public Game(int maxTurn) {
        this.maxTurn = maxTurn;
        this.turn = 0;
        this.isRunning = false;
        this.time = 100;
        
    }

    public abstract void initializeGame();
    public abstract void takeTurn();
    public abstract boolean gameContinue();
    public abstract void gameOver();

    public void init() {
        turn = 0;
        isRunning = true;
        initializeGame();
    }

    public void step() {
        ++turn;
        if (isRunning && gameContinue() && turn <= maxTurn) {
            takeTurn();
        } else {
            isRunning = false;
            gameOver();
        }
    }

    public void pause() {
        isRunning = false;
    }

    public void run() {
        while (isRunning) {
            step();
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                 e.printStackTrace();
            }

        }
    }

    //Getters:
    public int getTurn() {
        return turn;
    }

    public int getMaxTurn() {
        return maxTurn;
    }

    public boolean isRunning() {
        return isRunning;
    }
     public Thread getThread() {
        return thread;
    }

    public long getTime(){
        return time;
    }

    public void setTime(long t){
        this.time = t;
    }
}


