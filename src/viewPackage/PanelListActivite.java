/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.ListFormationException;
import exceptionPackage.NotIdentified;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import modelPackage.Formation;

/**
 *
 * @author Joachim
 */
public class PanelListActivite extends JPanel {
    // Panels et Layouts
    private JPanel panelListForm, panelInfoActivite;
    private GridBagConstraints c;
    
    // Champs, label,...
    private JList listForm;
    private JScrollPane scrollPaneForm;
    private DefaultListModel listModelForm;
    
    private ArrayList<Formation> arrayForm = new ArrayList<Formation>();
    
    ApplicationController app = new ApplicationController();
    
    public PanelListActivite() {
        this.setLayout(new BorderLayout());
        
        panelListForm = new JPanel();
        panelListForm.setLayout(new BorderLayout());
        
            listForm = new JList();
            listModelForm = new DefaultListModel();
            listForm.setModel(listModelForm);      
            
            try {            
                arrayForm = app.listForm();
                listModelForm.clear();
                for(Formation form : arrayForm) {
                    listModelForm.addElement(new QueryResult(form.getIdFormation(), form.getIntitule()));
                }
                listForm.validate();
            } 
            catch (ListFormationException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
            }
            
            scrollPaneForm = new JScrollPane(listForm);
            scrollPaneForm.setPreferredSize(new Dimension(200, Fenetre.getCont().getHeight()));
            
            panelListForm.add(scrollPaneForm, BorderLayout.CENTER);
            
        this.add(panelListForm, BorderLayout.WEST);
        
        panelInfoActivite = new JPanel();
        panelInfoActivite.setLayout(new GridBagLayout());
        
            
        
        this.add(panelInfoActivite, BorderLayout.CENTER);        
    }
    
    private class QueryResult {  
        int id;  
        String desc;  
        public QueryResult(int i, String d) {  
            id = i;
            desc = d;  
        }          
        @Override
        public String toString(){return desc;}  
    } 
}
