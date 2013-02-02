/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.ListMembreException;
import exceptionPackage.NewMembreException;
import exceptionPackage.NotIdentified;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private JPanel panelListMembre, panelInfoMembre, panelStatus, panelButtons;
    private FlowLayout flowStatus, flowButtons;
    private GridBagConstraints c;
        
    // Champs & Labels
    private JList listMembre;
    private JScrollPane scrollPaneMembre;
    private DefaultListModel listModelMembre;
	private JLabel title, labelNom, labelPrenom, labelRue, labelNumero, labelCodePostal, labelVille, labelFixe, labelGSM, labelEmail, labelDateNaiss, labelProvenance, labelContact;
	private JTextField fieldNom, fieldPrenom, fieldRue, fieldNumero, fieldCodePostal, fieldVille, fieldFixe, fieldGSM, fieldEmail, fieldFilter;
    private JFormattedTextField fieldDate;
    private JCheckBox checkBoxClientME, checkBoxAssistant, checkBoxAnimateur, checkBoxEcarte;
    private JComboBox comboBoxProvenance, comboBoxContact;
    private JButton buttonSubmit, buttonNewMembre, buttonHistory;	

	// Variables temporaires
	private String prenom, nom, rue, numero, ville, email;	
	private Integer codePostal, contact, media, fixe, gsm;    
    private Boolean clientME, assistant, animateur;
    private GregorianCalendar dateNaiss;
	private ArrayList<Membre> arrayMembre, arrayMembreComboBox;
	
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
            listMembre.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listMembre.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {                                        
                    if(listMembre.getValueIsAdjusting()) {
                        UpdateInfoMembre(listMembre.getSelectedIndex());
                    }
                }
            });
            listModelMembre = new DefaultListModel();
            listMembre.setModel(listModelMembre);                           
            UpdateListMembre(null);      
            scrollPaneMembre = new JScrollPane(listMembre);                           
            scrollPaneMembre.setPreferredSize(new Dimension(200, this.getHeight()));
            panelListMembre.add(scrollPaneMembre, BorderLayout.LINE_START);             
            
            fieldFilter = new JTextField();
            fieldFilter.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent ke) {
                    UpdateListMembre(fieldFilter.getText());
                }
                @Override
                public void keyPressed(KeyEvent ke) {}
                @Override
                public void keyReleased(KeyEvent ke) {}
            });
            panelListMembre.add(fieldFilter, BorderLayout.SOUTH);
        
        this.add(panelListMembre, BorderLayout.WEST);
        
        // Panel contenant les champs éditables renseignants nom, prénom,...
        panelInfoMembre = new JPanel();      
        panelInfoMembre.setLayout(new GridBagLayout());
        c.insets = new Insets(10,20,0,0);  

            // Titre 
            title = new JLabel("Gestion des Membres");
            title.setFont(new Font("Serif", Font.BOLD, 18));
            c.gridx = 0; c.gridy = 0; 
            c.gridwidth = 4;
            panelInfoMembre.add(title, c);

            // Nom (Field)
            labelNom = new JLabel("Nom : ");
            c.gridx = 0; c.gridy++;             
            c.gridwidth = 1; // reset wide
            panelInfoMembre.add(labelNom, c);

            fieldNom = new JTextField(12);
            c.gridx = 1;
            panelInfoMembre.add(fieldNom, c);	
            
            // Prénom (Field)
            labelPrenom = new JLabel("Prénom : ");
            c.gridx = 2; 
            panelInfoMembre.add(labelPrenom, c);

            fieldPrenom = new JTextField(12);
            c.gridx = 3; 
            panelInfoMembre.add(fieldPrenom, c);

            // Date Naiss
            labelDateNaiss = new JLabel("Date de naissance : ");
            c.gridx = 0; c.gridy++;
            panelInfoMembre.add(labelDateNaiss, c);

            try {
                fieldDate = new JFormattedTextField(new MaskFormatter("##'/##'/####"));
            } 
            catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors du parsing de la date, veuillez contacter l'administrateur.", "Erreur parsing", JOptionPane.ERROR_MESSAGE);
            }                
            fieldDate.setValue("01/01/1900");
            fieldDate.setColumns(8);
            c.gridx = 1; 
            panelInfoMembre.add(fieldDate, c);
            
            // Rue (Field)
            labelRue = new JLabel("Rue : ");
            c.gridx = 0; c.gridy++; 
            c.insets = new Insets(40,20,0,0);
            panelInfoMembre.add(labelRue, c);

            fieldRue = new JTextField(12);                
            c.gridx = 1;
            panelInfoMembre.add(fieldRue, c);   

            // Numero (Field)
            labelNumero = new JLabel("Numero : ");
            c.gridx = 2; 
            panelInfoMembre.add(labelNumero, c);

            fieldNumero = new JTextField(6);                
            c.gridx = 3;
            panelInfoMembre.add(fieldNumero, c);

            // CodePostal (Field)
            labelCodePostal = new JLabel("CodePostal : ");
            c.gridx = 0; c.gridy++; 
            c.insets = new Insets(10,20,0,0);
            panelInfoMembre.add(labelCodePostal, c);

            fieldCodePostal = new JTextField(6);                
            c.gridx = 1;
            panelInfoMembre.add(fieldCodePostal, c);   

            // Ville (Field)
            labelVille = new JLabel("Ville : ");
            c.gridx = 2; 
            panelInfoMembre.add(labelVille, c);

            fieldVille = new JTextField(12);                
            c.gridx = 3;
            panelInfoMembre.add(fieldVille, c);

            // Fixe (Field)
            labelFixe = new JLabel("Téléphone Fixe : ");
            c.gridx = 0; c.gridy++; 
            c.insets = new Insets(40,20,0,0);
            panelInfoMembre.add(labelFixe, c);

            fieldFixe = new JTextField(12);
            c.gridx = 1;
            panelInfoMembre.add(fieldFixe, c); 

            // GSM (Field)
            labelGSM = new JLabel("GSM : ");
            c.gridx = 2; 
            panelInfoMembre.add(labelGSM, c);

            fieldGSM = new JTextField(12);
            c.gridx = 3;
            panelInfoMembre.add(fieldGSM, c);         

            // Email (Field)
            labelEmail = new JLabel("Email : ");
            c.gridx = 0; c.gridy++; 
            c.insets = new Insets(10,20,0,0);
            panelInfoMembre.add(labelEmail, c);

            fieldEmail = new JTextField(16);
            c.gridx = 1;
            panelInfoMembre.add(fieldEmail, c);	 

            // Provenance (ComboBox)
            labelProvenance = new JLabel("Provenance : ");
            c.gridx = 0; c.gridy++; 
            c.insets = new Insets(40,20,0,0);
            panelInfoMembre.add(labelProvenance, c);

            comboBoxProvenance = new JComboBox();
            comboBoxProvenance.addItem("Aucune");
            comboBoxProvenance.addItem("Site");
            comboBoxProvenance.addItem("Conférence");
            comboBoxProvenance.addItem("Maison de l'Ecologie");
            c.gridx = 1;
            panelInfoMembre.add(comboBoxProvenance, c);	 

            // Contact (ComboBox auto-complete)
            labelContact = new JLabel("Contact : ");
            c.gridx = 2;
            panelInfoMembre.add(labelContact, c);

            comboBoxContact = new JComboBox();
            c.gridx = 3;
            panelInfoMembre.add(comboBoxContact, c);	 
            try {            
                arrayMembreComboBox = app.listMembre(null);
                comboBoxContact.addItem(new QueryResult(0,"Aucun"));
                for(Membre membre : arrayMembreComboBox) {
                    comboBoxContact.addItem(new QueryResult(membre.getId(),membre.getNom().toString().toUpperCase()+", "+membre.getPrenom().toString().toLowerCase()));
                }
            } 
            catch (ListMembreException ex) {
                Logger.getLogger(PanelMembre.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (NotIdentified ex) {
                Logger.getLogger(PanelMembre.class.getName()).log(Level.SEVERE, null, ex);
            }
            AutoCompleteDecorator.decorate(comboBoxContact);
            
            panelStatus = new JPanel();
            flowStatus = new FlowLayout();
            flowStatus.setAlignment(FlowLayout.CENTER);
            flowStatus.setHgap(50);
            panelStatus.setLayout(flowStatus);            
                        
                // Client Maison de l'Ecologie
                checkBoxClientME = new JCheckBox("Client ME");
                panelStatus.add(checkBoxClientME);

                // Assistant
                checkBoxAssistant = new JCheckBox("Assistant");
                panelStatus.add(checkBoxAssistant);        

                // Animateur
                checkBoxAnimateur = new JCheckBox("Animateur");
                panelStatus.add(checkBoxAnimateur); 

                // Ecarte
                checkBoxEcarte = new JCheckBox("Ecarte");
                panelStatus.add(checkBoxEcarte); 
            
            c.gridx = 0; c.gridy++; c.gridwidth = 4;
            c.insets = new Insets(15,20,0,0);
            panelInfoMembre.add(panelStatus, c);           

            panelButtons = new JPanel();
            flowButtons = new FlowLayout();
            flowButtons.setAlignment(FlowLayout.LEFT);
            flowButtons.setHgap(2);
            panelButtons.setLayout(flowButtons);

                // Bouton Enregistrer
                buttonSubmit = new JButton("Enregistrer");
                panelButtons.add(buttonSubmit);	

                // Bouton Nouveau Membre
                buttonNewMembre = new JButton("Nouveau Membre");
                panelButtons.add(buttonNewMembre);
                
                // Bouton Historique
                buttonHistory = new JButton("History");
                panelButtons.add(buttonHistory);

            c.gridy++; c.gridx = 0; 
            panelInfoMembre.add(panelButtons, c);

        this.add(panelInfoMembre, BorderLayout.CENTER);
            
		// Création de l'écouteur d'évenement
		ActionManager AM = new ActionManager();
		buttonSubmit.addActionListener(AM);
		buttonNewMembre.addActionListener(AM);			
		
	}
	
	// Vide et re-remplit la fenetre
	private void reset() {
		Fenetre.getCont().removeAll();
		panelMembre = new PanelMembre();
		Fenetre.getCont().add(panelMembre, BorderLayout.CENTER);
		Fenetre.getCont().validate();		
	}
    
    private void UpdateListMembre(String filter){
        listModelMembre.clear();
        try {        
            arrayMembre = app.listMembre(filter);
            for(Membre me : arrayMembre){
                listModelMembre.addElement(new QueryResult(me.getId(),me.getNom().toString().toUpperCase()+", "+me.getPrenom().toString().toLowerCase()));
            }        
        } 
        catch (ListMembreException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
        } 
        catch (NotIdentified ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
            new LoginPopup();
        }
        listMembre.validate();
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
    
    private void UpdateInfoMembre(Integer indexList) {
        try {
            QueryResult temp = (QueryResult)listModelMembre.getElementAt(indexList);  
            Membre me = app.getMembre(temp.id);
            fieldNom.setText(me.getNom());
            fieldPrenom.setText(me.getPrenom());
            fieldRue.setText(me.getRue());
            fieldNumero.setText(me.getNumero().toString());
            fieldCodePostal.setText(me.getCodePostal().toString());
            fieldVille.setText(me.getVille());
            if(me.getGsm()!=0) {
                fieldGSM.setText(0+me.getGsm().toString());
            }            
            else {
                fieldGSM.setText("");
            }
            if(me.getFixe()!=0) {
                fieldFixe.setText(0+me.getFixe().toString());
            }
            else {
                fieldFixe.setText("");
            }           
            fieldDate.setText(me.getFormatedDateNaiss());
            fieldEmail.setText(me.getEmail());
            comboBoxProvenance.setSelectedIndex(me.getProvenance());
            comboBoxContact.setSelectedIndex(0);
            for(Membre cMembre : arrayMembreComboBox){
                if(cMembre.getId() == me.getIdContact()) {
                    QueryResult qr = new QueryResult(me.getIdContact(), cMembre.getNom().toString().toUpperCase()+", "+cMembre.getPrenom().toString().toLowerCase()); 
                    comboBoxContact.setSelectedItem(qr);
                }
            }
            checkBoxAnimateur.setSelected(me.getAnimateur());
            checkBoxAssistant.setSelected(me.getAssistant());
            checkBoxClientME.setSelected(me.getClientME());
            checkBoxEcarte.setSelected(me.getEcarte());
        } 
        catch (ListMembreException ex) {
            Logger.getLogger(PanelMembre.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (NotIdentified ex) {
            Logger.getLogger(PanelMembre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
	// Action Manager
	private class ActionManager implements ActionListener { 
        @Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == buttonNewMembre){				
                dateNaiss 	= new GregorianCalendar();
                reset();
			}
			else if(e.getSource() == buttonSubmit){
				// Récupération des informations d'inscriptions
				prenom 		= fieldPrenom.getText();				
				nom 		= fieldNom.getText();
                rue         = fieldRue.getText();
                numero      = fieldNumero.getText();
                ville       = fieldVille.getText();
                email       = fieldEmail.getText();
                media       = comboBoxProvenance.getSelectedIndex();
                contact     = comboBoxContact.getSelectedIndex();
                clientME    = checkBoxClientME.isSelected();
                assistant   = checkBoxAssistant.isSelected();
                animateur   = checkBoxAnimateur.isSelected();
				dateNaiss 	= new GregorianCalendar();
                try {
                    if(fieldCodePostal.getText().isEmpty() == false) {
                        codePostal = Integer.parseInt(fieldCodePostal.getText());
                    }                
                    if(fieldGSM.getText().isEmpty() == false) {
                        gsm = Integer.parseInt(fieldGSM.getText());
                    }
                    if(fieldFixe.getText().isEmpty() == false) {
                        fixe = Integer.parseInt(fieldFixe.getText());
                    }
                }
                catch(NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "N'inclure que des entiers positifs dans les champs suivants :\nCode Postal, GSM, Fixe", "Erreur champs numérique", JOptionPane.ERROR_MESSAGE);                    
                }
				try {                 
                    dateNaiss.set(Integer.parseInt(fieldDate.getText(6,4)), Integer.parseInt(fieldDate.getText(3,2)), Integer.parseInt(fieldDate.getText(0,2)));
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
