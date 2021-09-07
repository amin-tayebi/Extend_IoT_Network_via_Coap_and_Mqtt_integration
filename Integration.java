//COAP inside MQTT
package com.iot.coap.demo;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * A simple CoAP Synchronous Client implemented using Californium Java Library
 * The client Observe a target resource for 10 Seconds and then cancel the request and ends the execution
 */
public class Integration {

	private final static Logger logger = LoggerFactory.getLogger(Integration.class);
	//private static String SERVER_URI = "tcp://iot.eclipse.org:1883";
	private static String SERVER_URI = "tcp://127.0.0.1:1883";



	public static void main(String [ ] args) throws MqttException {

		    logger.info("SimpleProducer started ...");
			try{

			String publisherId = UUID.randomUUID().toString();
			IMqttClient publisher = new MqttClient(SERVER_URI ,publisherId);

			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			options.setConnectionTimeout(10);
			publisher.connect(options);
			logger.info("Connected !");

	           
			//this is the heart of the MQTT function- it is publish
			CoapObservingClientProcess coapObservingClientProcess = new CoapObservingClientProcess(publisher);
			coapObservingClientProcess.call();
		
			logger.info("Message Sent !");


			publisher.disconnect();
			publisher.close();

			logger.info("Disconnected !");

		}catch (Exception e){
			e.printStackTrace();
		}
	}

}


