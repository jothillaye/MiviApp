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
    int id, type;  
    String desc;  
    
    public QueryResult(Integer id, Integer type, String desc) {  
        this.id = id;
        this.type = type;
        this.desc = desc;  
    }  
        
    public QueryResult(int id, String desc) {  
        this.id = id;
        this.desc = desc;  
    }          
    
    @Override
    public String toString(){return desc;}  
    public Integer getId() {return id;}
}
