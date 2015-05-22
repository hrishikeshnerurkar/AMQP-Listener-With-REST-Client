#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Named
@Path ( "/echo" )
public class EchoEndpoint {

    @Path ( "{message}" )
    @GET
    public String echo( @PathParam ( "message" ) String message ) {
        return message;
    }
}
