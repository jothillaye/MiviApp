package dataAccessPackage;

import exceptionPackage.*;
import java.sql.*;
import java.util.Properties;

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
            String dbUrl = "jdbc:odbc:mividb";
			Properties props = new Properties();
            props.put ("charSet", "ISO-8859-15");
            props.put("user", " ");
            props.put("password", pass);
            
            try	{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");		
				connexionUnique = DriverManager.getConnection(dbUrl, props);
			}
			catch(SQLException e) {
                
				String msg = "Mot de passe entré incorrecte.";
				if(pass.equals(""))
					msg = "Vous devez entrer un password!";
				throw new IdentificationError(e.getMessage());
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
