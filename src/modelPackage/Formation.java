package modelPackage;

public class Formation {
    private Integer idFormation;
    private String intitule;

	public Formation(String intitule) {
        this.intitule = intitule;
    }
	
	// Constructeur pour lister les clients lors d'une requete sql
	public Formation() {}

    public Integer getIdFormation() { return idFormation;}
    public String getIntitule() { return intitule;}

    public void setIdFormation(Integer idForm) { this.idFormation = idForm;}
    public void setIntitule(String intitule) { this.intitule = intitule;}
}