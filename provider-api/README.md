# Architecture

![Architecture](https://user-images.githubusercontent.com/5176062/115956139-c2be9480-a518-11eb-9aa6-6906b070b00b.png)

1.	A Contact Center Client will use AI in two different personas.
a.	Virtual Agent (VA): This is the persona in which Caller will deal with IVR / Virtual Assistant, which will be capable of Voice / DTMF inputs which will be processed by an NLP engine and provides the response in the form of synthesized audio.
b.	Agent Assist (AA): This is the persona where Caller / Agent Audio streams are analyzed and the conversation is transcribed and analyzed for different Agent Assist features like Answers / Sentiments etc on the Agent Desktop.
2.	The VA / AA request goes through API Gateway for billing / throttling.
3.	CCAI Orchestrator Platform will based on the request fetch the Config which will have the Connector details.
4.	Orchestrator to establish the connection with the Connector based on the credentials provided by the config.
5.	Orchestrator sends the request to External AI Connector over standard gRPC protobuf spec defined by Orchestrator.
6.	External AI Connector receives the media and translate it into the desired External AI Service format and route it to the request.
7.	The responses received from External AI Services are returned back to Orchestrator by the External AI Connector.

## Virtual Agent Callflow
![VA](https://user-images.githubusercontent.com/5176062/116988874-a37cef80-acee-11eb-9123-bd7fa37373f0.jpg)

## Agent Assist Callflow
![AA](https://user-images.githubusercontent.com/5176062/116988897-ab3c9400-acee-11eb-8dd0-5bfe932a4acf.jpg)

# Onboarding
Webex Contact Center Control Hub to be used by the customer to create a configuration. 
1.	A Connector will need to be created for each provider which includes the Provider endpoint, its services and Auth token.
2.	A configuration will need to be created which is represented by a configId and will define the Services / Features to be used. 
3.	Orchestrator will orchestrate the call to a specific provider and add the necessary feature flags based on the config details.
API
For detailed description of the API, please refer the protobuf files.
•	StreamingAnalyzeVAContent: API to be called for Virtual Agent request.
•	StreamingAnalyzeAAContent: API to be used for Agent Assist request.
Request: The requests will be passed with the generic params defined in the protobuf and Provider specific params will be set in the Map contained in the request in the form of Key / Value.
Response: The response will need to populate the generic params defined in the protobuf in the specific params and Provider specific objects will be passed back to client in the form of object json.
Features: The request will also carry the desired features needed on the incoming request. The response object should return the responses accordingly.
# Billing
1.	API Gateway to perform the Metering and Billing for the CCAI Platform on per Webex CI tenant basis.
2.	Providers can perform Billing based on the onboarding auth key offered which will be different for each CCAI Config.
# Authentication
Provider to provide a mechanism to create a tenant specific auth key / token. Which will be onboarded during Onboarding. This key/token will be offered by Orchestrator to External Connector for Authentication, it will also be used by External Connector to identify the tenant and billing.
TBD: Design for the Auth Server in Provider.
# Serviceability
Each Provider endpoint to expose certain APIs to monitor the health of endpoint.
https://<Service endpoint>/<service Name>/v1/ping
Response:
```sh
{
"serviceName": "<Service Name>",
"serviceType": "REQUIRED",
"serviceState": "online",
"message": "<Service Name> is ONLINE",
"lastUpdated": "2021-01-22T12:24:37.382Z",
}
```
# Glossary
* Connector: Integration details to a Provider.
* Provider: AI service provider.
* AI Service: Service / Feature offered by a Provider.
