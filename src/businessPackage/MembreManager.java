package businessPackage;

import java.util.ArrayList;

import modelPackage.Membre;
import dataAccessPackage.AccessMembreDB;
import exceptionPackage.GetMembreException;
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
	
	public ArrayList<Membre> getMembre(String search) throws GetMembreException, NotIdentified {
        System.out.println("ok 3");
		return new AccessMembreDB().getMembre(search);
	}
}