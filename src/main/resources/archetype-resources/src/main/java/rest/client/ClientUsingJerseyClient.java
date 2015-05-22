#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * ClientUsingJerseyClient.java
 * 
 * Copyright (c) 2015 by General Electric Company. All rights reserved.
 * 
 * The copyright to the computer software herein is the property of General Electric Company. The software may be used
 * and/or copied only with the written permission of General Electric Company or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the software has been supplied.
 */
package ${package}.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 502287061
 */
@Component
public class ClientUsingJerseyClient {

    @Value ( "${symbol_dollar}{endpointUrl}" )
    String endpointUrl;

    public boolean invokeRestService( String message ) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target( endpointUrl ).path( "echo" ).path( "{message}" ).resolveTemplate( "message", message );
        String echoedMessage = target.request().get( String.class );
        if ( echoedMessage == null || echoedMessage.isEmpty() ) {
            return false;
        } else {
            System.out.println( "Echoed Message in ClientUsingJerseyClient :: " + echoedMessage );
            return true;
        }
    }

}
