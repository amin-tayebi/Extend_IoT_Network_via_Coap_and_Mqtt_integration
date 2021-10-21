# integration of CoAp and MQTT PROTOCOLs
1-install eclips include californium
2-run coap server as a java app in eclips
3-run a local MQTT-BROKER
4-run COAP-MQTT integration module
5-run subscriber to subscribe the topic in the MQTT-BROKER

# As a part of discovering the services offered by a CoAP server, a client has to learn about the endpoint used by a server.

- A server is discovered by a client (knowing or) learning a URI that references a resource in the namespace of the server.

- The CoAP default port number 5683 MUST be supported by a server that offers resources for the resource-discovery and SHOULD be supported for providing access to
other resources.

- The default port number 5684 for DTLS-secured CoAP MAY be supported by a server for resource discovery and for providing access to other resources. In addition, other endpoints may be hosted at other ports, space.
