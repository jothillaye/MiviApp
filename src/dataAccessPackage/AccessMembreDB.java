package dataAccessPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exceptionPackage.ListMembreException;
import exceptionPackage.NewMembreException;
import exceptionPackage.NotIdentified;
import java.sql.Date;
import java.util.GregorianCalendar;
import modelPackage.Membre;

public class AccessMembreDB {
	private String request;
	private PreparedStatement prepStat;
	private ResultSet data;
	
	// Ajour d'un membre
	public void newMembre(Membre membre) throws NewMembreException, NotIdentified {
		try {
			request = "insert into Membre (nom, prenom, DateNaiss) VALUES(?,?,?);";
			prepStat = AccessDB.getInstance().prepareStatement(request);			
			
			prepStat.setString(1, membre.getNom());
			prepStat.setString(2, membre.getPrenom());			
			prepStat.setDate(3,  new java.sql.Date(membre.getDateNaiss().getTimeInMillis()));	
			
			prepStat.executeUpdate();
		} 
		catch (SQLException e) {
			throw new NewMembreException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
	
	// Obtention d'une liste de membre (sur base du nom et ou prénom)
	public ArrayList<Membre> listMembre(String search) throws ListMembreException, NotIdentified {
		try {
			request = "select id, nom, prenom from Membre";	
			if(search!=null){
				request += " where nom like ? or prenom like ? order by nom";
				prepStat = AccessDB.getInstance().prepareStatement(request);				
				prepStat.setString(1, "%" + search + "%");
				prepStat.setString(2, "%" + search + "%");
			}	
			else {
                request += " order by nom";
                prepStat = AccessDB.getInstance().prepareStatement(request);
            }
					
			data = prepStat.executeQuery();
			
			ArrayList<Membre> arrayMembre = new ArrayList<Membre>();
			
			while (data.next()) { 
                Membre membre = new Membre();
                membre.setId(data.getInt(1));
                membre.setNom(data.getString(2));
                membre.setPrenom(data.getString(3));                
				arrayMembre.add(membre);	
			}
			return arrayMembre;
		} 
		catch (SQLException e) {
			throw new ListMembreException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
   
    public Membre getMembre(Integer id) throws ListMembreException, NotIdentified {
        try {
			request = "select * from Membre where id = ?";	
            prepStat = AccessDB.getInstance().prepareStatement(request);				
            prepStat.setInt(1,id);
					
			data = prepStat.executeQuery();			
			
            Membre membre = new Membre();
			
			while (data.next()) { 
                membre.setId(data.getInt(1));
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
                membre.setGsm(data.getInt(6));
                membre.setFixe(data.getInt(7));
                membre.setRue(data.getString(8));
                membre.setNumero(data.getInt(9));
                membre.setCodePostal(data.getInt(10));
                membre.setVille(data.getString(11));
                membre.setProvenance(data.getInt(12));
                membre.setIdContact(data.getInt(13));
                membre.setAssistant(data.getBoolean(14));
                membre.setAnimateur(data.getBoolean(15)); 
                membre.setClientME(data.getBoolean(16));
                membre.setEcarte(data.getBoolean(17));
                membre.setSoldeCrediteur(data.getFloat(18));
			}
			return membre;
		} 
		catch (SQLException e) {
			throw new ListMembreException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
        
    }
}