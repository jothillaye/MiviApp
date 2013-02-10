package businessPackage;

import dataAccessPackage.AccessMembreDB;
import exceptionPackage.DeleteMembreException;
import exceptionPackage.ListMembreException;
import exceptionPackage.ModifyMembreException;
import exceptionPackage.NewMembreException;
import exceptionPackage.NotIdentified;
import java.util.ArrayList;
import modelPackage.Membre;

public class MembreManager {
    private AccessMembreDB accessMembre = new AccessMembreDB();

	public Integer newMembre(Membre membre) throws NewMembreException, NotIdentified {
        // TODO : compléter les champs obligatoires
		if(membre.getNom().isEmpty() || membre.getPrenom().isEmpty()) {
            throw new NewMembreException("Il est obligatoire de remplir les champs nom et prénom");
        }	
		else {
            return accessMembre.newMembre(membre);
        }
	}
	
	public ArrayList<Membre> listMembre(String search) throws ListMembreException, NotIdentified {
		return accessMembre.listMembre(search);
	}

    public Membre getMembre(Integer id) throws ListMembreException, NotIdentified {
        return accessMembre.getMembre(id);
    }

    public void modifyMembre(Membre membre) throws ModifyMembreException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(membre.getNom().isEmpty() || membre.getPrenom().isEmpty()) {
            throw new ModifyMembreException("Il est obligatoire de remplir les champs nom et prénom");
        }	
		else {
            accessMembre.modifyMembre(membre);
        }
    }

    public void deleteMembre(Integer idMembre) throws DeleteMembreException, NotIdentified {
        accessMembre.deleteMembre(idMembre);
    }
}