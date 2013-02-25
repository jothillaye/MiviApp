/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;
import modelPackage.AccordPaiementList;
import modelPackage.Activite;
import modelPackage.Formation;
import modelPackage.Inscription;
import modelPackage.Membre;
import modelPackage.Paiement;
import modelPackage.PaiementList;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Joachim
 */
public class PanelInscription extends JPanel {
    private JPanel panelFormAct, panelListInscription, panelRight, panelInfoInscription, panelPaiement, panelAccordPaiement;
    private FlowLayout flowPanels, flowIns;
    private GridBagConstraints c;
    
    private JList listFormation, listActivite, listInscription;
    private JScrollPane scrollPaneFormation, scrollPaneActivite, scrollPaneMembre, scrollPaneTablePaiement, scrollPaneTableAccordPaiement;
    private DefaultListModel listModelFormation, listModelActivite, listModelInscription;
    private JLabel labelTarifSpecial;
    private JTextField fieldTarifSpecial;
    private JCheckBox checkAbandonne, checkCertifie;
    private JTable tablePaiement, tableAccordPaiement;
    private TableCellListener tclP, tclA;
    private JComboBox comboBoxType, comboBoxMembre;
    private JButton buttonAddPaiement, buttonDelPaiement, buttonAddAccord, buttonDelAccord, buttonModify, buttonNewIns, buttonDelIns;
    private JFormattedTextField fieldDate;
    
    private ArrayList<Formation> arrayFormation = new ArrayList<Formation>();
    private ArrayList<Activite> arrayActivite = new ArrayList<Activite>();
    private ArrayList<Membre> arrayInscription = new ArrayList<Membre>();
    private ArrayList<Membre> arrayMembre = new ArrayList<Membre>();
    private ArrayList<Paiement> arrayPaiement = new ArrayList<Paiement>();
    private ArrayList<Paiement> arrayAccordPaiement = new ArrayList<Paiement>();
    private Paiement paiement;
    
    private String desc, date;
    private QueryResult queryResult;
    private Inscription ins;
    private GregorianCalendar datePaiement, oldDate;    
    private URL iconURL; 
    private Dimension dimButton;
    private Float solde, prix, prixSpecial;
    
    ApplicationController app = new ApplicationController();
    
    public PanelInscription() {
        flowPanels = new FlowLayout();
        this.setLayout(flowPanels);
        flowPanels.setAlignment(FlowLayout.LEFT);
        flowPanels.setHgap(1);
        flowPanels.setVgap(1);
        
        panelFormAct = new PanelFormAct();
        this.add(panelFormAct);
        
        panelListInscription = new PanelListInscription();        
        this.add(panelListInscription);
        
        panelRight = new PanelRight();       
        this.add(panelRight);
        
        ListListener LL = new ListListener();
        listFormation.addListSelectionListener(LL);
        listActivite.addListSelectionListener(LL); 
        listInscription.addListSelectionListener(LL);
        
        ActionManager AM = new ActionManager();
        buttonNewIns.addActionListener(AM);
        buttonDelIns.addActionListener(AM);
        buttonAddPaiement.addActionListener(AM);
        buttonDelPaiement.addActionListener(AM);
        buttonAddAccord.addActionListener(AM);
        buttonDelAccord.addActionListener(AM);
        buttonModify.addActionListener(AM);        
        
        try {
            iconURL = this.getClass().getResource("/viewPackage/resources/images/validate.png");
            buttonModify.setIcon(new ImageIcon(iconURL));

            iconURL = this.getClass().getResource("/viewPackage/resources/images/add.png");
            buttonAddPaiement.setIcon(new ImageIcon(iconURL));
            buttonAddAccord.setIcon(new ImageIcon(iconURL));
            buttonNewIns.setIcon(new ImageIcon(iconURL));                    

            iconURL = this.getClass().getResource("/viewPackage/resources/images/delete.png");
            buttonDelAccord.setIcon(new ImageIcon(iconURL));
            buttonDelIns.setIcon(new ImageIcon(iconURL));
            buttonDelPaiement.setIcon(new ImageIcon(iconURL));
        }
        catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des icônes.\nVeuillez contacter l'administrateur", "Erreur de récupération des icônes", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private class PanelFormAct extends JPanel {
        public PanelFormAct(){
            this.setLayout(new BorderLayout());
            listFormation = new JList();            
            listModelFormation = new DefaultListModel();
            listFormation.setModel(listModelFormation);
            listFormation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
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
            
            this.add(scrollPaneFormation, BorderLayout.NORTH);
            
            listActivite = new JList();            
            listModelActivite = new DefaultListModel();
            listActivite.setModel(listModelActivite);      
            listActivite.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            listModelActivite.addElement(new QueryResult(-1,"-- Aucune formation sélectionnée --"));
            
            scrollPaneActivite = new JScrollPane(listActivite);
            scrollPaneActivite.setPreferredSize(new Dimension(210, (Fenetre.getCont().getHeight())/2));
            
            this.add(scrollPaneActivite, BorderLayout.SOUTH);
        }
    }
    
