package com.fredrickb.arquillian.test.ejb;

import com.fredrickb.arquillian.test.models.Esport;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class EsportEBJIT {

    @Inject
    private EsportEJB esportEJB;

    @Deployment
    public static Archive<?> createDeployment() {
        // Build the WAR file to deploy to the embedded glassfish
        return ShrinkWrap.create(WebArchive.class, "arquillian-test.war")
                // Add the classes you want to include in the WAR-file
                .addClass(EsportEJB.class)
                .addClass(Esport.class)
                // Overwrite src/main/resources/META-INF/persistence.xml with src/test/resources/META-INF/test-persistence.xml
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                // Add beans.xml from src/main/resources/META-INF/beans.xml
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setUp() throws Exception {
        assertTrue(esportEJB.all().isEmpty());
    }

    @After
    public void tearDown() throws Exception {
        for (Esport esport : esportEJB.all()) {
            esportEJB.delete(esport.getId());
        }
    }

    @Test
    public void testSaveProperlyUpdatesCountForAll() throws Exception {
        int sizeBefore = esportEJB.all().size();

        Esport esport = new Esport();
        esport.setSportName("Starcraft 1: Brood War");

        esportEJB.save(esport);
        Long dbGeneratedIdForEntity = esport.getId();

        int sizeAfter = esportEJB.all().size();

        assertNotNull(dbGeneratedIdForEntity);
        assertTrue(sizeAfter > sizeBefore);
    }

    @Test
    public void testFindByIdReturnsEntityForValidId() throws Exception {
        Esport esport = new Esport();
        esport.setSportName("Starcraft 2: Legacy of the void");
        esportEJB.save(esport);

        Long generatedIdFromDbInsert = esport.getId();
        assertNotNull(generatedIdFromDbInsert);
        assertNotNull(esportEJB.findById(generatedIdFromDbInsert));
    }

    @Test(expected = Exception.class)
    public void testDuplicateNamedEsportShouldThrowException() throws Exception {
        final String sportName = "Starcraft 2";

        Esport esport = new Esport();
        esport.setSportName(sportName);

        esportEJB.save(esport);

        Esport esportWithDuplicateName = new Esport();
        esportWithDuplicateName.setSportName(sportName);
        esportEJB.save(esportWithDuplicateName);
    }
}
