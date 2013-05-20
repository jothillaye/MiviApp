package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.DBException;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;
import modelPackage.Activite;
import modelPackage.Formation;

/**
 *
 * @author Joachim
 */
public class PanelActivite extends JPanel {
    private JPanel panelFormAct, panelInfoActivite, panelButtons;
    private FlowLayout flowButtons = new FlowLayout();
    private GridBagConstraints c;
    
    private JList listFormation, listActivite;
    private JScrollPane scrollPaneFormation, scrollPaneActivite;
    private DefaultListModel listModelFormation, listModelActivite;
    private JLabel labelForm, labelDate, labelPromo, labelPrix, labelDateFin, labelAccompte, labelTVA; 
    private JFormattedTextField fieldDateDeb, fieldDateFin;
    private JCheckBox checkPromo, checkDateDeb, checkDateFin;
    private JButton buttonInsert, buttonModify, buttonDelete;
    private JSpinner spinPromo, spinPrix, spinAccompte, spinTVA;
    private JSplitPane splitFormAct;
    
    private ArrayList<Formation> arrayFormation = new ArrayList<Formation>();
    private ArrayList<Activite> arrayActivite = new ArrayList<Activite>();
    private Activite act;
    
    private String desc;
    private QueryResult queryResult;
    private URL iconURL;
    
    ApplicationController app = new ApplicationController();
    
    public PanelActivite() {
        this.setLayout(new BorderLayout());
        
        panelFormAct = new PanelFormAct();        
        this.add(panelFormAct, BorderLayout.WEST);
        
        ListListener LL = new ListListener();
        listFormation.addListSelectionListener(LL);
        listActivite.addListSelectionListener(LL);
        
        panelInfoActivite = new PanelInfoAct();        
        this.add(panelInfoActivite, BorderLayout.CENTER);
    }
    
