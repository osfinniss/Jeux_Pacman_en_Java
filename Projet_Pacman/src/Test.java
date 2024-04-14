
import Controlleur.*;
import Modele.*;

public class Test {
    public static void main(String[] args) throws Exception {

        PacmanGame G = new PacmanGame(10000);
        G.init();
        ControllerPacmanGame c = new ControllerPacmanGame(G);
          
    }

}
