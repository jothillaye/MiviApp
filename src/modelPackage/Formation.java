package modelPackage;

public class Formation {
    private Integer id;
    private String intitule;

	public Formation(String intitule) {
        this.intitule = intitule;
    }
	
	// Constructeur pour lister les clients lors d'une requete sql
	public Formation() {}

    public Integer getId() { return id;}
    public String getIntitule() { return intitule;}

    public void setId(Integer id) { this.id = id;}
    public void setIntitule(String intitule) { this.intitule = intitule;}
}