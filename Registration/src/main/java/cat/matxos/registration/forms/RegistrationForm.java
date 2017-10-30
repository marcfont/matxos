package cat.matxos.registration.forms;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationForm {

    @NotNull
    @NotBlank
    private String race;

    @NotNull
    private String route;

    @NotNull
    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 1, max=50)
    private String surname1;

    @Size(min = 1, max=50)
    private String surname2;

    @Size(max=20)
    private String bibname;

    @NotBlank
    @Size( min=10, max=10)
    private String birthday;

    @NotBlank
    @Size( max=10)
    private String dni;

    @NotBlank
    @Size( min=8, max=15)
    private String telf;

    @NotBlank
    @Size(min=1, max=50)
    private String town;

    @Size( max=50)
    private String club;

    @Size( max=6)
    private String feec;

    @NotBlank
    @Size(min=10, max=100)
    private String telfemer;

    @NotBlank
    @Size(min=1, max=1)
    private String gender;

    private String size;

    @Email
    @NotBlank
    @Size(min=5, max=100)
    private String email;

    private Boolean solidari = false;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
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

    public String getBibname() {
        return bibname;
    }

    public void setBibname(String bibname) {
        this.bibname = bibname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getFeec() {
        return feec;
    }

    public void setFeec(String feec) {
        this.feec = feec;
    }

    public String getTelfemer() {
        return telfemer;
    }

    public void setTelfemer(String telfemer) {
        this.telfemer = telfemer;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Boolean getSolidari() {
        return solidari;
    }

    public void setSolidari(Boolean solidari) {
        this.solidari = solidari;
    }

    @Override
    public String toString() {
        return "RegistrationForm{" +
                "race='" + race + '\'' +
                ", route='" + route + '\'' +
                ", name='" + name + '\'' +
                ", surname1='" + surname1 + '\'' +
                ", surname2='" + surname2 + '\'' +
                ", bibname='" + bibname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", dni='" + dni + '\'' +
                ", telf='" + telf + '\'' +
                ", town='" + town + '\'' +
                ", club='" + club + '\'' +
                ", feec='" + feec + '\'' +
                ", telfemer='" + telfemer + '\'' +
                ", gender='" + gender + '\'' +
                ", size='" + size + '\'' +
                ", email='" + email + '\'' +
                ", solidari=" + solidari +
                '}';
    }
}
