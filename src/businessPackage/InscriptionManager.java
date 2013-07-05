package businessPackage;

import dataAccessPackage.AccessInscriptionDB;
import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.util.ArrayList;
import modelPackage.Activite;
import modelPackage.Formation;
import modelPackage.Inscription;

public class InscriptionManager {
    private AccessInscriptionDB accessIns = new AccessInscriptionDB();
    
    public Integer newInscription(Inscription ins) throws DBException, NotIdentified {
        // TODO : complèter les champs obligatoires
        if(ins.getIdActivite()  == null || ins.getIdMembre() == null) {
            throw new DBException("Il est obligatoire de choisir un membre et une activité");
        }	
		else {
            ins.setCertifie(false);
            ins.setTarifSpecial(new Float(0));
            return accessIns.newInscription(ins);
        }
    }

    public ArrayList<Inscription> listInscription(Integer idActivite, Integer typeIns) throws DBException, NotIdentified {
        return accessIns.listInscription(idActivite, typeIns);
    }
    
    public Inscription getInscription(Integer idInscription) throws DBException, NotIdentified {
        return accessIns.getInscription(idInscription);
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
                Activite act = (Activite) array.get(1);
                Inscription ins = (Inscription) array.get(2);
                
                if(ins.getTarifSpecial() != null && ins.getTarifSpecial() != 0) {
                    prixTotal += ins.getTarifSpecial();
                }
                else {
                    prixTotal += act.getPrix();
                }            
                payeTotal += ins.getSolde();
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
            ins.setSolde(payeTotal);
            newArray.add(ins);
      
            arrayIns.add(newArray);        
        }
        
        return arrayIns;
    }
} 