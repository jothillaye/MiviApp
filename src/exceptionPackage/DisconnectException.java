package exceptionPackage;

public class DisconnectException extends Exception {
	private String msg;
	
	public DisconnectException(String msg) {
		this.msg = msg;
	}
	
    @Override
	public String toString() {
		return msg;
	}
}