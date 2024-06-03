package com.cisco.wccai.grpc.server;

import com.cisco.wcc.ccai.v1.CcaiApi;
import com.cisco.wcc.ccai.v1.Recognize;
import com.cisco.wcc.ccai.v1.Suggestions;
import com.cisco.wcc.ccai.v1.Virtualagent;
import com.cisco.wccai.grpc.model.Response;
import com.cisco.wccai.grpc.utils.Utils;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;
import com.google.protobuf.UnknownFieldSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Prepare response.
 */
public class PrepareResponse {
    private static final Random random = new Random();
    /**
     * The constant EN_US.
     */
    public static final String EN_US = "en-US";
    private static Recognize.StreamingRecognitionResult recognitionResult;
    private static final List<List<String>> listOfLists = Lists.newArrayList();

    /**
     * Instantiates a new Prepare response.
     */
    PrepareResponse() {

    }

    /**
     * Prepare call start response.
     *
     * @return the response
     * @throws IOException the io exception
     */
    public static Response prepareCallStartResponse() throws IOException {
        Virtualagent.VirtualAgentResult result = Virtualagent.VirtualAgentResult.newBuilder().setResponsePayload("CALL_START event received")
                .addPrompts(Virtualagent.Prompt.newBuilder()
                        .setText("setting prompt from dialog simulator")
                        .setAudioContent(ByteString.readFrom(Utils.getInputStreamForBookAFlight()))
                        .setBargein(Boolean.TRUE)
                        .build())
                .setNlu(Virtualagent.NLU.newBuilder()
                        .addReplyText("Hi ! I'm your virtual agent for ticket booking from dialog simulator. How can I assist you today")
                        .setIntent(Virtualagent.Intent.newBuilder()
                                .setDisplayName("Display name from dialog simulator")
                                .setName("name from dialog simulator")
                                .setMatchConfidence(0.95f)
                                .build()))
                .setInputMode(Virtualagent.InputMode.INPUT_VOICE_DTMF)
                .build();

        return Response.builder().callStartResponse(CcaiApi.StreamingAnalyzeContentResponse.newBuilder().setVaResult(result).build()).build();
    }

    /**
     * Start of input response.
     *
     * @return the response
     */
    public static Response startOfInputResponse() {
        return Response.builder().startOfInputResponse(CcaiApi.StreamingAnalyzeContentResponse.newBuilder().setRecognitionResult(Recognize.StreamingRecognitionResult.newBuilder().setResponseEvent(Recognize.OutputEvent.EVENT_START_OF_INPUT).build()).build()).build();
    }

    /**
     * Prepare partial recognition response.
     *
     * @return the response
     */
    public static Response preparePartialRecognitionResponse() {

        listOfLists.add(Lists.newArrayList("I want to ", "I want to book", "I want to book tickets"));

        List<Recognize.SpeechRecognitionAlternative> speechAlternativeList = new ArrayList<>(100);

        for (int i = 0; i < listOfLists.get(0).size(); i++) {
            speechAlternativeList.add(Recognize.SpeechRecognitionAlternative.newBuilder()
                    .setTranscript(listOfLists.get(0).get(i))
                    .setConfidence(0.876f)
                    .build());

        }
        if (speechAlternativeList.iterator().hasNext()) {
            recognitionResult = Recognize.StreamingRecognitionResult.newBuilder()
                    .setLanguageCode(EN_US)
                    .setMessageType("interim response from dialog simulator")
                    .setIsFinal(false)
                    .addSpeakerIds(1)
                    .setChannelTag(3)
                    .setResultEndTime(Recognize.Duration.newBuilder()
                            .setSeconds(System.currentTimeMillis() / 1000)
                            .build())
                    .addAlternatives(speechAlternativeList.stream().iterator().next())
                    .build();
        }
        return Response.builder().partialRecognitionResponse(CcaiApi.StreamingAnalyzeContentResponse.newBuilder().setRecognitionResult(recognitionResult).build()).build();
    }

    /**
     * Prepare final recognition response.
     *
     * @return the response
     */
    public static Response prepareFinalRecognitionResponse() {
        String[] finalList = {"I want to book tickets from Bengaluru to Kolkata", "when will I get the confirmation over mail"};
        List<Recognize.SpeechRecognitionAlternative> speechAlternativeList = new ArrayList<>(100);

        speechAlternativeList.add(Recognize.SpeechRecognitionAlternative.newBuilder()
                .setTranscript(finalList[random.nextInt(1)])
                .setConfidence(0.876f)
                .build());

        if (speechAlternativeList.iterator().hasNext()) {
            recognitionResult = Recognize.StreamingRecognitionResult.newBuilder()
                    .setLanguageCode(EN_US)
                    .setMessageType("final recognition response from dialog connector")
                    .setIsFinal(true)
                    .addSpeakerIds(1)
                    .setChannelTag(3)
                    .setResultEndTime(Recognize.Duration.newBuilder()
                            .setSeconds(System.currentTimeMillis() / 1000)
                            .build())
                    .addAlternatives(speechAlternativeList.stream().iterator().next())
                    .build();
        }
        return Response.builder().partialRecognitionResponse(CcaiApi.StreamingAnalyzeContentResponse.newBuilder().setRecognitionResult(recognitionResult).build()).build();

    }

