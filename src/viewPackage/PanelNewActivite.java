/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

import controllerPackage.ApplicationController;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *
 * @author Joachim
 */
public class PanelNewActivite extends JPanel {
    // Panels et Layouts
    private GridBagConstraints c;
    
    ApplicationController app = new ApplicationController();
    
    public PanelNewActivite() {        
        this.setLayout(new GridBagLayout());
        
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START; // left alignement
        
    }
}
