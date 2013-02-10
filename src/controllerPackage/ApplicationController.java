package controllerPackage;

import businessPackage.*;
import exceptionPackage.*;
import java.util.ArrayList;
import modelPackage.*;


public class ApplicationController {	
	private LoginManager loginManager;	
    private MembreManager membreManager;
    private FormationManager formationManager;

	public ApplicationController(){	
		loginManager = new LoginManager();
        membreManager = new MembreManager();        
        formationManager = new FormationManager();
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
    public Integer newMembre(Membre membre) throws NewMembreException, NotIdentified {
        return membreManager.newMembre(membre);
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

    // Formation Manager
    public ArrayList<Formation> listForm() throws ListFormationException, NotIdentified {
        return formationManager.listForm();
    }
    
    public void newFormation(Formation form) throws NewFormationException, NotIdentified {
        formationManager.newFormation(form);
    }
    
    public void modifyFormation(Formation form) throws ModifyFormationException, NotIdentified {
        formationManager.modifyFormation(form);
    }
    
    public void deleteFormation(Integer idFormation) throws DeleteFormationException, NotIdentified {
                formationManager.deleteFormation(idFormation);
    }
}