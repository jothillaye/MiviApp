package modelPackage;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class HistoryMembreList extends AbstractTableModel {
	private ArrayList<String> listCol = new ArrayList<String>();
	private ArrayList<ArrayList<Object>> content = new ArrayList<ArrayList<Object>>();
    
	private Formation form;
    private Activite act;
    private Inscription ins;
    private Float paye;
	
	public HistoryMembreList(ArrayList<ArrayList<Object>> arrayIns) {
		this.content = arrayIns;	
		listCol.add("Intitulé");
		listCol.add("Promotion");
        listCol.add("Date Début");
        listCol.add("Prix");
        listCol.add("Payé");
        listCol.add("Certifié");
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
		form = (Formation)content.get(row).get(0);
        act = (Activite)content.get(row).get(1);
        ins = (Inscription)content.get(row).get(2);
        paye = (Float)content.get(row).get(3);
        
        switch(col)	{ 	
			case 0: return form.getIntitule();
            case 1: if(act.getPromotion() != 0) { return act.getPromotion();}
                    else { return "";}
            case 2: if(act.getDateDeb() != null){ return act.getFormatedDateDeb();}
                    else { return "";}
            case 3: if(ins.getTarifSpecial() != null && ins.getTarifSpecial() != 0) { return ins.getTarifSpecial() + "€";}
                    else { return act.getPrix() + "€";}
            case 4: return paye + "€";
            case 5: return ins.getCertifie();
			
			default: return null;
		}
	}
	
    @Override
	public Class getColumnClass(int col) {         
		return getValueAt(0, col).getClass();
	}
}