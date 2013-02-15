/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

/**
 *
 * @author Joachim
 */
import controllerPackage.*;
import exceptionPackage.DisconnectException;
import exceptionPackage.IdentificationError;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class Fenetre extends JFrame	{
	private static Container cont;
	
	// Panneaux accessibles
    private PanelAccueil panelAccueil;
	private PanelMembre panelMembre;
    private PanelFormation panelFormation;
    private PanelActivite panelActivite;
	private PanelInscription panelInscription;
    private PanelExportParticipants panelExportParticipants;
    private PanelExportFormation panelExportFormation;
    private PanelExportPrixSpeciaux panelExportPrixSpeciaux;
    private PanelExportAccordsPaiements panelExportAccordsPaiements;
    
	// Menu
	private JMenuBar barre;
    private JButton buttonAccueil, buttonMembre, buttonInscription;
	private JMenu menuActivite, menuExport;
	private JMenuItem itemActivite, itemFormation,
            itemExportParticipants, itemExportFormation, itemExportPrixSpeciaux, itemExportAccordsPaiements;
    
    private URL iconURL;
    
	private ApplicationController app = new ApplicationController();
	private WindowAdapter close;
	
	// Constructeur
	public Fenetre(){
		// Creation Fenetre
		super("MiviApp");
		setBounds(150,100,900,550); 
		setMinimumSize(new Dimension(900, 550));
        
        iconURL = this.getClass().getResource("/viewPackage/resources/images/icon.png");
        setIconImage(new ImageIcon(iconURL).getImage());
        
		// Ecoute Fenetre        
		close = new CloseWindow();
		addWindowListener(close);
        // TODO : correct the close operation to disconnect properly
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Creation Container
		setContent(getContentPane());
		getCont().setLayout(new BorderLayout());
		
		// Creation Menu
		barre = new JMenuBar();
		setJMenuBar(barre);	
		
		// Ecoute les évenements
		ActionManager AM = new ActionManager();
		
        // Accueil
        buttonAccueil = new JButton("Accueil");
        buttonAccueil.setBorderPainted(false);
        buttonAccueil.setFocusPainted(false);
        buttonAccueil.setContentAreaFilled(false);
        buttonAccueil.addActionListener(AM);
        barre.add(buttonAccueil);
        
		// Membres
        buttonMembre = new JButton("Membres");
        buttonMembre.setBorderPainted(false);
        buttonMembre.setFocusPainted(false);
        buttonMembre.setContentAreaFilled(false);
        buttonMembre.addActionListener(AM);        
		barre.add(buttonMembre);	
        
		// Activités - Inscription
		menuActivite = new JMenu("Activités");               
		barre.add(menuActivite);
        
			// Activite Management
			itemActivite = new JMenuItem("Gestion des Activités");
			itemActivite.addActionListener(AM);
			menuActivite.add(itemActivite);
		
            // Formation Management
			itemFormation = new JMenuItem("Gestion des Formation");
			itemFormation.addActionListener(AM);
			menuActivite.add(itemFormation);
            
        // Inscription
        buttonInscription = new JButton("Inscriptions");
        buttonInscription.setBorderPainted(false);
        buttonInscription.setFocusPainted(false);
        buttonInscription.setContentAreaFilled(false);
        buttonInscription.addActionListener(AM);        
		barre.add(buttonInscription);
				
		// Export
		menuExport = new JMenu("Export");
		barre.add(menuExport);
			
			itemExportParticipants = new JMenuItem("Export Participants");
			itemExportParticipants.addActionListener(AM);
			menuExport.add(itemExportParticipants);
			
            itemExportFormation = new JMenuItem("Export Formation");
			itemExportFormation.addActionListener(AM);
			menuExport.add(itemExportFormation);
			
            itemExportAccordsPaiements = new JMenuItem("Export Accords de Paiements");
			itemExportAccordsPaiements.addActionListener(AM);
			menuExport.add(itemExportAccordsPaiements);
			
            itemExportPrixSpeciaux = new JMenuItem("Export Prix Spéciaux");
			itemExportPrixSpeciaux.addActionListener(AM);
			menuExport.add(itemExportPrixSpeciaux);
            
        try {
            iconURL = this.getClass().getResource("/viewPackage/resources/images/home.png");
            buttonAccueil.setIcon(new ImageIcon(iconURL));
            
            iconURL = this.getClass().getResource("/viewPackage/resources/images/membre.png");
            buttonMembre.setIcon(new ImageIcon(iconURL));
            
            iconURL = this.getClass().getResource("/viewPackage/resources/images/activite.png");
            menuActivite.setIcon(new ImageIcon(iconURL));
            
            iconURL = this.getClass().getResource("/viewPackage/resources/images/inscription.png");
            buttonInscription.setIcon(new ImageIcon(iconURL));
            
            iconURL = this.getClass().getResource("/viewPackage/resources/images/export.png");
            menuExport.setIcon(new ImageIcon(iconURL));
        }
        catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des icônes.\nVeuillez contacter l'administrateur", "Erreur de récupération des icônes", JOptionPane.ERROR_MESSAGE);
        }    
            
		/// FIN DU MENU ///                     
            
        panelAccueil = new PanelAccueil();
		getCont().add(panelAccueil, BorderLayout.CENTER);
	}
	
	
	// Getter & Setter
	public static Container getCont() {return cont;}
	private void setContent(Container contentPane) { Fenetre.cont = contentPane;}
	
	// Fermeture fenêtre
	private class CloseWindow extends WindowAdapter {		
		public void WindowClosing(WindowEvent e) {	
		   	try {
				app.Disconnect();
				System.exit(0);
			}
		   	catch (DisconnectException e1) {
		   		JOptionPane.showMessageDialog(null, e1, "Erreur", JOptionPane.ERROR_MESSAGE);						
			}
            finally {
                System.exit(0);
            }
		}
	}
	
	// Action Manager
	private class ActionManager implements ActionListener{ 		
        @Override
		public void actionPerformed(ActionEvent e){					   		
            // Accès à un autre panel			
            getCont().removeAll();  // supprime le contenu actuel	
            
            // Accueil
            if(e.getSource() == buttonAccueil){	
                panelAccueil = new PanelAccueil();
                getCont().add(panelAccueil, BorderLayout.CENTER);
            }            
            // Gestion des Membres
            else if(e.getSource() == buttonMembre){	
                panelMembre = new PanelMembre();
                getCont().add(panelMembre, BorderLayout.CENTER);
            }
            // Gestion des Formations
            else if(e.getSource() == itemFormation){
                panelFormation = new PanelFormation();
                getCont().add(panelFormation, BorderLayout.CENTER);
            }
            // Gestion des Activités
            else if(e.getSource() == itemActivite){
                panelActivite = new PanelActivite();
                getCont().add(panelActivite, BorderLayout.CENTER);			   						
            }
            // Gestion des Inscriptions
            else if(e.getSource() == buttonInscription){
                panelInscription = new PanelInscription();
                getCont().add(panelInscription, BorderLayout.CENTER);			   						
            }
            // Export Participants
            else if(e.getSource() == itemExportParticipants){
                panelExportParticipants = new PanelExportParticipants();
                getCont().add(panelExportParticipants, BorderLayout.CENTER);			   						
            }
            // Export Formation
            else if(e.getSource() == itemExportFormation){
                panelExportFormation = new PanelExportFormation();
                getCont().add(panelExportFormation, BorderLayout.CENTER);			   						
            }
            // Export Prix Speciaux
            else if(e.getSource() == itemExportPrixSpeciaux){
                panelExportPrixSpeciaux = new PanelExportPrixSpeciaux();
                getCont().add(panelExportPrixSpeciaux, BorderLayout.CENTER);			   						
            }
            // Export Accords Paiements
            else if(e.getSource() == itemExportAccordsPaiements){
                panelExportAccordsPaiements = new PanelExportAccordsPaiements();
                getCont().add(panelExportAccordsPaiements, BorderLayout.CENTER);			   						
            }            
            getCont().validate();	// refresh la fenêtre		   				
		}
	}	
}
