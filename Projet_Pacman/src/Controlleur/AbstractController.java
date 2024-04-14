    package Controlleur;
    import Modele.Game;
    import Modele.PacmanGame;

    public abstract class AbstractController {

        public PacmanGame game;
        public long time;

        public AbstractController(PacmanGame game) {
            this.game = game;
            this.time = game.getTime();
        }

     
        public void restart() {
          
            game.initializeGame();
        
        }   

        public void run() {
            game.launch();
        }

        public void step(){
            game.step();
        }
        public void play() {
            game.launch();
        }
        public void pause() {
            game.pause();
        }

        public void setSpeed(double speed){
            game.setTime((long)(time/speed));
           
        }


    }
