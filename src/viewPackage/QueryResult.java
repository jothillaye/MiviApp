/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

/**
 *
 * @author Joachim
 */
public class QueryResult {
    int id;  
    String desc;  
    
    public QueryResult(int id, String desc) {  
        this.id = id;
        this.desc = desc;  
    }          
    
    @Override
    public String toString(){return desc;}  
}
