package modelPackage;

import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Membre {
    private Integer id;
	private String nom, prenom;
	private GregorianCalendar dateNaiss;

	public Membre(String nom, String prenom, GregorianCalendar dateNaiss) {
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaiss = dateNaiss;		
	}
	
	// Constructeur pour lister les clients lors d'une requ�te sql
	public Membre() {}

	// Getters
    public Integer getId() { return id;}
	public String getNom() { return nom;}
	public String getPrenom() { return prenom;}
	public GregorianCalendar getDateNaiss() { return dateNaiss;}
    
    public String getFormatedDateNaiss() {    
        TimeZone tz = TimeZone.getTimeZone("Europe/Brussels");
        DateFormat df = DateFormat.getDateTimeInstance();  
        df.setTimeZone(tz);          
        return df.format(dateNaiss.getTime());
    }

	// Setters
    public void setId(Integer id) { this.id = id;}
	public void setNom(String nom) { this.nom = nom;}
	public void setPrenom(String prenom) { this.prenom = prenom;}
	public void setDateNaiss(GregorianCalendar dateNaiss) { this.dateNaiss = dateNaiss;}
    
}
