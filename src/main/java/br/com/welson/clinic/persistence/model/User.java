package br.com.welson.clinic.persistence.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User extends AbstractEntity {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
