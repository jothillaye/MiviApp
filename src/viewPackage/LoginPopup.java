package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.IdentificationError;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginPopup extends JFrame{
	private Container cont;	
	private JLabel labelPass;
	private JPasswordField fieldPass;
	private JButton buttonValid;
	
	ApplicationController app = new ApplicationController();
	
	public LoginPopup() {
		super("Connexion");		
		
		setBounds(100,100,250,100);				
		setLayout(null);
		
		cont = getContentPane();
		cont.setLayout(null);
		
		labelPass = new JLabel("Mot de passe :");
		labelPass.setBounds(10, 10, 100, 20);
		cont.add(labelPass);
		
		fieldPass = new JPasswordField();
		fieldPass.setBounds(120,10,100,20);
		cont.add(fieldPass);	
		
		buttonValid = new JButton("Valider");
		buttonValid.setBounds(120,40,100,20);
		cont.add(buttonValid);
		
		ActionManager AM = new ActionManager();
		buttonValid.addActionListener(AM);	
		
		setVisible(true);
	}	
	
	private class ActionManager implements ActionListener {
        @Override
		public void actionPerformed(ActionEvent e)	{
            try {
                app.Connection(new String(fieldPass.getPassword()));
            }  
            catch(IdentificationError idE) {
                JOptionPane.showMessageDialog(null, idE, "Erreur d'identification", JOptionPane.ERROR_MESSAGE);
                new LoginPopup();
            }
            setVisible(false);               
		}
	}
}