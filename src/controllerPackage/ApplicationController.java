package controllerPackage;

import businessPackage.*;
import exceptionPackage.*;
import java.util.ArrayList;
import modelPackage.*;


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
	
    public ArrayList<Membre> listMembre(String search) throws ListMembreException, NotIdentified {
        return membreManager.listMembre(search);
	}
    
    public Membre getMembre(Integer id) throws ListMembreException, NotIdentified {
        return membreManager.getMembre(id);
    }

    public void modifyMembre(Membre membre) throws ModifyMembreException, NotIdentified {
        membreManager.modifyMembre(membre);
    }

    public void deleteMembre(Integer idMembre) throws DeleteMembreException, NotIdentified {
        membreManager.deleteMembre(idMembre);
    }
}