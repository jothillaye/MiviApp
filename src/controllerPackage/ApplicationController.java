package controllerPackage;

import businessPackage.*;
import exceptionPackage.*;
import java.util.ArrayList;
import modelPackage.*;


public class ApplicationController {	
	private LoginManager loginManager;	
    private MembreManager membreManager;
    private FormationManager formationManager;
    private ActiviteManager activiteManager;
    private InscriptionManager inscriptionManager;
    private PaiementManager paiementManager;

	public ApplicationController(){	
		loginManager = new LoginManager();
        membreManager = new MembreManager();        
        formationManager = new FormationManager();
        activiteManager = new ActiviteManager();
        inscriptionManager = new InscriptionManager();
        paiementManager = new PaiementManager();
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
    public Integer newMembre(Membre membre) throws DBException, NotIdentified {
        return membreManager.newMembre(membre);
    }
	
    public ArrayList<Membre> listMembre(String nom, String prenom) throws DBException, NotIdentified {
        return membreManager.listMembre(nom, prenom);
	}
    
    public Membre getMembre(Integer id) throws DBException, NotIdentified {
        return membreManager.getMembre(id);
    }

    public void modifyMembre(Membre membre) throws DBException, NotIdentified {
        membreManager.modifyMembre(membre);
    }

    public void deleteMembre(Integer idMembre) throws DBException, NotIdentified {
        membreManager.deleteMembre(idMembre);
    }
    
    public ArrayList<Membre> listMembreSupprime() throws DBException, NotIdentified {
        return membreManager.listMembreSupprime();
	}
    
    public void restoreMembre(Integer idMembre) throws DBException, NotIdentified {
        membreManager.restoreMembre(idMembre);
    }
    
    public void definitivelyDeleteMembre(Integer idMembre) throws DBException, NotIdentified {
        membreManager.definitivelyDeleteMembre(idMembre);
    }

    // Formation Manager
    public ArrayList<Formation> listForm() throws DBException, NotIdentified {
        return formationManager.listForm();
    }
    
    public void newFormation(Formation form) throws DBException, NotIdentified {
        formationManager.newFormation(form);
    }
    
    public void modifyFormation(Formation form) throws DBException, NotIdentified {
        formationManager.modifyFormation(form);
    }
    
    public void deleteFormation(Integer idFormation) throws DBException, NotIdentified {
        formationManager.deleteFormation(idFormation);
    }
    
    // Activite Manager
    public ArrayList<Activite> listActivite(Integer idFormation) throws DBException, NotIdentified {
        return activiteManager.listActivite(idFormation);
    }
    
    public Activite getActivite(Integer idActivite) throws DBException, NotIdentified {
        return activiteManager.getActivite(idActivite);
    }
    
    public Integer newActivite(Activite act) throws DBException, NotIdentified {
        return activiteManager.newActivite(act);
    }
    
    public void modifyActivite(Activite act) throws DBException, NotIdentified {
        activiteManager.modifyActivite(act);
    }
    
    public void deleteActivite(Integer idActivite) throws DBException, NotIdentified {
        activiteManager.deleteActivite(idActivite);
    }
    
    // Inscription Manager
    public Integer  newInscription(Inscription ins) throws DBException, NotIdentified {
        return inscriptionManager.newInscription(ins);
    }
    
    public ArrayList<Inscription> listInscription(Integer idActivite, Integer typeIns) throws DBException, NotIdentified {
        return inscriptionManager.listInscription(idActivite, typeIns);
    }    
    
    public Inscription getInscription(Integer idInscription) throws DBException, NotIdentified {
        return inscriptionManager.getInscription(idInscription);
    }    
    
    public void deleteInscription(Inscription ins) throws DBException, NotIdentified {
        inscriptionManager.deleteInscription(ins);
    }
    
    public void modifyInscription(Inscription ins) throws DBException, NotIdentified {
        inscriptionManager.modifyInscription(ins);
    }
    
    public ArrayList<ArrayList<Object>> listInsMembre(Integer idMembre) throws DBException, NotIdentified {
        return inscriptionManager.listInsMembre(idMembre);
    }

    // Paiement Manager
    public void newPaiement(Paiement paiement) throws DBException, NotIdentified {
        paiementManager.newPaiement(paiement);
    }
    
    public ArrayList<Paiement> listPaiement(Paiement paiement) throws DBException, NotIdentified {
        return paiementManager.listPaiement(paiement);
    }

    public void modifyPaiement(Paiement paiement) throws DBException, NotIdentified {
        paiementManager.modifyPaiement(paiement);
    }
    
    public void deletePaiement(Integer idPaiement) throws DBException, NotIdentified {
        paiementManager.deletePaiement(idPaiement);
    }
    
    public Float getSolde(Integer idInscription) throws DBException, NotIdentified {
        return paiementManager.getSolde(idInscription);
    }    
}