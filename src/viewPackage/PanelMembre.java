package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.DBException;
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
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import modelPackage.HistoryMembreList;
import modelPackage.Membre;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Joachim
 */
public class PanelMembre extends JPanel {
    // Panels & Layout
    private JPanel panelListMembre, panelRight, panelButtons, panelFicheMembre, panelHistory;
    private JTabbedPane tabbedPane;
    private FlowLayout flowButtons;
    private GridBagConstraints c;
        
    // Champs & Labels
    private JList listMembre;
    private JScrollPane scrollPaneMembre, scrollPaneHistory;
    private DefaultListModel listModelMembre;
	private JTextField fieldId, fieldNom, fieldPrenom, fieldRue, fieldNumero, fieldCodePostal, fieldVille, fieldEmail, fieldFilter, fieldSolde, fieldPays;
    private JFormattedTextField fieldDate, fieldFixe, fieldGSM;
    private JComboBox comboBoxProvenance, comboBoxContact;
    private JButton buttonInsert, buttonNewMembre, buttonModify, buttonDelete;	
    private JTable tableHistory;
    private DefaultComboBoxModel modelContact = new DefaultComboBoxModel();
    
    private QueryResult structMembre;	
    private ArrayList<Membre> arrayMembre, arrayMembreComboBox;
    private URL iconURL;
    private ArrayList<String> arrayPrefix;
	
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
                    if(listMembre.getValueIsAdjusting()){return;} // ignore extra message
                    if(listMembre.getSelectedIndex() != -1) {
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
        
        arrayPrefix = new ArrayList<String>(); 
        arrayPrefix.add("02");
        arrayPrefix.add("03");
        arrayPrefix.add("04");
	}
    
    private class PanelFicheMembre extends JPanel {
        private JLabel labelNom, labelPrenom, labelRue, labelNumero, labelCodePostal, labelVille, labelFixe, labelGSM, labelEmail, labelDateNaiss, labelProvenance, labelContact, labelPays;
	
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
            
            // Pays (ComboBox)
            labelPays = new JLabel("Pays : ");
            c.gridx = 0; c.gridy++;
            this.add(labelPays, c);
            
            fieldPays = new JTextField("Belgique", 10);
            c.gridx++;
            this.add(fieldPays, c);            

            // Fixe (Field)
            labelFixe = new JLabel("Téléphone Fixe : ");
            c.gridx = 0; c.gridy++; 
            c.insets = new Insets(40,20,0,0);
            this.add(labelFixe, c);
            
            try {
                fieldFixe = new JFormattedTextField(new MaskFormatter("### ## ## ##"));
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors du parsing du numéro de téléphone, veuillez contacter l'administrateur.", "Erreur parsing", JOptionPane.ERROR_MESSAGE);
            }
            fieldFixe.setColumns(9);
            c.gridx = 1;
            this.add(fieldFixe, c); 

            // GSM (Field)
            labelGSM = new JLabel("GSM : ");
            c.gridx = 2; 
            this.add(labelGSM, c);
            try {
                fieldGSM = new JFormattedTextField(new MaskFormatter("#### ## ## ##"));
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors du parsing du numéro de gsm, veuillez contacter l'administrateur.", "Erreur parsing", JOptionPane.ERROR_MESSAGE);
            }
            fieldGSM.setColumns(10);
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
            comboBoxContact.setPreferredSize(new Dimension(180, 25));
            c.gridx = 3;
            this.add(comboBoxContact, c);	 
            try {            
                arrayMembreComboBox = app.listMembre(null, null);
                modelContact.addElement(new QueryResult(-1,"Aucun"));
                for(Membre membre : arrayMembreComboBox) {
                    modelContact.addElement(new QueryResult(membre.getIdMembre(),membre.getNom()+", "+membre.getPrenom()));
                }
                comboBoxContact.setModel(modelContact);
            } 
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
            }
            AutoCompleteDecorator.decorate(comboBoxContact);    

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
                buttonNewMembre = new JButton("Nouveau membre");
                buttonNewMembre.setContentAreaFilled(false);
                buttonNewMembre.setVisible(false);
                panelButtons.add(buttonNewMembre);
                
                // Bouton Enregistrer
                buttonInsert = new JButton("Insérer");
                buttonInsert.setContentAreaFilled(false);
                panelButtons.add(buttonInsert);	
                
                ActionManager AM = new ActionManager();
                buttonInsert.addActionListener(AM);
                buttonNewMembre.addActionListener(AM);	
                buttonModify.addActionListener(AM);	
                buttonDelete.addActionListener(AM);	
            
                try {
                    iconURL = this.getClass().getResource("/viewPackage/resources/images/validate.png");
                    buttonModify.setIcon(new ImageIcon(iconURL));

                    iconURL = this.getClass().getResource("/viewPackage/resources/images/delete.png");
                    buttonDelete.setIcon(new ImageIcon(iconURL));

                    iconURL = this.getClass().getResource("/viewPackage/resources/images/newMembre.png");
                    buttonNewMembre.setIcon(new ImageIcon(iconURL));

                    iconURL = this.getClass().getResource("/viewPackage/resources/images/add.png");
                    buttonInsert.setIcon(new ImageIcon(iconURL));
                }
                catch(NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des icônes.\nVeuillez contacter l'administrateur", "Erreur de récupération des icônes", JOptionPane.ERROR_MESSAGE);
                } 
                
