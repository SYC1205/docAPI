package com.e104.ErrorHandling;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.amazonaws.partitions.model.Service;
import com.amazonaws.services.apigateway.model.NotFoundException;

@Provider
public class ServerExceptionMapper implements ExceptionMapper<ServerErrorException> {
    @Override
    public Response toResponse(ServerErrorException e) {
        e.printStackTrace();
    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Content-Type", "application/json").entity(new String("INTERNAL SERVER ERROR")).build();
    }
   /* 
    @Override
    public Response toResponse(ServerErrorException e){
    	e.printStackTrace();
    	return Response.status(Response.Status.NOT_FOUND).header("Content-Type", "application/json").entity(new String("Service is not found")).build();
    }*/
}