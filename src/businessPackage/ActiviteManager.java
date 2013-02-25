package businessPackage;

import dataAccessPackage.AccessActiviteDB;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.util.ArrayList;
import modelPackage.Activite;

public class ActiviteManager {
    private AccessActiviteDB accessActivite = new AccessActiviteDB();
    
    public Integer newActivite(Activite act) throws DBException, NotIdentified {
        return accessActivite.newActivite(act);
    }

    public ArrayList<Activite> listActivite(Integer idFormation) throws DBException, NotIdentified {
        return accessActivite.listActivite(idFormation);
    }

    public Activite getActivite(Integer idActivite) throws DBException, NotIdentified {
        return accessActivite.getActivite(idActivite);
    }    
    
    public void modifyActivite(Activite act) throws DBException, NotIdentified {
        accessActivite.modifyActivite(act);
    }
    
    public void deleteActivite(Integer idActivite) throws DBException, NotIdentified {
        accessActivite.deleteActivite(idActivite);
    }
    
    
} 