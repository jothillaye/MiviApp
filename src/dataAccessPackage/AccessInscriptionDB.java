package dataAccessPackage;

import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import modelPackage.Activite;
import modelPackage.Formation;
import modelPackage.Inscription;
import modelPackage.Membre;

public class AccessInscriptionDB {
	private String request;
	private PreparedStatement prepStat;
	private ResultSet data;
	
	public void newInscription(Inscription ins) throws DBException, NotIdentified {
		try {
			request = "insert into inscription (idActivite, idMembre, tarifSpecial, abandonne, certifie, typeins) "
                    + " values (?,?,?,false, false, 0);";
			prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setInt(1, ins.getIdActivite());
            prepStat.setInt(2, ins.getIdMembre());
            prepStat.setFloat(3, ins.getTarifSpecial());
			
            prepStat.executeUpdate();    
		} 
		catch (SQLException e) {
			throw new DBException("Membre déjà inscris.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
	
	public ArrayList<Membre> listInscription(Integer idActivite, Integer typeIns) throws DBException, NotIdentified {
		try {
			request = "select me.idMembre, nom, prenom, gsm from inscription ins, membre me "
                    + " where ins.idActivite = ? and ins.idMembre = me.idMembre and ins.typeins = ? order by upper(nom);";	
			prepStat = AccessDB.getInstance().prepareStatement(request);	
            prepStat.setInt(1, idActivite);	
            prepStat.setInt(2, typeIns);
					
			data = prepStat.executeQuery();
			
			ArrayList<Membre> arrayInscription = new ArrayList<Membre>();
			
			while (data.next()) { 
                Membre me = new Membre();
                me.setIdMembre(data.getInt(1));
                me.setNom(data.getString(2));
                me.setPrenom(data.getString(3));
                me.setGsm(data.getString(4));   
				arrayInscription.add(me);	
			}
			return arrayInscription;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
   
    public Inscription getInscription(Integer idActivite, Integer idMembre) throws DBException, NotIdentified {
        try {
			request = "select ins.idActivite, ins.idMembre, tarifSpecial, abandonne, certifie, typeins from inscription ins, membre me"
                    + " where ins.idActivite = ? and ins.idMembre = ? and me.idMembre = ins.idMembre;";	
            prepStat = AccessDB.getInstance().prepareStatement(request);				
            prepStat.setInt(1,idActivite);
            prepStat.setInt(2,idMembre);
					
			data = prepStat.executeQuery();	
			
            Inscription ins = new Inscription();
			
			if(data.next()) { 
                ins.setIdActivite(data.getInt(1));
                ins.setIdMembre(data.getInt(2));  
                ins.setTarifSpecial(data.getFloat(3));
                ins.setAbandonne(data.getBoolean(4));
                ins.setCertifie(data.getBoolean(5));
                ins.setTypeIns(data.getInt(6));
            }
            
			return ins;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
        
    }

    public void modifyInscription(Inscription ins) throws DBException, NotIdentified {
        try {
            request = "update inscription set tarifSpecial = ?, abandonne = ?, certifie = ?, typeins = ?"
                    + " where idActivite = ? and idMembre = ? and typeins = ?;";
            prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setFloat(1, ins.getTarifSpecial());
            prepStat.setBoolean(2, ins.getAbandonne());
            prepStat.setBoolean(3, ins.getCertifie());
            prepStat.setInt(4, ins.getTypeIns());
            prepStat.setInt(5, ins.getIdActivite());
            prepStat.setInt(6, ins.getIdMembre());            
            prepStat.setInt(7, ins.getOldTypeIns());
            
			prepStat.executeUpdate();
        }	 
        catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }

    public void deleteInscription(Inscription ins) throws DBException, NotIdentified {
        try {
            request = "select idActivite from paiement where idActivite = ? and idMembre = ? and typeins = ?;";
            prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setInt(1, ins.getIdActivite());
            prepStat.setInt(2, ins.getIdMembre());            
            prepStat.setInt(3, ins.getTypeIns());
			data = prepStat.executeQuery();
            
            if(data.next()){
                throw new DBException("Cette inscription possède des paiements, elle est donc impossible à supprimer.");
            }
            else {
                request = "delete from inscription where idActivite = ? and idMembre = ? and typeins = ?;";
                prepStat = AccessDB.getInstance().prepareStatement(request);
                prepStat.setInt(1, ins.getIdActivite());
                prepStat.setInt(2, ins.getIdMembre());
                prepStat.setInt(3, ins.getTypeIns());
                prepStat.executeUpdate();
            }
        }	 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }
    
    public ArrayList<ArrayList<Object>> listInsMembre(Integer idMembre) throws DBException, NotIdentified {
		try {
			request = "select form.intitule, act.promotion, act.dateDeb, ins.tarifSpecial, act.prix, ins.certifie, "
                +"  case when (select count(idMembre) from paiement where paiement.idMembre = ins.idMembre and paiement.idActivite = ins.idActivite) > 0 then "
                    + "sum(paie.montant) else 0 end as paye "
                +" from inscription ins, activite act, formation form, paiement paie"
                +" where ins.idMembre = ? and ins.idActivite = act.idActivite and"
                    +" act.idFormation = form.idFormation and"
                    +" case when (select count(idMembre) from paiement where paiement.idMembre = ins.idMembre and paiement.idActivite = ins.idActivite) > 0 then "
                        +" paie.idMembre = ins.idMembre and paie.idActivite = ins.idActivite else ins.idMembre = ins.idMembre end"
                +" group by ins.idActivite order by form.intitule;";
			prepStat = AccessDB.getInstance().prepareStatement(request);	
            prepStat.setInt(1, idMembre);			
					
			data = prepStat.executeQuery();
			
			ArrayList<ArrayList<Object>> arrayInscription = new ArrayList<ArrayList<Object>>();
			
            while(data.next()) {
                ArrayList<Object> arrayObject = new ArrayList<Object>();
                
                Formation form = new Formation();
                form.setIntitule(data.getString(1));
                arrayObject.add(form);
                
                Activite act = new Activite();
                act.setPromotion(data.getInt(2));
                GregorianCalendar dateDeb = new GregorianCalendar();
                Date dateSql = data.getDate(3);
                if(dateSql != null){
                    dateDeb.setTimeInMillis(dateSql.getTime());
                    act.setDateDeb(dateDeb);
                }
                else {
                    act.setDateDeb(null);
                }                
                Float tarifSpecial = data.getFloat(4), prix = data.getFloat(5);
                if(tarifSpecial != 0) {
                    act.setPrix(tarifSpecial);
                }
                else {
                    act.setPrix(prix);
                }
                arrayObject.add(act);
                
                Inscription ins = new Inscription();
                ins.setCertifie(data.getBoolean(6));
                arrayObject.add(ins);
                
                arrayObject.add(data.getFloat(7));
                
                arrayInscription.add(arrayObject);
            }
			return arrayInscription;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}        
}
