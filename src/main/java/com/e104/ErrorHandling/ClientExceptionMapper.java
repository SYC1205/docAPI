package com.e104.ErrorHandling;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.amazonaws.services.apigateway.model.NotFoundException;
@Provider
public class ClientExceptionMapper implements ExceptionMapper<ClientErrorException>{
	@Override
    public Response toResponse(ClientErrorException e)  {
        e.printStackTrace();
    	return Response.status(Response.Status.NOT_FOUND).header("Content-Type", "application/json").entity(new String("NOT FOUND")).build();
    }
}
