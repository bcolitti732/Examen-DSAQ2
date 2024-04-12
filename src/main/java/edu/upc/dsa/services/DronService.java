package edu.upc.dsa.services;

import edu.upc.dsa.DronManagerImpl;
import edu.upc.dsa.DronManager;
import edu.upc.dsa.models.Dron;
import edu.upc.dsa.models.Pilot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Api(value = "/drones", description = "Endpoint to Drones Service")
@Path("/drones")
public class DronService {
    private DronManager pm;

    public DronService() {
        this.pm = DronManagerImpl.getInstance();
        if (pm.size()==0) {
            Dron dron1 = this.pm.addDron("1", "Dron1", "DronManufacturer1", "DronModel1");
            dron1.setFlightHours(100);

            Dron dron2 = this.pm.addDron("2", "Dron2", "DronManufacturer2", "DronModel2");
            dron2.setFlightHours(20);

            Dron dron3 = this.pm.addDron("3", "Dron3", "DronManufacturer3", "DronModel3");
            dron3.setFlightHours(50);

            Dron dron4 = this.pm.addDron("4", "Dron4", "DronManufacturer4", "DronModel4");
            dron4.setFlightHours(40);

            Pilot pilot1 = this.pm.addPiloto("1", "Pilot1", "PilotSurname1");
            pilot1.setFlightHours(60);

            Pilot pilot2 = this.pm.addPiloto("2", "Pilot2", "PilotSurname2");
            pilot2.setFlightHours(80);

            Pilot pilot3 = this.pm.addPiloto("3", "Pilot3", "PilotSurname3");
            pilot3.setFlightHours(70);

            Pilot pilot4 = this.pm.addPiloto("4", "Pilot4", "PilotSurname4");
            pilot4.setFlightHours(90);
        }
    }
    @GET
    @ApiOperation(value = "get all drones ordered by flight hours", notes = "Returns the list of all drones ordered by flight hours")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = List.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/drones/ordered")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDronesOrderedByFlightHours() {
        List<Dron> drones = this.pm.getDronesOrderedByFlightHours();

        GenericEntity<List<Dron>> entity = new GenericEntity<List<Dron>>(drones) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get all pilots ordered by flight hours", notes = "Returns the list of all pilots ordered by flight hours")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = List.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/pilots/ordered")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPilotosOrderedByFlightHours() {
        List<Pilot> pilots = this.pm.getPilotosOrderedByFlightHours();

        GenericEntity<List<Pilot>> entity = new GenericEntity<List<Pilot>>(pilots) {};
        return Response.status(200).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "store a drone for maintainance", notes = "Stores a drone for maintainance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/drones/maintainance/{id}")
    public Response storeDronForMaintainance(@PathParam("id") String id) {
        this.pm.storeDronForMaintenance(id);

        return Response.status(200).build();
    }

    @POST
    @ApiOperation(value = "add a pilot", notes = "Adds a new pilot")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Pilot.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/pilots/{id}/{name}/{surname}")
    public Response addPilot(@PathParam("id") String id, @PathParam("name") String name, @PathParam("surname") String surname) {
        Pilot newPilot = this.pm.addPiloto(id, name, surname);

        return Response.status(200).entity(newPilot).build();
    }

    @POST
    @ApiOperation(value = "add a drone", notes = "Adds a new drone")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Dron.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/drones/{id}/{name}/{manufacturer}/{model}")
    public Response addDron(@PathParam("id") String id, @PathParam("name") String name, @PathParam("manufacturer") String manufacturer, @PathParam("model") String model) {
        Dron newDron = this.pm.addDron(id, name, manufacturer, model);

        return Response.status(200).entity(newDron).build();
    }

}
