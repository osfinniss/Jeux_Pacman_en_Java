package Vue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import Modele.Observateur;
import java.awt.*;

public class ViewSimpleGame implements Observateur  {

        private JFrame viewSimGam;
        private JLabel turnLabel;
    
        public ViewSimpleGame(){

            viewSimGam = new JFrame("View Simple Game");
            viewSimGam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            viewSimGam.setSize(700,700); 
            Dimension windowSize = viewSimGam.getSize();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Point centerPoint = ge.getCenterPoint();
            int dx = centerPoint.x - windowSize.width / 2 ;
            int dy = centerPoint.y - windowSize.height / 2 - 350;
            viewSimGam.setLocation(dx, dy);

            this.turnLabel = new JLabel("Turn: 0"); 
            turnLabel.setHorizontalAlignment(JLabel.CENTER);
            viewSimGam.add(turnLabel, BorderLayout.NORTH);
            
            viewSimGam.setVisible(true);

        }

        @Override
        public void actualiser(int turn) {
           // turnLabel.setText("Turn: " + turn);
        }
        
        
}
