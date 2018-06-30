package br.com.welson.clinic.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
public class ApplicationUser extends AbstractEntity {

    private String username;

    @JsonProperty(access = WRITE_ONLY)
    private String password;

    @JsonIgnore
    @OneToOne
    private Admin admin;

    @JsonIgnore
    @OneToOne
    private Employee employee;

    @JsonIgnore
    @OneToOne
    private Doctor doctor;

    @JsonIgnore
    @OneToOne
    private Patient patient;

    @Transient
    private AccessToken accessToken;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        if (admin != null)
            return admin;
        if (employee != null)
            return employee;
        if (doctor != null)
            return doctor;
        if (patient != null)
            return patient;
        return null;
    }

    public String getUserType() {
        return getUser() != null ? getUser().getClass().getSimpleName() : null;
    }
}
