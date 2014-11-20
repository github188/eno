package com.energicube.eno.monitor.model;

public class UsersSet implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Users users;
    private Persons Persons;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Persons getPersons() {
        return Persons;
    }

    public void setPersons(Persons Persons) {
        this.Persons = Persons;
    }

}
