package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
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
    private JList listMembre;
    private JScrollPane scrollPaneMembre;
    private DefaultListModel listModelMembre;
    private GridBagConstraints c;
    
    private PanelMembreSupprime PMS;
    private ApplicationController app = new ApplicationController();
    
    private ArrayList<Membre> arrayMembre = new ArrayList<Membre>();
    
    public PanelMembreSupprime() {
        this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();		
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(20,0,0,0);	
		
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
        scrollPaneMembre.setPreferredSize(new Dimension(260, 500));
        c.gridy++;
        this.add(scrollPaneMembre, c);             
        
        try {            
            arrayMembre = app.listMembreSupprime();
            listModelMembre.clear();
            for(Membre membre : arrayMembre) {
                listModelMembre.addElement(new QueryResult(membre.getIdMembre(),membre.getNom()+", "+membre.getPrenom()));
            }
            listMembre.validate();
        } 
        catch (DBException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur Listing", JOptionPane.ERROR_MESSAGE);
        } 
        catch (NotIdentified ex) {
            JOptionPane.showMessageDialog(null, ex, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
        }        
    }
}
