/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelPackage.Activite;
import modelPackage.Formation;

/**
 *
 * @author Joachim
 */
public class PanelActivite extends JPanel {
    private JPanel panelLeft, panelInfoActivite;
    private FlowLayout flowPanels = new FlowLayout();
    private GridBagConstraints c;
    
    private JList listFormation, listActivite;
    private JScrollPane scrollPaneFormation, scrollPaneActivite;
    private DefaultListModel listModelFormation, listModelActivite;
    
    private ArrayList<Formation> arrayFormation = new ArrayList<Formation>();
    private ArrayList<Activite> arrayActivite = new ArrayList<Activite>();
    
    private String desc;
    private QueryResult queryResult;
    
    ApplicationController app = new ApplicationController();
    
    public PanelActivite() {
        this.setLayout(flowPanels);
        flowPanels.setAlignment(FlowLayout.LEFT);
        flowPanels.setHgap(1);
        flowPanels.setVgap(1);
        
        panelLeft = new JPanel();
        panelLeft.setLayout(new BorderLayout());
        
            listFormation = new JList();
            listModelFormation = new DefaultListModel();
            listFormation.setModel(listModelFormation);
            
            try {            
                arrayFormation = app.listForm();
                for(Formation form : arrayFormation) {
                    listModelFormation.addElement(new QueryResult(form.getIdFormation(), form.getIntitule()));
                }
                listFormation.validate();
            } 
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
            }
            
            scrollPaneFormation = new JScrollPane(listFormation);
            scrollPaneFormation.setPreferredSize(new Dimension(210, (Fenetre.getCont().getHeight())/2));
            
            panelLeft.add(scrollPaneFormation, BorderLayout.NORTH);
            
            listActivite = new JList();
            listModelActivite = new DefaultListModel();
            listActivite.setModel(listModelActivite);      
            
            listModelActivite.addElement(new QueryResult(-1,"-- Aucune formation sélectionnée --"));
            
            scrollPaneActivite = new JScrollPane(listActivite);
            scrollPaneActivite.setPreferredSize(new Dimension(210, (Fenetre.getCont().getHeight())/2));
            
            panelLeft.add(scrollPaneActivite, BorderLayout.SOUTH);
            
        this.add(panelLeft);
        
        ListListener LL = new ListListener();
        listFormation.addListSelectionListener(LL);
        
        panelInfoActivite = new JPanel();
        panelInfoActivite.setLayout(new BorderLayout());
        
        this.add(panelInfoActivite);
    }
    
    private void UpdateListActivite(Integer idFormation) {
        try {            
            arrayActivite = app.listActivite(idFormation);
            listModelActivite.clear();
            if(arrayActivite.isEmpty() == true) {
                listModelActivite.addElement(new QueryResult(-1,"-- Aucune activité --"));
            }
            else {            
                for(Activite act : arrayActivite) {                    
                    if(act.getPromotion() != null) {
                        desc = "Promotion " + act.getPromotion().toString();
                    }
                    else {
                        desc = act.getDateDeb().getTime().toString();
                    }
                    listModelActivite.addElement(new QueryResult(act.getIdActivite(), desc));
                }            
            }          
        } 
        catch (DBException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
        } 
        catch (NotIdentified ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class ListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {  
            if(lse.getValueIsAdjusting()){
                queryResult = (QueryResult)((JList)lse.getSource()).getSelectedValue();
                if(queryResult.id != -1) {
                    if(lse.getSource() == listFormation) {
                        UpdateListActivite(queryResult.id);
                    }
                }
            }
        }
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
