package com.fredrickb.arquillian.test.controllers;

import com.fredrickb.arquillian.test.controllers.routes.RouteHolder;
import com.fredrickb.arquillian.test.ejb.EsportEJB;
import com.fredrickb.arquillian.test.models.Esport;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;

@Model
public class EsportController {

    @Inject
    private EsportEJB esportEJB;

    private Esport esport;

    @PostConstruct
    public void init() {
        esport = new Esport();
    }

    public Esport getEsport() {
        return esport;
    }

    public void setEsport(Esport esport) {
        this.esport = esport;
    }

    public String save() {
        try {
            esportEJB.save(esport);
            return RouteHolder.INDEX_ROUTE;
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Failed to save Esports!"));
            return "";
        }
    }

    public List<Esport> getAll() {
        return esportEJB.all();
    }
}
