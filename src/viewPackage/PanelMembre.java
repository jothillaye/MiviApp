/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.GetMembreException;
import exceptionPackage.NewMembreException;
import exceptionPackage.NotIdentified;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import modelPackage.Membre;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Joachim
 */
public class PanelMembre extends JPanel {
    // Panels & Layout
    private JPanel panelListMembre, panelInfoMembre, panelCoordonnee, panelStatus, panelButtons;
    private GridBagConstraints c;
    
    // Champs & Labels
    private JList listMembre;
    private DefaultListModel listModelMembre;
	private JLabel title, labelNom, labelPrenom, labelAdresse, labelFixe, labelGSM, labelEmail, labelDateNaiss, labelProvenance, labelContact;
	private JTextField fieldNom, fieldPrenom, fieldFixe, fieldGSM, fieldEmail;
    private JTextArea areaAdresse;
    private JFormattedTextField fieldDate;
    private JCheckBox checkBoxClientME, checkBoxAssistant, checkBoxAnimateur;
    private JComboBox comboBoxProvenance, comboBoxContact;
    private JButton buttonSubmit, buttonReset;	

	// Variables temporaires
	private String prenom, nom, adresse, email, fixe, gsm;
	private GregorianCalendar dateNaiss;
	private int contact, media;
	private ArrayList<Membre> arrayMembre;
    private Boolean clientME, assistant, animateur;
	
	private PanelMembre panelMembre;	
    
    private ApplicationController app = new ApplicationController();
	
	public PanelMembre() {	
        this.setLayout(new BorderLayout());
        
        // Gestion de l'affichage via GridBagLayout
        c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START; // left alignement
        
        // Panel contenant la liste des membres déjà enregistré, filtrable
        panelListMembre = new JPanel();
        panelListMembre.setLayout(new BorderLayout());        
        
            listMembre = new JList();
            panelListMembre.add(listMembre, BorderLayout.LINE_START);    

            listModelMembre = new DefaultListModel();
            try {        
                System.out.println("ok 1");
                arrayMembre = app.getMembre("");
                for(Membre me : arrayMembre){
                    listModelMembre.addElement(me.getNom().toString()+", "+me.getPrenom().toString());
                }        
            } 
            catch (GetMembreException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
                new LoginPopup();
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
            } 
            listMembre.setModel(listModelMembre);
            listMembre.validate();
        
        this.add(panelListMembre, BorderLayout.WEST);
        
        // Panel contenant les champs permetant l'ajout ou la modification de membre
        panelInfoMembre = new JPanel();
        panelInfoMembre.setLayout(new BorderLayout());
        
            // Panel contenant les champs éditables renseignants nom, prénom,...
            panelCoordonnee = new JPanel();      
            panelCoordonnee.setLayout(new GridBagLayout());
            c.insets = new Insets(15,30,0,0);  // top padding

                // Titre 
                title = new JLabel("Nouveau Membre");
                title.setFont(new Font("Serif", Font.BOLD, 18));
                c.gridx = 0; c.gridy = 0; 
                c.gridwidth = 3; // 3 columns large
                panelCoordonnee.add(title, c);

                // Prénom (Field)
                labelPrenom = new JLabel("Prénom : ");
                c.gridx = 0; c.gridy++; 
                c.gridwidth = 1; // reset wide
                panelCoordonnee.add(labelPrenom, c);

                fieldPrenom = new JTextField(20);
                c.gridx = 1; 
                panelCoordonnee.add(fieldPrenom, c);		

                // Nom (Field)
                labelNom = new JLabel("Nom : ");
                c.gridx = 0; c.gridy++; 
                panelCoordonnee.add(labelNom, c);

                fieldNom = new JTextField(20);
                Border b = fieldNom.getBorder();
                c.gridx = 1;
                panelCoordonnee.add(fieldNom, c);		

                // Adresse (Area)
                labelAdresse = new JLabel("Adresse : ");
                c.gridx = 0; c.gridy++; 
                panelCoordonnee.add(labelAdresse, c);
                
                areaAdresse = new JTextArea(2,20);
                areaAdresse.setBorder(b);
                c.gridx = 1;
                panelCoordonnee.add(areaAdresse, c);        

                // Fixe (Field)
                labelFixe = new JLabel("Téléphone Fixe : ");
                c.gridx = 0; c.gridy++; 
                panelCoordonnee.add(labelFixe, c);

                fieldFixe = new JTextField(20);
                c.gridx = 1;
                panelCoordonnee.add(fieldFixe, c); 

                // GSM (Field)
                labelGSM = new JLabel("GSM : ");
                c.gridx = 0; c.gridy++; 
                panelCoordonnee.add(labelGSM, c);

                fieldGSM = new JTextField(20);
                c.gridx = 1;
                panelCoordonnee.add(fieldGSM, c);         

                // Email (Field)
                labelEmail = new JLabel("Email : ");
                c.gridx = 0; c.gridy++; 
                panelCoordonnee.add(labelEmail, c);

                fieldEmail = new JTextField(20);
                c.gridx = 1;
                panelCoordonnee.add(fieldEmail, c);	 
                
                // Date Naiss
                labelDateNaiss = new JLabel("Date de naissance : ");
                c.gridx = 0; c.gridy++;
                panelCoordonnee.add(labelDateNaiss, c);
                
                try {
                    fieldDate = new JFormattedTextField(new MaskFormatter("##'/##'/####"));
                } 
                catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors du parsing de la date, veuillez contacter l'administrateur.", "Erreur parsing", JOptionPane.ERROR_MESSAGE);
                }                
                fieldDate.setValue("01/01/1900");
                fieldDate.setColumns(8);
                c.gridx = 1; 
                panelCoordonnee.add(fieldDate, c);
                
