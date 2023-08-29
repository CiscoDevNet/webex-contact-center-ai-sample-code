# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [utils.proto](#utils-proto)
    - [CmsConfig](#com-cisco-wcc-ccai-v1-CmsConfig)
    - [CmsConfig.ConfigEntry](#com-cisco-wcc-ccai-v1-CmsConfig-ConfigEntry)
    - [ConsumerInfo](#com-cisco-wcc-ccai-v1-ConsumerInfo)
  
- [Scalar Value Types](#scalar-value-types)



<a name="utils-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## utils.proto
Proto file contains all the messages that can be used as utilities.


<a name="com-cisco-wcc-ccai-v1-CmsConfig"></a>

### CmsConfig
Represents the CMS Config object to be passed to upstream services


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| connector_id | [string](#string) |  | connector id from cms - E.g. 6225ef12-84f9-4ef0-ba67-5e45954c2945 |
| conversation_profile_id | [string](#string) |  | conversation profile id from cms - E.g. projects/ciscoss-dev-9gkv/conversationProfiles/rLyvp5sqSKCoczWEdMHiJA |
| default_answers | [bool](#bool) |  | default answers enabled / disabled - boolean value |
| default_virtual_agent | [bool](#bool) |  | default virtual agent enabled / disabled - boolean value |
| description | [string](#string) |  | description retrieved from cms - E.g. Google Connector\nProject Name: CiscoSS\nProfile Name: Gourav-cx\nAgent Name: Gourav |
| id | [string](#string) |  | unique identifier - E.g. 2e0ceae1-5aa4-420f-a640-39c7b36e8c70 |
| name | [string](#string) |  | name of the config |
| org_id | [string](#string) |  | org id information - E.g. 1d6b8641-de43-4e8d-b88d-5b5e33e94fda |
| type | [string](#string) |  | type of the provider - E.g. Google or Nuance |
| config | [CmsConfig.ConfigEntry](#com-cisco-wcc-ccai-v1-CmsConfig-ConfigEntry) | repeated | config object - can contain multiple key value pairs |
| product_version | [string](#string) |  | product version - E.g. 2.0 |
| system_default | [bool](#bool) |  |  |






<a name="com-cisco-wcc-ccai-v1-CmsConfig-ConfigEntry"></a>

### CmsConfig.ConfigEntry



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| key | [string](#string) |  |  |
| value | [string](#string) |  |  |






<a name="com-cisco-wcc-ccai-v1-ConsumerInfo"></a>

### ConsumerInfo
Describes the consumer information like home region details


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| wxcc_cluster_id | [string](#string) |  | Represents WxCC Home Cluster Id the Call Belongs to |





 

 

 

 



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

