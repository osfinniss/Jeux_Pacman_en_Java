
package Modele;
import Controlleur.*;
import javax.swing.*;



public interface EtatBouton {
    void gererLancer(AbstractController controller, JButton boutonLancer, JButton boutonPause, JButton boutonPas, JButton boutonRecommencer);
    void gererPause(AbstractController controller, JButton boutonLancer, JButton boutonPause, JButton boutonPas, JButton boutonRecommencer);
    void gererPas(AbstractController controller, JButton boutonLancer, JButton boutonPause, JButton boutonPas, JButton boutonRecommencer);
    void gererRecommencer(AbstractController controller, JButton boutonLancer, JButton boutonPause, JButton boutonPas, JButton boutonRecommencer);
}
