package com.cisco.wccai.grpc.model;


import com.cisco.wcc.ccai.v1.CcaiApi;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Response {

    private CcaiApi.StreamingAnalyzeContentResponse callStartResponse;
    private CcaiApi.StreamingAnalyzeContentResponse startOfInputResponse;
    private CcaiApi.StreamingAnalyzeContentResponse partialRecognitionResponse;
    private CcaiApi.StreamingAnalyzeContentResponse endOfInputResponse;
    private CcaiApi.StreamingAnalyzeContentResponse finalRecognitionResponse;
    private CcaiApi.StreamingAnalyzeContentResponse finalVAResponse;
    private CcaiApi.StreamingAnalyzeContentResponse finalDTMFResponse;
    private CcaiApi.StreamingAnalyzeContentResponse callEndResponse;
    private CcaiApi.StreamingAnalyzeContentResponse aaResponse;
}
