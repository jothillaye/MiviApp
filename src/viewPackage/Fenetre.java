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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    private PanelMembreSupprime panelMembreSupprime;
    
	// Menu
	private JMenuBar barre;
    private JButton buttonAccueil, buttonMembre, buttonInscription;
	private JMenu menuActivite, menuDivers;
	private JMenuItem itemActivite, itemFormation, itemMembresSupprimes;
    private JLabel labelVer;
    
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
        
        // Activités - Inscription
//		menuDivers = new JMenu("Divers");               
//		barre.add(menuDivers);
//        
//			// Activite Management
//			itemMembresSupprimes = new JMenuItem("Membres Supprimes");
//			itemMembresSupprimes.addActionListener(AM);
//			menuDivers.add(itemMembresSupprimes);		
        
        barre.add(Box.createHorizontalGlue());
        
        labelVer = new JLabel("ver 1.03 ");
        labelVer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        
        barre.add(labelVer);
            
        try {
            iconURL = this.getClass().getResource("/viewPackage/resources/images/home.png");
            buttonAccueil.setIcon(new ImageIcon(iconURL));
            
            iconURL = this.getClass().getResource("/viewPackage/resources/images/membre.png");
            buttonMembre.setIcon(new ImageIcon(iconURL));
            
            iconURL = this.getClass().getResource("/viewPackage/resources/images/activite.png");
            menuActivite.setIcon(new ImageIcon(iconURL));
            
            iconURL = this.getClass().getResource("/viewPackage/resources/images/inscription.png");
            buttonInscription.setIcon(new ImageIcon(iconURL));
        }
        catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des icônes.\nVeuillez contacter l'administrateur", "Erreur de récupération des icônes", JOptionPane.ERROR_MESSAGE);
        }    
        
        HoverButton HB = new HoverButton();
        buttonAccueil.addMouseListener(HB);
        buttonMembre.addMouseListener(HB);
        menuActivite.addMouseListener(HB);
        buttonInscription.addMouseListener(HB);
        
        Login();
        setResizable(false);
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
            // Gestion des Inscriptions
            else if(e.getSource() == itemMembresSupprimes){
                panelMembreSupprime = new PanelMembreSupprime();
                getCont().add(panelMembreSupprime, BorderLayout.CENTER);			   						
            }
            getCont().validate();	// refresh la fenêtre		   				
		}
	}
    
    private class HoverButton implements MouseListener {        
        @Override
        public void mouseEntered(MouseEvent me) {
            String src = me.getComponent().getClass().getSimpleName();
            if(src.equals("JMenu")) {
                ((JMenu)me.getSource()).setForeground(Color.GRAY);
            }
            else {
                ((JButton)me.getSource()).setForeground(Color.GRAY);
            }
        }
        @Override
        public void mouseExited(MouseEvent me) {            
            String src = me.getComponent().getClass().getSimpleName();
            if(src.equals("JMenu")) {
                ((JMenu)me.getSource()).setForeground(Color.BLACK);
            }
            else {
                ((JButton)me.getSource()).setForeground(Color.BLACK);
            }
        }
        @Override
        public void mouseClicked(MouseEvent me) {}
        @Override
        public void mousePressed(MouseEvent me) {}
        @Override
        public void mouseReleased(MouseEvent me) {}    
    }
    
    private class PanelLogin extends JPanel {
        private JLabel labelConnexion;
        private JButton buttonValid;
        
        public PanelLogin() {
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.CENTER;
            c.insets = new Insets(0, 0, 40, 0);
            
            labelConnexion = new JLabel("Connexion à la base de donnée");
            labelConnexion.setFont(new Font("Arial", Font.BOLD, 18));
            c.gridy = 0;
            this.add(labelConnexion, c);
            
            buttonValid = new JButton("Nouvelle Tentative de Connexion");
            buttonValid.setContentAreaFilled(false);
            buttonValid.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Login();
                }
            });	
            c.gridy ++;
            this.add(buttonValid, c);
        }	            
    }
    
    private void Login() {
        String pass = "";
        try {
            File filePW = new File("pass/pw.txt");
            BufferedReader br = new BufferedReader(new FileReader(filePW));
            pass = br.readLine();
            app.Connection(pass);
            ChangeLockStatus(true);
        }  
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Fichier contenant le mot de passe introuvable.", "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
            ChangeLockStatus(false);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lecture du mot de passe impossible.", "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
            ChangeLockStatus(false);
        }
        catch(IdentificationError idE) {
            JOptionPane.showMessageDialog(null, idE, "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
            ChangeLockStatus(false);
        }
    }
            
    private void ChangeLockStatus(Boolean b) {
        buttonAccueil.setEnabled(b);
        buttonMembre.setEnabled(b);
        menuActivite.setEnabled(b);
        buttonInscription.setEnabled(b);

        if(b == true) {
            Fenetre.getCont().removeAll();
            Fenetre.getCont().add(new PanelAccueil(), BorderLayout.CENTER);            
        }
        else {
            Fenetre.getCont().removeAll();
            Fenetre.getCont().add(new PanelLogin(), BorderLayout.CENTER);
        }
        Fenetre.getCont().repaint();
        Fenetre.getCont().validate();
    }
}