    private class PanelFormAct extends JPanel {
        public PanelFormAct() {
            this.setLayout(new BorderLayout());
        
            listFormation = new JList();
            listModelFormation = new DefaultListModel();
            listFormation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listFormation.setModel(listModelFormation);
            
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
            scrollPaneFormation.setPreferredSize(new Dimension(240, (Fenetre.getCont().getHeight())/2));                        
            
            listActivite = new JList();
            listActivite.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listModelActivite = new DefaultListModel();
            listActivite.setModel(listModelActivite);      
            
            listModelActivite.addElement(new QueryResult(-1,"-- Aucune formation sélectionnée --"));
            
            scrollPaneActivite = new JScrollPane(listActivite);
            scrollPaneActivite.setPreferredSize(new Dimension(240, (Fenetre.getCont().getHeight())/2));
            
            splitFormAct = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneFormation, scrollPaneActivite);
            splitFormAct.setDividerLocation((Fenetre.getCont().getHeight()/3)*2);
            splitFormAct.setDividerSize(2);
            this.add(splitFormAct, BorderLayout.CENTER);
        }
    }
    
    private class PanelInfoAct extends JPanel {
        public PanelInfoAct() {
            this.setLayout(new GridBagLayout());
            c = new GridBagConstraints();
            c.insets = new Insets(10, 20, 10, 10);
            c.anchor = GridBagConstraints.LINE_START;
            
            labelForm = new JLabel("Gestion des Activités");
            labelForm.setFont(new Font("Arial", Font.BOLD, 21));
            c.gridx = 0; c.gridy = 0;
            c.gridwidth = 6;
            this.add(labelForm, c);
            
            labelDate = new JLabel("Date : ");
            c.gridy++; c.gridwidth = 1;
            this.add(labelDate, c);       
            
            checkDateDeb = new JCheckBox();
            checkDateDeb.addItemListener(new CheckBoxListener());
            c.gridx++;
            this.add(checkDateDeb, c);
            
            try{
                fieldDateDeb = new JFormattedTextField(new MaskFormatter("##/##/####"));            
            } 
            catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Erreur dans la date. Veuillez contacter l'administrateur.", "Erreur date", JOptionPane.ERROR_MESSAGE);
            }
            fieldDateDeb.setColumns(10);
            fieldDateDeb.setEditable(false);
            c.gridx++;
            this.add(fieldDateDeb, c);
            
            labelDateFin = new JLabel("Date Fin : ");
            c.gridx++;
            this.add(labelDateFin, c);
            
            checkDateFin = new JCheckBox();
            checkDateFin.addItemListener(new CheckBoxListener());
            c.gridx++;
            this.add(checkDateFin, c);
            
            try{
                fieldDateFin = new JFormattedTextField(new MaskFormatter("##/##/####"));            
            } 
            catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, 
                        "Erreur dans la date. Veuillez contacter l'administrateur.", 
                        "Erreur date", JOptionPane.ERROR_MESSAGE);
            }
            fieldDateFin.setColumns(10);
            fieldDateFin.setEditable(false);
            c.gridx++;
            this.add(fieldDateFin, c);
            
            spinPromo = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));            
            ((DefaultEditor) spinPromo.getEditor()).getTextField().setEditable(false);
            c.gridy++;
            this.add(spinPromo, c);
            
            checkPromo = new JCheckBox();
            checkPromo.addItemListener(new CheckBoxListener());
            c.gridx--;
            this.add(checkPromo, c);
            
            labelPromo = new JLabel("Promotion : ");
            c.gridx--;
            this.add(labelPromo, c);
            
            labelPrix = new JLabel("Prix : ");
            c.gridx = 0; c.gridy++;
            this.add(labelPrix, c);       
            
            spinPrix = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 0.01));
            spinPrix.setEditor(new JSpinner.NumberEditor(spinPrix, "0.00€"));
            c.gridx+=2; 
            
            this.add(spinPrix, c);            
            
            labelAccompte = new JLabel("Accompte : ");
            c.gridy++; c.gridx = 0;
            this.add(labelAccompte, c);
            
            spinAccompte = new JSpinner(new SpinnerNumberModel(0, 0, 100, 0.01));
            spinAccompte.setEditor(new JSpinner.NumberEditor(spinAccompte, "0.00%"));
            c.gridx+=2;
            this.add(spinAccompte, c);
            
            labelTVA = new JLabel("TVA : ");
            c.gridx++;
            this.add(labelTVA, c);
            
            spinTVA = new JSpinner(new SpinnerNumberModel(0, 0, 100, 0.01));
            spinTVA.setEditor(new JSpinner.NumberEditor(spinTVA, "0.00%"));
            c.gridx+=2;
            this.add(spinTVA, c);
            
            panelButtons = new JPanel(flowButtons);
            
                buttonInsert = new JButton("Insérer");
                buttonInsert.setContentAreaFilled(false);
                panelButtons.add(buttonInsert);
                
                buttonModify = new JButton("Modifier");
                buttonModify.setContentAreaFilled(false);
                buttonModify.setVisible(false);
                panelButtons.add(buttonModify);
                
                buttonDelete = new JButton("Supprimer");
                buttonDelete.setContentAreaFilled(false);
                buttonDelete.setVisible(false);
                panelButtons.add(buttonDelete);
                
            c.gridwidth = 6; 
            c.gridx = 0; c.gridy++;
            this.add(panelButtons, c);            
            
            ActionManager AM = new ActionManager();
            buttonInsert.addActionListener(AM);
            buttonModify.addActionListener(AM);
            buttonDelete.addActionListener(AM);

            try {
                iconURL = this.getClass().getResource("/viewPackage/resources/images/validate.png");
                buttonModify.setIcon(new ImageIcon(iconURL));

                iconURL = this.getClass().getResource("/viewPackage/resources/images/add.png");
                buttonInsert.setIcon(new ImageIcon(iconURL));

                iconURL = this.getClass().getResource("/viewPackage/resources/images/delete.png");
                buttonDelete.setIcon(new ImageIcon(iconURL));
            }
            catch(NullPointerException ex) {
                JOptionPane.showMessageDialog(null, 
                        "Erreur lors de la récupération des icônes.\nVeuillez contacter l'administrateur", 
                        "Erreur de récupération des icônes", JOptionPane.ERROR_MESSAGE);
            }            
        }      
    }
    
    private class ActionManager implements ActionListener {
        private Activite newAct;
        private Date dateDeb, dateFin;
        private GregorianCalendar dateGC = new GregorianCalendar();
        private Integer idFormation, idActivite, reply;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(listFormation.getSelectedIndex() != -1){
                idFormation = ((QueryResult)listFormation.getSelectedValue()).id;
                if(e.getSource() == buttonInsert || e.getSource() == buttonModify) {
                    newAct = new Activite();
                    newAct.setIdFormation(idFormation);                    
                    
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        if(checkDateDeb.isSelected() == true) {
                            dateDeb = dateFormat.parse(fieldDateDeb.getText());
                            dateGC.setTime(dateDeb);
                            newAct.setDateDeb(dateGC);
                        }                        
                        else {
                            newAct.setDateDeb(null);                            
                        }
                        if(checkDateFin.isSelected() == true) {
                            dateFin = dateFormat.parse(fieldDateFin.getText());
                            dateGC.setTime(dateFin);
                            newAct.setDateFin(dateGC);
                        }
                        else {
                            newAct.setDateFin(null);                            
                        }
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(null, 
                                "Les dates doivent être sous la forme 'jj/mm/aaaa'.", 
                                "Erreur champs date", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    newAct.setPromotion((Integer)spinPromo.getValue());
                    newAct.setPrix(Float.parseFloat(spinPrix.getValue().toString()));                    
                    newAct.setAccompte(Float.parseFloat(spinAccompte.getValue().toString())*100);
                    newAct.setTva(Float.parseFloat(spinTVA.getValue().toString())*100);
                    
                    if(newAct.getDateDeb() == null && (newAct.getPromotion() == null || newAct.getPromotion() == 0)){
                        JOptionPane.showMessageDialog(null, 
                                "Attention, l'activité ne comporte ni date ni promotion, veuillez compléter au minimum l'un des deux.", 
                                "Insertion Activité", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{                    
                        // Nouvelle activitée
                        if(e.getSource() == buttonInsert) {
                            try {
                                idActivite = app.newActivite(newAct);
                                JOptionPane.showMessageDialog(null, "Activité ajoutée.", "Insertion Activité", JOptionPane.INFORMATION_MESSAGE);
                            } 
                            catch (DBException ex) {
                                JOptionPane.showMessageDialog(null, ex, "Erreur Insertion", JOptionPane.ERROR_MESSAGE);
                            } 
                            catch (NotIdentified ex) {
                                JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        // Modification Activité
                        else {
                            idActivite = ((QueryResult)listActivite.getSelectedValue()).id;
                            newAct.setIdActivite(idActivite);
                            reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment modifier cette activtée ?", "Modification Activité", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {       
                                try {                                                   
                                    app.modifyActivite(newAct);
                                    JOptionPane.showMessageDialog(null, "Activité modifiée.", "Modification Activité", JOptionPane.INFORMATION_MESSAGE);
                                } 
                                catch (DBException ex) {
                                    JOptionPane.showMessageDialog(null, ex, "Erreur Modification", JOptionPane.ERROR_MESSAGE);
                                } 
                                catch (NotIdentified ex) {
                                    JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
                                }                    
                            }       
                        }
                        UpdateListActivite(idFormation);
                        UpdateInfoActivite(idActivite);

                        for(Object o : listModelActivite.toArray()){      
                            if(((QueryResult)o).id == idActivite){
                                listActivite.setSelectedValue(o, true);
                            }
                        }  
                    }
                }
                
                // Suppression activité
                else if(e.getSource() == buttonDelete) {
                    idActivite = ((QueryResult)listActivite.getSelectedValue()).id;
                    reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette activtée ?", "Suppression Activité", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {       
                        try {
                            app.deleteActivite(idActivite);
                            JOptionPane.showMessageDialog(null, "Activité supprimée.", "Suppression activité", JOptionPane.INFORMATION_MESSAGE);                            
                        } 
                        catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur Listing", JOptionPane.ERROR_MESSAGE);
                        } 
                        catch (NotIdentified ex) {
                            JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
                        }
                        UpdateListActivite(idFormation);
                        UpdateInfoActivite(-1);
                        listActivite.setSelectedIndex(0);
                    }
                }
            }
        }
    }
    
    private class ListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {  
            if(!lse.getValueIsAdjusting() && ((JList)lse.getSource()).getSelectedIndex() != -1){
                queryResult = (QueryResult)((JList)lse.getSource()).getSelectedValue();
                if(lse.getSource() == listFormation && queryResult.id != -1) {
                    UpdateListActivite(queryResult.id);
                    UpdateInfoActivite(-1);
                }
                else if(lse.getSource() == listActivite) {
                    UpdateInfoActivite(queryResult.id);
                }
            }
        }
    }
    
    private class CheckBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getItem() == checkDateDeb) {
                if(fieldDateDeb.isEditable() == true) {
                    fieldDateDeb.setEditable(false);
                }
                else {
                    fieldDateDeb.setEditable(true);
                }                
            }
            else if(e.getItem() == checkDateFin) {
                if(fieldDateFin.isEditable() == true) {
                    fieldDateFin.setEditable(false);
                }
                else {
                    fieldDateFin.setEditable(true);
                }                
            }
            else if(e.getItem() == checkPromo) {
                if(((DefaultEditor) spinPromo.getEditor()).getTextField().isEditable() == true) {
                    ((DefaultEditor) spinPromo.getEditor()).getTextField().setEditable(false);
                }
                else {
                    ((DefaultEditor) spinPromo.getEditor()).getTextField().setEditable(true);
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
                listModelActivite.addElement(new QueryResult(-1,"-- Nouvelle activité --"));
                for(Activite activite : arrayActivite) {                    
                    if(activite.getPromotion() != 0) {
                        desc = "Promotion " + activite.getPromotion().toString();
                    }
                    else if(activite.getDateDeb() != null){
                        desc = activite.getFormatedDateDeb();
                    }
                    else {
                        desc = "Activité sans date ni promotion!";
                    }
                    listModelActivite.addElement(new QueryResult(activite.getIdActivite(), desc));
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
    
    private void UpdateInfoActivite(Integer idActivite) {
        Date nDate = new Date();
        // New Activity
        if(idActivite == -1) {
            checkDateDeb.setSelected(false);
            fieldDateDeb.setValue(null);
            checkDateFin.setSelected(false);
            fieldDateFin.setValue(null);
            checkPromo.setSelected(false);
            spinPromo.setValue(0);
            spinPrix.setValue(0);
            spinAccompte.setValue(0);
            spinTVA.setValue(0);
            
            if(buttonInsert.isVisible() == false) {
                buttonInsert.setVisible(true);
            }
            
            if(buttonModify.isVisible() == true) {
                buttonModify.setVisible(false);
            }
            if(buttonDelete.isVisible() == true) {
                buttonDelete.setVisible(false);
            }
        }
        // Existant Activity
        else {
            try {
                act = app.getActivite(idActivite);
                if(act.getDateDeb() != null) {
                    checkDateDeb.setSelected(true);
                    fieldDateDeb.setValue(act.getFormatedDateDeb());
                }
                else {
                    checkDateDeb.setSelected(false);
                    fieldDateDeb.setValue(null);
                }
                if(act.getDateFin() != null) {
                    checkDateFin.setSelected(true);
                    fieldDateFin.setValue(act.getFormatedDateFin());
                }
                else {
                    checkDateFin.setSelected(false);
                    fieldDateFin.setValue(null);
                }
                if(act.getPromotion() != 0) {
                    checkPromo.setSelected(true);
                    spinPromo.setValue(act.getPromotion());
                }
                else {
                    checkPromo.setSelected(false);
                    spinPromo.setValue(0);
                }
                spinPrix.setValue(act.getPrix());
                spinAccompte.setValue(act.getAccompte()/100);
                spinTVA.setValue(act.getTva()/100);
                
                if(buttonInsert.isVisible() == true) {
                    buttonInsert.setVisible(false);
                }

                if(buttonModify.isVisible() == false) {
                    buttonModify.setVisible(true);
                }
                if(buttonDelete.isVisible() == false) {
                    buttonDelete.setVisible(true);
                }
            } 
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Récupération", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
