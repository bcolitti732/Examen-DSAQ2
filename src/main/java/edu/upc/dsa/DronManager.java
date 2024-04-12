package edu.upc.dsa;

import edu.upc.dsa.models.Dron;
import edu.upc.dsa.models.FlightPlanReservation;
import edu.upc.dsa.models.Pilot;

import java.util.Date;
import java.util.List;

public interface DronManager {
    public Dron addDron(String id, String name, String manufacturer, String model);
    public Pilot addPiloto(String id, String nombre, String apellidos);
    public List<Dron> getDronesOrderedByFlightHours();
    public List<Pilot> getPilotosOrderedByFlightHours();
    public void storeDronForMaintenance(String dronId);
    public Dron performMaintenanceOnDron();
    public void addFlightPlanReservation(String dronId, Date date, int estimatedTime, String startPosition, String endPosition, String pilotoId);
    public List<FlightPlanReservation> getFlightPlanReservationsForPiloto(String pilotoId);
    public List<FlightPlanReservation> getFlightPlanReservationsForDron(String dronId);
    public int size();
}