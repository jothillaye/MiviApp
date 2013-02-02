package businessPackage;

import java.util.ArrayList;

import modelPackage.Membre;
import dataAccessPackage.AccessMembreDB;
import exceptionPackage.ListMembreException;
import exceptionPackage.NewMembreException;
import exceptionPackage.NotIdentified;

public class MembreManager {

	public void newMembre(Membre membre) throws NewMembreException, NotIdentified {
		if(membre.getNom().isEmpty() || membre.getPrenom().isEmpty()) {
            throw new NewMembreException("Il est obligatoire de remplir les champs nom et pr�nom");
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
}