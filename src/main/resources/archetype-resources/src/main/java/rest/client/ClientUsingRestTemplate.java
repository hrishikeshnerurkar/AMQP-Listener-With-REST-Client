#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * ClientUsingRestTemplate.java
 * 
 * Copyright (c) 2015 by General Electric Company. All rights reserved.
 * 
 * The copyright to the computer software herein is the property of General Electric Company. The software may be used
 * and/or copied only with the written permission of General Electric Company or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the software has been supplied.
 */
package ${package}.rest.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author 502287061
 */
@Component
public class ClientUsingRestTemplate {

    @Value ( "${symbol_dollar}{endpointUrl}" )
    String endpointUrl;

    public boolean invokeRestService( String message ) {
        RestTemplate template = new RestTemplate();
        String echoedMessage = template.getForObject( endpointUrl + "/echo/{message}", String.class, message );
        if ( echoedMessage == null || echoedMessage.isEmpty() ) {
            return false;
        } else {
            System.out.println( "Echoed Message in ClientUsingRestTemplate :: " + echoedMessage );
            return true;
        }
    }
}
