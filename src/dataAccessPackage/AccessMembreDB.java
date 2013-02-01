package dataAccessPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exceptionPackage.GetMembreException;
import exceptionPackage.NewMembreException;
import exceptionPackage.NotIdentified;
import modelPackage.Membre;

public class AccessMembreDB {
	private String request;
	private PreparedStatement prepStat;
	private ResultSet data;
	
	// Ajour d'un membre
	public void newMembre(Membre membre) throws NewMembreException, NotIdentified {
		try {
			request = "insert into Membre (Nom, Pre, DateNaiss) VALUES(?,?,?);";
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
	
	// Obtention d'une liste de membre (sur base du nom)
	public ArrayList<Membre> getMembre(String search) throws GetMembreException, NotIdentified {
		try {
            System.out.println("ok 4");
			request = "select id, nom, prenom from Membre";	
			if(search!=null){
				request += " where nom like ? or prenom like ?;";
				prepStat = AccessDB.getInstance().prepareStatement(request);				
				prepStat.setString(1, "%" + search + "%");
				prepStat.setString(2, "%" + search + "%");
			}	
			else {
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
			throw new GetMembreException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
}