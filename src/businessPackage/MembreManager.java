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

	public void newMembre(Membre membre) throws NewMembreException, NotIdentified {
        // TODO : complèter les champs obligatoires
		if(membre.getNom().isEmpty() || membre.getPrenom().isEmpty()) {
            throw new NewMembreException("Il est obligatoire de remplir les champs nom et prénom");
        }	
		else {
            new AccessMembreDB().newMembre(membre);
        }
	}
	
	public ArrayList<Membre> listMembre(String search) throws ListMembreException, NotIdentified {
		return new AccessMembreDB().listMembre(search);
	}

    public Membre getMembre(Integer id) throws ListMembreException, NotIdentified {
        return new AccessMembreDB().getMembre(id);
    }

    public void modifyMembre(Membre membre) throws ModifyMembreException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(membre.getNom().isEmpty() || membre.getPrenom().isEmpty()) {
            throw new ModifyMembreException("Il est obligatoire de remplir les champs nom et prénom");
        }	
		else {
            new AccessMembreDB().modifyMembre(membre);
        }
    }

    public void deleteMembre(Integer idMembre) throws DeleteMembreException, NotIdentified {
        new AccessMembreDB().deleteMembre(idMembre);
    }
}