                // Provenance (ComboBox)
                labelProvenance = new JLabel("Provenance : ");
                c.gridx = 0; c.gridy++; 
                panelCoordonnee.add(labelProvenance, c);

                comboBoxProvenance = new JComboBox();
                c.gridx = 1;
                panelCoordonnee.add(comboBoxProvenance, c);	 
                
                // Contact (ComboBox auto-complete)
                labelContact = new JLabel("Contact : ");
                c.gridx = 0; c.gridy++; 
                panelCoordonnee.add(labelContact, c);

                comboBoxContact = new JComboBox();
                c.gridx = 1;
                panelCoordonnee.add(comboBoxContact, c);	 
                
                AutoCompleteDecorator.decorate(comboBoxContact);
                
                panelButtons = new JPanel();
                panelButtons.setLayout(new BorderLayout());

                    // Boutons Enregistrer
                    buttonSubmit = new JButton("Enregistrer");
                    panelButtons.add(buttonSubmit, BorderLayout.LINE_START);	

                    // Boutons Reset
                    buttonReset = new JButton("Reset");
                    panelButtons.add(buttonReset, BorderLayout.LINE_END);

                c.gridy++;
                panelCoordonnee.add(panelButtons, c);
                
            panelInfoMembre.add(panelCoordonnee, BorderLayout.WEST);
            
            // Panel Status contenant les cases informants si le membre est client maison ecologie, assisant,...
            panelStatus = new JPanel();
            panelStatus.setLayout(new GridBagLayout());
            c.gridy = 0;
            c.anchor = GridBagConstraints.LINE_START; // left alignement
            c.insets = new Insets(15,30,0,0);  // top padding

                // Client Maison de l'Ecologie
                checkBoxClientME = new JCheckBox("Client Maison de l'Ecologie : ");
                c.gridy++;
                panelStatus.add(checkBoxClientME, c);

                // Assistant
                checkBoxAssistant = new JCheckBox("Assistant : ");
                c.gridy++;
                panelStatus.add(checkBoxAssistant, c);        

                // Animateur
                checkBoxAnimateur = new JCheckBox("Animateur : ");
                c.gridy++;
                panelStatus.add(checkBoxAnimateur, c); 
                
            panelInfoMembre.add(panelStatus, BorderLayout.EAST);
            
        this.add(panelInfoMembre, BorderLayout.EAST);               
            
		// Création de l'écouteur d'évenement
		ActionManager AM = new ActionManager();
		buttonSubmit.addActionListener(AM);
		buttonReset.addActionListener(AM);			
		
	}
	
	// Vide et re-remplit la fenetre
	private void reset() {
		Fenetre.getCont().removeAll();
		panelInfoMembre = new PanelMembre();
		Fenetre.getCont().add(panelInfoMembre, BorderLayout.CENTER);
		Fenetre.getCont().validate();		
	}
		
	// Action Manager
	private class ActionManager implements ActionListener { 
        @Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == buttonReset){				
                dateNaiss 	= new GregorianCalendar();
                reset();
			}
			else if(e.getSource() == buttonSubmit){
				// Récupération des informations d'inscriptions
				prenom 		= fieldPrenom.getText();				
				nom 		= fieldNom.getText();
                adresse     = areaAdresse.getText();
                email       = fieldEmail.getText();
                gsm         = fieldGSM.getText();
                media       = comboBoxProvenance.getSelectedIndex();
                contact     = comboBoxContact.getSelectedIndex();
                clientME    = checkBoxClientME.isSelected();
                assistant   = checkBoxAssistant.isSelected();
                animateur   = checkBoxAnimateur.isSelected();
				dateNaiss 	= new GregorianCalendar();
				try {                 
                    dateNaiss.set(Integer.parseInt(fieldDate.getText(6,4)), Integer.parseInt(fieldDate.getText(3,2))-1, Integer.parseInt(fieldDate.getText(0,2)));
                } 
                catch (BadLocationException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur dans la date à corriger.", "Erreur date", JOptionPane.ERROR_MESSAGE);
                }
                catch(ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur dans la date à corriger.", "Erreur date", JOptionPane.ERROR_MESSAGE);
                }
                try {
					Membre membre = new Membre(nom, prenom, dateNaiss);
					app.newMembre(membre);
					JOptionPane.showMessageDialog(null, "Ajout du membre réussi", "Ajout Membre", JOptionPane.INFORMATION_MESSAGE);
					reset();
				} 
				catch (NewMembreException ex) {
					JOptionPane.showMessageDialog(null, ex, "Erreur ajout", JOptionPane.ERROR_MESSAGE);
				} 
				catch (NotIdentified ex) {
					JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
					new LoginPopup();
				}
			}				
		}			
	}
}
