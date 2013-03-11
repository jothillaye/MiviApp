package dataAccessPackage;

import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import modelPackage.Membre;

public class AccessMembreDB {
	private String request;
	private PreparedStatement prepStat;
	private ResultSet data;
	
	// Ajour d'un membre
	public Integer newMembre(Membre membre) throws DBException, NotIdentified {
		try {
			request = "insert into membre (nom, prenom, email, dateNaiss, gsm, fixe, rue, numero, codePostal, ville, pays, provenance, idContact, supprime) "
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,false);";
			prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setString(1, membre.getNom());
			prepStat.setString(2, membre.getPrenom());			
			prepStat.setString(3, membre.getEmail());
            prepStat.setDate(4,  new java.sql.Date(membre.getDateNaiss().getTimeInMillis()));
            prepStat.setString(5, membre.getGsm());
            prepStat.setString(6, membre.getFixe());
            prepStat.setString(7, membre.getRue());
            prepStat.setString(8, membre.getNumero());
            prepStat.setInt(9, membre.getCodePostal());
            prepStat.setString(10, membre.getVille());
            prepStat.setString(11, membre.getPays());
            prepStat.setInt(12, membre.getProvenance());
            prepStat.setInt(13, membre.getIdContact());
			
            prepStat.executeUpdate();    
            
            request = "SELECT LAST_INSERT_ID()";
            prepStat = AccessDB.getInstance().prepareStatement(request);			
            data = prepStat.executeQuery();
            
            Integer idMembre = -1;
            if(data.next()) {
                idMembre = data.getInt(1);
            }
            
            return idMembre;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
	
	// Obtention d'une liste de membre (sur base du nom et/ou prenom)
	public ArrayList<Membre> listMembre(String nom, String prenom) throws DBException, NotIdentified {
		try {
			request = "select idMembre, nom, prenom from membre ";	
			if(nom != null && prenom != null){
				request += " where supprime <> true and (upper(nom) like ? or upper(prenom) like ?) order by upper(nom)";
				prepStat = AccessDB.getInstance().prepareStatement(request);		
				prepStat.setString(1, "%" + nom.toUpperCase() + "%");
				prepStat.setString(2, "%" + prenom.toUpperCase() + "%");
			}	
            else if(nom != null){
				request += " where supprime <> true and upper(nom) like ? order by upper(nom)";
				prepStat = AccessDB.getInstance().prepareStatement(request);		
				prepStat.setString(1, "%" + nom.toUpperCase() + "%");            
            }
			else {
                request += " where supprime <> true order by upper(nom)";
                prepStat = AccessDB.getInstance().prepareStatement(request);
            }
					
			data = prepStat.executeQuery();
			
			ArrayList<Membre> arrayMembre = new ArrayList<Membre>();
			
			while (data.next()) { 
                Membre membre = new Membre();
                membre.setIdMembre(data.getInt(1));
                membre.setNom(data.getString(2));
                membre.setPrenom(data.getString(3));                
				arrayMembre.add(membre);	
			}
			return arrayMembre;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
   
    public Membre getMembre(Integer idMembre) throws DBException, NotIdentified {
        try {
			request = "select * from membre where idMembre = ?";	
            prepStat = AccessDB.getInstance().prepareStatement(request);				
            prepStat.setInt(1,idMembre);
					
			data = prepStat.executeQuery();	
			
            Membre membre = new Membre();
			
			while (data.next()) { 
                membre.setIdMembre(data.getInt(1));
                membre.setNom(data.getString(2));
                membre.setPrenom(data.getString(3));  
                membre.setEmail(data.getString(4));
                GregorianCalendar dateNaiss = new GregorianCalendar();
                Date dateSQL = data.getDate(5);
                if(dateSQL != null) {
                    dateNaiss.setTimeInMillis(dateSQL.getTime());
                }
                else {
                    dateNaiss.set(1900,1,1);
                }
                membre.setDateNaiss(dateNaiss);
                membre.setGsm(data.getString(6));
                membre.setFixe(data.getString(7));
                membre.setRue(data.getString(8));
                membre.setNumero(data.getString(9));
                membre.setCodePostal(data.getInt(10));
                membre.setVille(data.getString(11));
                membre.setPays(data.getString(12));
                membre.setProvenance(data.getInt(13));
                membre.setIdContact(data.getInt(14));
                membre.setSupprime(data.getBoolean(15));
			}
			return membre;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
        
    }

    public void modifyMembre(Membre membre) throws DBException, NotIdentified {
        try {
            request = "update membre set nom = ?, prenom = ?, email = ?, dateNaiss = ?, gsm = ?, fixe = ?, rue = ?, numero = ?, codePostal = ?, ville = ?, pays = ?, provenance = ?, idContact = ?"
                    + " where idMembre = ?;";
            prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setString(1, membre.getNom());
			prepStat.setString(2, membre.getPrenom());			
			prepStat.setString(3, membre.getEmail());
            prepStat.setDate(4,  new java.sql.Date(membre.getDateNaiss().getTimeInMillis()));
            prepStat.setString(5, membre.getGsm());
            prepStat.setString(6, membre.getFixe());
            prepStat.setString(7, membre.getRue());
            prepStat.setString(8, membre.getNumero());
            prepStat.setInt(9, membre.getCodePostal());
            prepStat.setString(10, membre.getVille());
            prepStat.setString(11, membre.getPays());
            prepStat.setInt(12, membre.getProvenance());
            prepStat.setInt(13, membre.getIdContact());
            prepStat.setInt(14, membre.getIdMembre());
            
			prepStat.executeUpdate();
        }	 
        catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }

    public void deleteMembre(Integer idMembre) throws DBException, NotIdentified {
        try {
			request = "update membre set supprime = true where idMembre = ?";	
            prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setInt(1, idMembre);
            
            prepStat.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }
        
}