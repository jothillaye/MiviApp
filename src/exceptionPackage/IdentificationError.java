package exceptionPackage;

public class IdentificationError extends Exception {
	private String msg;
	
	public IdentificationError(String m) { 
		this.msg = m;
	}
	
	public String toString() {
		return "Erreur lors de la connexion :\n" + msg;
	}
}

