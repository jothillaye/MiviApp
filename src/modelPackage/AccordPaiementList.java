package modelPackage;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
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
			case 0: return paiement.getDatePaiementFormated();
            case 1: return paiement.getMontant();              
			
			default: return null;
		}
	}
	
    @Override
	public Class getColumnClass(int col) { 
		 return getValueAt(0, col).getClass();
	}
    
    @Override
    public boolean isCellEditable(int row, int col) { 
        return true;    
    }
    
    @Override
    public void setValueAt(Object o, int row, int col) {
        if(col == 0){
            try {
                GregorianCalendar datePaiement = new GregorianCalendar(Integer.parseInt(o.toString().substring(6, 10)), Integer.parseInt(o.toString().substring(3, 5))-1, Integer.parseInt(o.toString().substring(0, 2)));
                paiement.setDatePaiement(datePaiement);            
            }
            catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Erreur dans la date.", "Erreur modification", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(col == 1) {
            paiement.setMontant(Float.parseFloat(o.toString()));                
        }   
        fireTableCellUpdated(row, col);
    }
}