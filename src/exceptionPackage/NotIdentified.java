package exceptionPackage;

public class NotIdentified extends Exception {	
	public NotIdentified() { 
		
	}
	
	public String toString() {
		return "Vous n'êtes pas connecté.";
	}
}