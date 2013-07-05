package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import modelPackage.Membre;

/**
 *
 * @author Joachim
 */
public class PanelMembreSupprime extends JPanel{
    private JLabel titleAccueil;
    private JButton buttonRestore, buttonDelete;
    private JList listMembre;
    private JScrollPane scrollPaneMembre;
    private DefaultListModel listModelMembre;
    private GridBagConstraints c;
    
    private PanelMembreSupprime PMS;
    private ApplicationController app = new ApplicationController();
    
    private URL iconURL; 
    
    private ArrayList<Membre> arrayMembre = new ArrayList<Membre>();
    
    public PanelMembreSupprime() {
        this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();		
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(20,10,0,0);	
		
        // Titre 
        titleAccueil = new JLabel("Membres supprimés");
        titleAccueil.setFont(new Font("Serif", Font.BOLD, 21));
        c.gridx = 0; c.gridy = 0;
        this.add(titleAccueil, c);

        listMembre = new JList();            
        listMembre.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  

        listModelMembre = new DefaultListModel();
        listMembre.setModel(listModelMembre);                           

        scrollPaneMembre = new JScrollPane(listMembre);                           
        scrollPaneMembre.setPreferredSize(new Dimension(240, 300));
        c.gridy++; c.gridheight = 15;
        this.add(scrollPaneMembre, c);       
        
        buttonRestore = new JButton("Restaurer");
        buttonRestore.setContentAreaFilled(false);
        c.gridx++; c.gridheight = 1;
        this.add(buttonRestore, c);
        
        buttonDelete = new JButton("Supprimer");
        buttonDelete.setContentAreaFilled(false);
        c.gridy++;
        this.add(buttonDelete, c);
        
        ActionManager AM = new ActionManager();
        buttonRestore.addActionListener(AM);
        buttonDelete.addActionListener(AM);
        
        UpdateListMembre();
        
        try {
            iconURL = this.getClass().getResource("/viewPackage/resources/images/validate.png");
            buttonRestore.setIcon(new ImageIcon(iconURL));

            iconURL = this.getClass().getResource("/viewPackage/resources/images/delete.png");
            buttonDelete.setIcon(new ImageIcon(iconURL));
            
            iconURL = this.getClass().getResource("/viewPackage/resources/images/export.png");
        }
        catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des icônes.\nVeuillez contacter l'administrateur.", "Erreur de récupération des icônes", JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
    public void UpdateListMembre(){
        try {            
            listModelMembre.clear();
            arrayMembre = app.listMembreSupprime();
            if(arrayMembre.isEmpty() == true){
                listModelMembre.addElement(new QueryResult(-1,"Aucun membre supprimé"));
            }
            else {            
                for(Membre membre : arrayMembre) {
                    listModelMembre.addElement(new QueryResult(membre.getIdMembre(),membre.getNom()+", "+membre.getPrenom()));
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
    
    public class ActionManager implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Integer idMembre = ((QueryResult)listMembre.getSelectedValue()).id;
            try {
                if(e.getSource() == buttonRestore){
                    app.restoreMembre(idMembre);
                    JOptionPane.showMessageDialog(null, "Restauration de "+listMembre.getSelectedValue()+" réussie", "Restauration Membre", JOptionPane.INFORMATION_MESSAGE);

                }
                else if(e.getSource() == buttonDelete){
                    Integer reply = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir définitivement supprimer ce membre ?", "Suppression Membre", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) { 
                        app.definitivelyDeleteMembre(idMembre);
                        JOptionPane.showMessageDialog(null, "Suppression de "+listMembre.getSelectedValue()+" réussie", "Suppression Membre", JOptionPane.INFORMATION_MESSAGE);                
                    }
                }
                UpdateListMembre();
            }
            catch (DBException ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Listing", JOptionPane.ERROR_MESSAGE);
            } 
            catch (NotIdentified ex) {
                JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
            }  
        }
    }
}
