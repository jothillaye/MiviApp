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
	private ResultSet data, dataPaiement;
	
	public void newInscription(Inscription ins) throws DBException, NotIdentified {
		try {
			request = "insert into inscription (idActivite, idMembre, tarifSpecial, abandonne, certifie, typeins) "
                    + " values (?,?,?,false, false, ?);";
			prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setInt(1, ins.getIdActivite());
            prepStat.setInt(2, ins.getIdMembre());
            prepStat.setFloat(3, ins.getTarifSpecial());
			prepStat.setInt(4, ins.getTypeIns());
            prepStat.executeUpdate();    
		} 
		catch (SQLException e) {
			throw new DBException("Erreur lors de l'insertion de l'inscription. Vérifiez que le membre n'est pas déjà inscris.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
	
	public ArrayList<Inscription> listInscription(Integer idActivite, Integer typeIns) throws DBException, NotIdentified {
		try {
			request = "select me.idMembre, nom, prenom, gsm, email, idInscription, abandonne, tarifspecial from membre me, inscription ins "
                +" where ins.idMembre = me.idMembre and ins.idActivite = ? and ins.typeins = ? "
                +" order by nom;";
                    
			prepStat = AccessDB.getInstance().prepareStatement(request);	
            prepStat.setInt(1, idActivite);	
            prepStat.setInt(2, typeIns);
					
			data = prepStat.executeQuery();
            
			
			
			ArrayList<Inscription> arrayInscription = new ArrayList<Inscription>();
			
			while (data.next()) { 
                Membre me = new Membre();
                me.setIdMembre(data.getInt(1));
                me.setNom(data.getString(2));
                me.setPrenom(data.getString(3));
                me.setGsm(data.getString(4));   
                me.setEmail(data.getString(5)); 
                
                Inscription ins = new Inscription();
                
                ins.setMembre(me);                
                ins.setIdInscription(data.getInt(6));
                ins.setAbandonne(data.getBoolean(7));
                ins.setTarifSpecial(data.getFloat(8));
                
                request = "select sum(montant) from paiement where idInscription = ? and accord = false;"; 
                prepStat = AccessDB.getInstance().prepareStatement(request);	
                prepStat.setInt(1, ins.getIdInscription());
                
                dataPaiement = prepStat.executeQuery();                
                
                if(dataPaiement.next()) {
                    ins.setSolde(dataPaiement.getFloat(1));
                }      
                else {
                    ins.setSolde(new Float(0));
                }
                
				arrayInscription.add(ins);	
			}
			return arrayInscription;
		} 
		catch (SQLException e) {
			throw new DBException("Erreur lors du listing des inscriptions.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
   
    public Inscription getInscription(Integer idInscription) throws DBException, NotIdentified {
        try {
			request = "select idActivite, idMembre, tarifSpecial, abandonne, certifie, typeins from inscription "
                    + " where idInscription = ?;";	
            prepStat = AccessDB.getInstance().prepareStatement(request);				
            prepStat.setInt(1,idInscription);
					
			data = prepStat.executeQuery();	
			
            Inscription ins = new Inscription();
			
			if(data.next()) { 
                ins.setIdInscription(idInscription);
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
			throw new DBException("Erreur lors de la récupération de l'inscription.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
        
    }

    public void modifyInscription(Inscription ins) throws DBException, NotIdentified {
        try {
            request = "update inscription set tarifSpecial = ?, abandonne = ?, certifie = ?"
                    + " where idInscription = ?;";
            prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setFloat(1, ins.getTarifSpecial());
            prepStat.setBoolean(2, ins.getAbandonne());
            prepStat.setBoolean(3, ins.getCertifie());
            prepStat.setInt(4, ins.getIdInscription());
            
			prepStat.executeUpdate();
        }	 
        catch (SQLException e) {
			throw new DBException("Erreur lors de la modification de l'inscription.");
		}
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }

    public void deleteInscription(Inscription ins) throws DBException, NotIdentified {
        try {
            request = "select idInscription from paiement where idInscription = ?;";
            prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setInt(1, ins.getIdInscription());
			data = prepStat.executeQuery();
            
            if(data.next()){
                throw new DBException("Cette inscription possède des paiements, elle est donc impossible à supprimer.");
            }
            else {
                request = "delete from inscription where idInscription = ?;";
                prepStat = AccessDB.getInstance().prepareStatement(request);
                prepStat.setInt(1, ins.getIdInscription());
                prepStat.executeUpdate();
            }
        }	 
		catch (SQLException e) {
			throw new DBException("Erreur lors de la suppression de l'inscription.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }
    
    public ArrayList<ArrayList<Object>> listInsMembre(Integer idMembre) throws DBException, NotIdentified {
		try {
			request = "select form.intitule, act.promotion, act.dateDeb, ins.tarifSpecial, act.prix, ins.idInscription, ins.certifie "
                +" from inscription ins, activite act, formation form "
                +" where ins.idMembre = ? and ins.idActivite = act.idActivite and act.idFormation = form.idFormation "
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
                ins.setIdInscription(data.getInt(6));
                ins.setCertifie(data.getBoolean(7));             
                
                request = "select sum(montant) from paiement where idInscription = ?;";
                prepStat = AccessDB.getInstance().prepareStatement(request);	
                prepStat.setInt(1, ins.getIdInscription());
                
                dataPaiement = prepStat.executeQuery();	
                
                if(dataPaiement.next()) {
                    ins.setSolde(dataPaiement.getFloat(1));
                }
                else {
                    ins.setSolde(new Float(0));
                }
                
                arrayObject.add(ins);
                arrayInscription.add(arrayObject);
            }
			return arrayInscription;
		} 
		catch (SQLException e) {
			throw new DBException("Erreur lors de la récupération de l'historique des inscriptions du client.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}        
}
