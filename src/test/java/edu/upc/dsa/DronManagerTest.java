package edu.upc.dsa;

import edu.upc.dsa.models.Dron;
import edu.upc.dsa.models.Pilot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DronManagerTest {
    private DronManager dm;

    @Before
    public void setUp() {

        this.dm = new DronManagerImpl();
        dm = DronManagerImpl.getInstance();

        dm.addDron("1", "Dron1", "DronManufacturer1", "DronModel1");
        dm.addDron("2", "Dron2", "DronManufacturer2", "DronModel2");
        dm.addDron("3", "Dron3", "DronManufacturer3", "DronModel3");

        dm.addPiloto("1", "Pilot1", "PilotSurname1");
        dm.addPiloto("2", "Pilot2", "PilotSurname2");
        dm.addPiloto("3", "Pilot3", "PilotSurname3");
    }

    @After
    public void tearDown(){this.dm = null;}

    @Test
    public void testStoreDronForMaintenance() throws Exception {
        Dron dron = dm.addDron("4", "Dron4", "DronManufacturer4", "DronModel4");
        dm.storeDronForMaintenance("4");
        assertTrue(dron.isInMaintenance());
    }

    @Test
    public void testPerformMaintenanceOnDron() throws Exception {
        Dron dron = dm.addDron("4", "Dron4", "DronManufacturer4", "DronModel4");
        dm.storeDronForMaintenance("4");
        dm.performMaintenanceOnDron();
        assertFalse(dron.isInMaintenance());
    }

    @Test
    public void testAddPiloto() throws Exception{
        Pilot pilot = dm.addPiloto("4", "Pilot4", "PilotSurname4");

        assertNotNull(pilot);
        assertEquals("4", pilot.getId());
        assertEquals("Pilot4", pilot.getNombre());
        assertEquals("PilotSurname4", pilot.getApellidos());
    }

    @Test
    public void testAddDron() throws Exception{
        Dron dron = dm.addDron("4", "Dron4", "DronManufacturer4", "DronModel4");

        assertNotNull(dron);
        assertEquals("4", dron.getId());
        assertEquals("Dron4", dron.getName());
        assertEquals("DronManufacturer4", dron.getManufacturer());
        assertEquals("DronModel4", dron.getModel());
    }


}
