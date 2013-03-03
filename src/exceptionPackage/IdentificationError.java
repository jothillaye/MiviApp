package exceptionPackage;

/**
 *
 * @author Joachim
 */
public class IdentificationError extends Exception {
    private String msg;
    
    public IdentificationError(String msg) {
        this.msg = msg;
    }

    @Override
	public String toString() {
		return msg;
	}
    
}
