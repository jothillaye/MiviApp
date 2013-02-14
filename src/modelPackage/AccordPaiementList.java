package modelPackage;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

public class AccordPaiementList extends AbstractTableModel {
	private ArrayList<String> listCol = new ArrayList<String>();
	private ArrayList<Paiement> content = new ArrayList<Paiement>();
    
	private Paiement paiement;
	
	public AccordPaiementList(ArrayList<Paiement> arrayPaiement) {
		this.content = arrayPaiement;	
		listCol.add("Date");
		listCol.add("Montant");
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
		paiement = content.get(row);
		switch(col)	{ 	
			case 0: return paiement.getDatePaiement().getTimeInMillis();
            case 1: return paiement.getMontant();             
			
			default: return null;
		}
	}
	
	public Class getColumnClass(int col) { 
		 switch (col) {
		 	 case 0: return Date.class;	 
		 	 case 1: return Float.class;
			 
			 default: return null;
		 }
	}
}