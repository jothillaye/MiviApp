package dataAccessPackage;

import exceptionPackage.ListFormationException;
import exceptionPackage.ModifyFormationException;
import exceptionPackage.NewFormationException;
import exceptionPackage.NotIdentified;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelPackage.Formation;

public class AccessFormationDB {
	private String request;
	private PreparedStatement prepStat;
	private ResultSet data;
	
	// Ajour d'un form
	public void newFormation(Formation form) throws NewFormationException, NotIdentified {
		try {
			request = "insert into Formation (String intitule) VALUES(?);";
			prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setString(1, form.getIntitule());
			prepStat.executeUpdate();
		} 
		catch (SQLException e) {
			throw new NewFormationException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
	
	// Obtention d'une liste de formation
	public ArrayList<Formation> listFormation() throws ListFormationException, NotIdentified {
		try {
			request = "select id, intitule Formation";	
            prepStat = AccessDB.getInstance().prepareStatement(request);					
			data = prepStat.executeQuery();
			
			ArrayList<Formation> arrayFormation = new ArrayList<Formation>();
			
			while (data.next()) { 
                Formation form = new Formation();
                form.setId(data.getInt(1));
                form.setIntitule(data.getString(2));
				arrayFormation.add(form);
            }
			return arrayFormation;
		} 
		catch (SQLException e) {
			throw new ListFormationException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}

    public void modifyFormation(Formation form) throws ModifyFormationException, NotIdentified {
        try {
            request = "UPDATE Formation SET intitule = ?"
                    + " WHERE id = ?;";
            prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setInt(1, form.getId());
            prepStat.setString(2, form.getIntitule());
			prepStat.executeUpdate();
        }	 
        catch (SQLException e) {
			throw new ModifyFormationException(e.getMessage());
		}
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }        
}