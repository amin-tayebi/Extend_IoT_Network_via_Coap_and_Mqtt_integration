package com.iot.coap.demo;
import java.io.IOException;
import java.util.concurrent.Callable;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.Request;

import org.eclipse.californium.elements.exception.ConnectorException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class CoapObservingClientProcess implements Callable<Void>{


	private final IMqttClient mclient;

	public static final String TOPIC2 = "coap://localhost:5683/hello-world-obs";

	public  CoapObservingClientProcess(IMqttClient mclient) throws Exception{
		this.mclient = mclient;
	}

	public Void call() throws Exception {        

		if ( !mclient.isConnected()) {
			return null;
		}   

		MqttMessage msg = readTemp();
		msg.setQos(0);
		msg.setRetained(true);
		mclient.publish(TOPIC2,msg);

		return null;        
	}	

	//COAP start================================================================================================

	public MqttMessage readTemp() throws ConnectorException, IOException  {

		CoapClient client = new CoapClient(TOPIC2);

		System.out.println("OBSERVING..");
		//start OBSERVING
		Request request = Request.newGet().setURI(TOPIC2).setObserve();
		request.setMID(8888);
		byte[] token = "a".getBytes();
		request.setToken(token);

		//instant=relation
		CoapObserveRelation relation = client.observe(request, new CoapHandler() {

			public  void onLoad(CoapResponse response) {

				System.out.println("NOTIFICATION444: "+ response.getResponseText());

			}
			public void onError() {
				System.err.println("OBSERVING FAILED");
			}

		});

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String t= client.get().getResponseText();

		System.out.println("NOTIFICATION555: " +t);
		byte[] payload = t.getBytes();

		return new MqttMessage(payload);  
		
	       

	}
}



