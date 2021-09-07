package com.iot.coap.demo;

//import com.iot.demo.mqtt.sensors.EngineTemperatureSensor;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimpleConsumer {

    private final static Logger logger = LoggerFactory.getLogger(SimpleConsumer.class);
    //private static String SERVER_URI = "tcp://iot.eclipse.org:1883";
    private static String SERVER_URI = "tcp://127.0.0.1:1883";

    public static void main(String [ ] args) {

        logger.info("Consumer started ...");

        try{

            String publisherId = UUID.randomUUID().toString();
            IMqttClient subscriber = new MqttClient(SERVER_URI,publisherId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            subscriber.connect(options);

            logger.info("Connected !");

            CountDownLatch receivedSignal = new CountDownLatch(10);
            subscriber.subscribe(CoapObservingClientProcess.TOPIC2, (topic, msg) -> {
                byte[] payload = msg.getPayload();

                logger.info("New Message Received: {}", new String(payload));

                receivedSignal.countDown();
            });
            receivedSignal.await(1, TimeUnit.MINUTES);

        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
