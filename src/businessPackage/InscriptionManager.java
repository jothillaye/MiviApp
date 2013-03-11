package businessPackage;

import dataAccessPackage.AccessInscriptionDB;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.util.ArrayList;
import modelPackage.Activite;
import modelPackage.Formation;
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

    public ArrayList<Membre> listInscription(Integer idActivite, Integer typeIns) throws DBException, NotIdentified {
        return accessIns.listInscription(idActivite, typeIns);
    }
    
    public Inscription getInscription(Integer idActivite, Integer idMembre) throws DBException, NotIdentified {
        return accessIns.getInscription(idActivite, idMembre);
    }
    
    public void modifyInscription(Inscription ins) throws DBException, NotIdentified {
        accessIns.modifyInscription(ins);
    }
    
    public void deleteInscription(Inscription ins) throws DBException, NotIdentified {
        accessIns.deleteInscription(ins);
    }
    
    public ArrayList<ArrayList<Object>> listInsMembre(Integer idMembre) throws DBException, NotIdentified {
        ArrayList<ArrayList<Object>> arrayIns = accessIns.listInsMembre(idMembre);
        
        if(arrayIns.isEmpty() == false) {        
            Float prixTotal = new Float(0), payeTotal = new Float(0);
            for(ArrayList<Object> array : arrayIns){
                if(((Inscription)array.get(2)).getTarifSpecial() != null && ((Inscription)array.get(2)).getTarifSpecial() != 0) {
                    prixTotal += ((Inscription)array.get(2)).getTarifSpecial();
                }
                else {
                    prixTotal += ((Activite)array.get(1)).getPrix();
                }            
                payeTotal += ((Float)array.get(3));
            }
            ArrayList<Object> newArray = new ArrayList<Object>();

            Formation form = new Formation("TOTAL");
            newArray.add(form);

            Activite act = new Activite();
            act.setPrix(prixTotal);
            act.setPromotion(0);
            newArray.add(act);

            Inscription ins = new Inscription();
            ins.setTarifSpecial(new Float(0));
            newArray.add(ins);

            newArray.add(payeTotal);        
            arrayIns.add(newArray);        
        }
        
        return arrayIns;
    }
} 