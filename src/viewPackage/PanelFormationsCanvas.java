/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

import controllerPackage.ApplicationController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Joachim
 */
public class PanelFormationsCanvas extends JPanel {
    // Panels et Layouts
    private JPanel panelListForm, panelInfoForm;
    private GridBagConstraints c;
    
    // Champs, label,...
    private JList listForm;
    private JScrollPane scrollPaneForm;
    private DefaultListModel listModelForm;
    
    ApplicationController app = new ApplicationController();
    
    public PanelFormationsCanvas() {
        this.setLayout(new BorderLayout());
        
        panelListForm = new JPanel();
        panelListForm.setLayout(new BorderLayout());
        
            listForm = new JList();
            listModelForm = new DefaultListModel();
            listForm.setModel(listModelForm);      
            
            app.listForm();
            
            scrollPaneForm = new JScrollPane(listForm);
            scrollPaneForm.setPreferredSize(new Dimension(200, Fenetre.getCont().getHeight()));
            
            panelListForm.add(scrollPaneForm, BorderLayout.CENTER);
        
        this.add(panelListForm, BorderLayout.WEST);
        
        panelInfoForm = new JPanel();
        panelInfoForm.setLayout(new GridBagLayout());
        
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START; // left alignement
        
        this.add(panelInfoForm, BorderLayout.CENTER);
        
        
         
    }
}