    private class PanelListInscription extends JPanel {
        public PanelListInscription() {
            this.setLayout(new GridBagLayout());
            c = new GridBagConstraints();
            c.insets = new Insets(0, 0, 0, 0);
            c.anchor = GridBagConstraints.LINE_START;
            c.gridx = 0; c.gridy = 0;
            c.gridwidth = 2;
        
            listInscription = new JList();
            listModelInscription = new DefaultListModel();
            listInscription.setModel(listModelInscription);
            listInscription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            listModelInscription.addElement(new QueryResult(-1,"-- Aucune activité sélectionnée --"));
            
            scrollPaneMembre = new JScrollPane(listInscription);
            scrollPaneMembre.setPreferredSize(new Dimension(220, Fenetre.getCont().getHeight()-60));
            
            this.add(scrollPaneMembre, c);
            
            comboBoxMembre = new JComboBox();
            comboBoxMembre.setPreferredSize(new Dimension(219, 28));
            c.gridy++;
            this.add(comboBoxMembre, c);	 
            
            try {            
                arrayMembre = app.listMembre(null);
                comboBoxMembre.addItem(new QueryResult(-1,"Aucun"));
                for(Membre membre : arrayMembre) {
                    comboBoxMembre.addItem(new QueryResult(membre.getIdMembre(),membre.getNom().toString().toUpperCase()+", "+membre.getPrenom().toString().toLowerCase()));
                }
            } 
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
            }
            AutoCompleteDecorator.decorate(comboBoxMembre);
            
            c.insets = new Insets(3, 0, 0, 0);
            
            buttonNewIns = new JButton("Ajout");
            buttonNewIns.setContentAreaFilled(false);
            buttonNewIns.setPreferredSize(new Dimension(106, 25));
            c.gridy++; c.gridwidth = 1;
            this.add(buttonNewIns, c);
            
