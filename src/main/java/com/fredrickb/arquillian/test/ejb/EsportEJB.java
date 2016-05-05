package com.fredrickb.arquillian.test.ejb;

import com.fredrickb.arquillian.test.models.Esport;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class EsportEJB {

    @PersistenceContext(unitName = "PUnit")
    private EntityManager manager;

    public List<Esport> all() {
        return manager.createNamedQuery(Esport.QUERY_ALL, Esport.class).getResultList();
    }

    public Esport save(Esport esport) {
        manager.persist(esport);
        return esport;
    }

    public Esport findById(Long id) {
        return manager.find(Esport.class, id);
    }

    public boolean delete(Long id) {
        Esport esportToDelete = findById(id);
        if (esportToDelete == null) {
            return false;
        }
        manager.remove(esportToDelete);
        return true;
    }
}
