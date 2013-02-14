package dataAccessPackage;

import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelPackage.Inscription;
import modelPackage.Membre;

public class AccessInscriptionDB {
	private String request;
	private PreparedStatement prepStat;
	private ResultSet data;
	
	public Integer newInscription(Inscription inscription) throws DBException, NotIdentified {
		try {
			request = "insert into inscription (idActivite, idMembre, tarifSpecial, abandonne, certifie) "
                    + " values (?,?,?,?,?);";
			prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setInt(1, inscription.getIdActivite());
            prepStat.setInt(2, inscription.getIdMembre());
            prepStat.setFloat(3, inscription.getTarifSpecial());
            prepStat.setBoolean(4, inscription.getAbandonne());
            prepStat.setBoolean(5, inscription.getCertifie());
			
            prepStat.executeUpdate();    
            
            request = "SELECT @@IDENTITY";
            prepStat = AccessDB.getInstance().prepareStatement(request);			
            data = prepStat.executeQuery();
            
            Integer idInscription = -1;
            if(data.next()) {
                idInscription = data.getInt(1);
            }
            
            return idInscription;
		} 
		catch (SQLException e) {
			throw new DBException("Membre déjà inscris.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
	
	public ArrayList<Membre> listInscription(Integer idActivite) throws DBException, NotIdentified {
		try {
			request = "select me.idMembre, nom, prenom from inscription ins, membre me "
                    + " where ins.idActivite = ? and ins.idMembre = me.idMembre;";	
			prepStat = AccessDB.getInstance().prepareStatement(request);	
            prepStat.setInt(1, idActivite);			
					
			data = prepStat.executeQuery();
			
			ArrayList<Membre> arrayInscription = new ArrayList<Membre>();
			
			while (data.next()) { 
                Membre membre = new Membre();
                membre.setIdMembre(data.getInt(1));
                membre.setNom(data.getString(2));
                membre.setPrenom(data.getString(3));
				arrayInscription.add(membre);	
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
			request = "select ins.idActivite, ins.idMembre, tarifSpecial, abandonne, certifie from inscription ins, membre me"
                    + " where ins.idActivite = ? and ins.idMembre = ? and me.idMembre = ins.idMembre;";	
            prepStat = AccessDB.getInstance().prepareStatement(request);				
            prepStat.setInt(1,idActivite);
            prepStat.setInt(2,idMembre);
					
			data = prepStat.executeQuery();	
			
            Inscription inscription = new Inscription();
			
			if(data.next()) { 
                inscription.setIdActivite(data.getInt(1));
                inscription.setIdMembre(data.getInt(2));  
                inscription.setTarifSpecial(data.getFloat(3));
                inscription.setAbandonne(data.getBoolean(4));
                inscription.setCertifie(data.getBoolean(5));
            }
            
			return inscription;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
        
    }

    public void modifyInscription(Inscription inscription) throws DBException, NotIdentified {
        try {
            request = "update inscription set idActivite = ?, idMembre = ?, tarifSpecial = ?, abandonne = ?, certifie = ?"
                    + " where idInscription = ?;";
            prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setInt(1, inscription.getIdActivite());
            prepStat.setInt(2, inscription.getIdMembre());
            prepStat.setFloat(3, inscription.getTarifSpecial());
            prepStat.setBoolean(4, inscription.getAbandonne());
            prepStat.setBoolean(5, inscription.getCertifie());
            
			prepStat.executeUpdate();
        }	 
        catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }

    public void deleteInscription(Integer idActivite, Integer idMembre) throws DBException, NotIdentified {
        try {
            request = "select idActivite from paiement where idActivite = ? and idMembre = ?;";
            prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setInt(1, idActivite);
            prepStat.setInt(2, idMembre);
			data = prepStat.executeQuery();
            
            if(data.next()){
                throw new DBException("Cette inscription possède des paiements, elle est donc impossible à supprimer.");
            }
            else {
                request = "delete from inscription where idActivite = ? and idMembre = ?;";
                prepStat = AccessDB.getInstance().prepareStatement(request);
                prepStat.setInt(1, idActivite);
                prepStat.setInt(2, idMembre);
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
        
}