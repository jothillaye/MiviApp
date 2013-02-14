package dataAccessPackage;

import exceptionPackage.NotIdentified;
import exceptionPackage.DBException;
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
	public void newFormation(Formation form) throws DBException, NotIdentified {
		try {
			request = "insert into formation (intitule) values(?);";
			prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setString(1, form.getIntitule());
			prepStat.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
	
	public ArrayList<Formation> listFormation() throws DBException, NotIdentified {
		try {
			request = "select idFormation, intitule from formation";	
            prepStat = AccessDB.getInstance().prepareStatement(request);					
			data = prepStat.executeQuery();
			
			ArrayList<Formation> arrayFormation = new ArrayList<Formation>();
			
			while (data.next()) { 
                Formation form = new Formation();
                form.setIdFormation(data.getInt(1));
                form.setIntitule(data.getString(2));
				arrayFormation.add(form);
            }
			return arrayFormation;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}

    public void modifyFormation(Formation form) throws DBException, NotIdentified {
        try {
            request = "update formation set intitule = ?"
                    + " where idFormation = ?;";
            prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setString(1, form.getIntitule());
            prepStat.setInt(2, form.getIdFormation());
			prepStat.executeUpdate();
        }	 
        catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }
    
    public void deleteFormation(Integer idFormation) throws DBException, NotIdentified {
        try {
            request = "select idActivite from activite where idFormation = ?";
            prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setInt(1, idFormation);
			data = prepStat.executeQuery();
            
            if(data.next()){
                throw new DBException("Cette formation possède des dates, elle est donc impossible à supprimer.");
            }
            else {
                request = "delete from formation where idFormation = ?";
                prepStat = AccessDB.getInstance().prepareStatement(request);
                prepStat.setInt(1, idFormation);
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