/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.DeleteMembreException;
import exceptionPackage.ListMembreException;
import exceptionPackage.ModifyMembreException;
import exceptionPackage.NewMembreException;
import exceptionPackage.NotIdentified;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MaskFormatter;
import javax.swing.text.StyledDocument;
import modelPackage.Membre;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Joachim
 */
public class PanelMembre extends JPanel {
    // Panels & Layout
    private JPanel panelListMembre, panelRight, panelStatus, panelButtons, panelFicheMembre, panelHistory;
    private JTabbedPane tabbedPane;
    private FlowLayout flowStatus, flowButtons;
    private GridBagConstraints c;
        
    // Champs & Labels
    private JList listMembre;
    private JScrollPane scrollPaneMembre;
    private DefaultListModel listModelMembre;
	private JTextField fieldId, fieldNom, fieldPrenom, fieldRue, fieldNumero, fieldCodePostal, fieldVille, fieldFixe, fieldGSM, fieldEmail, fieldFilter, fieldSolde;
    private JFormattedTextField fieldDate;
    private JCheckBox checkBoxClientME, checkBoxAssistant, checkBoxAnimateur, checkBoxEcarte;
    private JComboBox comboBoxProvenance, comboBoxContact;
    private JButton buttonSubmit, buttonNewMembre, buttonModify, buttonDelete;	
    private JTextPane textPaneHistory;
    private StyledDocument docHistory;
    
    private QueryResult structMembre;
	
    private ArrayList<Membre> arrayMembre, arrayMembreComboBox;
	
	private PanelMembre panelMembre;	
    
    private ApplicationController app = new ApplicationController();
	
