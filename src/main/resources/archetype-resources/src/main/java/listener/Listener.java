#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * Listener.java
 * 
 * Copyright (c) 2015 by General Electric Company. All rights reserved.
 * 
 * The copyright to the computer software herein is the property of General Electric Company. The software may be used
 * and/or copied only with the written permission of General Electric Company or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the software has been supplied.
 */
package ${package}.listener;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import ${package}.rest.client.ClientUsingJerseyClient;
import ${package}.rest.client.ClientUsingRestTemplate;

/**
 * @author 502287061
 */
@Component
public class Listener implements MessageListener {

    @Value ( "${symbol_dollar}{queueName}" )
    String queueName;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private ClientUsingJerseyClient jerseyClient;
    @Autowired
    private ClientUsingRestTemplate restTemplate;

    @Bean
    public SimpleMessageListenerContainer container() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer( connectionFactory );
        MessageListenerAdapter adapter = new MessageListenerAdapter( this );
        container.setMessageListener( adapter );
        container.setQueueNames( queueName );
        return container;
    }

    @Bean
    public SimpleMessageConverter converter() {
        return new SimpleMessageConverter();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.amqp.core.MessageListener${symbol_pound}onMessage(org.springframework.amqp.core.Message)
     */
    @Override
    public void onMessage( Message message ) {
        String messageStr = (String)converter().fromMessage( message );
        System.out.println( "Message received from queue : " + messageStr );
        boolean jerseyClientResult = jerseyClient.invokeRestService( messageStr );
        System.out.println( "Jersey Client result :: " + jerseyClientResult );
        boolean restTemplateResult = restTemplate.invokeRestService( messageStr );
        System.out.println( "Rest Template result :: " + restTemplateResult );
    }

}
