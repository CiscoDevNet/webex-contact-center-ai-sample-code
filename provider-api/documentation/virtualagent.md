# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [virtualagent.proto](#virtualagent-proto)
    - [AgentTransfer](#com-cisco-wcc-ccai-v1-AgentTransfer)
    - [DtmfEvents](#com-cisco-wcc-ccai-v1-DtmfEvents)
    - [EndVirtualAgent](#com-cisco-wcc-ccai-v1-EndVirtualAgent)
    - [ExecuteRequest](#com-cisco-wcc-ccai-v1-ExecuteRequest)
    - [ExitEvent](#com-cisco-wcc-ccai-v1-ExitEvent)
    - [InputConfigInfer](#com-cisco-wcc-ccai-v1-InputConfigInfer)
    - [InputEvent](#com-cisco-wcc-ccai-v1-InputEvent)
    - [Intent](#com-cisco-wcc-ccai-v1-Intent)
    - [NLU](#com-cisco-wcc-ccai-v1-NLU)
    - [Prompt](#com-cisco-wcc-ccai-v1-Prompt)
    - [VirtualAgentResult](#com-cisco-wcc-ccai-v1-VirtualAgentResult)
  
    - [Dtmf](#com-cisco-wcc-ccai-v1-Dtmf)
    - [ExitEvent.EventType](#com-cisco-wcc-ccai-v1-ExitEvent-EventType)
    - [InputEvent.EventType](#com-cisco-wcc-ccai-v1-InputEvent-EventType)
    - [InputMode](#com-cisco-wcc-ccai-v1-InputMode)
    - [VirtualAgentResult.ResponseType](#com-cisco-wcc-ccai-v1-VirtualAgentResult-ResponseType)
  
- [Scalar Value Types](#scalar-value-types)



<a name="virtualagent-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## virtualagent.proto
Proto file for the VA flow


<a name="com-cisco-wcc-ccai-v1-AgentTransfer"></a>

### AgentTransfer
Call Transferred to Human Agent


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| metadata | [google.protobuf.Struct](#google-protobuf-Struct) |  | Call Transfer Metadata |






<a name="com-cisco-wcc-ccai-v1-DtmfEvents"></a>

### DtmfEvents
A wrapper of repeated TelephonyDtmf digits.


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| dtmf_events | [Dtmf](#com-cisco-wcc-ccai-v1-Dtmf) | repeated | A sequence of TelephonyDtmf digits. |






<a name="com-cisco-wcc-ccai-v1-EndVirtualAgent"></a>

### EndVirtualAgent
Represents the Virtual Agent End Indication


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| metadata | [google.protobuf.Struct](#google-protobuf-Struct) |  | Call Transfer Metadata |






<a name="com-cisco-wcc-ccai-v1-ExecuteRequest"></a>

### ExecuteRequest
Message containing the details of the event being called by Dialog Manager to be executed on client side.


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| event_name | [string](#string) |  | Name of the event to be used to return back to the callflow. |
| event_data | [string](#string) |  | Custom data to be used by the client to execute the request. |






<a name="com-cisco-wcc-ccai-v1-ExitEvent"></a>

### ExitEvent
Event received from the Virtual Agent


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| event_type | [ExitEvent.EventType](#com-cisco-wcc-ccai-v1-ExitEvent-EventType) |  | Event Type |
| name | [string](#string) |  | Optional: To be used for the custom event. |
| metadata | [google.protobuf.Struct](#google-protobuf-Struct) |  | Optional: Map to pass the custom params. |






<a name="com-cisco-wcc-ccai-v1-InputConfigInfer"></a>

### InputConfigInfer
Input config for the client.


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| dtmf_config | [DTMFConfig](#com-cisco-wcc-ccai-v1-DTMFConfig) |  | DTMF Input Configurations |
| speech_timers | [SpeechTimers](#com-cisco-wcc-ccai-v1-SpeechTimers) |  | Represents the TimerInfo object |






<a name="com-cisco-wcc-ccai-v1-InputEvent"></a>

### InputEvent
Represents the Events


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| event_type | [InputEvent.EventType](#com-cisco-wcc-ccai-v1-InputEvent-EventType) |  | Event Type |
| name | [string](#string) |  | Optional: To be used for the custom event. |
| parameters | [google.protobuf.Struct](#google-protobuf-Struct) |  | Optional: Map to pass the custom params. |






<a name="com-cisco-wcc-ccai-v1-Intent"></a>

### Intent
Represents the Intent Detected form user utterance


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| name | [string](#string) |  | Name of the Intent |
| display_name | [string](#string) |  | Display name of the Intent |
| parameters | [google.protobuf.Struct](#google-protobuf-Struct) |  | Parameters of an Intent, filled / not filled. |
| match_confidence | [float](#float) |  | Match Confidence |






<a name="com-cisco-wcc-ccai-v1-NLU"></a>

### NLU
NLU Object generated from User Utterance.


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reply_text | [string](#string) | repeated | Response in text. This will be used for Virtual Agent Transcript. |
| intent | [Intent](#com-cisco-wcc-ccai-v1-Intent) |  | Intent detected from the last utterance. |
| agent_transfer | [AgentTransfer](#com-cisco-wcc-ccai-v1-AgentTransfer) |  | **Deprecated.** Sent when the call is transferred to Agent |
| end_virtual_agent | [EndVirtualAgent](#com-cisco-wcc-ccai-v1-EndVirtualAgent) |  | **Deprecated.** Call Ended |
| input_text | [string](#string) |  | user input uttered by caller |
| exit_event | [ExitEvent](#com-cisco-wcc-ccai-v1-ExitEvent) |  | Exit Event to return the control back to the calling flow |






<a name="com-cisco-wcc-ccai-v1-Prompt"></a>

### Prompt
Prompt object to be played by Client.
Google / Nuance
// Google: Merge the response messages with text and audio into one Prompt object.
// Nuance:


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| text | [string](#string) |  | Response in text. |
| audio_uri | [string](#string) |  | Response in the form of a Audio URL. Client Need to play the locally. Optional |
| audio_content | [bytes](#bytes) |  | Response in the form of Audio Content. In case of Chunked audio this will repeat. Optional |
| bargein | [bool](#bool) |  | Whether the current prompt is barge-in enabled. Optional, Default: false |
| final | [bool](#bool) |  | Whether the current chunk is last chunk. Optional, Default: false |






<a name="com-cisco-wcc-ccai-v1-VirtualAgentResult"></a>

### VirtualAgentResult
Represents the Response of Virtual Agent


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| prompts | [Prompt](#com-cisco-wcc-ccai-v1-Prompt) | repeated | Return 1 or more prompts to be played by client |
| response_payload | [string](#string) |  | Provider specific Response object in JSON encoded string |
| nlu | [NLU](#com-cisco-wcc-ccai-v1-NLU) |  | NLU |
| input_mode | [InputMode](#com-cisco-wcc-ccai-v1-InputMode) |  | Input Mode for the next input |
| is_secure | [bool](#bool) |  | secure flag from provider to indicate sensitive input |
| execute_request | [ExecuteRequest](#com-cisco-wcc-ccai-v1-ExecuteRequest) |  | **Deprecated.** Request to execute the routing at client side by breaking the callflow. |
| response_type | [VirtualAgentResult.ResponseType](#com-cisco-wcc-ccai-v1-VirtualAgentResult-ResponseType) |  | VA response type from provider (Partial/Final) |
| input_config_infer | [InputConfigInfer](#com-cisco-wcc-ccai-v1-InputConfigInfer) |  | Input config for the clients to handle timeouts and buffering of DTMF user inputs |





 


<a name="com-cisco-wcc-ccai-v1-Dtmf"></a>

### Dtmf
[DTMF](https://en.wikipedia.org/wiki/Dual-tone_multi-frequency_signaling)
dtmf digit

| Name | Number | Description |
| ---- | ------ | ----------- |
| TELEPHONY_DTMF_UNSPECIFIED | 0 | Not specified. |
| DTMF_ONE | 1 | Number: &#39;1&#39;. |
| DTMF_TWO | 2 | Number: &#39;2&#39;. |
| DTMF_THREE | 3 | Number: &#39;3&#39;. |
| DTMF_FOUR | 4 | Number: &#39;4&#39;. |
| DTMF_FIVE | 5 | Number: &#39;5&#39;. |
| DTMF_SIX | 6 | Number: &#39;6&#39;. |
| DTMF_SEVEN | 7 | Number: &#39;7&#39;. |
| DTMF_EIGHT | 8 | Number: &#39;8&#39;. |
| DTMF_NINE | 9 | Number: &#39;9&#39;. |
| DTMF_ZERO | 10 | Number: &#39;0&#39;. |
| DTMF_A | 11 | Letter: &#39;A&#39;. |
| DTMF_B | 12 | Letter: &#39;B&#39;. |
| DTMF_C | 13 | Letter: &#39;C&#39;. |
| DTMF_D | 14 | Letter: &#39;D&#39;. |
| DTMF_STAR | 15 | Asterisk/star: &#39;*&#39;. |
| DTMF_POUND | 16 | Pound/diamond/hash/square/gate/octothorpe: &#39;#&#39;. |



<a name="com-cisco-wcc-ccai-v1-ExitEvent-EventType"></a>

### ExitEvent.EventType


| Name | Number | Description |
| ---- | ------ | ----------- |
| UNSPECIFIED | 0 |  |
| VA_CALL_END | 1 |  |
| AGENT_TRANSFER | 2 |  |
| CUSTOM | 3 |  |



<a name="com-cisco-wcc-ccai-v1-InputEvent-EventType"></a>

### InputEvent.EventType


| Name | Number | Description |
| ---- | ------ | ----------- |
| UNSPECIFIED | 0 |  |
| CALL_START | 1 | If sent this event will make the connector to start the session with provider. |
| CALL_END | 2 |  |
| CUSTOM | 3 |  |
| NO_INPUT | 4 | No input event in case of no input received from caller. |
| START_OF_DTMF | 5 | event to indicate start of dtmf input. |



<a name="com-cisco-wcc-ccai-v1-InputMode"></a>

### InputMode
Type of Input expected from User

| Name | Number | Description |
| ---- | ------ | ----------- |
| INPUT_MODE_UNSPECIFIED | 0 |  |
| INPUT_VOICE | 1 |  |
| INPUT_DTMF | 2 |  |
| INPUT_VOICE_DTMF | 3 |  |



<a name="com-cisco-wcc-ccai-v1-VirtualAgentResult-ResponseType"></a>

### VirtualAgentResult.ResponseType


| Name | Number | Description |
| ---- | ------ | ----------- |
| RESPONSE_FINAL | 0 | No more response expected |
| RESPONSE_PARTIAL | 1 | Expect more response from server |


 

 

 



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

