package cat.altimiras.matxos.pojo;


import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "registration")
public class Registration {

    private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String race;

    private String route;

    private String name;

    private String surname1;

    private String surname2;

    private String bibname;

    private Date birthday;

    private String dni;

    private String telf;

    private String town;

    private String club;

    private String feec;

    private String telfemer;

    private String gender;

    private String size;

    private String email;

    private String bib;

    private boolean isHere;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "payment_date")
    private Date paymentDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBirthdayStr(String birthday) {
        try {
            this.birthday = df.parse(birthday);
        } catch (Exception e) {
            this.birthday = null;
        }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getBib() {
        return bib;
    }

    public void setBib(String bib) {
        this.bib = bib;
    }

    public boolean isHere() {
        return isHere;
    }

    public void setIsHere(boolean isHere) {
        this.isHere = isHere;
    }
}