            buttonDelIns = new JButton("Supprimer");
            buttonDelIns.setContentAreaFilled(false);
            buttonDelIns.setPreferredSize(new Dimension(114, 25));
            c.gridx++;
            this.add(buttonDelIns, c);
        }
    }
    
    private class PanelRight extends JPanel {
        public PanelRight() {        
            this.setLayout(new BorderLayout());
        
            panelInfoInscription = new JPanel();
            panelInfoInscription.setPreferredSize(new Dimension(450, (Fenetre.getCont().getHeight())*1/14));
            flowIns = new FlowLayout();
            panelInfoInscription.setLayout(flowIns);
            flowIns.setHgap(12);
            
            labelTarifSpecial = new JLabel("Tarif special : ");
            panelInfoInscription.add(labelTarifSpecial);
            
            fieldTarifSpecial = new JTextField(5);
            panelInfoInscription.add(fieldTarifSpecial);
            
            checkAbandonne = new JCheckBox("Abandonné");
            panelInfoInscription.add(checkAbandonne);
            
            checkCertifie = new JCheckBox("Certifié");
            panelInfoInscription.add(checkCertifie);
            
            buttonModify = new JButton("Modifier");
            buttonModify.setContentAreaFilled(false);
            panelInfoInscription.add(buttonModify);
            
            this.add(panelInfoInscription, BorderLayout.NORTH);
        
            panelPaiement = new JPanel();
            panelPaiement.setLayout(new GridBagLayout());              
            c.insets = new Insets(0, 0, 0, 0);
            c.gridx = 0; c.gridy = 0;
            c.gridwidth = 2;
            dimButton = new Dimension(224, 25);
        
                tablePaiement = new JTable();
                tablePaiement.getTableHeader().setReorderingAllowed(false);
                tablePaiement.setModel(new PaiementList(arrayPaiement)); 
                scrollPaneTablePaiement = new JScrollPane(tablePaiement);
                scrollPaneTablePaiement.setPreferredSize(new Dimension(450, (Fenetre.getCont().getHeight())*5/13));                                
                panelPaiement.add(scrollPaneTablePaiement, c);
                
                buttonAddPaiement = new JButton("Ajout Paiement");  
                buttonAddPaiement.setContentAreaFilled(false);
                buttonAddPaiement.setPreferredSize(dimButton);
                c.gridy++; c.gridwidth = 1;
                panelPaiement.add(buttonAddPaiement, c);
                
                buttonDelPaiement = new JButton("Supprimer Paiement");
                buttonDelPaiement.setContentAreaFilled(false);
                buttonDelPaiement.setPreferredSize(dimButton);
                c.gridx++;
                panelPaiement.add(buttonDelPaiement, c);
            
            this.add(panelPaiement, BorderLayout.CENTER);

            panelAccordPaiement = new JPanel();
            panelAccordPaiement.setLayout(new GridBagLayout());
            c.insets = new Insets(24, 0, 0, 0);
            c.gridwidth = 2;
            c.gridx = 0; c.gridy = 0;
        
                tableAccordPaiement = new JTable();
                tableAccordPaiement.setModel(new AccordPaiementList(arrayAccordPaiement));  
                scrollPaneTableAccordPaiement = new JScrollPane(tableAccordPaiement);
                scrollPaneTableAccordPaiement.setPreferredSize(new Dimension(450, (Fenetre.getCont().getHeight())*5/13));   
                panelAccordPaiement.add(scrollPaneTableAccordPaiement, c);
            
                buttonAddAccord = new JButton("Ajout Accord");
                buttonAddAccord.setContentAreaFilled(false);
                buttonAddAccord.setPreferredSize(dimButton);
                c.gridy++; c.gridwidth = 1;
                c.insets = new Insets(0, 0, 0, 0);
                panelAccordPaiement.add(buttonAddAccord, c);
                
                buttonDelAccord = new JButton("Supprimer Accord");
                buttonDelAccord.setContentAreaFilled(false);
                buttonDelAccord.setPreferredSize(dimButton);
                c.gridx++;
                panelAccordPaiement.add(buttonDelAccord, c);
            
            this.add(panelAccordPaiement, BorderLayout.SOUTH);

            Action action = new ModifPaiement();
            tclP = new TableCellListener(tablePaiement, action);
            tclA = new TableCellListener(tableAccordPaiement, action);     
        }
    }
    
    private class ModifPaiement extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            TableCellListener tcl = (TableCellListener)e.getSource();            
            paiement = new Paiement();
            paiement.setIdActivite(((QueryResult)listActivite.getSelectedValue()).id);
            paiement.setIdMembre(((QueryResult)listInscription.getSelectedValue()).id);
            if(tcl.getTable() == tablePaiement) {
                paiement.setAccord(false);
                oldDate = arrayPaiement.get(tcl.getRow()).getOldDate();
            }
            else {
                paiement.setAccord(true);
                paiement.setTypePaiement(0);
                oldDate = arrayAccordPaiement.get(tcl.getRow()).getOldDate();
            }

            // Date
            if(tcl.getColumn() == 0) {
                date = tcl.getNewValue().toString();
                try {
                    datePaiement = new GregorianCalendar(Integer.parseInt(date.substring(6, 10)), Integer.parseInt(date.substring(3, 5))-1, Integer.parseInt(date.substring(0, 2)));
                    paiement.setDatePaiement(datePaiement);
                }
                catch(ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'insertion veuillez vérifier le contenu des champs et si le problème persiste contacter l'administrateur.", "Erreur insertion", JOptionPane.ERROR_MESSAGE);
                }                            
            }
            // Montant
            else if(tcl.getColumn() == 1) {
                paiement.setMontant(Float.parseFloat(tcl.getNewValue().toString()));
            }
            // Type
            else if(tcl.getColumn() == 2) {
                if(tcl.getNewValue().toString().equals("Liquide") == true){
                    paiement.setTypePaiement(1);
                }
                else if(tcl.getNewValue().toString().equals("Virement") == true) {
                    paiement.setTypePaiement(2);
                }
                else if(tcl.getNewValue().toString().equals("Echange") == true) {
                    paiement.setTypePaiement(3);
                }
                else {
                    paiement.setTypePaiement(0);
                }  
            }
            try {
                app.modifyPaiement(paiement, oldDate);
                if(tcl.getColumn() == 1 && tcl.getTable() == tablePaiement) {
                    Integer index = listInscription.getSelectedIndex();
                    UpdateListInscription(((QueryResult)listActivite.getSelectedValue()).id);
                    listInscription.setSelectedIndex(index);
                }
            } 
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur modification", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    
    private class ActionManager implements ActionListener {
        private Inscription ins;
        private Integer idFormation = -1, idActivite = -1, idMembre = -1, idComboMembre, indexIns;
        private Object src;
        @Override
        public void actionPerformed(ActionEvent e) {
            src = e.getSource();
            if(listFormation.getSelectedIndex() != -1){
                idFormation = ((QueryResult)listFormation.getSelectedValue()).id;
            }
            if(listActivite.getSelectedIndex() != -1) {
                idActivite = ((QueryResult)listActivite.getSelectedValue()).id;
            }
            if(listInscription.getSelectedIndex() != -1) {
                idMembre = ((QueryResult)listInscription.getSelectedValue()).id;            
            }
            if(idFormation != -1 && idActivite != -1){
                if(src == buttonNewIns){
                    idComboMembre = ((QueryResult)comboBoxMembre.getSelectedItem()).id;
                    if(idComboMembre != -1) {
                        ins = new Inscription();
                        ins.setIdActivite(idActivite);
                        ins.setIdMembre(idComboMembre);                        
                        try {
                            app.newInscription(ins);
                            UpdateListInscription(idActivite);
                        } 
                        catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur Insertion", JOptionPane.ERROR_MESSAGE);
                        } 
                        catch (NotIdentified ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else if(idMembre != -1) {
                    if(src == buttonDelIns) { 
                        try {
                            Integer reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette inscription ?", "Modification Membre", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {       
                                app.deleteInscription(idActivite, idMembre);
                                UpdateListInscription(idActivite);
                            }
                        } 
                        catch (DBException ex) {
                                JOptionPane.showMessageDialog(null, ex, "Erreur Insertion", JOptionPane.ERROR_MESSAGE);
                        } 
                        catch (NotIdentified ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
                        }
                    }                
                    else if(src == buttonModify) {
                        ins = new Inscription();
                        ins.setIdActivite(idActivite);
                        ins.setIdMembre(idMembre); 
                        ins.setTarifSpecial(Float.parseFloat(fieldTarifSpecial.getText()));
                        ins.setCertifie(checkCertifie.isSelected());
                        ins.setAbandonne(checkAbandonne.isSelected());
                        try {
                            app.modifyInscription(ins);
                            JOptionPane.showMessageDialog(null, "Inscription modifiée", "Modification inscription", JOptionPane.INFORMATION_MESSAGE);
                            UpdateInfoInscription(idActivite, idMembre);                            
                            indexIns = listInscription.getSelectedIndex();
                            UpdateListInscription(((QueryResult)listActivite.getSelectedValue()).id);
                            listInscription.setSelectedIndex(indexIns);
                        } 
                        catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur Modification", JOptionPane.ERROR_MESSAGE);
                        } 
                        catch (NotIdentified ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        TrtPaiement(src, idActivite, idMembre);                        
                    }
                }
            }
        }
    }
    
    private void TrtPaiement(Object src, Integer idActivite, Integer idMembre){
        Paiement newPaiement = new Paiement();
        newPaiement.setIdActivite(idActivite);
        newPaiement.setIdMembre(idMembre);
        newPaiement.setMontant(new Float(0));
        newPaiement.setAccord(false);
        newPaiement.setDatePaiement(new GregorianCalendar(2015,0,tablePaiement.getRowCount()));
        newPaiement.setTypePaiement(0);
        
        if(src == buttonAddAccord || src == buttonDelAccord) {
            newPaiement.setAccord(true);
            newPaiement.setDatePaiement(new GregorianCalendar(2015,0,tableAccordPaiement.getRowCount()));
        }
        
        if(src == buttonAddAccord || src == buttonAddPaiement) {
            try {
                app.newPaiement(newPaiement);
            }
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, "Paiement déjà existant. Modifier la date pour en ajouter un supplémentaire.", "Erreur Insertion", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if((src == buttonDelAccord && tableAccordPaiement.getSelectedRow() != -1) ||  (src == buttonDelPaiement && tablePaiement.getSelectedRow() != -1)){
            try {                
                Integer reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce paiement ?", "Suppression Paiement", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {       
                    if(src == buttonDelPaiement) { 
                        date = (String)tablePaiement.getValueAt(tablePaiement.getSelectedRow(), 0);
                        datePaiement = new GregorianCalendar(Integer.parseInt(date.substring(6, 10)), Integer.parseInt(date.substring(3, 5))-1, Integer.parseInt(date.substring(0, 2)));
                        newPaiement.setDatePaiement(datePaiement);
                        app.deletePaiement(newPaiement);
                    }
                    else {
                        date = (String)tableAccordPaiement.getValueAt(tableAccordPaiement.getSelectedRow(), 0);
                        datePaiement = new GregorianCalendar(Integer.parseInt(date.substring(6, 10)), Integer.parseInt(date.substring(3, 5))-1, Integer.parseInt(date.substring(0, 2)));
                        newPaiement.setDatePaiement(datePaiement);
                        app.deletePaiement(newPaiement);
                    }
                }
            }                
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Suppression", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
            }
        }
        UpdateInfoInscription(idActivite, idMembre);
    }
    
    private class ListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {  
            if(lse.getValueIsAdjusting()){
                queryResult = (QueryResult)((JList)lse.getSource()).getSelectedValue();
                if(queryResult.id != -1) {
                    if(lse.getSource() == listFormation) {
                        UpdateListActivite(queryResult.id);
                        UpdateListInscription(-1);
                        UpdateInfoInscription(-1, -1);
                    }
                    else if(lse.getSource() == listActivite) {
                        UpdateListInscription(queryResult.id);
                        UpdateInfoInscription(-1, -1);
                    }
                    else if(lse.getSource() == listInscription) {
                        QueryResult structAct = (QueryResult)listActivite.getSelectedValue();
                        UpdateInfoInscription(structAct.id, queryResult.id);
                    }
                }
            }
        }
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
    
    private void UpdateListInscription(Integer idActivite) {
        try {            
            if(idActivite != -1) {
                arrayInscription = app.listInscription(idActivite);
            }
            listModelInscription.clear();
            if(arrayInscription.isEmpty() == true || idActivite == -1) {
                listModelInscription.addElement(new QueryResult(-1,"-- Aucune inscription --"));
            }
            else {            
                prix = (app.getActivite(idActivite).getPrix())*-1;
                
                for(Membre me : arrayInscription) {                    
                    desc =  me.getNom().toString().toUpperCase() + ", " + me.getPrenom().toString().toLowerCase();
                    prixSpecial = app.getInscription(idActivite, me.getIdMembre()).getTarifSpecial();
                    solde = app.getSolde(idActivite, me.getIdMembre()) + prix + prixSpecial;
                    desc += " ("+solde+")";
                    listModelInscription.addElement(new QueryResult(me.getIdMembre(),desc));
                }
                listInscription.setCellRenderer(new MyCellRenderer());
            }          
        } 
        catch (DBException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
        } 
        catch (NotIdentified ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    private void UpdateInfoInscription(int idActivite, int idMembre) {
        try {
            if(idActivite == -1) {              
                fieldTarifSpecial.setText("");
                checkAbandonne.setSelected(false);
                checkCertifie.setSelected(false);                
                
                arrayPaiement.clear();
                arrayAccordPaiement.clear();
                
                tablePaiement.setModel(new PaiementList(arrayPaiement));
                tableAccordPaiement.setModel(new AccordPaiementList(arrayAccordPaiement));
            }
            else {
                ins = app.getInscription(idActivite, idMembre);
                fieldTarifSpecial.setText(ins.getTarifSpecial().toString());
                checkAbandonne.setSelected(ins.getAbandonne());
                checkCertifie.setSelected(ins.getCertifie());
                
                arrayPaiement = app.listPaiement(idActivite, idMembre, false);
                arrayAccordPaiement = app.listPaiement(idActivite, idMembre, true);
                
                tablePaiement.setModel(new PaiementList(arrayPaiement));
                tableAccordPaiement.setModel(new AccordPaiementList(arrayAccordPaiement)); // true pour obtenir accordPaiement            
                
                comboBoxType = new JComboBox();
                comboBoxType.addItem("-- Choisir --");
                comboBoxType.addItem("Liquide");
                comboBoxType.addItem("Virement");      
                comboBoxType.addItem("Echange");   
                tablePaiement.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBoxType));    
                
                fieldDate = new JFormattedTextField(new MaskFormatter("##'/##'/####")); 
                tablePaiement.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(fieldDate));
                tableAccordPaiement.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(fieldDate));
            }
        }
        catch (DBException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur listing", JOptionPane.ERROR_MESSAGE);
        } 
        catch (NotIdentified ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur connexion", JOptionPane.ERROR_MESSAGE);
        } 
        catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erreur dans le champs de la date.", "Erreur date", JOptionPane.ERROR_MESSAGE);
        }
    }   
    
    private class MyCellRenderer extends JLabel implements ListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String s = value.toString();
            setText(s);
            if(value.toString().contains("(-") == true){;
                setForeground(Color.RED);
            }
            else {
                setForeground(Color.BLACK);
            }
            if (isSelected) {
                setBackground(list.getSelectionBackground());
            }
            else {
                setBackground(list.getBackground());
            }
 
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            return this;
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
