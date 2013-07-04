package businessPackage;

import dataAccessPackage.AccessPaiementDB;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.util.ArrayList;
import modelPackage.Paiement;

public class PaiementManager {
    private AccessPaiementDB accessPaiement = new AccessPaiementDB();
    
    public void newPaiement(Paiement paiement) throws DBException, NotIdentified {
        accessPaiement.newPaiement(paiement);
    }

    public ArrayList<Paiement> listPaiement(Paiement paiement) throws DBException, NotIdentified {
        return accessPaiement.listPaiement(paiement);
    }
    
    public void modifyPaiement(Paiement paiement) throws DBException, NotIdentified {
        accessPaiement.modifyPaiement(paiement);
    }
    
    public void deletePaiement(Integer idPaiement) throws DBException, NotIdentified {
        accessPaiement.deletePaiement(idPaiement);
    }
    
    public Float getSolde(Integer idInscription) throws DBException, NotIdentified {
        return accessPaiement.getSolde(idInscription);
    }
} 