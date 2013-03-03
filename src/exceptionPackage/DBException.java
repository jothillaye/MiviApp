package exceptionPackage;

/**
 *
 * @author Joachim
 */
public class DBException extends Exception {
    private String msg;
    
    public DBException(String msg) {
        this.msg = msg;
    }

    @Override
	public String toString() {
		return msg;
	}
    
}
