package entities;

//import javax.persistence.Entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Version;
//import javax.persistence.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Container(containerName = "LazyHound")
public class DatiSportivi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String nomeUtente;

    private int durata;
    @PartitionKey
    private String tipoAttivita;

    private String note;


    public DatiSportivi(String id, String nomeUtente, int durata, String tipoAttivita, String note) {
    }

    public DatiSportivi() {

    }

    public String getNomeUtente(){return this.nomeUtente;}
    public String getId(){return this.id;}
    public int getDurata(){
        return this.durata;
    }
    public String gettipoAttivita(){
        return this.tipoAttivita;
    }
    public String getNote(){
        return this.note;
    }

    public void setNomeUtente(String nomeUtente){
        this.nomeUtente =nomeUtente;}

    public void setDurata(int durata){
        this.durata=durata;
    }

    public void setTipoAttivita(String tipoAttivita){
        this.tipoAttivita=tipoAttivita;
    }

    public void setNote(String note){
        this.note=note;
    }

    @Override
    public String toString() {
        return "Dato{" +
                "id='" + id + '\'' +
                ", durata=" + durata +
                ", tipo Attività=" + tipoAttivita +
                ", note='" + note + '\'' +
                '}';
    }
}
