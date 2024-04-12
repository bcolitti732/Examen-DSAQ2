package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Pilot {
    private String id;
    private String nombre;
    private String apellidos;
    private int flightHours;
    private List<FlightPlanReservation> flightPlanReservations;

    public Pilot(String id, String nombre, String apellidos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.flightHours = 0;
        this.flightPlanReservations = new ArrayList<>();
    }

    public Pilot() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getFlightHours() {
        return flightHours;
    }

    public void setFlightHours(int flightHours) {
        this.flightHours = flightHours;
    }

    public List<FlightPlanReservation> getFlightPlanReservations() {
        return flightPlanReservations;
    }

    public void setFlightPlanReservations(List<FlightPlanReservation> flightPlanReservations) {
        this.flightPlanReservations = flightPlanReservations;
    }

    public void addFlightPlanReservation(FlightPlanReservation flightPlanReservation) {
        this.flightPlanReservations.add(flightPlanReservation);
    }
}