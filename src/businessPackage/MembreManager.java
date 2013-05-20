package businessPackage;

import dataAccessPackage.AccessMembreDB;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.util.ArrayList;
import modelPackage.Membre;

public class MembreManager {
    private AccessMembreDB accessMembre = new AccessMembreDB();

	public Integer newMembre(Membre membre) throws DBException, NotIdentified {
        // TODO : compléter les champs obligatoires
		if(membre.getNom().isEmpty() || membre.getPrenom().isEmpty()) {
            throw new DBException("Il est obligatoire de remplir les champs nom et prénom");
        }	
		else {
            return accessMembre.newMembre(membre);
        }
	}
	
	public ArrayList<Membre> listMembre(String nom, String prenom) throws DBException, NotIdentified {
		return accessMembre.listMembre(nom, prenom);
	}

    public Membre getMembre(Integer id) throws DBException, NotIdentified {
        return accessMembre.getMembre(id);
    }

    public void modifyMembre(Membre membre) throws DBException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(membre.getNom().isEmpty() || membre.getPrenom().isEmpty()) {
            throw new DBException("Il est obligatoire de remplir les champs nom et prénom");
        }	
		else {
            accessMembre.modifyMembre(membre);
        }
    }

    public void deleteMembre(Integer idMembre) throws DBException, NotIdentified {
        accessMembre.deleteMembre(idMembre);
    }

	public ArrayList<Membre> listMembreSupprime() throws DBException, NotIdentified {
		return accessMembre.listMembreSupprime();
	}
}