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

public class AccessActiviteDB {
	private String request;
	private PreparedStatement prepStat;
	private ResultSet data;
	
	// Ajour d'un act
	public Integer newActivite(Activite act) throws DBException, NotIdentified {
		try {
			request = "insert into activite (idFormation, promotion, dateDeb, dateFin, prix, accompte, tva) "
                    + " values(?, ?, ?, ?, ?, ?, ?);";
			prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setInt(1, act.getIdFormation());
            prepStat.setInt(2, act.getPromotion());
            if(act.getDateDeb() != null) {
                prepStat.setDate(3, new java.sql.Date(act.getDateDeb().getTimeInMillis()));
            }
            else {
                prepStat.setDate(3, null);
            }
            if(act.getDateFin() != null) {
                prepStat.setDate(4, new java.sql.Date(act.getDateFin().getTimeInMillis()));
            }
            else {
                prepStat.setDate(4, null);
            }
            prepStat.setFloat(5, act.getPrix());
            prepStat.setFloat(6, act.getAccompte());
            prepStat.setFloat(7, act.getTva());
			prepStat.executeUpdate();
            
            request = "SELECT LAST_INSERT_ID()";
            prepStat = AccessDB.getInstance().prepareStatement(request);			
            data = prepStat.executeQuery();
            
            Integer idActivite = -1;
            if(data.next() == true){
                idActivite = data.getInt(1);
            }
            
            return idActivite;
		} 
		catch (SQLException e) {
			throw new DBException("Erreur lors de l'insertion de l'activité.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
	
	public Activite getActivite(Integer idActivite) throws DBException, NotIdentified {
		Date dateSQL;
        GregorianCalendar dateDeb, dateFin;
        
        try {
			request = "select * from activite where idActivite = ?";	
            prepStat = AccessDB.getInstance().prepareStatement(request);					
            prepStat.setInt(1, idActivite);
			data = prepStat.executeQuery();
			
			Activite activite = new Activite();
			
			if(data.next()) {                 
                activite.setIdActivite(data.getInt(1));
                activite.setIdFormation(data.getInt(2));
                activite.setPromotion(data.getInt(3));
                dateDeb = new GregorianCalendar();
                dateSQL = data.getDate(4);
                if(dateSQL != null) {
                    dateDeb.setTimeInMillis(dateSQL.getTime());
                }
                else {
                    dateDeb = null;
                }
                activite.setDateDeb(dateDeb);
                dateSQL = data.getDate(5);
                dateFin = new GregorianCalendar();
                if(dateSQL != null) {
                    dateFin.setTimeInMillis(dateSQL.getTime());
                }
                else {
                    dateFin = null;
                }
                activite.setDateFin(dateFin);
                activite.setPrix(data.getFloat(6));
                activite.setAccompte(data.getFloat(7));
                activite.setTva(data.getFloat(8));
            }
			return activite;
		} 
		catch (SQLException e) {
			throw new DBException("Erreur lors de la récupération de l'activité.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
    
    public ArrayList<Activite> listActivite(Integer idFormation) throws DBException, NotIdentified {
        Date dateSQL;
        GregorianCalendar dateDeb;
        
        try {
			request = "select idActivite, idFormation, promotion, dateDeb, prix from activite where idFormation = ?";
            prepStat = AccessDB.getInstance().prepareStatement(request);					
            prepStat.setInt(1, idFormation);
			data = prepStat.executeQuery();
			
			ArrayList<Activite> arrayActivite = new ArrayList<Activite>();
			
			while (data.next()) { 
                Activite act = new Activite();
                act.setIdActivite(data.getInt(1));
                act.setIdFormation(data.getInt(2));
                act.setPromotion(data.getInt(3));
                
                dateDeb = new GregorianCalendar();
                dateSQL = data.getDate(4);
                if(dateSQL != null) {
                    dateDeb.setTimeInMillis(dateSQL.getTime());
                }
                else {
                    dateDeb = null;
                }
                act.setDateDeb(dateDeb);
                act.setPrix(data.getFloat(5));
				arrayActivite.add(act);
            }
			return arrayActivite;
		} 
		catch (SQLException e) {
			throw new DBException("Erreur lors du listing des activités.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}        
    }

    public void modifyActivite(Activite act) throws DBException, NotIdentified {
        try {
            request = "update activite set promotion = ?, dateDeb = ?, dateFin = ?, prix = ?, accompte = ?, tva = ? "
                    + " where idActivite = ?;";
			prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setInt(1, act.getPromotion());            
            if(act.getDateDeb() != null) {
                prepStat.setDate(2, new java.sql.Date(act.getDateDeb().getTimeInMillis()));
            }
            else {
                prepStat.setDate(2, null);
            }
            if(act.getDateFin() != null) {
                prepStat.setDate(3, new java.sql.Date(act.getDateFin().getTimeInMillis()));
            }
            else {
                prepStat.setDate(3, null);
            }
            prepStat.setFloat(4, act.getPrix());
            prepStat.setFloat(5, act.getAccompte());
            prepStat.setFloat(6, act.getTva());
            prepStat.setInt(7, act.getIdActivite());
			prepStat.executeUpdate();
        }	 
        catch (SQLException e) {
			throw new DBException("Erreur lors de la modification de l'activité.");
		}
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }
    
    public void deleteActivite(Integer idActivite) throws DBException, NotIdentified {
        try {
            request = "select idMembre from inscription where idActivite = ?";
            prepStat = AccessDB.getInstance().prepareStatement(request);
			prepStat.setInt(1, idActivite);
			data = prepStat.executeQuery();
            
            if(data.next()){
                throw new DBException("Cette activite possède des inscriptions, elle est donc impossible à supprimer.");
            }
            else {
                request = "delete from activite where idActivite = ?";
                prepStat = AccessDB.getInstance().prepareStatement(request);
                prepStat.setInt(1, idActivite);
                prepStat.executeUpdate();
            }
        }	 
        catch (SQLException e) {
			throw new DBException("Erreur lors de la suppression de l'activité.");
		}
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }
    
    public ArrayList<Object[]> listFuturAct() throws DBException, NotIdentified {
        Date dateSQL;
        GregorianCalendar dateDeb;
        
        try {
			request = "select idActivite, idFormation, promotion, dateDeb, intitule from activite act, formation form where form.idFormation = act.idFormation and datedeb > now() limit 5";
            prepStat = AccessDB.getInstance().prepareStatement(request);	
			data = prepStat.executeQuery();
			
			ArrayList<Object[]> arrayActivite = new ArrayList<Object[]>();
			
			while (data.next()) { 
                
                Activite act = new Activite();
                act.setIdActivite(data.getInt(1));
                act.setIdFormation(data.getInt(2));
                act.setPromotion(data.getInt(3));
                
                dateDeb = new GregorianCalendar();
                dateSQL = data.getDate(4);
                if(dateSQL != null) {
                    dateDeb.setTimeInMillis(dateSQL.getTime());
                }
                else {
                    dateDeb = null;
                }
                act.setDateDeb(dateDeb);
                                
                Formation form = new Formation(data.getString(5));
				
                Object[] tabAct = {act, form};
                arrayActivite.add(tabAct);
            }
			return arrayActivite;
		} 
		catch (SQLException e) {
			throw new DBException("Erreur lors du listing des activités.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}        
    }

    
}