    /**
     * Prepare end of input response.
     *
     * @return the response
     */
    public static Response prepareEndOfInputResponse()
    {
        return Response.builder().endOfInputResponse(CcaiApi.StreamingAnalyzeContentResponse.newBuilder().setRecognitionResult(Recognize.StreamingRecognitionResult.newBuilder().setResponseEvent(Recognize.OutputEvent.EVENT_END_OF_INPUT).build()).build()).build();
    }

    /**
     * Prepare aa response.
     *
     * @return the response
     */
    public static Response prepareAAResponse() {
        List<String> snippet = new ArrayList<>(100);
        snippet.add("snippet1");
        Suggestions.Answer answer = Suggestions.Answer.newBuilder().setTitle("response from dialog simulator")
                .setUri("")
                .addSnippets(snippet.get(0))
                .setAnswerRecord("You can generate your mTicket online. Click on the Print/SMS ticket link on the home page on www.redBus.in. Enter your TIN number mentioned on the e-ticket we e-mailed you. Choose the SMS option and click on Submit. In case you don't have a copy of the e-ticket either, contact our call center and our executive will assist you.")
                .setConfidence(0.06597688794136047f)
                .setDescription("I didn't receive my mTicket. Can you re-send it?")
                .setSource("projects/ciscoss-dev-9gkv/knowledgeBases/MTI1MDY1OTA3MjMyMDc4NTYxMjg/documents/MTY2NjE3MzY0MjQwMzg0NjU1MzY")
                .setAnswerRecord("projects/ciscoss-dev-9gkv/answerRecords/6ccb05ec305684c5")
                .setUnknownFields(UnknownFieldSet.newBuilder().build())
                .build();

        Suggestions.AgentAnswer agentAnswer = Suggestions.AgentAnswer.newBuilder().addAnswers(0, answer)
                .build();

        Suggestions.AgentAnswerResult agentAnswerResult = Suggestions.AgentAnswerResult.newBuilder()
                .setAgentanswer(agentAnswer)
                .build();

        return Response.builder().aaResponse(CcaiApi.StreamingAnalyzeContentResponse.newBuilder().setAgentAnswerResult(agentAnswerResult).build()).build();
    }

    /**
     * Prepare final nlu response.
     *
     * @return the response
     * @throws IOException the io exception
     */
    public static Response prepareFinalVAResponse() throws IOException {
        Virtualagent.VirtualAgentResult result = Virtualagent.VirtualAgentResult.newBuilder().setResponsePayload("Final NLU Response")
                .addPrompts(Virtualagent.Prompt.newBuilder()
                        .setText("setting prompt from dialog simulator for final NLU Response")
                        .setAudioContent(ByteString.readFrom(Utils.getInputStreamForBookAFlight()))
                        .setBargein(Boolean.TRUE)
                        .build())
//                .setNlu(Virtualagent.NLU.newBuilder()
//                        .setIntent(Virtualagent.Intent.newBuilder()
//                                .setMatchConfidence(0.32f)
//                                .build()))
                .setNlu(Virtualagent.NLU.newBuilder()
                        .setExitEvent(Virtualagent.ExitEvent.newBuilder().setEventType(Virtualagent.ExitEvent.EventType.AGENT_TRANSFER).build())
                        .build())
                .setInputMode(Virtualagent.InputMode.INPUT_VOICE)
                .build();

        return Response.builder().finalVAResponse(CcaiApi.StreamingAnalyzeContentResponse.newBuilder().setVaResult(result).build()).build();
    }


    /**
     * Prepare call end response.
     *
     * @return the response
     * @throws IOException the io exception
     */
    public static Response prepareCallEndResponse() throws IOException {
        Virtualagent.VirtualAgentResult result = Virtualagent.VirtualAgentResult.newBuilder().setResponsePayload("CALL_END response")
                .addPrompts(Virtualagent.Prompt.newBuilder()
                        .setText("setting up prompt from dialog simulator for CALL_END event")
                        .setAudioContent(ByteString.readFrom(Utils.getInputStreamForBookAFlight()))
                        .setBargein(Boolean.TRUE)
                        .build())
                .setNlu(Virtualagent.NLU.newBuilder()
                        .setIntent(Virtualagent.Intent.newBuilder()
                                .setMatchConfidence(0.32f)
                                .build()))
                .setInputMode(Virtualagent.InputMode.INPUT_VOICE_DTMF)
                .build();

        return Response.builder().finalDTMFResponse(CcaiApi.StreamingAnalyzeContentResponse.newBuilder().setVaResult(result).build()).build();
    }

    /**
     * Prepare dtmf response response.
     *
     * @return the response
     * @throws IOException the io exception
     */
    public static Response prepareDTMFResponse() throws IOException {
        Virtualagent.VirtualAgentResult result = Virtualagent.VirtualAgentResult.newBuilder()
                .addPrompts(Virtualagent.Prompt.newBuilder()
                        .setText("setting up prompt from dialog simulator for DTMF")
                        .setAudioContent(ByteString.readFrom(Utils.getInputStreamForBookAFlight()))
                        .setBargein(Boolean.TRUE)
                        .build())
                .setInputMode(Virtualagent.InputMode.INPUT_DTMF)
                .setNlu(Virtualagent.NLU.newBuilder()
                        .addReplyText("Reply text for DTMF")
                        .setInputText("DTMF event received from client"))
                .setResponsePayload("Response payload for DTMF event")
                .build();
        return Response.builder().finalDTMFResponse(CcaiApi.StreamingAnalyzeContentResponse.newBuilder().setVaResult(result).build()).build();

    }

}

