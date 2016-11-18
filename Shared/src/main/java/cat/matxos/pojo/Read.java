package cat.matxos.pojo;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "time_reads")
public class Read implements Serializable, Persistable{

    public Read(){}

    public Read(String race, String control, String bib, String time){
        this.time = time;
        ReadKey key = new ReadKey();
        key.setBib(bib);
        key.setControl(control);
        key.setRace(race);
        this.readKey = key;
    }

    @EmbeddedId
    private ReadKey readKey;

    private String time;


    public ReadKey getReadKey() {
        return readKey;
    }

    public void setReadKey(ReadKey readKey) {
        this.readKey = readKey;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
/*
    public String getRace() {
        return readKey.getRace();
    }

    public void setRace(String race){
        this.readKey.setRace(race);
    }

    public String getBib() {
        return readKey.getBib();
    }

    public void setBib(String bib) {
        this.readKey.setBib(bib);
    }

    public String getControl() {
        return this.readKey.getControl();
    }

    public void setControl(String control) {
        this.readKey.setControl(control);
    }

*/



    @Override
    public Serializable getId() {
        return readKey;
    }

    @Override
    public boolean isNew() {
        return true; //always is new, so no updates are done
    }

    @Embeddable
    @Access(AccessType.FIELD)
    public static class ReadKey implements Serializable {

        public ReadKey() {
        }

        private String race;

        private String bib;

        private String control;

        public String getRace() {
            return race;
        }

        public void setRace(String race) {
            this.race = race;
        }

        public String getBib() {
            return bib;
        }

        public void setBib(String bib) {
            this.bib = bib;
        }

        public String getControl() {
            return control;
        }

        public void setControl(String control) {
            this.control = control;
        }
    }

}
