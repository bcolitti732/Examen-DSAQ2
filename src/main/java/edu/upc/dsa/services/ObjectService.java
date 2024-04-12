package edu.upc.dsa.services;

import edu.upc.dsa.ObjectManager;
import edu.upc.dsa.ObjectManagerImpl;
import edu.upc.dsa.models.Object;
import edu.upc.dsa.models.ServerResponse;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;

@Api(value = "/objects", description = "Endpoint to Object Service")
@Path("/objects")
public class ObjectService {
    private ObjectManager om;

    public ObjectService() {
        this.om = ObjectManagerImpl.getInstance();
        if (om.size()==0) {
            this.om.addObject("mesa", "va de locos", 5);
            this.om.addObject("silla", "fua sientate y flipa", 20);
            this.om.addObject("cama", "duerme y descansa", 60);

            this.om.registerUser("Bru", "Colitti", "2000-01-01", "brunocolitti@gmail.com", "password");
            this.om.registerUser("John", "Doe", "2000-01-02", "johndoe@gmail.com", "password");
            this.om.registerUser("Jane", "Doe", "2000-01-03", "janedoe@gmail.com", "password");
            this.om.registerUser("Alice", "Smith", "2000-01-04", "alicesmith@gmail.com", "password");

        }


    }
    @GET
    @ApiOperation(value = "get users ordered alphabetically", notes = "Returns the users ordered alphabetically")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = List.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/users/ordered")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersOrderedAlphabetically() {
        List<User> users = this.om.getUsersOrderedAlphabetically();

        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get objects ordered by price", notes = "aya")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = List.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/objects/ordered")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjectsOrderedByPrice() {
        List<edu.upc.dsa.models.Object> objects = this.om.getObjectsOrderedByPrice();

        GenericEntity<List<edu.upc.dsa.models.Object>> entity = new GenericEntity<List<edu.upc.dsa.models.Object>>(objects) {};
        return Response.status(200).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "Buy an object", notes = "This operation can be used to buy an object.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = ServerResponse.class),
            @ApiResponse(code = 400, message = "Unable to buy object"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @Path("/buy/{email}/{objectName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyObject(@PathParam("email") String email, @PathParam("objectName") String objectName) {
        try {
            ServerResponse s = new ServerResponse();
            s.setResponse("Object bought successfully");
            this.om.buyObject(email, objectName);
            if (this.om.buyObject(email, objectName) == -1) {
                s.setResponse("User does not exist");
                return Response.status(400).entity(s).build();
            } else if (this.om.buyObject(email, objectName) == -2) {
                s.setResponse("Object does not exist");
                return Response.status(400).entity(s).build();
            } else if (this.om.buyObject(email, objectName) == -3) {
                s.setResponse("Not enough DSA coins");
                return Response.status(400).entity(s).build();
            }
            return Response.status(200).entity(s).build();
        } catch (Exception e) {
            ServerResponse s = new ServerResponse();
            s.setResponse("Server error");
            return Response.status(500).entity(s).build();
        }
    }

    @GET
    @ApiOperation(value = "Get objects bought by user", notes = "This operation can be used to get objects bought by a specific user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = List.class),
            @ApiResponse(code = 400, message = "Unable to get objects"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @Path("/bought/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjectsBoughtByUser(@PathParam("email") String email) {
        try {
            List<Object> objects = this.om.getObjectsBoughtByUser(email);
            GenericEntity<List<Object>> entity = new GenericEntity<List<Object>>(objects) {};
            return Response.status(200).entity(entity).build();
        } catch (Exception e) {
            ServerResponse s = new ServerResponse();
            s.setResponse("Server error");
            return Response.status(500).entity(s).build();
        }
    }



}
