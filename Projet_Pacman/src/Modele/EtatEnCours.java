
package Modele;
import Controlleur.*;
import javax.swing.*;


public class EtatEnCours implements EtatBouton {
    @Override
    public void gererLancer(AbstractController controller, JButton boutonLancer, JButton boutonPause, JButton boutonPas, JButton boutonRecommencer) {
        boutonLancer.setEnabled(false);
        boutonPause.setEnabled(true);
        boutonPas.setEnabled(false);
        boutonRecommencer.setEnabled(false);

        controller.run(); 
    }

    @Override
    public void gererPause(AbstractController controller, JButton boutonLancer, JButton boutonPause, JButton boutonPas, JButton boutonRecommencer) {
        boutonLancer.setEnabled(true);
        boutonPause.setEnabled(true);
        boutonPas.setEnabled(true);
        boutonRecommencer.setEnabled(true);

        controller.pause();
    }

    @Override
    public void gererPas(AbstractController controller, JButton boutonLancer, JButton boutonPause, JButton boutonPas, JButton boutonRecommencer) {
        boutonRecommencer.setEnabled(true);
        controller.step(); 
    }

    @Override
    public void gererRecommencer(AbstractController controller, JButton boutonLancer, JButton boutonPause, JButton boutonPas, JButton boutonRecommencer) {
        boutonLancer.setEnabled(true);
        boutonPause.setEnabled(false);
        boutonPas.setEnabled(false);
        boutonRecommencer.setEnabled(true);

        controller.restart(); 
    }
}