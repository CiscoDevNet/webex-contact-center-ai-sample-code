package com.cisco.wccai.grpc.model;

public enum State {
    CALL_START,
    START_OF_INPUT,
    PARTIAL_RECOGNITION,
    FINAL_RECOGNITION,
    DTMF,
    END_OF_INPUT,
    VA,
    CALL_END,
    AA
}
