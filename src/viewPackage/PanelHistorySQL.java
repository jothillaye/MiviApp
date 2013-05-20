/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import modelPackage.Formation;
import modelPackage.HistorySqlList;

/**
 *
 * @author Joachim
 */
public class PanelHistorySQL extends JPanel {
    private JLabel labelTitle;
    private JTable tableHistory;
    private JButton buttonUndo;
    
    private ApplicationController app;
    
    public PanelHistorySQL() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0; c.gridy = 0;
        
        labelTitle = new JLabel("Hitorique des requêtes");
        this.add(labelTitle, c);
        
        tableHistory = new JTable(new HistorySqlList(null));
        c.gridy++;
        this.add(tableHistory, c);
        
        buttonUndo = new JButton("Annuler la modification");
        c.gridy++;
        this.add(buttonUndo, c);
        
//        try {            
//            ArrayList<String> arraySql = app.historySql();
//        } 
//        catch (DBException ex) {
//            JOptionPane.showMessageDialog(null, ex, "Erreur Listing", JOptionPane.ERROR_MESSAGE);
//        } 
//        catch (NotIdentified ex) {
//            JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
//        }
    }
    
}
