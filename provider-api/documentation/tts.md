# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [tts.proto](#tts-proto)
    - [AudioConfig](#com-cisco-wcc-ccai-v1-AudioConfig)
    - [Provider](#com-cisco-wcc-ccai-v1-Provider)
    - [ProviderVoices](#com-cisco-wcc-ccai-v1-ProviderVoices)
    - [SpeakInput](#com-cisco-wcc-ccai-v1-SpeakInput)
    - [SpeakerParams](#com-cisco-wcc-ccai-v1-SpeakerParams)
    - [Timepoint](#com-cisco-wcc-ccai-v1-Timepoint)
    - [Voice](#com-cisco-wcc-ccai-v1-Voice)
  
    - [OutputAudioEncoding](#com-cisco-wcc-ccai-v1-OutputAudioEncoding)
    - [SsmlVoiceGender](#com-cisco-wcc-ccai-v1-SsmlVoiceGender)
  
- [Scalar Value Types](#scalar-value-types)



<a name="tts-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## tts.proto
This proto contains all the messages required for tts synthesize service


<a name="com-cisco-wcc-ccai-v1-AudioConfig"></a>

### AudioConfig
Description of audio data to be synthesized.


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| audio_encoding | [OutputAudioEncoding](#com-cisco-wcc-ccai-v1-OutputAudioEncoding) |  | Required. The format of the audio byte stream. |
| speaking_rate | [double](#double) |  | Optional. Input only. Speaking rate/speed, in the range [0.25, 4.0]. 1.0 is the normal native speed supported by the specific voice. 2.0 is twice as fast, and 0.5 is half as fast. If unset(0.0), defaults to the native 1.0 speed. Any other values &lt; 0.25 or &gt; 4.0 will return an error. |
| pitch | [double](#double) |  | Optional. Input only. Speaking pitch, in the range [-20.0, 20.0]. 20 means increase 20 semitones from the original pitch. -20 means decrease 20 semitones from the original pitch. |
| volume_gain_db | [double](#double) |  | Optional. Input only. Volume gain (in dB) of the normal native volume supported by the specific voice, in the range [-96.0, 16.0]. If unset, or set to a value of 0.0 (dB), will play at normal native signal amplitude. A value of -6.0 (dB) will play at approximately half the amplitude of the normal native signal amplitude. A value of &#43;6.0 (dB) will play at approximately twice the amplitude of the normal native signal amplitude. Strongly recommend not to exceed &#43;10 (dB) as there&#39;s usually no effective increase in loudness for any value greater than that. |
| sample_rate_hertz | [int32](#int32) |  | Optional. The synthesis sample rate (in hertz) for this audio. |
| voice | [SpeakerParams](#com-cisco-wcc-ccai-v1-SpeakerParams) |  | Required. The desired voice of the synthesized audio. |






<a name="com-cisco-wcc-ccai-v1-Provider"></a>

### Provider
Represents the Provider object used in list providers api


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| provider_id | [string](#string) |  | Provider id |
| provider_name | [string](#string) |  | Provider Name |






<a name="com-cisco-wcc-ccai-v1-ProviderVoices"></a>

### ProviderVoices
Represents the Provider Voice object mentioning the provider and voice information


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| provider | [Provider](#com-cisco-wcc-ccai-v1-Provider) |  |  |
| voice | [Voice](#com-cisco-wcc-ccai-v1-Voice) | repeated |  |






<a name="com-cisco-wcc-ccai-v1-SpeakInput"></a>

### SpeakInput
Contains text input to be synthesized. Either `text` or `ssml` must be
supplied. Supplying both or neither returns
[google.rpc.Code.INVALID_ARGUMENT][]. The input size is limited to 5000
characters.


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| text | [string](#string) |  | The raw text to be synthesized. |
| ssml | [string](#string) |  | The SSML document to be synthesized. The SSML document must be valid and well-formed. Otherwise the RPC will fail and return [google.rpc.Code.INVALID_ARGUMENT][]. For more information, see [SSML](https://cloud.google.com/text-to-speech/docs/ssml). |
| url | [string](#string) |  | The URL which needs to be fetched and played. |






<a name="com-cisco-wcc-ccai-v1-SpeakerParams"></a>

### SpeakerParams
Description of which voice to use for a synthesis request.


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| language_code | [string](#string) |  | Required. The language (and potentially also the region) of the voice expressed as a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag, e.g. &#34;en-US&#34;. This should not include a script tag (e.g. use &#34;cmn-cn&#34; rather than &#34;cmn-Hant-cn&#34;), because the script will be inferred from the input provided in the SynthesisInput. The TTS service will use this parameter to help choose an appropriate voice. Note that the TTS service may choose a voice with a slightly different language code than the one selected; it may substitute a different region (e.g. using en-US rather than en-CA if there isn&#39;t a Canadian voice available), or even a different language, e.g. using &#34;nb&#34; (Norwegian Bokmal) instead of &#34;no&#34; (Norwegian)&#34;. |
| name | [string](#string) |  | The name of the voice. If not set, the service will choose a voice based on the other parameters such as language_code and gender. |
| ssml_gender | [SsmlVoiceGender](#com-cisco-wcc-ccai-v1-SsmlVoiceGender) |  | The preferred gender of the voice. If not set, the service will choose a voice based on the other parameters such as language_code and name. Note that this is only a preference, not requirement; if a voice of the appropriate gender is not available, the synthesizer should substitute a voice with a different gender rather than failing the request. |






<a name="com-cisco-wcc-ccai-v1-Timepoint"></a>

### Timepoint
This contains a mapping between a certain point in the input text and a
corresponding time in the output audio.


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| mark_name | [string](#string) |  | Timepoint name as received from the client within `&lt;mark&gt;` tag. |
| time_seconds | [double](#double) |  | Time offset in seconds from the start of the synthesized audio. |






<a name="com-cisco-wcc-ccai-v1-Voice"></a>

### Voice
Description of a voice supported by the TTS service.


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| voice_language | [string](#string) |  | The languages that this voice supports, expressed as [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tags (e.g. &#34;en-US&#34;, &#34;es-419&#34;, &#34;cmn-tw&#34;). |
| voice_name | [string](#string) |  | The name of this voice. Each distinct voice has a unique name. |
| voice_gender | [SsmlVoiceGender](#com-cisco-wcc-ccai-v1-SsmlVoiceGender) |  | The gender of this voice. |
| voice_rate | [int32](#int32) |  | The natural sample rate (in hertz) for this voice. |





 


<a name="com-cisco-wcc-ccai-v1-OutputAudioEncoding"></a>

### OutputAudioEncoding
Represents the output audio encoding formats

| Name | Number | Description |
| ---- | ------ | ----------- |
| OUTPUT_ENCODING_UNSPECIFIED | 0 | Not specified. |
| OUTPUT_LINEAR16 | 1 | Uncompressed 16-bit signed little-endian samples (Linear PCM). |
| OUTPUT_MULAW | 2 | 8-bit samples that compound 14-bit audio samples using G.711 PCMU/mu-law. |
| OUTPUT_ALAW | 3 | G.711 A-law, 8kHz |
| OUTPUT_MP3 | 4 | MP3 audio at 32kbps. |
| OGG_OPUS | 5 | Opus encoded audio wrapped in an ogg container. The result will be a file which can be played natively on Android, and in browsers (at least Chrome and Firefox). The quality of the encoding is considerably higher than MP3 while using approximately the same bitrate. |



<a name="com-cisco-wcc-ccai-v1-SsmlVoiceGender"></a>

### SsmlVoiceGender
Gender of the voice as described in
[SSML voice element](https://www.w3.org/TR/speech-synthesis11/#edef_voice).

| Name | Number | Description |
| ---- | ------ | ----------- |
| SSML_VOICE_GENDER_UNSPECIFIED | 0 | An unspecified gender. In VoiceSelectionParams, this means that the client doesn&#39;t care which gender the selected voice will have. In the Voice field of ListVoicesResponse, this may mean that the voice doesn&#39;t fit any of the other categories in this enum, or that the gender of the voice isn&#39;t known. |
| MALE | 1 | A male voice. |
| FEMALE | 2 | A female voice. |
| NEUTRAL | 3 | A gender-neutral voice. This voice is not yet supported. |


 

 

 



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

