# CCAI Serving API

CCAI Serving API interface is the serving layer to fetch the insights of the call. It can offer the following:
- Historic Insights of IVR Conversation.
- Live / Historic Insights of Caller / Agent Conversation.

> Note: Historic insights of a conversation are available only for a shorter duration.

CCAI Serving API Service runs a gRPC Server which can be connected from a thick Client by implementing gRPC client or by Browser directly by connecting using grpc-web protocol.

- Serving API gRPC Endpoint: serving-api-streaming.wxcc-_<<data-center>>_.cisco.com:443
- Serving API HTTP Ping Endpoint: https://serving-api-streaming.wxcc-_<<data-center>>_.cisco.com/serving-api-streaming/v1/ping

Example: serving-api-streaming.wxcc-us1.cisco.com:443

![Sequence Diagram](./Serving-Sequence-Diagram.png)

> A sample grpc client implementation based on JavaScript is provided in this repo as a reference. 