package businessPackage;

import dataAccessPackage.AccessInscriptionDB;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.util.ArrayList;
import modelPackage.Inscription;
import modelPackage.Membre;

public class InscriptionManager {
    private AccessInscriptionDB accessIns = new AccessInscriptionDB();
    
    public void newInscription(Inscription ins) throws DBException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(ins.getIdActivite()  == null || ins.getIdMembre() == null) {
            throw new DBException("Il est obligatoire de choisir un membre et une activité");
        }	
		else {
            ins.setAbandonne(false);
            ins.setCertifie(false);
            ins.setTarifSpecial(new Float(0));
            accessIns.newInscription(ins);
        }
    }

    public ArrayList<Membre> listInscription(Integer idActivite) throws DBException, NotIdentified {
        return accessIns.listInscription(idActivite);
    }
    
    public Inscription getInscription(Integer idActivite, Integer idMembre) throws DBException, NotIdentified {
        return accessIns.getInscription(idActivite, idMembre);
    }
    
    public void modifyInscription(Inscription ins) throws DBException, NotIdentified {
        accessIns.modifyInscription(ins);
    }
    
    public void deleteInscription(Integer idActivite, Integer idMembre) throws DBException, NotIdentified {
        accessIns.deleteInscription(idActivite, idMembre);
    }
    
    public ArrayList<String> listInsMembre(Integer idMembre) throws DBException, NotIdentified {
        return accessIns.listInsMembre(idMembre);
    }
} 