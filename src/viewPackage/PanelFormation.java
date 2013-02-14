/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.NotIdentified;
import exceptionPackage.DBException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelPackage.Formation;

/**
 *
 * @author Joachim
 */
public class PanelFormation extends JPanel {
    // Panels et Layouts
    private JPanel panelButtons;
    private FlowLayout flowButtons;
    private GridBagConstraints c;
    
    // Champs, label,...
    private JComboBox comboBoxForm;
    private JTextField fieldIntitule;
    private JLabel labelIntitule, titleFormation;
    private JButton buttonNewForm, buttonModify, buttonDelete, buttonInsert;    
    
    private ArrayList<Formation> arrayForm = new ArrayList<Formation>();
    private URL iconURL;    
            
    ApplicationController app = new ApplicationController();
    PanelFormation panelFormation;
    
    public PanelFormation() {        
        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(20, 0, 0, 0);
        c.gridx = 0; c.gridy = 0;
        c.gridwidth = 2;
        
        // Titre 
        titleFormation = new JLabel("Gestion des canvas");
        titleFormation.setFont(new Font("Serif", Font.BOLD, 21));
        add(titleFormation, c);
        
        comboBoxForm = new JComboBox();    

        try {            
            arrayForm = app.listForm();
            comboBoxForm.addItem(new QueryResult(-1, "-- Nouvelle Formation --"));
            for(Formation form : arrayForm) {
                comboBoxForm.addItem(new QueryResult(form.getIdFormation(), form.getIntitule().toString()));
            }
        } 
        catch (DBException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
        } 
        catch (NotIdentified ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
        }
        
        c.gridy++;
        this.add(comboBoxForm, c);
        
        c.gridwidth = 1;
        
        labelIntitule = new JLabel("Intitulé : ");
        c.gridy++;
        this.add(labelIntitule, c);

        fieldIntitule = new JTextField(20);
        c.gridx++;
        this.add(fieldIntitule, c);

        panelButtons = new JPanel();
        flowButtons = new FlowLayout();
        flowButtons.setAlignment(FlowLayout.LEFT);
        flowButtons.setHgap(5);
        panelButtons.setLayout(flowButtons);

            // Bouton Modify
            buttonModify = new JButton("Modifier");
            buttonModify.setContentAreaFilled(false);
            buttonModify.setVisible(false);
            panelButtons.add(buttonModify);

            // Bouton Suprpesion
            buttonDelete = new JButton("Supprimer");
            buttonDelete.setContentAreaFilled(false);
            buttonDelete.setVisible(false);
            panelButtons.add(buttonDelete);

            // Bouton Vider les champs (Nouveau Membre)
            buttonNewForm = new JButton("Nouveau membre");
            buttonNewForm.setContentAreaFilled(false);
            buttonNewForm.setVisible(false);
            panelButtons.add(buttonNewForm);

            // Bouton Enregistrer
            buttonInsert = new JButton("Insérer");
            buttonInsert.setContentAreaFilled(false);
            panelButtons.add(buttonInsert);	

            ActionManager AM = new ActionManager();
            buttonInsert.addActionListener(AM);
            buttonNewForm.addActionListener(AM);	
            buttonModify.addActionListener(AM);	
            buttonDelete.addActionListener(AM);
            comboBoxForm.addActionListener(AM);

            try {
                iconURL = this.getClass().getResource("/viewPackage/resources/images/validate.png");
                buttonModify.setIcon(new ImageIcon(iconURL));

                iconURL = this.getClass().getResource("/viewPackage/resources/images/delete.png");
                buttonDelete.setIcon(new ImageIcon(iconURL));

                iconURL = this.getClass().getResource("/viewPackage/resources/images/newMembre.png");
                buttonNewForm.setIcon(new ImageIcon(iconURL));

                iconURL = this.getClass().getResource("/viewPackage/resources/images/add.png");
                buttonInsert.setIcon(new ImageIcon(iconURL));
            }
            catch(NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des icônes.\nVeuillez contacter l'administrateur", "Erreur de récupération des icônes", JOptionPane.ERROR_MESSAGE);
            } 

        c.gridy++; c.gridx = 0; 
        c.gridwidth = 2;
        this.add(panelButtons, c);  
    }

    private class ActionManager implements ActionListener {        
        private Formation form;
        private QueryResult structForm;
        private Integer reply;
        
        @Override
		public void actionPerformed(ActionEvent e) {
            if(e.getSource() == comboBoxForm) {
                if(comboBoxForm.getSelectedIndex() == 0) {
                    fieldIntitule.setText("");

                    // Show buttons modify, delete, emptyField on existing membre
                    if(buttonDelete.isVisible() == true) {
                        buttonDelete.setVisible(false);
                    }
                    if(buttonModify.isVisible() == true) {
                        buttonModify.setVisible(false);
                    }        

                    // Hide button to insert a membre on new membre
                    if(buttonInsert.isVisible() == false) {
                        buttonInsert.setVisible(true);
                    }                    
                }
                else {
                    structForm = (QueryResult)comboBoxForm.getSelectedItem();
                    fieldIntitule.setText(structForm.intitule);

                    // Show buttons modify, delete, emptyField on existing membre
                    if(buttonDelete.isVisible() == false) {
                        buttonDelete.setVisible(true);
                    }
                    if(buttonModify.isVisible() == false) {
                        buttonModify.setVisible(true);
                    }        

                    // Hide button to insert a membre on new membre
                    if(buttonInsert.isVisible() == true) {
                        buttonInsert.setVisible(false);
                    }
                }
            }
            else if(e.getSource() == buttonInsert) {
                form = new Formation(fieldIntitule.getText());
                try {
                    app.newFormation(form);
                    reset();
                } 
                catch (DBException ex) {
                    JOptionPane.showMessageDialog(null, ex, "Erreur insertion", JOptionPane.ERROR_MESSAGE);
                } 
                catch (NotIdentified ex) {
                    JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(e.getSource() == buttonModify) {
                structForm = (QueryResult)comboBoxForm.getSelectedItem();
                
                form = new Formation();
                form.setIdFormation(structForm.id);
                form.setIntitule(fieldIntitule.getText());
                
                try {
                    reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment modifier ce canvas ?", "Modification Canvas", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {                        
                        app.modifyFormation(form);
                        reset();
                    }
                } 
                catch (DBException ex) {
                    JOptionPane.showMessageDialog(null, ex, "Erreur modification", JOptionPane.ERROR_MESSAGE);
                } 
                catch (NotIdentified ex) {
                    JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(e.getSource() == buttonDelete) {
                structForm = (QueryResult)comboBoxForm.getSelectedItem();
                try {
                    reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce canvas ?", "Suppresion Canvas", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {                        
                        app.deleteFormation(structForm.id);
                        reset();
                    }                        
                } 
                catch (DBException ex) {
                    JOptionPane.showMessageDialog(null, ex, "Erreur suppression", JOptionPane.ERROR_MESSAGE);
                } 
                catch (NotIdentified ex) {
                    JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void reset() {
 		Fenetre.getCont().removeAll();
		panelFormation = new PanelFormation();
		Fenetre.getCont().add(panelFormation, BorderLayout.CENTER);
		Fenetre.getCont().validate();	
    }
    
    private class QueryResult {  
        int id;  
        String intitule;  
        public QueryResult(int i, String desc) {  
            id = i;
            intitule = desc;  
        }          
        @Override
        public String toString(){return intitule;} 
    } 
}
