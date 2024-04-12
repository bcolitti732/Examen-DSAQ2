package edu.upc.dsa;

import edu.upc.dsa.models.Dron;
import edu.upc.dsa.models.FlightPlanReservation;
import edu.upc.dsa.models.Pilot;
import org.apache.log4j.Logger;

import java.util.*;

public class DronManagerImpl implements DronManager{
    private static DronManagerImpl instance;
    private List<Dron> drones;
    private List<Pilot> pilots;
    private List<FlightPlanReservation> flightPlanReservations;
    private Queue<Dron> maintenanceQueue;
    final static Logger logger = Logger.getLogger(DronManagerImpl.class);



    public DronManagerImpl() {
        this.drones = new ArrayList<>();
        this.pilots = new ArrayList<>();
        this.flightPlanReservations = new ArrayList<>();
        this.maintenanceQueue = new LinkedList<>();
    }

    public static DronManagerImpl getInstance() {
        if (instance == null) {
            instance = new DronManagerImpl();
        }
        return instance;
    }
    @Override
    public Dron addDron(String id, String name, String manufacturer, String model) {
        logger.info("addDron called with parameters: id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", model=" + model);

        Dron newDron = new Dron(id, name, manufacturer, model);
        this.drones.add(newDron);

        logger.info("addDron finished, new dron added: " + newDron.getName());
        return newDron;
    }

    @Override
    public Pilot addPiloto(String id, String nombre, String apellidos) {
        logger.info("addPiloto called with parameters: id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos);

        Pilot newPilot = new Pilot(id, nombre, apellidos);
        this.pilots.add(newPilot);

        logger.info("addPiloto finished, new pilot added: " + newPilot.getNombre());
        return newPilot;
    }

    @Override
    public List<Dron> getDronesOrderedByFlightHours() {
        logger.info("getDronesOrderedByFlightHours called");

        List<Dron> sortedDrones = new ArrayList<>(this.drones);
        sortedDrones.sort((d1, d2) -> d2.getFlightHours() - d1.getFlightHours());

        logger.info("getDronesOrderedByFlightHours finished, returning sorted list of drones" + sortedDrones);
        return sortedDrones;
    }

    @Override
    public List<Pilot> getPilotosOrderedByFlightHours() {
        logger.info("getPilotosOrderedByFlightHours called");

        List<Pilot> sortedPilots = new ArrayList<>(this.pilots);
        sortedPilots.sort((p1, p2) -> p2.getFlightHours() - p1.getFlightHours());

        logger.info("getPilotosOrderedByFlightHours finished, returning sorted list of pilots" + sortedPilots);
        return sortedPilots;
    }

    @Override
    public void storeDronForMaintenance(String dronId) {
        logger.info("storeDronForMaintenance called with parameter: dronId=" + dronId);

        for (Dron dron : this.drones) {
            if (dron.getId().equals(dronId)) {
                dron.setInMaintenance(true);
                this.maintenanceQueue.add(dron);
                logger.info("storeDronForMaintenance finished, dron with id: " + dronId + " is now in maintenance");
                break;
            }
        }
    }

    @Override
    public Dron performMaintenanceOnDron() {
        logger.info("performMaintenanceOnDron called");

        if (!this.maintenanceQueue.isEmpty()) {
            Dron dron = this.maintenanceQueue.poll();
            dron.setInMaintenance(false);
            logger.info("performMaintenanceOnDron finished, dron with id: " + dron.getId() + " is now out of maintenance");
            return dron;
        }

        logger.info("performMaintenanceOnDron finished, no dron was in maintenance");
        return null;
    }

    @Override
    public void addFlightPlanReservation(String dronId, Date date, int estimatedTime, String startPosition, String endPosition, String pilotoId){
        logger.info("addFlightPlanReservation called with parameters: dronId=" + dronId + ", date=" + date + ", estimatedTime=" + estimatedTime + ", startPosition=" + startPosition + ", endPosition=" + endPosition + ", pilotoId=" + pilotoId);

        Dron dron = null;
        Pilot pilot = null;

        for (Dron d : this.drones) {
            if (d.getId().equals(dronId)) {
                dron = d;
                break;
            }
        }

        for (Pilot p : this.pilots) {
            if (p.getId().equals(pilotoId)) {
                pilot = p;
                break;
            }
        }

        if (dron == null || pilot == null) {
            logger.error("Dron or Pilot not found");
        }

        if (dron.isInMaintenance()) {
            logger.error("Dron is in maintenance");
        }

        Date endDate = new Date(date.getTime() + estimatedTime * 60 * 60 * 1000);

        for (FlightPlanReservation reservation : this.flightPlanReservations) {
            if (reservation.getDronId().equals(dron.getId()) || reservation.getPilotoId().equals(pilot.getId())) {
                Date reservationEndDate = new Date(reservation.getDate().getTime() + reservation.getEstimatedTime() * 60 * 60 * 1000);
                if ((date.before(reservationEndDate) && endDate.after(reservation.getDate()))) {
                    logger.error("Dron or Pilot already has a flight plan reservation in the given time interval");
                }
            }
        }

        FlightPlanReservation newReservation = new FlightPlanReservation(dron.getId(), date, estimatedTime, startPosition, endPosition, pilot.getId());
        this.flightPlanReservations.add(newReservation);
        dron.addFlightPlanReservation(newReservation);
        pilot.addFlightPlanReservation(newReservation);

        logger.info("addFlightPlanReservation finished, new flight plan reservation added for dronId: " + dronId + " and pilotoId: " + pilotoId);
    }

    @Override
    public List<FlightPlanReservation> getFlightPlanReservationsForPiloto(String pilotoId) {
        logger.info("getFlightPlanReservationsForPiloto called with parameter: pilotoId=" + pilotoId);

        List<FlightPlanReservation> pilotReservations = new ArrayList<>();
        for (FlightPlanReservation reservation : this.flightPlanReservations) {
            if (reservation.getPilotoId().equals(pilotoId)) {
                pilotReservations.add(reservation);
            }
        }

        logger.info("getFlightPlanReservationsForPiloto finished, returning flight plan reservations for pilotoId: " + pilotoId);
        return pilotReservations;
    }

    @Override
    public List<FlightPlanReservation> getFlightPlanReservationsForDron(String dronId) {
        logger.info("getFlightPlanReservationsForDron called with parameter: dronId=" + dronId);

        List<FlightPlanReservation> dronReservations = new ArrayList<>();
        for (FlightPlanReservation reservation : this.flightPlanReservations) {
            if (reservation.getDronId().equals(dronId)) {
                dronReservations.add(reservation);
            }
        }

        logger.info("getFlightPlanReservationsForDron finished, returning flight plan reservations for dronId: " + dronId);
        return dronReservations;
    }

    @Override
    public int size() {
        int ret = this.drones.size();
        logger.info("size " + ret);

        return ret;
    }
}
