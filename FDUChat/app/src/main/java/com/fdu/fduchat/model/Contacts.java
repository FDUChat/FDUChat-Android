package com.fdu.fduchat.model;

import java.util.ArrayList;

public class Contacts {

    private ArrayList<Group> contacts = new ArrayList<Group>();

    public ArrayList<Group> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Group> contacts) {
        this.contacts = contacts;
    }

    public Contacts(ArrayList<Group> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "contacts=" + contacts +
                '}';
    }

    public Contacts() {

    }
}
