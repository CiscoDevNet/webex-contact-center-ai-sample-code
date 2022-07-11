# CCAI Serving API

CCAI Serving API interface is the serving layer to fetch the insights of the call. It can offer the following insights for the following stage of a call.
- Historic Insights of IVR Conversation.
- Live / Historic Insights of Caller / Agent Conversation.

CCAI Serving API Service runs a gRPC Server which can be connected from a thick Client by implementing gRPC client or by Browser directly by connecting using grpc-web protocol.

The serving API gRPC Endpoint: https://serving-api-streaming.wxcc-us1.cisco.com/serving-api-streaming/v1/
The serving API HTTP Ping Endpoint: https://serving-api-streaming.wxcc-us1.cisco.com/serving-api-streaming/v1/ping
