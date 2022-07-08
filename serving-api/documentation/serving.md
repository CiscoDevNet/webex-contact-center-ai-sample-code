# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [serving.proto](#serving-proto)
    - [InsightServingRequest](#com-cisco-wcc-ccai-v1-InsightServingRequest)
    - [InsightServingResponse](#com-cisco-wcc-ccai-v1-InsightServingResponse)
    - [ResponseContent](#com-cisco-wcc-ccai-v1-ResponseContent)
    - [StreamingInsightServingRequest](#com-cisco-wcc-ccai-v1-StreamingInsightServingRequest)
    - [StreamingInsightServingResponse](#com-cisco-wcc-ccai-v1-StreamingInsightServingResponse)
  
    - [InsightServingResponse.Role](#com-cisco-wcc-ccai-v1-InsightServingResponse-Role)
    - [InsightServingResponse.ServiceProvider](#com-cisco-wcc-ccai-v1-InsightServingResponse-ServiceProvider)
    - [InsightServingResponse.ServiceType](#com-cisco-wcc-ccai-v1-InsightServingResponse-ServiceType)
  
    - [AiInsight](#com-cisco-wcc-ccai-v1-AiInsight)
  
- [Scalar Value Types](#scalar-value-types)



<a name="serving-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## serving.proto
This proto file contains the Serving API
Service streaming gRPC calls for retrieving the streaming insights based on the conversation id


<a name="com-cisco-wcc-ccai-v1-InsightServingRequest"></a>

### InsightServingRequest
Represents the request content for retrieving AI Insights


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| conversationId | [string](#string) |  | Required. Conversation ID for which insights are needed. The subscription will start listening to any insights for this conversation across multiple legs( IVR, Caller, Agent) and services (Transcription, Agent Assist) |
| orgId | [string](#string) |  | Required.Control Hub OrgID for the org, this conversation belongs to. The Access token should have authorization for this Org. |
| realTimeTranscripts | [bool](#bool) |  | Is real time transcripts required? Default: false |
| historicalTranscripts | [bool](#bool) |  | Is historical transcripts from start of the conversation required? Default: false |
| realtimeAgentAssist | [bool](#bool) |  | Is real time agent assist required? Default: False |
| historicalAgentAssist | [bool](#bool) |  | Is historical agent assist from start of the conversation required? Default: false |






<a name="com-cisco-wcc-ccai-v1-InsightServingResponse"></a>

### InsightServingResponse
Represents the insight serving response


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| orgId | [string](#string) |  | Org Identifier (control hub) for which the insights need to be delivered |
| conversationId | [string](#string) |  | Identifier for the Conversation. Equivalent to Call ID, CallGUID etc. |
| roleId | [string](#string) |  | Identifier for the individual leg, based on the party. GUID |
| utteranceId | [string](#string) |  | Identifier for a given utterance. The same utterance ID will be published for the transcript utterance and the insights generated from it |
| role | [InsightServingResponse.Role](#com-cisco-wcc-ccai-v1-InsightServingResponse-Role) |  | Role specifying IVR, Caller or Agent |
| insightType | [InsightServingResponse.ServiceType](#com-cisco-wcc-ccai-v1-InsightServingResponse-ServiceType) |  | Type of insight : ASR, Agent Assist etc. |
| insightProvider | [InsightServingResponse.ServiceProvider](#com-cisco-wcc-ccai-v1-InsightServingResponse-ServiceProvider) |  | Service Provider who produced this insight. |
| publishTimestamp | [int64](#int64) |  | Epoch Timestamp when this insight record was created/published. This field is always available, can be used for sorting messages by time. |
| startTimestamp | [int64](#int64) |  | Start time corresponds to the start time to which this insight belongs. Epoch Timestamp. These are optional fields, not always available |
| endTimestamp | [int64](#int64) |  | End time corresponds to the speech end time to which this insight belongs. Epoch Timestamp. These are optional fields, not always available |
| isFinal | [bool](#bool) |  | Whether the insight is final or intermediate. Intermediate results will be overridden by the final result that follows them. |
| responseContent | [ResponseContent](#com-cisco-wcc-ccai-v1-ResponseContent) |  | Content of the insight. This will vary based on the type of insight |
| messageId | [string](#string) |  | message id |






<a name="com-cisco-wcc-ccai-v1-ResponseContent"></a>

### ResponseContent
Represents the response content message


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| rawContent | [string](#string) |  | Placeholder for any other types. Not returned unless stated |
| recognitionResult | [StreamingRecognitionResult](#com-cisco-wcc-ccai-v1-StreamingRecognitionResult) |  | For Service Type = TRANSCRIPTION |
| agentAnswerResult | [AgentAnswer](#com-cisco-wcc-ccai-v1-AgentAnswer) |  | For Service Type = AGENT_ANSWERS |






<a name="com-cisco-wcc-ccai-v1-StreamingInsightServingRequest"></a>

### StreamingInsightServingRequest
Represents the request for retrieving insights for a given conversation ID


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| insightServingRequest | [InsightServingRequest](#com-cisco-wcc-ccai-v1-InsightServingRequest) |  | Insight serving request |






<a name="com-cisco-wcc-ccai-v1-StreamingInsightServingResponse"></a>

### StreamingInsightServingResponse
Response returned with Insights. There would be multiple messages in the stream.
Each service type may have zero or more messages


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| insightServingResponse | [InsightServingResponse](#com-cisco-wcc-ccai-v1-InsightServingResponse) |  | Insight serving response |





 


<a name="com-cisco-wcc-ccai-v1-InsightServingResponse-Role"></a>

### InsightServingResponse.Role
Identifier for the party.

| Name | Number | Description |
| ---- | ------ | ----------- |
| IVR | 0 | Role - IVR |
| CALLER | 1 | Role - Caller |
| AGENT | 2 | Role - Agent |



<a name="com-cisco-wcc-ccai-v1-InsightServingResponse-ServiceProvider"></a>

### InsightServingResponse.ServiceProvider
Provider List for Services

| Name | Number | Description |
| ---- | ------ | ----------- |
| DEFAULT | 0 | Service provider - Default |
| GOOGLE | 1 | Service provider - Google |



<a name="com-cisco-wcc-ccai-v1-InsightServingResponse-ServiceType"></a>

### InsightServingResponse.ServiceType
Type of service this Insight belongs to

| Name | Number | Description |
| ---- | ------ | ----------- |
| DEFAULT_TRANSCRIPTION | 0 | Service type - Default |
| AGENT_ANSWERS | 1 | Service type - Agent answers |
| TRANSCRIPTION | 2 | Service type - Transcription |


 

 


<a name="com-cisco-wcc-ccai-v1-AiInsight"></a>

### AiInsight
Service to subscribe to, to get AI Insights

| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| StreamingInsightServing | [StreamingInsightServingRequest](#com-cisco-wcc-ccai-v1-StreamingInsightServingRequest) | [StreamingInsightServingResponse](#com-cisco-wcc-ccai-v1-StreamingInsightServingResponse) stream | Server side Streaming gRPC Call that produces streaming insights for a given conversation ID |

 



## Scalar Value Types

| .proto Type | Notes | C++ | Java | Python | Go | C# | PHP | Ruby |
| ----------- | ----- | --- | ---- | ------ | -- | -- | --- | ---- |
| <a name="double" /> double |  | double | double | float | float64 | double | float | Float |
| <a name="float" /> float |  | float | float | float | float32 | float | float | Float |
| <a name="int32" /> int32 | Uses variable-length encoding. Inefficient for encoding negative numbers – if your field is likely to have negative values, use sint32 instead. | int32 | int | int | int32 | int | integer | Bignum or Fixnum (as required) |
| <a name="int64" /> int64 | Uses variable-length encoding. Inefficient for encoding negative numbers – if your field is likely to have negative values, use sint64 instead. | int64 | long | int/long | int64 | long | integer/string | Bignum |
| <a name="uint32" /> uint32 | Uses variable-length encoding. | uint32 | int | int/long | uint32 | uint | integer | Bignum or Fixnum (as required) |
| <a name="uint64" /> uint64 | Uses variable-length encoding. | uint64 | long | int/long | uint64 | ulong | integer/string | Bignum or Fixnum (as required) |
| <a name="sint32" /> sint32 | Uses variable-length encoding. Signed int value. These more efficiently encode negative numbers than regular int32s. | int32 | int | int | int32 | int | integer | Bignum or Fixnum (as required) |
| <a name="sint64" /> sint64 | Uses variable-length encoding. Signed int value. These more efficiently encode negative numbers than regular int64s. | int64 | long | int/long | int64 | long | integer/string | Bignum |
| <a name="fixed32" /> fixed32 | Always four bytes. More efficient than uint32 if values are often greater than 2^28. | uint32 | int | int | uint32 | uint | integer | Bignum or Fixnum (as required) |
| <a name="fixed64" /> fixed64 | Always eight bytes. More efficient than uint64 if values are often greater than 2^56. | uint64 | long | int/long | uint64 | ulong | integer/string | Bignum |
| <a name="sfixed32" /> sfixed32 | Always four bytes. | int32 | int | int | int32 | int | integer | Bignum or Fixnum (as required) |
| <a name="sfixed64" /> sfixed64 | Always eight bytes. | int64 | long | int/long | int64 | long | integer/string | Bignum |
| <a name="bool" /> bool |  | bool | boolean | boolean | bool | bool | boolean | TrueClass/FalseClass |
| <a name="string" /> string | A string must always contain UTF-8 encoded or 7-bit ASCII text. | string | String | str/unicode | string | string | string | String (UTF-8) |
| <a name="bytes" /> bytes | May contain any arbitrary sequence of bytes. | string | ByteString | str | []byte | ByteString | string | String (ASCII-8BIT) |

