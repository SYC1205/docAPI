package com.e104.restapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/")
public class docAPI {
	
	@GET
	@Path("/getFileUrl/{parmeterString}")
	@ApiOperation(
			value = "Get operation with Response and @Default value", 
		    notes = "Get operation with Response and @Default value", 
		    response = String.class, 
		    responseContainer = "List"
	)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid username/password supplied") })
	public String Getfile( @ApiParam(value = "Created user object", required = true) @PathParam("parmeterString") String parmeterString){
		return parmeterString;
	}
}
