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
			request = "insert into paiement (idInscription, datePaiement, accord, montant, typePaiement) "
                    + " values (?,Now(),?,0,0);";
			prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setInt(1, paiement.getIdInscription());           
            prepStat.setBoolean(2, paiement.getAccord());
			
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
			throw new DBException("Erreur lors de l'insertion d'un nouveau paiement.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
	
	public ArrayList<Paiement> listPaiement(Paiement paiement) throws DBException, NotIdentified {
		try {
			request = "select idpaiement, montant, datePaiement, typePaiement from paiement "
                    + " where idInscription = ? and accord = ? order by datePaiement;";	
			prepStat = AccessDB.getInstance().prepareStatement(request);	
            prepStat.setInt(1, paiement.getIdInscription());
            prepStat.setBoolean(2, paiement.getAccord());	
					
			data = prepStat.executeQuery();
			
			ArrayList<Paiement> arrayPaiement = new ArrayList<Paiement>();
			
			while (data.next()) { 
                Paiement newPaiement = new Paiement();
                newPaiement.setIdPaiement(data.getInt(1));
                newPaiement.setMontant(data.getFloat(2));
                GregorianCalendar datePaiement = new GregorianCalendar();
                datePaiement.setTimeInMillis(data.getDate(3).getTime());
                newPaiement.setDatePaiement(datePaiement);
                newPaiement.setOldDate(datePaiement);
                newPaiement.setTypePaiement(data.getInt(4));
				arrayPaiement.add(newPaiement);	
			}
			return arrayPaiement;
		} 
		catch (SQLException e) {
			throw new DBException("Erreur lors du listing des paiements.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}

    public void modifyPaiement(Paiement paiement) throws DBException, NotIdentified {
        try {
            if(paiement.getDatePaiement() != null) {
                request = "update paiement set datePaiement = ? where idPaiement = ?;";                
                prepStat = AccessDB.getInstance().prepareStatement(request);            
                prepStat.setDate(1, new Date(paiement.getDatePaiement().getTimeInMillis()));
            }
            else if(paiement.getMontant() != null) {
                request = "update paiement set montant = ? where idPaiement = ?;";                
                prepStat = AccessDB.getInstance().prepareStatement(request);            
                prepStat.setFloat(1, paiement.getMontant());                
            }
            else if(paiement.getTypePaiement() != null) {
                request = "update paiement set typePaiement = ? where idPaiement = ?;";                
                prepStat = AccessDB.getInstance().prepareStatement(request);            
                prepStat.setInt(1, paiement.getTypePaiement());
            }                        
            prepStat.setInt(2, paiement.getIdPaiement());
			prepStat.executeUpdate();
        }	 
        catch (SQLException e) {
			throw new DBException("Erreur lors de la modification du paiement.");
		}
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }

    public void deletePaiement(Integer idPaiement) throws DBException, NotIdentified {
        try {
            request = "delete from paiement where idPaiement = ?;";
            prepStat = AccessDB.getInstance().prepareStatement(request);
            prepStat.setInt(1, idPaiement);
            prepStat.executeUpdate();            
        }	 
		catch (SQLException e) {
			throw new DBException("Erreur lors de la suppression du paiement.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
    }
    
    public Float getSolde(Integer idInscription) throws DBException, NotIdentified {
		try {
            request = "select montant from paiement "
                + " where idInscription = ? and accord = false;";	
            prepStat = AccessDB.getInstance().prepareStatement(request);	
            prepStat.setInt(1, idInscription);
            
			data = prepStat.executeQuery();
			
			Float solde = new Float(0);			
			while (data.next()) { 
                solde += data.getFloat(1);
			}
			return solde;
		} 
		catch (SQLException e) {
			throw new DBException("Erreur lors de la récupération du solde.");
		}	 
		catch (NotIdentified e) {
			throw new NotIdentified();
		}
	}
        
}