            c.gridy++; c.gridx = 0; 
            c.gridwidth = 4;
            this.add(panelButtons, c);   
        }
    }
    
    private class PanelHistory extends JPanel {
        private JLabel labelFormation;
        
        public PanelHistory() {
            this.setLayout(new BorderLayout());
            
            labelFormation = new JLabel("Historique formation :");
            this.add(labelFormation, BorderLayout.NORTH);
            
            tableHistory = new JTable();            
            tableHistory.getTableHeader().setReorderingAllowed(false);
            scrollPaneHistory = new JScrollPane(tableHistory);
                
            this.add(scrollPaneHistory, BorderLayout.CENTER);
        }
    }
	
	private void reset() {
		Fenetre.getCont().removeAll();
		panelMembre = new PanelMembre();
		Fenetre.getCont().add(panelMembre, BorderLayout.CENTER);
		Fenetre.getCont().validate();		
	}   
        
    private void UpdateListMembre(String filter){
        listModelMembre.removeAllElements();
        try {        
            arrayMembre = app.listMembre(filter, filter);
            for(Membre me : arrayMembre){
                listModelMembre.addElement(new QueryResult(me.getIdMembre(),me.getNom()+", "+me.getPrenom()));
            }        
        } 
        catch (DBException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
        } 
        catch (NotIdentified ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
        }   
    }  
    
    private void UpdateMembre(Integer indexList) {
        if(indexList != -1){
            try {
                structMembre = (QueryResult)listModelMembre.getElementAt(indexList);  
                Membre me = app.getMembre(structMembre.id);

                UpdateInfoMembre(me);
                UpdateHistoryMembre(me);
            }    
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
            }    
        }
    }
    
    private void UpdateInfoMembre(Membre me) {  
        fieldId.setText(me.getIdMembre().toString());
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
        fieldPays.setText(me.getPays());
     
        if(me.getFixe() != null && me.getFixe().length() >= 9) {
            String mask = "### ## ## ##", fixeString = me.getFixe();
            if(me.getFixe().subSequence(0, 2).equals("32")) {
                fixeString = "0" + fixeString.substring(2, fixeString.length());
            }                
            if(arrayPrefix.contains(fixeString.substring(0, 2))){
                mask = "## ### ## ##";                           
            }
                
            try {
                fieldFixe.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter(mask)));        
            } 
            catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors du parsing du numéro fixe. Veuillez contacter l'administrateur.", "Erreur Parsing", JOptionPane.ERROR_MESSAGE);
            }
            fieldFixe.setText(fixeString);
        }
        else {
            fieldFixe.setText("");
        }
        
        if(me.getGsm() != null && me.getGsm().length() >= 10){
            fieldGSM.setText(me.getGsm());
        }
        else {
            fieldGSM.setText("");
        }
        
        fieldDate.setText(me.getFormatedDateNaiss());
        fieldEmail.setText(me.getEmail());
        comboBoxProvenance.setSelectedIndex(me.getProvenance());        
        if(me.getIdContact() != -1){
            for(Membre cMembre : arrayMembreComboBox){
                if(cMembre.getIdMembre().equals(me.getIdContact()) == true) {
                    comboBoxContact.setSelectedIndex(arrayMembreComboBox.indexOf(cMembre)+1);
                }
            }
        }
        else {
            comboBoxContact.setSelectedIndex(0);
        }
        
        // Show buttons modify, delete, emptyField on existing membre
        if(buttonDelete.isVisible() == false) {
            buttonDelete.setVisible(true);
        }
        if(buttonModify.isVisible() == false) {
            buttonModify.setVisible(true);
        }        
        if(buttonNewMembre.isVisible() == false) {
            buttonNewMembre.setVisible(true);
        }
        
        // Hide button to insert a membre on new membre
        if(buttonInsert.isVisible() == true) {
            buttonInsert.setVisible(false);
        }
    }
    
    private void UpdateHistoryMembre(Membre me) {                
        try {
            tableHistory.setModel(new HistoryMembreList(app.listInsMembre(me.getIdMembre()))); 
            tableHistory.getColumnModel().getColumn(0).setMinWidth(250);
        }
        catch (DBException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur ajout", JOptionPane.ERROR_MESSAGE);
        }
        catch (NotIdentified ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
        }
    }
    
	private class ActionManager implements ActionListener { 
        // Variables temporaires
        String prenom, nom, rue, numero, ville, pays, email, filter, fixe, gsm, dateString;	
        Integer idMembre, idContact, codePostal = 0, provenance, idMembreList, reply, testGsm, testFixe;    
        Boolean clientME, assistant, animateur, supprime;
        GregorianCalendar dateNaiss;
        Float solde = new Float(0.0);
        
        @Override
		public void actionPerformed(ActionEvent e) {
            idMembreList = listMembre.getSelectedIndex();
			if(e.getSource() == buttonNewMembre){		
                reset();
			}
            else if(e.getSource() == buttonInsert || e.getSource() == buttonModify){
                // Récupération des informations d'inscriptions
                prenom      = fieldPrenom.getText();				
                nom         = fieldNom.getText();
                rue         = fieldRue.getText();
                numero      = fieldNumero.getText();
                ville       = fieldVille.getText();
                pays        = fieldPays.getText();
                email       = fieldEmail.getText();
                provenance  = comboBoxProvenance.getSelectedIndex();
                idContact   = ((QueryResult)comboBoxContact.getSelectedItem()).id;
                dateNaiss   = new GregorianCalendar();
                try {
                    // Test Code Postal
                    if(fieldCodePostal.getText().isEmpty() == false) {
                        codePostal = Integer.parseInt(fieldCodePostal.getText());
                    }
                    // Test GSM
                    if(fieldGSM.getValue() != null) {
                        gsm = fieldGSM.getText().replaceAll("\\s","");
                        testGsm = Integer.parseInt(gsm);
                    }
                    else {
                        gsm = null;
                    }
                    // Test Fixe
                    if(fieldFixe.getValue() != null) {
                        fixe = fieldFixe.getText().replaceAll("\\s","");
                        testFixe = Integer.parseInt(fixe);
                    }
                    else {
                        fixe = null;
                    }
                    
                    if(fieldDate.getValue() != null){
                        dateString = (String)fieldDate.getValue();
                        dateNaiss.set(Integer.parseInt(dateString.substring(6, 10)), Integer.parseInt(dateString.substring(3, 5))-1, Integer.parseInt(dateString.substring(0, 2)));
                    }
                    
                    Membre membre = new Membre(nom, prenom, email, dateNaiss, gsm, fixe, rue, numero, codePostal, ville, pays, provenance, idContact);
                    // Nouveau membre
                    if(e.getSource() == buttonInsert) {
                        idMembre = null;
                        ArrayList<Membre> arrayExistingMembre = app.listMembre(membre.getNom(), null);
                        if(arrayExistingMembre.isEmpty() == true) {
                            idMembre = app.newMembre(membre);     
                            membre.setIdMembre(idMembre);
                        }
                        else {
                            reply = JOptionPane.showConfirmDialog(null, "Il existe déjà "+arrayExistingMembre.size()+" membre(s) avec ce nom, êtes-vous sûr qu'il n'est pas déjà inscrit ?", "Insertion Membre", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                idMembre = app.newMembre(membre);     
                                membre.setIdMembre(idMembre);
                                JOptionPane.showMessageDialog(null, "Ajout de "+prenom+" "+nom+" réussi.", "Ajout Membre", JOptionPane.INFORMATION_MESSAGE);
                            }     
                        }
                    }
                    // Modification membre
                    else if(e.getSource() == buttonModify) {
                        idMembre = Integer.parseInt(fieldId.getText()); 
                        membre.setIdMembre(idMembre);
                        
                        reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment modifier ce membre ?", "Modification Membre", JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
                            app.modifyMembre(membre);
                            JOptionPane.showMessageDialog(null, "Modification de "+prenom+" "+nom+" réussie.", "Ajout Membre", JOptionPane.INFORMATION_MESSAGE);
                        }
					}
                    
                    // Récupération du filtre pour l'actualisation
                    filter = fieldFilter.getText();
                    if(filter.isEmpty()) {
                        filter = null;
                    }
                    
                    // Actualisation après modification, erreur possible ?
                    if(idMembre != null && idMembre == -1) {
                        JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout veuillez contacter l'administrateur.\nRécuperation de l'identifiant du membre impossible.", "Erreur lors de l'ajout", JOptionPane.INFORMATION_MESSAGE);
                        reset();
                    }    
                    else if (idMembre != null){                               
                        UpdateListMembre(filter);
                        UpdateInfoMembre(membre);  
                        for(Object o : listModelMembre.toArray()){      
                            if(((QueryResult)o).id == idMembre){
                                listMembre.setSelectedValue(o, true);
                            }
                        }
                    }
                }
                catch(NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Erreur : n'inclure que des chiffres dans les champs suivants :\nCode Postal, GSM, Téléphone Fixe", "Erreur champs numérique", JOptionPane.ERROR_MESSAGE);                    
                }
				catch (DBException ex) {
					JOptionPane.showMessageDialog(null, ex, "Erreur ajout", JOptionPane.ERROR_MESSAGE);
				}
				catch (NotIdentified ex) {
					JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
				}
			}
            else if(e.getSource() == buttonDelete) {
                idMembre = Integer.parseInt(fieldId.getText());
                try {
                    reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce membre ?", "Suppresion Membre", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {                        
                        app.deleteMembre(idMembre);
                        JOptionPane.showMessageDialog(null, "Suppression réussie.", "Suppression Membre", JOptionPane.INFORMATION_MESSAGE);
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
}
