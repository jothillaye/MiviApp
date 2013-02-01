package controllerPackage;

import modelPackage.*;
import businessPackage.*;
import exceptionPackage.*;
import java.util.ArrayList;


public class ApplicationController {	
	private LoginManager loginManager;	
    private MembreManager membreManager;

	public ApplicationController(){	
		loginManager = new LoginManager();
        membreManager = new MembreManager();        
	}	
	
	// Login Managing	
	public void Connection() throws NotIdentified {
		loginManager.Connection();
	}
	
	public void Connection(String pw) throws IdentificationError {
		loginManager.Connection(pw);
	}

	public void Disconnect() throws DisconnectException {
		loginManager.CloseConnection();
	}
	
	
	// Membre Managing	
    public void newMembre(Membre membre) throws NewMembreException, NotIdentified {
        membreManager.newMembre(membre);
    }
	
    public ArrayList<Membre> getMembre(String search) throws GetMembreException, NotIdentified {
        return membreManager.getMembre(search);
	}
}