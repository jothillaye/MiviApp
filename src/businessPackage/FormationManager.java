package businessPackage;

import dataAccessPackage.AccessFormationDB;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.util.ArrayList;
import modelPackage.Formation;

public class FormationManager {
    private AccessFormationDB accessForm = new AccessFormationDB();
    
    public void newFormation(Formation form) throws DBException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(form.getIntitule().isEmpty()) {
            throw new DBException("Il est obligatoire de remplir l'intitule");
        }	
		else {
            accessForm.newFormation(form);
        }
    }

    public ArrayList<Formation> listForm() throws DBException, NotIdentified {
        return accessForm.listFormation();
    }
    
    public void modifyFormation(Formation form) throws DBException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(form.getIntitule().isEmpty()) {
            throw new DBException("Il est obligatoire de remplir l'intitule");
        }	
		else {
            accessForm.modifyFormation(form);
        }
    }
    
    public void deleteFormation(Integer idFormation) throws DBException, NotIdentified {
        accessForm.deleteFormation(idFormation);
    }
    
    
} 