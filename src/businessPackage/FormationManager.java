package businessPackage;

import dataAccessPackage.AccessFormationDB;
import dataAccessPackage.AccessFormationDB;
import exceptionPackage.ListFormationException;
import exceptionPackage.ModifyFormationException;
import exceptionPackage.NotIdentified;
import java.util.ArrayList;
import modelPackage.Formation;
import modelPackage.Formation;

public class FormationManager {
    
    public void newFormation(Formation form) throws NewFormationException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(form.getIntitule().isEmpty()) {
            throw new NewFormationException("Il est obligatoire de remplir l'intitule");
        }	
		else {
            new AccessFormationDB().newFormation(form);
        }
    }

    public ArrayList<Formation> listForm() throws ListFormationException, NotIdentified {
        return new AccessFormationDB().listFormation();
    }
    
    public void modifyFormation(Formation form) throws ModifyFormationException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(form.getIntitule().isEmpty()) {
            throw new ModifyFormationException("Il est obligatoire de remplir l'intitule");
        }	
		else {
            new AccessFormationDB().modifyFormation(form);
        }
    }
    
}