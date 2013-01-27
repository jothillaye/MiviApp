package businessPackage;

import dataAccessPackage.AccessDB;
import exceptionPackage.DisconnectException;
import exceptionPackage.IdentificationError;
import exceptionPackage.NotIdentified;

public class LoginManager {
	public void Connection() throws NotIdentified {
		AccessDB.getInstance();
	}
	
	public void Connection(String pass) throws IdentificationError	{
		AccessDB.getInstance(pass);
	}
	
	public void CloseConnection() throws DisconnectException {
		AccessDB.CloseConnection();
	}
}