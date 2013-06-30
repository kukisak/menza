package sklad.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Polozka {

	private int PLU;
	private String nazev;
	private int cena;
	private int pocet;
	
	public int getPLU()
	{
		return PLU;
	}
	
	public String getNazev()
	{
		return nazev;
	}
	
	public int getCena()
	{
		return cena;
	}
	
	public int getPocet()
	{
		return pocet;
	}
	
	
	public void setPLU(int PLU)
	{
		this.PLU = PLU;
	}
	
	public void setNazev(String nazev)
	{
		this.nazev = nazev;
	}
	
	public void setCena(int cena)
	{
		this.cena = cena;
	}
	
	public void setPocet(int pocet)
	{
		this.pocet = pocet;
	}
	
	@Override
    public String toString() {
        return "Polozka{" + "PLU=" + PLU + ", nazev=" + nazev + ", cena="+ cena +", pocet="+pocet+"}";
    }
}
