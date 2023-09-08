# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [suggestions.proto](#suggestions-proto)
    - [AgentAnswer](#com-cisco-wcc-ccai-v1-AgentAnswer)
    - [AgentAnswerResult](#com-cisco-wcc-ccai-v1-AgentAnswerResult)
    - [Answer](#com-cisco-wcc-ccai-v1-Answer)
    - [Answer.MetadataEntry](#com-cisco-wcc-ccai-v1-Answer-MetadataEntry)
    - [range](#com-cisco-wcc-ccai-v1-range)
  
- [Scalar Value Types](#scalar-value-types)



<a name="suggestions-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## suggestions.proto
Proto file with messages related to Agent Answers


<a name="com-cisco-wcc-ccai-v1-AgentAnswer"></a>

### AgentAnswer
Represents the AgentAnswer object with answers and tags


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| answers | [Answer](#com-cisco-wcc-ccai-v1-Answer) | repeated | List of answers |
| tags | [string](#string) | repeated | List of tags |






<a name="com-cisco-wcc-ccai-v1-AgentAnswerResult"></a>

### AgentAnswerResult
Represents the AgentAnswerResult object with agent answer information


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| agentanswer | [AgentAnswer](#com-cisco-wcc-ccai-v1-AgentAnswer) |  | Agent Answers |






<a name="com-cisco-wcc-ccai-v1-Answer"></a>

### Answer
Represents Answer which is cumulative response of FaqAnswer and ArticleAnswer


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title | [string](#string) |  | ArticleAnswer Field - title |
| uri | [string](#string) |  | ArticleAnswer Field - uri |
| snippets | [string](#string) | repeated | ArticleAnswer Field - snippets |
| description | [string](#string) |  | Faq Answer Field - description |
| confidence | [float](#float) |  | Faq Answer Field - confidence |
| source | [string](#string) |  | Faq Answer Field - source |
| metadata | [Answer.MetadataEntry](#com-cisco-wcc-ccai-v1-Answer-MetadataEntry) | repeated | Common Field - metadata |
| answer_record | [string](#string) |  | Common Field - answer record |
| highlight | [range](#com-cisco-wcc-ccai-v1-range) | repeated | Common Fields - highlight |






<a name="com-cisco-wcc-ccai-v1-Answer-MetadataEntry"></a>

### Answer.MetadataEntry



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| key | [string](#string) |  |  |
| value | [string](#string) |  |  |






<a name="com-cisco-wcc-ccai-v1-range"></a>

### range
Represents the range object with start and end integer values


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| start | [int32](#int32) |  | start |
| end | [int32](#int32) |  | end |





 

 

 

 



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

