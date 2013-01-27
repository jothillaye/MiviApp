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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Fenetre extends JFrame	{
	private static Container cont;
	
	// Panneaux accessibles
    private PanelAccueil panelAccueil;
	private PanelNewMembre panelNewMembre;
    private PanelListMembres panelListMembres;
	private PanelNewActivite panelNewActivite;
    private PanelListActivites panelListActivites;
    private PanelFormationsCanvas panelFormationsCanvas;
    private PanelExportParticipants panelExportParticipants;
    private PanelExportFormation panelExportFormation;
    private PanelExportPrixSpeciaux panelExportPrixSpeciaux;
    private PanelExportAccordsPaiements panelExportAccordsPaiements;
    
	// Menu
	private JMenuBar barre;
    private JButton buttonAccueil;
	private JMenu menuMembres, menuActivites, menuCanvasFormations, menuExport;
	private JMenuItem itemNewMembre, itemListMembres, 
            itemNewActivite, itemListActivites, 
            itemCanvasFormation,
            itemExportParticipants, itemExportFormation, itemExportPrixSpeciaux, itemExportAccordsPaiements;
    
	private ApplicationController app = new ApplicationController();
	private WindowAdapter close;
	
	// Constructeur
	public Fenetre(){
		// Creation Fenetre
		super("MiviApp");
		setBounds(150,100,800,550); 
		
		// Ecoute Fenetre        
		close = new CloseWindow();
		addWindowListener(close);
        // TO-DO
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
		menuMembres = new JMenu("Membres");
		barre.add(menuMembres);	
			
			// Nouveau Client
			itemNewMembre = new JMenuItem("Nouveau Membre");
			itemNewMembre.addActionListener(AM);
			menuMembres.add(itemNewMembre);
			
			// Liste des Membres
			itemListMembres = new JMenuItem("Liste des Membres");
			itemListMembres.addActionListener(AM);
			menuMembres.add(itemListMembres);
		
		// Activités - Inscription
		menuActivites = new JMenu("Activités");
		barre.add(menuActivites);
			
			// Nouvelle Activité
			itemNewActivite = new JMenuItem("Nouvelle Activité");
			itemNewActivite.addActionListener(AM);
			menuActivites.add(itemNewActivite);
			
			// Liste des Activités
			itemListActivites = new JMenuItem("Liste des Activités");
			itemListActivites.addActionListener(AM);
			menuActivites.add(itemListActivites);
		
		// Canvas Formation
		menuCanvasFormations = new JMenu("Canvas Formation");
		barre.add(menuCanvasFormations);
			
			// Nouveau canvas
			itemCanvasFormation = new JMenuItem("Nouveau Canvas");
			itemCanvasFormation.addActionListener(AM);
			menuCanvasFormations.add(itemCanvasFormation);
				
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
            // Nouveau Membre
            else if(e.getSource() == itemNewMembre){	
                panelNewMembre = new PanelNewMembre();
                getCont().add(panelNewMembre, BorderLayout.CENTER);
            }
            // Liste des Membres
            else if(e.getSource() == itemListMembres){
                panelListMembres = new PanelListMembres();
                getCont().add(panelListMembres, BorderLayout.CENTER);
            }
            // Nouvelle Activité
            else if(e.getSource() == itemNewActivite){
                panelNewActivite = new PanelNewActivite();
                getCont().add(panelNewActivite, BorderLayout.CENTER);
            }
            // Liste des Activités
            else if(e.getSource() == itemListActivites){
                panelListActivites = new PanelListActivites();
                getCont().add(panelListActivites, BorderLayout.CENTER);			   						
            }
            // Canvas Formations
            else if(e.getSource() == itemCanvasFormation){
                panelFormationsCanvas = new PanelFormationsCanvas();
                getCont().add(panelFormationsCanvas, BorderLayout.CENTER);			   						
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
