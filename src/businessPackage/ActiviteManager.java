package businessPackage;

import dataAccessPackage.AccessActiviteDB;
import exceptionPackage.NotIdentified;
import exceptionPackage.DBException;
import java.util.ArrayList;
import modelPackage.Activite;

public class ActiviteManager {
    private AccessActiviteDB accessActivite = new AccessActiviteDB();
    
    public void newActivite(Activite act) throws DBException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(act.getDateDeb() == null && act.getPromotion() == null) {
            throw new DBException("Il est obligatoire de remplir soit la date de début soit un numéro de promotion");
        }	
		else {
            accessActivite.newActivite(act);
        }
    }

    public ArrayList<Activite> listActivite(Integer idFormation) throws DBException, NotIdentified {
        return accessActivite.listActivite(idFormation);
    }

    public Activite getActivite(Integer idActivite) throws DBException, NotIdentified {
        return accessActivite.getActivite(idActivite);
    }    
    
    public void modifyActivite(Activite act) throws DBException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(act.getDateDeb() == null && act.getPromotion() == null) {
            throw new DBException("Il est obligatoire de remplir soit la date de début soit un numéro de promotion");
        }	
		else {
            accessActivite.modifyActivite(act);
        }
    }
    
    public void deleteActivite(Integer idActivite) throws DBException, NotIdentified {
        accessActivite.deleteActivite(idActivite);
    }
    
    
} 