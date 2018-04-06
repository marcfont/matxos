package cat.matxos.pojo;

import java.util.Objects;

public class ReadRunner  {

    private String race;

    private String bib;

    private String control;

    private String time;

    private String name;

    private String surname1;

    private String surname2;

    private String telfemer;

    public ReadRunner(){

    }

    public ReadRunner(String race,String bib,String control, String time, String name, String surname1, String surname2, String telfemer){
        this.race = race;
        this.bib = bib;
        this.control=control;
        this.time=time;
        this.name=name;
        this.surname1=surname1;
        this.surname2=surname2;
        this.telfemer=telfemer;
    }

    public ReadRunner(Object[] args){
        this.race = (String)args[0];
        this.bib =  (String)args[1];
        this.control= (String)args[2];
        this.time= String.valueOf(args[3]);
        this.name= (String)args[4];
        this.surname1= (String)args[5];
        this.surname2= (String)args[6];
        this.telfemer= (String)args[7];
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getTelfemer() {
        return telfemer;
    }

    public void setTelfemer(String telfemer) {
        this.telfemer = telfemer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReadRunner that = (ReadRunner) o;

        return bib.equals(that.bib);

/*
        if (race != null ? !race.equals(that.race) : that.race != null) return false;
        if (bib != null ? !bib.equals(that.bib) : that.bib != null) return false;
        return !(control != null ? !control.equals(that.control) : that.control != null);
*/
    }

    @Override
    public int hashCode() {
      /*int result = race != null ? race.hashCode() : 0;
        result = 31 * result + (bib != null ? bib.hashCode() : 0);
        result = 31 * result + (control != null ? control.hashCode() : 0);
        return result;
        */
      return bib.hashCode();
    }
}
