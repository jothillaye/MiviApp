/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptionPackage;

/**
 *
 * @author Joachim
 */
public class ListMembreException extends Exception {
    private String msg;
    
    public ListMembreException(String msg) {
        this.msg = msg;
    }

    @Override
	public String toString() {
		return msg;
	}
    
}
