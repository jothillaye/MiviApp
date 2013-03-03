package viewPackage;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Joachim
 */
public class PanelAccueil extends JPanel{
    private JLabel titleAccueil;
    private GridBagConstraints c;
    
    public PanelAccueil() {
        setLayout(new GridBagLayout());
		c = new GridBagConstraints();		
		c.anchor = GridBagConstraints.LINE_START; // aligner � gauche
		c.insets = new Insets(20,0,0,0);  // top padding		
		
			// Titre 
			titleAccueil = new JLabel("Bienvenue");
			titleAccueil.setFont(new Font("Serif", Font.BOLD, 26));
			c.gridx = 0; c.gridy = 0;
			add(titleAccueil, c);
    }
}
