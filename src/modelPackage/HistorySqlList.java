/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelPackage;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Joachim
 */
public class HistorySqlList extends AbstractTableModel {
    private ArrayList<String> listCol = new ArrayList<String>();
	private ArrayList<String> content = new ArrayList<String>();
    
    public HistorySqlList(ArrayList<String> arraySql) {
        this.content = arraySql;
		listCol.add("Type");
		listCol.add("Table(s)");
        listCol.add("Membre");
        listCol.add("Formation");
        listCol.add("Date");
	}
	
    @Override
	public int getRowCount() {
		return content.size();
	}
    @Override
	public int getColumnCount() {
		return listCol.size();
	}
    @Override
	public String getColumnName(int col) {
		return listCol.get(col);
	}
	
    @Override
	public Object getValueAt(int row, int col) { 
        return content.get(row+1);
	}
	
    @Override
	public Class getColumnClass(int col) {         
		return getValueAt(0, col).getClass();
	}
}
