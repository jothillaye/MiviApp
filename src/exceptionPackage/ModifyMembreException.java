/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptionPackage;

/**
 *
 * @author Joachim
 */
public class ModifyMembreException extends Exception {
    private String msg;
    
    public ModifyMembreException(String msg) {
        this.msg = msg;
    }

    @Override
	public String toString() {
		return msg;
	}
    
}