	public PanelMembre() {	
        this.setLayout(new BorderLayout());
        
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
                        UpdateMembre(listMembre.getSelectedIndex());
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
                public void keyTyped(KeyEvent ke) {}
                @Override
                public void keyPressed(KeyEvent ke) {}
                @Override
                public void keyReleased(KeyEvent ke) {
                    UpdateListMembre(fieldFilter.getText());
                }
            });
            
            panelListMembre.add(fieldFilter, BorderLayout.SOUTH);
        
        this.add(panelListMembre, BorderLayout.WEST);
        
            // Panel de droite contenant un JTabbedPane de panelFicheMembre et panelHistory
            panelRight = new JPanel();
            panelRight.setLayout(new BorderLayout());

                panelFicheMembre = new PanelFicheMembre();      
                panelHistory = new PanelHistory();

                tabbedPane = new JTabbedPane(JTabbedPane.NORTH);    
                tabbedPane.addTab("Fiche Membre", panelFicheMembre);
                tabbedPane.addTab("History", panelHistory);

            panelRight.add(tabbedPane, BorderLayout.CENTER); 
        
        this.add(panelRight, BorderLayout.CENTER);	
	}
    
    private class PanelFicheMembre extends JPanel {
        private JLabel labelNom, labelPrenom, labelRue, labelNumero, labelCodePostal, labelVille, labelFixe, labelGSM, labelEmail, labelDateNaiss, labelProvenance, labelContact;
	
        public PanelFicheMembre() {            
            this.setLayout(new GridBagLayout());
            c.insets = new Insets(10,20,0,0); 
            
            // ID du membre
            fieldId = new JTextField();
            fieldId.setVisible(false);
            this.add(fieldId, c);	
            
            // Solde du membre (Détails dans le panel historique)
            fieldSolde = new JTextField();
            fieldSolde.setVisible(false);
            this.add(fieldSolde, c);
            
            // Nom (Field)
            labelNom = new JLabel("Nom : ");
            c.gridx = 0; c.gridy = 0;      
            this.add(labelNom, c);

            fieldNom = new JTextField(12);
            c.gridx = 1;
            this.add(fieldNom, c);	

            // Prénom (Field)
            labelPrenom = new JLabel("Prénom : ");
            c.gridx = 2; 
            this.add(labelPrenom, c);

            fieldPrenom = new JTextField(12);
            c.gridx = 3; 
            this.add(fieldPrenom, c);

            // Date Naiss
            labelDateNaiss = new JLabel("Date de naissance : ");
            c.gridx = 0; c.gridy++;
            this.add(labelDateNaiss, c);

            try {
                fieldDate = new JFormattedTextField(new MaskFormatter("##'/##'/####"));
            } 
            catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors du parsing de la date, veuillez contacter l'administrateur.", "Erreur parsing", JOptionPane.ERROR_MESSAGE);
            }                
            fieldDate.setValue("01/01/1900");
            fieldDate.setColumns(8);
            c.gridx = 1; 
            this.add(fieldDate, c);

            // Rue (Field)
            labelRue = new JLabel("Rue : ");
            c.gridx = 0; c.gridy++; 
            c.insets = new Insets(40,20,0,0);
            this.add(labelRue, c);

            fieldRue = new JTextField(12);                
            c.gridx = 1;
            this.add(fieldRue, c);   

            // Numero (Field)
            labelNumero = new JLabel("Numero : ");
            c.gridx = 2; 
            this.add(labelNumero, c);

            fieldNumero = new JTextField(6);                
            c.gridx = 3;
            this.add(fieldNumero, c);

            // CodePostal (Field)
            labelCodePostal = new JLabel("CodePostal : ");
            c.gridx = 0; c.gridy++; 
            c.insets = new Insets(10,20,0,0);
            this.add(labelCodePostal, c);

            fieldCodePostal = new JTextField(6);                
            c.gridx = 1;
            this.add(fieldCodePostal, c);   

            // Ville (Field)
            labelVille = new JLabel("Ville : ");
            c.gridx = 2; 
            this.add(labelVille, c);

            fieldVille = new JTextField(12);                
            c.gridx = 3;
            this.add(fieldVille, c);

            // Fixe (Field)
            labelFixe = new JLabel("Téléphone Fixe : ");
            c.gridx = 0; c.gridy++; 
            c.insets = new Insets(40,20,0,0);
            this.add(labelFixe, c);

            fieldFixe = new JTextField(12);
            c.gridx = 1;
            this.add(fieldFixe, c); 

            // GSM (Field)
            labelGSM = new JLabel("GSM : ");
            c.gridx = 2; 
            this.add(labelGSM, c);

            fieldGSM = new JTextField(12);
            c.gridx = 3;
            this.add(fieldGSM, c);         

            // Email (Field)
            labelEmail = new JLabel("Email : ");
            c.gridx = 0; c.gridy++; 
            c.insets = new Insets(10,20,0,0);
            this.add(labelEmail, c);

            fieldEmail = new JTextField(16);
            c.gridx = 1;
            this.add(fieldEmail, c);	 

            // Provenance (ComboBox)
            labelProvenance = new JLabel("Provenance : ");
            c.gridx = 0; c.gridy++; 
            c.insets = new Insets(40,20,0,0);
            this.add(labelProvenance, c);

            comboBoxProvenance = new JComboBox();
            comboBoxProvenance.addItem("Aucune");
            comboBoxProvenance.addItem("Site");
            comboBoxProvenance.addItem("Conférence");
            comboBoxProvenance.addItem("Maison de l'Ecologie");
            c.gridx = 1;
            this.add(comboBoxProvenance, c);	 

            // Contact (ComboBox auto-complete)
            labelContact = new JLabel("Contact : ");
            c.gridx = 2;
            this.add(labelContact, c);

            comboBoxContact = new JComboBox();
            c.gridx = 3;
            this.add(comboBoxContact, c);	 
            try {            
                arrayMembreComboBox = app.listMembre(null);
                comboBoxContact.addItem(new QueryResult(0,"Aucun"));
                for(Membre membre : arrayMembreComboBox) {
                    comboBoxContact.addItem(new QueryResult(membre.getId(),membre.getNom().toString().toUpperCase()+", "+membre.getPrenom().toString().toLowerCase()));
                }
            } 
            catch (ListMembreException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
                new LoginPopup();
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
            this.add(panelStatus, c);           

            panelButtons = new JPanel();
            flowButtons = new FlowLayout();
            flowButtons.setAlignment(FlowLayout.LEFT);
            flowButtons.setHgap(2);
            panelButtons.setLayout(flowButtons);
            
                // Bouton Modify
                buttonModify = new JButton("Modifier");
                buttonModify.setVisible(false);
                panelButtons.add(buttonModify);
                
                // Bouton Suprpesion
                buttonDelete = new JButton("Supprimer");
                buttonDelete.setVisible(false);
                panelButtons.add(buttonDelete);

                // Bouton Enregistrer
                buttonSubmit = new JButton("Enregistrer");
                panelButtons.add(buttonSubmit);	

                // Bouton Nouveau Membre
                buttonNewMembre = new JButton("Nouveau Membre");
                panelButtons.add(buttonNewMembre);
                
                ActionManager AM = new ActionManager();
                buttonSubmit.addActionListener(AM);
                buttonNewMembre.addActionListener(AM);	
                buttonModify.addActionListener(AM);	
                buttonDelete.addActionListener(AM);	

            c.gridy++; c.gridx = 0; 
            this.add(panelButtons, c);   
        }
    }
    
    private class PanelHistory extends JPanel {
        private JLabel labelFormation;
        
        public PanelHistory() {
            this.setLayout(new BorderLayout());
            
            labelFormation = new JLabel("Historique formation :");
            this.add(labelFormation, BorderLayout.NORTH);
            
            textPaneHistory = new JTextPane();
            textPaneHistory.setEditable(false);
            this.add(textPaneHistory, BorderLayout.CENTER);
        }
    }
	
	private void reset() {
		Fenetre.getCont().removeAll();
		panelMembre = new PanelMembre();
		Fenetre.getCont().add(panelMembre, BorderLayout.CENTER);
		Fenetre.getCont().validate();		
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
    
    private void UpdateMembre(Integer indexList) {
        try {
            structMembre = (QueryResult)listModelMembre.getElementAt(indexList);  
            Membre me = app.getMembre(structMembre.id);
            
            UpdateInfoMembre(me);
            UpdateHistoryMembre(me);
        }    
        catch (ListMembreException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
        } 
        catch (NotIdentified ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
            new LoginPopup();
        }         
    }
    
    private void UpdateInfoMembre(Membre me) {  
        fieldId.setText(me.getId().toString());
        fieldNom.setText(me.getNom());
        fieldPrenom.setText(me.getPrenom());
        fieldRue.setText(me.getRue());
        fieldNumero.setText(me.getNumero());
        if(me.getCodePostal() != 0) {
            fieldCodePostal.setText(me.getCodePostal().toString());
        }
        else {
            fieldCodePostal.setText("");
        }
        fieldVille.setText(me.getVille());
        if(me.getGsm() != 0) {
            fieldGSM.setText(0+me.getGsm().toString());
        }            
        else {
            fieldGSM.setText("");
        }
        if(me.getFixe() != 0) {
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
        if(me.getSoldeCrediteur() != 0) {
            fieldSolde.setText(0+me.getFixe().toString());
        }
        else {
            fieldSolde.setText("0");
        }   
        
        if(buttonDelete.isVisible() == false) {
            buttonDelete.setVisible(true);
        }
        if(buttonModify.isVisible() == false) {
            buttonModify.setVisible(true);
        }
        if(buttonSubmit.isVisible() == true) {
            buttonSubmit.setVisible(false);
        }
    }
    
    private void UpdateHistoryMembre(Membre me) {        
        docHistory = new DefaultStyledDocument();
        
        // TODO : Describe methode
        //ArrayList<Inscription> = app.getHistoryIns(me);
    }
    
	private class ActionManager implements ActionListener { 
        // Variables temporaires
        String prenom, nom, rue, numero, ville, email, filter;	
        Integer idMembre, idContact, codePostal, fixe, gsm, provenance, idMembreList, reply;    
        Boolean clientME, assistant, animateur, ecarte;
        GregorianCalendar dateNaiss;
        Float solde;
        
        @Override
		public void actionPerformed(ActionEvent e) {
            idMembreList = listMembre.getSelectedIndex();
			if(e.getSource() == buttonNewMembre){		
                reset();
			}
			else if(e.getSource() == buttonSubmit || e.getSource() == buttonModify){
				// Récupération des informations d'inscriptions
				prenom 		= fieldPrenom.getText();				
				nom 		= fieldNom.getText();
                rue         = fieldRue.getText();
                numero      = fieldNumero.getText();
                ville       = fieldVille.getText();
                email       = fieldEmail.getText();
                provenance  = comboBoxProvenance.getSelectedIndex();
                structMembre= (QueryResult)comboBoxContact.getSelectedItem();
                idContact   = structMembre.id;
                clientME    = checkBoxClientME.isSelected();
                assistant   = checkBoxAssistant.isSelected();
                animateur   = checkBoxAnimateur.isSelected();
                ecarte      = checkBoxEcarte.isSelected();
                solde       = Float.parseFloat(fieldSolde.getText());
				dateNaiss 	= new GregorianCalendar();
                try {
                    if(fieldCodePostal.getText().isEmpty() == false) {
                        codePostal = Integer.parseInt(fieldCodePostal.getText());
                    }
                    else {
                        codePostal = 0;
                    }
                    if(fieldGSM.getText().isEmpty() == false) {
                        gsm = Integer.parseInt(fieldGSM.getText());
                    }
                    else {
                        gsm = 0;
                    }
                    if(fieldFixe.getText().isEmpty() == false) {
                        fixe = Integer.parseInt(fieldFixe.getText());
                    }
                    else {
                        fixe = 0;
                    }
                    dateNaiss.set(Integer.parseInt(fieldDate.getText(6,4)), Integer.parseInt(fieldDate.getText(3,2)), Integer.parseInt(fieldDate.getText(0,2)));
                    
                    Membre membre = new Membre(nom, prenom, email, dateNaiss, gsm, fixe, rue, numero, codePostal, ville, provenance, idContact, assistant, animateur, clientME, ecarte, solde);
					
                    if(e.getSource() == buttonSubmit) {
                        reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment ajouter ce membre ?", "Insertion Membre", JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
                            app.newMembre(membre);                        
                            JOptionPane.showMessageDialog(null, "Ajout de "+prenom+" "+nom+" réussi", "Ajout Membre", JOptionPane.INFORMATION_MESSAGE);
                        }                        
                    }
                    else if(e.getSource() == buttonModify) {
                        idMembre = Integer.parseInt(fieldId.getText()); 
                        membre.setId(idMembre);
                        
                        reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment modifier ce membre ?", "Modification Membre", JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
                            app.modifyMembre(membre);
                            JOptionPane.showMessageDialog(null, "Modification de "+prenom+" "+nom+" réussie", "Ajout Membre", JOptionPane.INFORMATION_MESSAGE);
                        }
					}
                    filter = fieldFilter.getText();
                    if(filter.isEmpty()) {
                        filter = null;
                    }
                    UpdateListMembre(filter);
                    UpdateInfoMembre(membre);
                    
                }
                catch(NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Erreur : n'inclure que des chiffres dans les champs suivants :\nCode Postal, GSM, Fixe", "Erreur champs numérique", JOptionPane.ERROR_MESSAGE);                    
                }
                catch (BadLocationException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'insertion veuillez vérifier le contenu des champs et si le problème persiste contacter l'administrateur.", "Erreur insertion", JOptionPane.ERROR_MESSAGE);
                }
                catch(ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'insertion veuillez vérifier le contenu des champs et si le problème persiste contacter l'administrateur.", "Erreur insertion", JOptionPane.ERROR_MESSAGE);
                }
				catch (NewMembreException ex) {
					JOptionPane.showMessageDialog(null, ex, "Erreur ajout", JOptionPane.ERROR_MESSAGE);
				} 
                catch (ModifyMembreException ex) {
					JOptionPane.showMessageDialog(null, ex, "Erreur modification", JOptionPane.ERROR_MESSAGE);
				} 
				catch (NotIdentified ex) {
					JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
					new LoginPopup();
				}
			}
            else if(e.getSource() == buttonDelete) {
                idMembre = Integer.parseInt(fieldId.getText());
                try {
                    reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce membre ?", "Suppresion Membre", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {                        
                        app.deleteMembre(idMembre);
                        JOptionPane.showMessageDialog(null, "Suppression réussie", "Suppression Membre", JOptionPane.INFORMATION_MESSAGE);
                        reset();
                    }
                } 
                catch (DeleteMembreException ex) {
					JOptionPane.showMessageDialog(null, ex, "Erreur suppression", JOptionPane.ERROR_MESSAGE);
				} 
				catch (NotIdentified ex) {
					JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
					new LoginPopup();
				}               
            }
		}			
	}
}
