package dataAccessPackage;

import java.sql.*;

import exceptionPackage.*;

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
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");		
				connexionUnique = DriverManager.getConnection("jdbc:odbc:mividb"," ", pass);
			}
			catch(SQLException e) {
				String msg = "Mot de passe entré incorrecte.";
				if(pass.equals(""))
					msg = "Vous devez entrer un password!";
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
