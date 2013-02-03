package dataAccessPackage;

import exceptionPackage.DeleteMembreException;
import exceptionPackage.ListMembreException;
import exceptionPackage.ModifyMembreException;
import exceptionPackage.NewMembreException;
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
	public void newMembre(Membre membre) throws NewMembreException, NotIdentified {
		try {
			request = "insert into Membre (String nom, String prenom, String email, GregorianCalendar dateNaiss, Integer gsm, Integer fixe, String rue, String numero, Integer codePostal, String ville, Integer provenance, Integer idContact, Boolean assistant, Boolean animateur, Boolean clientME, Boolean ecarte, Float soldeCrediteur) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setString(1, membre.getNom());
			prepStat.setString(2, membre.getPrenom());			
			prepStat.setString(3, membre.getEmail());
            prepStat.setDate(4,  new java.sql.Date(membre.getDateNaiss().getTimeInMillis()));
            prepStat.setInt(5, membre.getGsm());
            prepStat.setInt(6, membre.getFixe());
            prepStat.setString(7, membre.getRue());
            prepStat.setString(8, membre.getNumero());
            prepStat.setInt(9, membre.getCodePostal());
            prepStat.setString(10, membre.getVille());
            prepStat.setInt(11, membre.getProvenance());
            prepStat.setInt(12, membre.getIdContact());
            prepStat.setBoolean(13, membre.getAssistant());
            prepStat.setBoolean(14, membre.getAnimateur());
            prepStat.setBoolean(15, membre.getClientME());
            prepStat.setBoolean(16, membre.getEcarte());
            prepStat.setFloat(17, membre.getSoldeCrediteur());
			prepStat.executeUpdate();
		} 
		catch (SQLException e) {
			throw new NewMembreException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
	
	// Obtention d'une liste de membre (sur base du nom et/ou prenom)
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
                membre.setNumero(data.getString(9));
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

    public void modifyMembre(Membre membre) throws ModifyMembreException, NotIdentified {
        try {
            request = "UPDATE Membre SET nom = ?, prenom = ?, email = ?, dateNaiss = ?, gsm = ?, fixe = ?, rue = ?, numero = ?, codePostal = ?, ville = ?, provenance = ?, idContact = ?, assistant = ?, animateur = ?, clientME = ?, ecarte = ?, soldeCrediteur = ?"
                    + " WHERE id = ?;";
            prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setString(1, membre.getNom());
			prepStat.setString(2, membre.getPrenom());			
			prepStat.setString(3, membre.getEmail());
            prepStat.setDate(4,  new java.sql.Date(membre.getDateNaiss().getTimeInMillis()));
            prepStat.setInt(5, membre.getGsm());
            prepStat.setInt(6, membre.getFixe());
            prepStat.setString(7, membre.getRue());
            prepStat.setString(8, membre.getNumero());
            prepStat.setInt(9, membre.getCodePostal());
            prepStat.setString(10, membre.getVille());
            prepStat.setInt(11, membre.getProvenance());
            prepStat.setInt(12, membre.getIdContact());
            prepStat.setBoolean(13, membre.getAssistant());
            prepStat.setBoolean(14, membre.getAnimateur());
            prepStat.setBoolean(15, membre.getClientME());
            prepStat.setBoolean(16, membre.getEcarte());
            prepStat.setFloat(17, membre.getSoldeCrediteur());
            prepStat.setInt(18, membre.getId());
			prepStat.executeUpdate();
        }	 
        catch (SQLException e) {
			throw new ModifyMembreException(e.getMessage());
		}
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }

    public void deleteMembre(Integer idMembre) throws DeleteMembreException, NotIdentified {
        try {
			request = "Delete from Membre Where id = ?";	
            prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setInt(1, idMembre);
            prepStat.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DeleteMembreException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }
        
}