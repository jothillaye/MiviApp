package exceptionPackage;

public class NotIdentified extends Exception {	
	public NotIdentified() {}
	
    @Override
	public String toString() {
		return "Vous n'êtes pas connecté.";
	}
}