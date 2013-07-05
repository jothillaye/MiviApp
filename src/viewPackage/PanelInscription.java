package viewPackage;

import controllerPackage.ApplicationController;
import controllerPackage.ExportToExcel;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
    private JPanel panelFormAct, panelListInscription, panelIns, panelInfoInscription, panelPaiement, panelAccordPaiement;
    private GridBagConstraints c, d;
    private JSplitPane splitFormAct;
    
    private JList listFormation, listActivite, listInscription;
    private JScrollPane scrollPaneFormation, scrollPaneActivite, scrollPaneMembre, scrollPaneTablePaiement, scrollPaneTableAccordPaiement;
    private DefaultListModel listModelFormation, listModelActivite, listModelInscription;
    private JLabel labelTarifSpecial, labelTotalInscris, labelPaiement, labelAccordPaiement;
    private JTextField fieldTarifSpecial;
    private JCheckBox checkAbandonne, checkCertifie;
    private JTable tablePaiement, tableAccordPaiement;
    private TableCellListener tclP, tclA;
    private JComboBox comboBoxType, comboBoxMembre;
    private JButton buttonAddPaiement, buttonDelPaiement, buttonAddAccord, buttonDelAccord, buttonModIns, buttonNewIns, buttonDelIns, buttonExportIns, buttonExportPaiement, buttonExportAccord;
    private JFormattedTextField fieldDate;
    
    private ArrayList<Formation> arrayFormation = new ArrayList<Formation>();
    private ArrayList<Activite> arrayActivite = new ArrayList<Activite>();
    private ArrayList<Inscription> arrayInscription = new ArrayList<Inscription>();
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
    private String tabHeader[] = {"_______Clients_______", "______Assistants______", "______Animateurs______", "______Abandonnés______"};
    private ArrayList<JButton> buttonsPaiement = new ArrayList<JButton>();
    
    
    private ApplicationController app = new ApplicationController();
    private ExportToExcel export = new ExportToExcel();
    
    public PanelInscription() {
        this.setLayout(new GridBagLayout());
        d = new GridBagConstraints();
        d.insets = new Insets(0, 0, 0, 0);
        d.anchor = GridBagConstraints.LINE_START;
        d.gridx = 0; d.gridy = 0;
        d.fill = GridBagConstraints.BOTH;
        
        panelFormAct = new PanelFormAct();
        panelFormAct.setPreferredSize(new Dimension(240, Fenetre.getCont().getHeight()));
        this.add(panelFormAct,d);
        
        panelListInscription = new PanelListInscription();        
        d.gridx++;
        this.add(panelListInscription,d);
        
        panelIns = new PanelIns();       
        d.gridx++;
        this.add(panelIns,d);
        
        ListListener LL = new ListListener();
        listFormation.addListSelectionListener(LL);
        listActivite.addListSelectionListener(LL); 
        listInscription.addListSelectionListener(LL);
        
        ActionManager AM = new ActionManager();
        buttonNewIns.addActionListener(AM);
        buttonDelIns.addActionListener(AM);
        buttonModIns.addActionListener(AM);
        buttonExportIns.addActionListener(AM);
        buttonAddPaiement.addActionListener(AM);
        buttonDelPaiement.addActionListener(AM);
        buttonExportPaiement.addActionListener(AM);
        buttonAddAccord.addActionListener(AM);
        buttonDelAccord.addActionListener(AM);
        buttonExportAccord.addActionListener(AM);
        
        
        try {
            iconURL = this.getClass().getResource("/viewPackage/resources/images/validate.png");
            buttonModIns.setIcon(new ImageIcon(iconURL));

            iconURL = this.getClass().getResource("/viewPackage/resources/images/add.png");
            buttonAddPaiement.setIcon(new ImageIcon(iconURL));
            buttonAddAccord.setIcon(new ImageIcon(iconURL));
            buttonNewIns.setIcon(new ImageIcon(iconURL));                    

            iconURL = this.getClass().getResource("/viewPackage/resources/images/delete.png");
            buttonDelAccord.setIcon(new ImageIcon(iconURL));
            buttonDelIns.setIcon(new ImageIcon(iconURL));
            buttonDelPaiement.setIcon(new ImageIcon(iconURL));
            
            iconURL = this.getClass().getResource("/viewPackage/resources/images/export.png");
            buttonExportIns.setIcon(new ImageIcon(iconURL));
            buttonExportPaiement.setIcon(new ImageIcon(iconURL));
            buttonExportAccord.setIcon(new ImageIcon(iconURL));
        }
        catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des icônes.\nVeuillez contacter l'administrateur.", "Erreur de récupération des icônes", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, ex, "Erreur Listing", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
            }
            
            scrollPaneFormation = new JScrollPane(listFormation);
            
            listActivite = new JList();            
            listModelActivite = new DefaultListModel();
            listActivite.setModel(listModelActivite);      
            listActivite.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            listModelActivite.addElement(new QueryResult(-1,"-- Aucune formation sélectionnée --"));
            
            scrollPaneActivite = new JScrollPane(listActivite);
            
            splitFormAct = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneFormation, scrollPaneActivite);
            splitFormAct.setDividerLocation((Fenetre.getCont().getHeight()/3)*2);
            splitFormAct.setDividerSize(2);
            this.add(splitFormAct, BorderLayout.CENTER);
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
            
            labelTotalInscris = new JLabel("Nombre d'inscris :");
            labelTotalInscris.setPreferredSize(new Dimension(220, 15));
            this.add(labelTotalInscris, c);
        
            listInscription = new JList();
            listModelInscription = new DefaultListModel();
            listInscription.setModel(listModelInscription);
            listInscription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            listModelInscription.addElement(new QueryResult(-1,"-- Aucune activité sélectionnée --"));
            
            scrollPaneMembre = new JScrollPane(listInscription);
            scrollPaneMembre.setPreferredSize(new Dimension(220, Fenetre.getCont().getHeight()-100));
            
            c.gridy++;
            this.add(scrollPaneMembre, c);
            
            comboBoxMembre = new JComboBox();
            comboBoxMembre.setPreferredSize(new Dimension(219, 28));
            c.gridy++;
            this.add(comboBoxMembre, c);	 
            
            try {            
                arrayMembre = app.listMembre(null, null);
                comboBoxMembre.addItem(new QueryResult(-1,"Aucun"));
                for(Membre membre : arrayMembre) {
                    comboBoxMembre.addItem(new QueryResult(membre.getIdMembre(),membre.getNom()+", "+membre.getPrenom()));
                }
            } 
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Listing", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
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
            
            buttonExportIns = new JButton("Export de la liste");
            buttonExportIns.setContentAreaFilled(false);
            buttonExportIns.setPreferredSize(new Dimension(220, 25));
            c.gridx=0; c.gridy++;
            c.gridwidth = 2;
            this.add(buttonExportIns, c);
        }
    }
    
    private class PanelIns extends JPanel {
        public PanelIns() {        
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
            panelInfoInscription = new JPanel();
            panelInfoInscription.setPreferredSize(new Dimension(430, 40));
            panelInfoInscription.setLayout(new GridBagLayout());            
            c.insets = new Insets(0, 8, 5, 0);
            c.gridx = 0; c.gridy = 0;
            c.gridwidth = 1;
            
            labelTarifSpecial = new JLabel("Tarif special : ");
            panelInfoInscription.add(labelTarifSpecial, c);
            
            fieldTarifSpecial = new JTextField(5);
            c.gridx++;
            panelInfoInscription.add(fieldTarifSpecial, c);
            
            checkAbandonne = new JCheckBox("Abandonné");
            c.gridx++;
            panelInfoInscription.add(checkAbandonne, c);
            
            checkCertifie = new JCheckBox("Certifié");
            c.gridx++;
            panelInfoInscription.add(checkCertifie, c);
            
            buttonModIns = new JButton("Modifier");
            c.gridx++;
            buttonModIns.setContentAreaFilled(false);
            panelInfoInscription.add(buttonModIns, c);
            buttonsPaiement.add(buttonModIns);
            
            this.add(panelInfoInscription);
        
            panelPaiement = new JPanel();
            panelPaiement.setLayout(new GridBagLayout());              
            c.insets = new Insets(0, 0, 0, 0);
            c.gridx = 0; c.gridy = 0;
            c.gridwidth = 3;
            dimButton = new Dimension(142, 25);
        
                labelPaiement = new JLabel("<html><u>Paiements</u> :</html>");
                labelPaiement.setFont(new Font("Arial", Font.ITALIC, 13));
                panelPaiement.add(labelPaiement, c);
                
                tablePaiement = new JTable();
                tablePaiement.setName("tablePaiement");
                tablePaiement.getTableHeader().setReorderingAllowed(false);
                tablePaiement.setModel(new PaiementList(arrayPaiement)); 
                scrollPaneTablePaiement = new JScrollPane(tablePaiement);
                scrollPaneTablePaiement.setPreferredSize(new Dimension(430, 180));                                
                c.gridy++;
                panelPaiement.add(scrollPaneTablePaiement, c);
                
                buttonAddPaiement = new JButton("Ajout Paiement");  
                buttonAddPaiement.setContentAreaFilled(false);
                buttonAddPaiement.setPreferredSize(dimButton);
                c.gridy++; c.gridwidth = 1;
                c.insets = new Insets(0, 1, 0, 0);
                panelPaiement.add(buttonAddPaiement, c);
                buttonsPaiement.add(buttonAddPaiement);
                
                buttonDelPaiement = new JButton("Supprimer Paiement");
                buttonDelPaiement.setContentAreaFilled(false);
                buttonDelPaiement.setPreferredSize(dimButton);
                c.gridx++;
                panelPaiement.add(buttonDelPaiement, c);
                buttonsPaiement.add(buttonDelPaiement);
                
                buttonExportPaiement = new JButton("Export Paiements");
                buttonExportPaiement.setContentAreaFilled(false);
                buttonExportPaiement.setPreferredSize(dimButton);
                c.gridx++;
                panelPaiement.add(buttonExportPaiement, c);
                buttonsPaiement.add(buttonExportPaiement);
            
            this.add(panelPaiement);
            
            this.add(Box.createVerticalStrut(10));

            panelAccordPaiement = new JPanel();
            panelAccordPaiement.setLayout(new GridBagLayout());
            c.insets = new Insets(0, 0, 0, 0);
            c.gridwidth = 3;
            c.gridx = 0; c.gridy = 0;
            
                labelAccordPaiement = new JLabel("<html><u>Accords Paiements</u> : </html>");
                labelAccordPaiement.setFont(new Font("Arial", Font.ITALIC, 13));
                panelAccordPaiement.add(labelAccordPaiement, c);
        
                tableAccordPaiement = new JTable();
                tableAccordPaiement.setName("tableAccordPaiement");
                tableAccordPaiement.setModel(new AccordPaiementList(arrayAccordPaiement));  
                scrollPaneTableAccordPaiement = new JScrollPane(tableAccordPaiement);
                scrollPaneTableAccordPaiement.setPreferredSize(new Dimension(430, 180));   
                c.gridy++;
                panelAccordPaiement.add(scrollPaneTableAccordPaiement, c);
                
            
                buttonAddAccord = new JButton("Ajout Accord");
                buttonAddAccord.setContentAreaFilled(false);
                buttonAddAccord.setPreferredSize(dimButton);
                c.gridy++; c.gridwidth = 1;
                c.insets = new Insets(0, 1, 0, 0);
                panelAccordPaiement.add(buttonAddAccord, c);
                buttonsPaiement.add(buttonAddAccord);
                                
                buttonDelAccord = new JButton("Supprimer Accord");
                buttonDelAccord.setContentAreaFilled(false);
                buttonDelAccord.setPreferredSize(dimButton);
                c.gridx++;
                panelAccordPaiement.add(buttonDelAccord, c);
                buttonsPaiement.add(buttonDelAccord);
                
                buttonExportAccord = new JButton("Export Accords");
                buttonExportAccord.setContentAreaFilled(false);
                buttonExportAccord.setPreferredSize(dimButton);
                c.gridx++;
                panelAccordPaiement.add(buttonExportAccord, c);
                buttonsPaiement.add(buttonExportAccord);
                
            this.add(panelAccordPaiement);

            Action action = new ModifPaiement();
            tclP = new TableCellListener(tablePaiement, action);
            tclA = new TableCellListener(tableAccordPaiement, action);     
        }
    }
    
    private class ListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {              
            if(!lse.getValueIsAdjusting() && ((JList)lse.getSource()).getSelectedIndex() != -1){                
                if(lse.getSource() == listFormation || lse.getSource() == listActivite){
                    queryResult = (QueryResult)((JList)lse.getSource()).getSelectedValue();
                    if(queryResult.id != -1) {
                        if(lse.getSource() == listFormation) {
                            UpdateListActivite(queryResult.id);
                            UpdateListInscription(-1);
                            UpdateInfoInscription(-1);
                        }
                        else if(lse.getSource() == listActivite) {
                            UpdateListInscription(queryResult.id);
                            UpdateInfoInscription(-1);
                        }
                    }
                }
                else if(lse.getSource() == listInscription) {
                    QueryResult structIns = (QueryResult)listInscription.getSelectedValue();
                    UpdateInfoInscription(structIns.id);
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
                    if(act.getPromotion() != 0) {
                        desc = "Promotion " + act.getPromotion().toString();
                    }
                    else if(act.getDateDeb() != null){
                        desc = act.getFormatedDateDeb();
                    }
                    listModelActivite.addElement(new QueryResult(act.getIdActivite(), desc));
                }            
            }          
        } 
        catch (DBException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur Listing", JOptionPane.ERROR_MESSAGE);
        } 
        catch (NotIdentified ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void UpdateListInscription(Integer idActivite) {
        listModelInscription.clear();
        if(idActivite != -1) {
            try {                 
                for(int i=0;i<4;i++){
                    arrayInscription = app.listInscription(idActivite,i);
                    if(arrayInscription.isEmpty() == true && i == 0) { // liste vide            
                        listModelInscription.addElement(new QueryResult(-1,"-- Aucune inscription --"));
                        labelTotalInscris.setText("Aucun inscris");
                    }
                    else if(arrayInscription.isEmpty() == false) {    
                        if(i == 0){
                            labelTotalInscris.setText("Nombre d'inscris : "+arrayInscription.size());
                            prix = (app.getActivite(idActivite).getPrix())*-1;
                        }    
                        listModelInscription.addElement(new QueryResult(-1,tabHeader[i]));
                            
                        for(Inscription iIns : arrayInscription) {
                            Membre me = iIns.getMembre();
                            desc =  me.getNom() + ", " + me.getPrenom();                            
                            if(i == 0){
                                solde = iIns.getSolde();
                                if(iIns.getTarifSpecial() != 0){
                                    solde += (prixSpecial*-1);
                                }
                                else {
                                    solde += prix;
                                }
                                if(solde != 0) {
                                    desc += " ("+solde+"€)";
                                }                                                              
                            }
                            listModelInscription.addElement(new QueryResult(iIns.getIdInscription(),desc));
                        }                        
                    }
                }
                listInscription.setCellRenderer(new MyCellRenderer());                
            }
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Listing", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
            }    
        }
        else {
            listModelInscription.addElement(new QueryResult(-1,"-- Aucune activité sélectionnée --"));
        }
    }
    
    private void UpdateInfoInscription(Integer idInscription) {
        try {
            if(idInscription == -1) {              
                fieldTarifSpecial.setText("");
                fieldTarifSpecial.setEnabled(false);
                
                checkAbandonne.setEnabled(false);
                checkCertifie.setEnabled(false);  
                
                arrayPaiement.clear();
                arrayAccordPaiement.clear();                
                tablePaiement.setModel(new PaiementList(arrayPaiement));
                tableAccordPaiement.setModel(new AccordPaiementList(arrayAccordPaiement));
                
                for(JButton button : buttonsPaiement){
                    button.setEnabled(false);
                }
            }
            else {
                ins = app.getInscription(idInscription);
                
                fieldTarifSpecial.setEnabled(true);
                fieldTarifSpecial.setText(ins.getTarifSpecial().toString());
                
                switch(ins.getTypeIns()){
                    case 0: 
                        checkAbandonne.setEnabled(true);
                        checkAbandonne.setSelected(false);
                        break;
                    case 1: 
                    case 2: 
                        checkAbandonne.setEnabled(false);
                        checkAbandonne.setSelected(false);   
                        break;
                    case 3:
                        checkAbandonne.setEnabled(true);
                        checkAbandonne.setSelected(true);
                        break;
                    default:
                        checkAbandonne.setEnabled(true);
                        checkAbandonne.setSelected(false);
                }
                
                checkCertifie.setEnabled(true);
                checkCertifie.setSelected(ins.getCertifie());
                
                Paiement paie = new Paiement();
                paie.setIdInscription(ins.getIdInscription());
                paie.setAccord(false);                
                
                arrayPaiement = app.listPaiement(paie);
                paie.setAccord(true);
                arrayAccordPaiement = app.listPaiement(paie);
                
                tablePaiement.setModel(new PaiementList(arrayPaiement));
                tableAccordPaiement.setModel(new AccordPaiementList(arrayAccordPaiement)); // true pour obtenir accordPaiement            
                
                comboBoxType = new JComboBox();
                comboBoxType.addItem(new QueryResult(0,"-- Choisir --"));
                comboBoxType.addItem(new QueryResult(1,"Liquide"));
                comboBoxType.addItem(new QueryResult(2,"Virement"));      
                comboBoxType.addItem(new QueryResult(3,"Echange"));   
                comboBoxType.addItem(new QueryResult(4,"Remboursement"));   
                comboBoxType.addItem(new QueryResult(5,"Rémunération"));
                tablePaiement.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBoxType));    
                
                fieldDate = new JFormattedTextField(new MaskFormatter("##'/##'/####")); 
                tablePaiement.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(fieldDate));
                tableAccordPaiement.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(fieldDate));
                
                for(JButton button : buttonsPaiement){
                    button.setEnabled(true);
                }
            }
        }
        catch (DBException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur Listing", JOptionPane.ERROR_MESSAGE);
        } 
        catch (NotIdentified ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
        } 
        catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erreur dans le champs de la date.", "Erreur date", JOptionPane.ERROR_MESSAGE);
        }
    }   
    
    private class ActionManager implements ActionListener {
        private Inscription ins;
        private Integer idFormation = -1, idActivite = -1, idInscription, typeIns, idComboMembre;
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
                idInscription = ((QueryResult)listInscription.getSelectedValue()).id;        
            }
            if(idFormation != -1 && idActivite != -1){
                ins = new Inscription();
                ins.setIdActivite(idActivite);                
                // Nouvelle Inscription
                if(src == buttonNewIns){
                    idComboMembre = ((QueryResult)comboBoxMembre.getSelectedItem()).id;
                    if(idComboMembre != -1) {              
                        //Custom button text
                        Object[] options = {"Client", "Assistant", "Animateur"};
                        String s = (String)JOptionPane.showInputDialog(
                            null,
                            "Type de l'inscription : ",
                            "Nouvelle Inscription",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            options,
                            options[0]
                        );
                        
                        if(s != null) {
                            for(int i=0; i<options.length; i++){
                                if(s.equals(options[i]) == true) {
                                    typeIns = i;
                                }
                            }
                        }
                        ins.setTypeIns(typeIns);
                        ins.setIdMembre(idComboMembre);    
                        try {
                            idInscription = app.newInscription(ins);
                            UpdateListInscription(idActivite);
                            
                            for(Object o : listModelInscription.toArray()){      
                                if(((QueryResult)o).id == idInscription){
                                    listInscription.setSelectedValue(o, true);
                                }
                            }
                            UpdateInfoInscription(idInscription);
                        } 
                        catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur Insertion", JOptionPane.ERROR_MESSAGE);
                        } 
                        catch (NotIdentified ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                // Exportation liste des inscriptions
                else if(src == buttonExportIns) {
                    Object[] options = {"Liste complète", "Email"};
                    String s = (String)JOptionPane.showInputDialog(
                        null,
                        "Type d'exportation : ",
                        "Exportation Inscription",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]
                    );
                    Integer typeExport = 0;
                    if(s != null) {
                        for(int i=0; i<options.length; i++){
                            if(s.equals(options[i]) == true) {
                                typeExport = i;
                            }
                        }
                    }
                    
                    String formInt = ((QueryResult)listFormation.getSelectedValue()).desc;
                    Integer idActiviteExport = ((QueryResult)listActivite.getSelectedValue()).id;
                    try {
                        export.ExportListToExcel(formInt, idActiviteExport, typeExport);
                    } 
                    catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur lors de l'exportion de la liste.", "Erreur Exportation", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (DBException ex) {
                        JOptionPane.showMessageDialog(null, ex, "Erreur Modification", JOptionPane.ERROR_MESSAGE);
                    } 
                    catch (NotIdentified ex) {
                        JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
                    }
                }
                // Suppression Inscription
                else if(idInscription != -1) {
                    if(src == buttonDelIns) { 
                        try {
                            Integer reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette inscription ?", "Modification Inscription", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {       
                                ins.setIdInscription(idInscription);
                                app.deleteInscription(ins);
                                UpdateListInscription(idActivite);
                                UpdateInfoInscription(-1);
                            }
                        } 
                        catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur Insertion", JOptionPane.ERROR_MESSAGE);
                        } 
                        catch (NotIdentified ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
                        }
                    }                
                    // Modifier Inscription
                    else if(src == buttonModIns) {
                        ins.setIdInscription(idInscription); 
                        String tarifString = fieldTarifSpecial.getText();
                        if(tarifString.isEmpty() == false){                        
                            ins.setTarifSpecial(Float.parseFloat(tarifString));
                        }
                        else {
                            ins.setTarifSpecial(new Float(0));
                        }
                        ins.setCertifie(checkCertifie.isSelected());
                        if(checkAbandonne.isSelected() == true){
                            ins.setTypeIns(3);
                        }
                        else {
                            ins.setTypeIns(0);
                        }
                        try {
                            app.modifyInscription(ins);
                            JOptionPane.showMessageDialog(null, "Inscription modifiée.", "Modification Inscription", JOptionPane.INFORMATION_MESSAGE);
                            UpdateInfoInscription(idInscription);                                                        
                            UpdateListInscription(((QueryResult)listActivite.getSelectedValue()).id);
                            
                            for(Object o : listModelInscription.toArray()){      
                                if(((QueryResult)o).id == idInscription){
                                    listInscription.setSelectedValue(o, true);
                                }
                            }
                        } 
                        catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur Modification", JOptionPane.ERROR_MESSAGE);
                        } 
                        catch (NotIdentified ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    
                    // Exportation des accords ou paiements
                    else if(src == buttonExportPaiement || src == buttonExportAccord) {
                        String formInt = ((QueryResult)listFormation.getSelectedValue()).desc;
                        Integer idInsExport = ((QueryResult)listInscription.getSelectedValue()).id;
                        JTable table;
                        
                        if(src == buttonExportPaiement) {
                            table = tablePaiement;
                        }
                        else {
                            table = tableAccordPaiement;
                        }
                        
                        try {                            
                            export.ExportTableToExcel(table, table.getName(), formInt, idInsExport);
                        } 
                        catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Erreur lors de l'exportation des paiements.", "Erreur Exportation", JOptionPane.ERROR_MESSAGE);
                        }
                        catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur Modification", JOptionPane.ERROR_MESSAGE);
                        } 
                        catch (NotIdentified ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    // Traitement des accords ou paiements
                    else{     
                        if(idInscription != -1) {
                            TrtPaiement(src, idActivite, idInscription);
                        }                        
                    }
                }
            }
        }
    }
    
    private void TrtPaiement(Object src, Integer idActivite, Integer idInscription){        
        Paiement newPaiement = new Paiement();        
        newPaiement.setIdInscription(idInscription);
        if(src == buttonAddAccord || src == buttonDelAccord) {
            newPaiement.setAccord(true);
        }
        else {
            newPaiement.setAccord(false);
        }

        if(src == buttonAddAccord || src == buttonAddPaiement) {
            try {
                app.newPaiement(newPaiement);
            }
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Insertion", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if((src == buttonDelAccord && tableAccordPaiement.getSelectedRow() != -1) ||  (src == buttonDelPaiement && tablePaiement.getSelectedRow() != -1)){
            try {                
                Integer reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce paiement ?", "Suppression Paiement", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {       
                    Integer idPaiement;
                    if(src == buttonDelPaiement) { 
                        idPaiement = arrayPaiement.get(tablePaiement.getSelectedRow()).getIdPaiement();
                    }
                    else {
                        idPaiement = arrayAccordPaiement.get(tableAccordPaiement.getSelectedRow()).getIdPaiement();
                    }
                    app.deletePaiement(idPaiement);
                }
            }                
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Suppression", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
            }
        }
        UpdateInfoInscription(idInscription);
        UpdateListInscription(idActivite);

        for(Object o : listModelInscription.toArray()) {
            if(((QueryResult)o).id == idInscription) {
                listInscription.setSelectedValue(o, true);
            }
        }        
    }
    
    private class ModifPaiement extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            TableCellListener tcl = (TableCellListener)e.getSource();            
            paiement = new Paiement();
            paiement.setIdInscription(((QueryResult)listInscription.getSelectedValue()).id);
            
            if(tcl.getTable() == tablePaiement) {
                paiement.setAccord(false);
                paiement.setIdPaiement(arrayPaiement.get(tablePaiement.getSelectedRow()).getIdPaiement());
            }
            else {
                paiement.setAccord(true);
                paiement.setIdPaiement(arrayAccordPaiement.get(tableAccordPaiement.getSelectedRow()).getIdPaiement());
            }
            
            switch(tcl.getColumn()){
                case 0 : 
                    date = tcl.getNewValue().toString();
                    try {
                        datePaiement = new GregorianCalendar(Integer.parseInt(date.substring(6, 10)), Integer.parseInt(date.substring(3, 5))-1, Integer.parseInt(date.substring(0, 2)));
                        paiement.setDatePaiement(datePaiement);
                    }
                    catch(ArrayIndexOutOfBoundsException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur lors de l'insertion veuillez vérifier le contenu des champs et si le problème persiste contacter l'administrateur.", "Erreur Insertion", JOptionPane.ERROR_MESSAGE);
                    } 
                    break;
                case 1 :
                    paiement.setMontant(Float.parseFloat(tcl.getNewValue().toString()));
                    break;
                case 2 :
                    Integer typePaiement = ((QueryResult)tcl.getNewValue()).id;
                    if(typePaiement < 1 || typePaiement > 5){
                        typePaiement = 0;
                    }
                    paiement.setTypePaiement(typePaiement);
                    break;
                default: break;
            }
            try {
                app.modifyPaiement(paiement);
                if(tcl.getColumn() == 1 && tcl.getTable() == tablePaiement) {
                    Integer index = listInscription.getSelectedIndex();
                    UpdateListInscription(((QueryResult)listActivite.getSelectedValue()).id);
                    listInscription.setSelectedIndex(index);
                }
            } 
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Modification", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
