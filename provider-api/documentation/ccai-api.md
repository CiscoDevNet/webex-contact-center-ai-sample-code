# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [ccai-api.proto](#ccai-api-proto)
    - [AnswerFeedback](#com-cisco-wcc-ccai-v1-AnswerFeedback)
    - [AnswerFeedbackRequest](#com-cisco-wcc-ccai-v1-AnswerFeedbackRequest)
    - [AnswerFeedbackResponse](#com-cisco-wcc-ccai-v1-AnswerFeedbackResponse)
    - [AnswerRecord](#com-cisco-wcc-ccai-v1-AnswerRecord)
    - [OutputAudioConfig](#com-cisco-wcc-ccai-v1-OutputAudioConfig)
    - [RecognitionConfig](#com-cisco-wcc-ccai-v1-RecognitionConfig)
    - [StreamingAnalyzeContentRequest](#com-cisco-wcc-ccai-v1-StreamingAnalyzeContentRequest)
    - [StreamingAnalyzeContentRequest.AdditionalInfoEntry](#com-cisco-wcc-ccai-v1-StreamingAnalyzeContentRequest-AdditionalInfoEntry)
    - [StreamingAnalyzeContentResponse](#com-cisco-wcc-ccai-v1-StreamingAnalyzeContentResponse)
  
    - [AudioEncoding](#com-cisco-wcc-ccai-v1-AudioEncoding)
    - [CorrectnessLevel](#com-cisco-wcc-ccai-v1-CorrectnessLevel)
    - [OperationStatus](#com-cisco-wcc-ccai-v1-OperationStatus)
    - [Role](#com-cisco-wcc-ccai-v1-Role)
    - [StreamingAnalyzeContentRequest.RequestType](#com-cisco-wcc-ccai-v1-StreamingAnalyzeContentRequest-RequestType)
  
    - [AnalyzeContentService](#com-cisco-wcc-ccai-v1-AnalyzeContentService)
  
- [Scalar Value Types](#scalar-value-types)



<a name="ccai-api-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## ccai-api.proto
This proto file has APIs for retrieving streaming content analyze responses from the the upstream
For the streaming analyze content requests that is sent from orchestrator, relevant streaming
analyze content response will be sent by the upstream connectors.


<a name="com-cisco-wcc-ccai-v1-AnswerFeedback"></a>

### AnswerFeedback
Represents the Answer Feedback message


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| correctness_level | [CorrectnessLevel](#com-cisco-wcc-ccai-v1-CorrectnessLevel) |  | Field to set the correctness level for answer record |
| clicked | [bool](#bool) |  | to capture whether answer record is clicked or not |
| displayed | [bool](#bool) |  | to capture whether answer record displayed or not |






<a name="com-cisco-wcc-ccai-v1-AnswerFeedbackRequest"></a>

### AnswerFeedbackRequest
Request message for the answer feedback


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| answer_record | [AnswerRecord](#com-cisco-wcc-ccai-v1-AnswerRecord) |  | Answer record for which the feedback to be obtained |
| ccaiConfigId | [string](#string) |  | config id |
| orgId | [string](#string) |  | OrgId for supporting OrgId based access |






<a name="com-cisco-wcc-ccai-v1-AnswerFeedbackResponse"></a>

### AnswerFeedbackResponse
Returns only operation status for update operation.


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| status | [OperationStatus](#com-cisco-wcc-ccai-v1-OperationStatus) |  | Status denoting unknown/success/failure |






<a name="com-cisco-wcc-ccai-v1-AnswerRecord"></a>

### AnswerRecord
Represents the Answer record message with name and feedback information


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| name | [string](#string) |  | Unique Answer record name |
| answer_feedback | [AnswerFeedback](#com-cisco-wcc-ccai-v1-AnswerFeedback) |  | Answer feedback |






<a name="com-cisco-wcc-ccai-v1-OutputAudioConfig"></a>

### OutputAudioConfig
Represents the Output audio config object


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| config | [AudioConfig](#com-cisco-wcc-ccai-v1-AudioConfig) |  | audio config |






<a name="com-cisco-wcc-ccai-v1-RecognitionConfig"></a>

### RecognitionConfig
Represents the message for recognition config with audio encoding, sample rate hertz and language code


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| encoding | [AudioEncoding](#com-cisco-wcc-ccai-v1-AudioEncoding) |  | Type of audio encoding |
| sample_rate_hertz | [int32](#int32) |  | sample rate in hertz |
| language_code | [string](#string) |  | languate code |
| caller_channel_number | [int32](#int32) |  | Channel number which has the caller stream in a multichannel file URL. Other streams will be considered as Agent Stream. Default value will be based on RTMS usecase |
| model | [Model](#com-cisco-wcc-ccai-v1-Model) |  | Model definition to be used for recognition |






<a name="com-cisco-wcc-ccai-v1-StreamingAnalyzeContentRequest"></a>

### StreamingAnalyzeContentRequest
Represents the streamingAnalyzeContentRequest message to be sent to the connectors with config/audio/text/dtmf information


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| audio | [bytes](#bytes) |  | audio |
| text | [string](#string) |  | text |
| dtmf | [DtmfEvents](#com-cisco-wcc-ccai-v1-DtmfEvents) |  | dtmf |
| event | [InputEvent](#com-cisco-wcc-ccai-v1-InputEvent) |  | event |
| url | [string](#string) |  | URL to stream the audio |
| inputAudioConfig | [RecognitionConfig](#com-cisco-wcc-ccai-v1-RecognitionConfig) |  | InputAudio Recognition Config |
| interim_results | [bool](#bool) |  | Is the interim results required? |
| single_utterance | [bool](#bool) |  | Is this a single utterance? |
| conversationId | [string](#string) |  | Unique conversationId. Callguid will be used as conversationId |
| roleId | [string](#string) |  | CALLGUID &#43; StreamId can form the global unique Id for tracking stream |
| ccaiConfigId | [string](#string) |  | Indicates the global config id for services. |
| role | [Role](#com-cisco-wcc-ccai-v1-Role) |  | Role specifying the type of user |
| additionalInfo | [StreamingAnalyzeContentRequest.AdditionalInfoEntry](#com-cisco-wcc-ccai-v1-StreamingAnalyzeContentRequest-AdditionalInfoEntry) | repeated | Map to capture any additional or miscellaneous info |
| orgId | [string](#string) |  | OrgId for supporting OrgId based access |
| outputAudioConfig | [OutputAudioConfig](#com-cisco-wcc-ccai-v1-OutputAudioConfig) |  | Output audio config |
| requestType | [StreamingAnalyzeContentRequest.RequestType](#com-cisco-wcc-ccai-v1-StreamingAnalyzeContentRequest-RequestType) |  | Type of the request indicating the call stage at which the API is called. |
| cmsConfig | [CmsConfig](#com-cisco-wcc-ccai-v1-CmsConfig) |  | passing cms config object to upstream services |
| urlTimestamp | [int64](#int64) |  | start timestamp for the url based transcripts, epoch time in millis |
| enablePartialResponse | [bool](#bool) |  | Flag to enable partial response for VA |
| consumerInfo | [ConsumerInfo](#com-cisco-wcc-ccai-v1-ConsumerInfo) |  | Any additional client information can be passed using this object |






<a name="com-cisco-wcc-ccai-v1-StreamingAnalyzeContentRequest-AdditionalInfoEntry"></a>

### StreamingAnalyzeContentRequest.AdditionalInfoEntry



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| key | [string](#string) |  |  |
| value | [string](#string) |  |  |






<a name="com-cisco-wcc-ccai-v1-StreamingAnalyzeContentResponse"></a>

### StreamingAnalyzeContentResponse
Represents the StreamingAnalyzeContentResponse object that is received from connector


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| recognition_result | [StreamingRecognitionResult](#com-cisco-wcc-ccai-v1-StreamingRecognitionResult) |  | Recognition result corresponding to a portion of the audio that is being processed |
| agentAnswerResult | [AgentAnswerResult](#com-cisco-wcc-ccai-v1-AgentAnswerResult) |  | Agent answer information for the audio being processed |
| message | [Message](#com-cisco-wcc-ccai-v1-Message) |  | Message object with various attributes including timestamp and sentiment analysis |
| reply_text | [string](#string) |  | **Deprecated.** Reply text |
| reply_audio | [bytes](#bytes) |  | **Deprecated.** Reply audio, use VirtualAgentResult.Prompt.audio_content instead |
| reply_payload | [string](#string) |  | **Deprecated.** Reply payload |
| va_result | [VirtualAgentResult](#com-cisco-wcc-ccai-v1-VirtualAgentResult) |  | Response of Virtual Agent |





 


<a name="com-cisco-wcc-ccai-v1-AudioEncoding"></a>

### AudioEncoding
Type of the audio encoding - could be
linear16,
mulaw or
alaw

| Name | Number | Description |
| ---- | ------ | ----------- |
| ENCODING_UNSPECIFIED | 0 | Not specified. |
| LINEAR16 | 1 | Uncompressed 16-bit signed little-endian samples (Linear PCM).

TODO : Do we still need this, if not remove. |
| MULAW | 2 | 8-bit samples that compound 14-bit audio samples using G.711 PCMU/mu-law. |
| ALAW | 3 | alaw format |



<a name="com-cisco-wcc-ccai-v1-CorrectnessLevel"></a>

### CorrectnessLevel
Type of the correctness level to be included in answers feedback

| Name | Number | Description |
| ---- | ------ | ----------- |
| CORRECTNESS_LEVEL_UNSPECIFIED | 0 | level unspecified |
| NOT_CORRECT | 1 | not correct |
| PARTIALLY_CORRECT | 2 | partially correct |
| FULLY_CORRECT | 3 | fully correct |



<a name="com-cisco-wcc-ccai-v1-OperationStatus"></a>

### OperationStatus
Represents the Types of status to be included in the answer feedback response

| Name | Number | Description |
| ---- | ------ | ----------- |
| UNKNOWN | 0 | type unknown |
| SUCCESS | 1 | type success |
| FAILURE | 2 | type failure |



<a name="com-cisco-wcc-ccai-v1-Role"></a>

### Role
Type of the user - could be
human agent,
virtual agent or
end user

| Name | Number | Description |
| ---- | ------ | ----------- |
| ROLE_UNSPECIFIED | 0 |  |
| HUMAN_AGENT | 1 |  |
| AUTOMATED_AGENT | 2 |  |
| END_USER | 3 |  |



<a name="com-cisco-wcc-ccai-v1-StreamingAnalyzeContentRequest-RequestType"></a>

### StreamingAnalyzeContentRequest.RequestType


| Name | Number | Description |
| ---- | ------ | ----------- |
| DEFAULT_UNSPECIFIED | 0 | Default Value |
| VIRTUAL_AGENT | 1 | Virtual Agent Request |
| AGENT_ASSIST | 2 | Agent Assist Request |


 

 


<a name="com-cisco-wcc-ccai-v1-AnalyzeContentService"></a>

### AnalyzeContentService
Analyze content service between orchestrator and connectors

| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| StreamingAnalyzeContent | [StreamingAnalyzeContentRequest](#com-cisco-wcc-ccai-v1-StreamingAnalyzeContentRequest) stream | [StreamingAnalyzeContentResponse](#com-cisco-wcc-ccai-v1-StreamingAnalyzeContentResponse) stream | Bi-Directional Streaming API for streaming the audio content to connectors |
| AnswerFeedback | [AnswerFeedbackRequest](#com-cisco-wcc-ccai-v1-AnswerFeedbackRequest) | [AnswerFeedbackResponse](#com-cisco-wcc-ccai-v1-AnswerFeedbackResponse) | Set Feedback API |

 



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

