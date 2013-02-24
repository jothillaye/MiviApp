package dataAccessPackage;

import exceptionPackage.*;
import java.io.FileReader;
import java.sql.*;
import org.h2.jdbcx.JdbcDataSource;
import sun.misc.IOUtils;

public class AccessDB {
	
	private static Connection connexionUnique;	

	public static Connection getInstance() throws NotIdentified {
		if(connexionUnique == null) {
			throw new NotIdentified();
		}
		else {
			return connexionUnique;
		}
	}
	
	public static Connection getInstance(String pass) throws IdentificationError {		
		if (connexionUnique == null ) {                       
            try	{
                Class.forName("org.h2.Driver");
                JdbcDataSource ds = new JdbcDataSource();
                ds.setURL("jdbc:h2:~/Documents/NetBeansProjects/MiviApp/db/mividb");
                ds.setUser("michel");
                ds.setPassword(pass);				
                connexionUnique = ds.getConnection();
			}
			catch(SQLException e) {                
				String msg = "Mot de passe entré incorrecte.";
				if(pass.equals("")) {
                    msg = "Vous devez entrer un password!";
                }
                if(e.getErrorCode() == 90020){
                    msg = "Connexion à la base de donnée déjà existante, veuillez fermer cette connexion pour continuer.";
                }
				throw new IdentificationError(msg);
			}
			catch (ClassNotFoundException e) {	
				throw new IdentificationError("Base de donnée introuvable.");
            }
		}		
		return connexionUnique;
	}
	
	public static Connection getStatus() {
		return connexionUnique;
	}
	
	public static void CloseConnection() throws DisconnectException {
		try {
			if(connexionUnique != null){
				connexionUnique.close();
				connexionUnique = null;
			}
		}
		catch (SQLException e) {
			throw new DisconnectException("Erreur lors de la fermeture de la connexion.");
		}
	}
}
