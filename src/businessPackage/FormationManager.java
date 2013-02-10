package businessPackage;

import dataAccessPackage.AccessFormationDB;
import exceptionPackage.DeleteFormationException;
import exceptionPackage.ListFormationException;
import exceptionPackage.ModifyFormationException;
import exceptionPackage.NewFormationException;
import exceptionPackage.NotIdentified;
import java.util.ArrayList;
import modelPackage.Formation;

public class FormationManager {
    private AccessFormationDB accessForm = new AccessFormationDB();
    
    public void newFormation(Formation form) throws NewFormationException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(form.getIntitule().isEmpty()) {
            throw new NewFormationException("Il est obligatoire de remplir l'intitule");
        }	
		else {
            accessForm.newFormation(form);
        }
    }

    public ArrayList<Formation> listForm() throws ListFormationException, NotIdentified {
        return accessForm.listFormation();
    }
    
    public void modifyFormation(Formation form) throws ModifyFormationException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(form.getIntitule().isEmpty()) {
            throw new ModifyFormationException("Il est obligatoire de remplir l'intitule");
        }	
		else {
            accessForm.modifyFormation(form);
        }
    }
    
    public void deleteFormation(Integer idFormation) throws DeleteFormationException, NotIdentified {
        accessForm.deleteFormation(idFormation);
    }
    
    
} 