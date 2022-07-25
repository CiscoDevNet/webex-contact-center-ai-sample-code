/* Error Codes */
// General error codes
const CANCELLED = 1;
const ABORTED = 10;
const UNKNOWN = 2;
const UNIMPLEMENTED = 12;
// Network error codes
const UNAVAILABLE = 14;
const DEADLINE_EXCEEDED = 4;
// Protocol error codes
const NOT_FOUND = 5;
const INTERNAL = 13;
const RESOURCE_EXHAUSTED = 8;
const UNAUTHENTICATED = 16;
const PERMISSION_DENIED = 7;
// Service error codes
const INVALID_ARGUMENT = 3;
const FAILED_PRECONDITION = 9;

export const DEFAULT_CONNECTION_DEADLINE = 3;

/**
 * Error code explanation
 * @param {Integer} code 
 */
export const errorMessageGenerator = function (code) {
    switch(code) {
        case CANCELLED:
        case ABORTED:
        case UNKNOWN:
        case UNIMPLEMENTED: return 'GRPC_GENERAL_FAILURE';
        case UNAVAILABLE:
        case DEADLINE_EXCEEDED: return 'GRPC_ERROR_NETWORK_FAILURE';
        case NOT_FOUND:
        case INTERNAL:
        case RESOURCE_EXHAUSTED:
        case UNAUTHENTICATED:
        case PERMISSION_DENIED:
        case FAILED_PRECONDITION:
        case INVALID_ARGUMENT: return 'GRPC_PROTOCOL_FAILURE';
        default: return 'GRPC_ERROR_DEFAULT';
    }
}