package dataAccessPackage;

import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import modelPackage.Paiement;

public class AccessPaiementDB {
	private String request;
	private PreparedStatement prepStat;
	private ResultSet data;
	
	public Integer newPaiement(Paiement paiement) throws DBException, NotIdentified {
		try {
			request = "insert into paiement (idActivite, idMembre, datePaiement, accord, montant, typePaiement) "
                    + " values (?,?,?,?,?,?);";
			prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setInt(1, paiement.getIdActivite());
            prepStat.setInt(2, paiement.getIdMembre());            
            prepStat.setDate(3, new Date(paiement.getDatePaiement().getTimeInMillis()));
            prepStat.setBoolean(4, paiement.getAccord());
            prepStat.setFloat(5, paiement.getMontant());
            prepStat.setInt(6, paiement.getTypePaiement());
			
            prepStat.executeUpdate();    
            
            request = "SELECT LAST_INSERT_ID()";
            prepStat = AccessDB.getInstance().prepareStatement(request);			
            data = prepStat.executeQuery();
            
            Integer idPaiement = -1;
            if(data.next()) {
                idPaiement = data.getInt(1);
            }
            
            return idPaiement;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
	
	public ArrayList<Paiement> listPaiement(Integer idActivite, Integer idMembre, Boolean accord) throws DBException, NotIdentified {
		try {
			request = "select montant, datePaiement, typePaiement from paiement "
                    + " where idActivite = ? and idMembre = ? and accord = ? order by datePaiement;";	
			prepStat = AccessDB.getInstance().prepareStatement(request);	
            prepStat.setInt(1, idActivite);	
            prepStat.setInt(2, idMembre);	
            prepStat.setBoolean(3, accord);		
					
			data = prepStat.executeQuery();
			
			ArrayList<Paiement> arrayPaiement = new ArrayList<Paiement>();
			
			while (data.next()) { 
                Paiement paiement = new Paiement();
                paiement.setMontant(data.getFloat(1));
                GregorianCalendar datePaiement = new GregorianCalendar();
                datePaiement.setTimeInMillis(data.getDate(2).getTime());
                paiement.setDatePaiement(datePaiement);
                paiement.setOldDate(datePaiement);
                paiement.setTypePaiement(data.getInt(3));
				arrayPaiement.add(paiement);	
			}
			return arrayPaiement;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}

    public void modifyPaiement(Paiement paiement, GregorianCalendar oldDate) throws DBException, NotIdentified {
        try {
            if(paiement.getDatePaiement() != null) {
                request = "update paiement set datePaiement = ?"
                    + " where idActivite = ? and idMembre = ? and datePaiement = ? and accord = ?;";                
                prepStat = AccessDB.getInstance().prepareStatement(request);            
                prepStat.setDate(1, new Date(paiement.getDatePaiement().getTimeInMillis()));
            }
            else if(paiement.getMontant() != null) {
                request = "update paiement set montant = ?"
                    + " where idActivite = ? and idMembre = ? and datePaiement = ? and accord = ?;";                
                prepStat = AccessDB.getInstance().prepareStatement(request);            
                prepStat.setFloat(1, paiement.getMontant());                
            }
            else if(paiement.getTypePaiement() != null) {
                request = "update paiement set typePaiement = ?"
                    + " where idActivite = ? and idMembre = ? and datePaiement = ? and accord = ?;";                
                prepStat = AccessDB.getInstance().prepareStatement(request);            
                prepStat.setInt(1, paiement.getTypePaiement());
            }                        
            prepStat.setInt(2, paiement.getIdActivite());
            prepStat.setInt(3, paiement.getIdMembre());
            prepStat.setDate(4, new Date(oldDate.getTimeInMillis()));                
            prepStat.setBoolean(5, paiement.getAccord());
			prepStat.executeUpdate();
        }	 
        catch (SQLException e) {
			throw new DBException("Erreur lors de la modification du paiement.\nAttention aux doublons dans les dates.");
		}
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }

    public void deletePaiement(Paiement paiement) throws DBException, NotIdentified {
        try {
            request = "delete from paiement where idActivite = ? and idMembre = ? and datePaiement = ? and accord = ?;";
            prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setInt(1, paiement.getIdActivite());
            prepStat.setInt(2, paiement.getIdMembre());
            prepStat.setDate(3, new Date(paiement.getDatePaiement().getTimeInMillis()));
            prepStat.setBoolean(4, paiement.getAccord());
            prepStat.executeUpdate();            
        }	 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }
    
    public Float getSolde(Integer idActivite, Integer idMembre) throws DBException, NotIdentified {
		try {
            if(idActivite != null){
                request = "select montant from paiement "
                    + " where accord = false and idActivite = ? and idMembre = ?;";	
                prepStat = AccessDB.getInstance().prepareStatement(request);	
                prepStat.setInt(1, idActivite);	
                prepStat.setInt(2, idMembre);	
            }
            else {
                request = "select montant from paiement "
                        + " where accord = false and idMembre = ?;";	
                prepStat = AccessDB.getInstance().prepareStatement(request);	
                prepStat.setInt(1, idMembre);	            
            }
            
			data = prepStat.executeQuery();
			
			Float solde = new Float(0);			
			while (data.next()) { 
                solde += data.getFloat(1);
			}
			return solde;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
        
}