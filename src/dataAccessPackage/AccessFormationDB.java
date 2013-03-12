package dataAccessPackage;

import exceptionPackage.DBException;
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
	public void newFormation(Formation form) throws DBException, NotIdentified {
		try {
			request = "insert into formation (intitule) values(?);";
			prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setString(1, form.getIntitule());
			prepStat.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DBException("Erreur lors de l'insertion de la formation.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
	
	public ArrayList<Formation> listFormation() throws DBException, NotIdentified {
		try {
			request = "select form.idFormation, form.intitule, (select count(act.idActivite) from activite act where act.idFormation = form.idFormation) as countIns from formation form order by countIns desc;";	
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
			throw new DBException("Erreur lors du listing des formations.");
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
			throw new DBException("Erreur lors de la modification de la formation.");
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
			throw new DBException("Erreur lors de la suppression de la formation.");
		}
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }
}