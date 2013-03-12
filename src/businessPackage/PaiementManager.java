package businessPackage;

import dataAccessPackage.AccessPaiementDB;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import modelPackage.Paiement;

public class PaiementManager {
    private AccessPaiementDB accessPaiement = new AccessPaiementDB();
    
    public void newPaiement(Paiement paiement) throws DBException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(paiement.getIdActivite()  == null || paiement.getIdMembre() == null) {
            throw new DBException("Il est obligatoire de choisir un membre et une activité");
        }	
		else {
            accessPaiement.newPaiement(paiement);
        }
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
    
    public Float getSolde(Integer idActivite, Integer idMembre) throws DBException, NotIdentified {
        return accessPaiement.getSolde(idActivite, idMembre);
    }
} 