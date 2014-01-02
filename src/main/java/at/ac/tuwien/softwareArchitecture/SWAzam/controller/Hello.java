package at.ac.tuwien.softwareArchitecture.SWAzam.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Hello {

    private String name;
    private String message;

    public String createMessage() {
        message = "Hello,  Amin!";
        return null; // No navigation, so goes back to same view.
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return "Hi Amin";
    }

}