package controllerPackage;

import modelPackage.*;
import businessPackage.*;
import exceptionPackage.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ApplicationController {	
	private LoginManager _loginManager;	

	public ApplicationController(){	
		_loginManager = new LoginManager( );
	}	
	
	// Login Managing	
	public void Connection() throws NotIdentified {
		_loginManager.Connection();
	}
	
	public void Connection(String pw) throws IdentificationError {
		_loginManager.Connection(pw);
	}

	public void Disconnect() throws DisconnectException {
		_loginManager.CloseConnection();
	}
	
	
	// Client Managing	
	//public void newCli(Client cli) throws NewCliException, NotIdentified {
	//	cm.newCli(cli);
	//}
		
	//public ArrayList<String>[] getCli(String nom) throws GetCliException, NotIdentified {
	//	return cm.getCli(nom);
	//}
	
}