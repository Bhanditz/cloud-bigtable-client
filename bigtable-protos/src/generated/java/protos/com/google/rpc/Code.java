// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/rpc/code.proto

package com.google.rpc;

/**
 * Protobuf enum {@code google.rpc.Code}
 *
 * <pre>
 * The canonical error codes for Google APIs.
 * Warnings:
 * -   Do not change any numeric assignments.
 * -   Changes to this list should only be made if there is a compelling
 *     need that can't be satisfied in another way.
 * Sometimes multiple error codes may apply.  Services should return
 * the most specific error code that applies.  For example, prefer
 * OUT_OF_RANGE over FAILED_PRECONDITION if both codes apply.
 * Similarly prefer NOT_FOUND or ALREADY_EXISTS over FAILED_PRECONDITION.
 * </pre>
 */
public enum Code
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>OK = 0;</code>
   *
   * <pre>
   * Not an error; returned on success
   * HTTP Mapping: 200 OK
   * </pre>
   */
  OK(0, 0),
  /**
   * <code>CANCELLED = 1;</code>
   *
   * <pre>
   * The operation was cancelled (typically by the caller).
   * HTTP Mapping: 499 Client Closed Request
   * </pre>
   */
  CANCELLED(1, 1),
  /**
   * <code>UNKNOWN = 2;</code>
   *
   * <pre>
   * Unknown error.  An example of where this error may be returned is
   * if a Status value received from another address space belongs to
   * an error-space that is not known in this address space.  Also
   * errors raised by APIs that do not return enough error information
   * may be converted to this error.
   * HTTP Mapping: 500 Internal Server Error
   * </pre>
   */
  UNKNOWN(2, 2),
  /**
   * <code>INVALID_ARGUMENT = 3;</code>
   *
   * <pre>
   * Client specified an invalid argument.  Note that this differs
   * from FAILED_PRECONDITION.  INVALID_ARGUMENT indicates arguments
   * that are problematic regardless of the state of the system
   * (e.g., a malformed file name).
   * HTTP Mapping: 400 Bad Request
   * </pre>
   */
  INVALID_ARGUMENT(3, 3),
  /**
   * <code>DEADLINE_EXCEEDED = 4;</code>
   *
   * <pre>
   * Deadline expired before operation could complete.  For operations
   * that change the state of the system, this error may be returned
   * even if the operation has completed successfully.  For example, a
   * successful response from a server could have been delayed long
   * enough for the deadline to expire.
   * HTTP Mapping: 504 Gateway Timeout
   * </pre>
   */
  DEADLINE_EXCEEDED(4, 4),
  /**
   * <code>NOT_FOUND = 5;</code>
   *
   * <pre>
   * Some requested entity (e.g., file or directory) was not found.
   * For privacy reasons, this code *may* be returned when the client
   * does not have the access right to the entity.
   * HTTP Mapping: 404 Not Found
   * </pre>
   */
  NOT_FOUND(5, 5),
  /**
   * <code>ALREADY_EXISTS = 6;</code>
   *
   * <pre>
   * Some entity that we attempted to create (e.g., file or directory)
   * already exists.
   * HTTP Mapping: 409 Conflict
   * </pre>
   */
  ALREADY_EXISTS(6, 6),
  /**
   * <code>PERMISSION_DENIED = 7;</code>
   *
   * <pre>
   * The caller does not have permission to execute the specified
   * operation.  PERMISSION_DENIED must not be used for rejections
   * caused by exhausting some resource (use RESOURCE_EXHAUSTED
   * instead for those errors).  PERMISSION_DENIED must not be
   * used if the caller can not be identified (use UNAUTHENTICATED
   * instead for those errors).
   * HTTP Mapping: 403 Forbidden
   * </pre>
   */
  PERMISSION_DENIED(7, 7),
  /**
   * <code>UNAUTHENTICATED = 16;</code>
   *
   * <pre>
   * The request does not have valid authentication credentials for the
   * operation.
   * HTTP Mapping: 401 Unauthorized
   * </pre>
   */
  UNAUTHENTICATED(8, 16),
  /**
   * <code>RESOURCE_EXHAUSTED = 8;</code>
   *
   * <pre>
   * Some resource has been exhausted, perhaps a per-user quota, or
   * perhaps the entire file system is out of space.
   * HTTP Mapping: 429 Too Many Requests
   * </pre>
   */
  RESOURCE_EXHAUSTED(9, 8),
  /**
   * <code>FAILED_PRECONDITION = 9;</code>
   *
   * <pre>
   * Operation was rejected because the system is not in a state
   * required for the operation's execution.  For example, directory
   * to be deleted may be non-empty, an rmdir operation is applied to
   * a non-directory, etc.
   * A litmus test that may help a service implementor in deciding
   * between FAILED_PRECONDITION, ABORTED, and UNAVAILABLE:
   *  (a) Use UNAVAILABLE if the client can retry just the failing call.
   *  (b) Use ABORTED if the client should retry at a higher-level
   *      (e.g., restarting a read-modify-write sequence).
   *  (c) Use FAILED_PRECONDITION if the client should not retry until
   *      the system state has been explicitly fixed.  E.g., if an "rmdir"
   *      fails because the directory is non-empty, FAILED_PRECONDITION
   *      should be returned since the client should not retry unless
   *      they have first fixed up the directory by deleting files from it.
   *  (d) Use FAILED_PRECONDITION if the client performs conditional
   *      REST Get/Update/Delete on a resource and the resource on the
   *      server does not match the condition. E.g., conflicting
   *      read-modify-write on the same resource.
   * HTTP Mapping: 400 Bad Request
   * NOTE: HTTP spec says 412 Precondition Failed should only be used if
   * the request contains Etag related headers. So if the server does see
   * Etag related headers in the request, it may choose to return 412
   * instead of 400 for this error code.
   * </pre>
   */
  FAILED_PRECONDITION(10, 9),
  /**
   * <code>ABORTED = 10;</code>
   *
   * <pre>
   * The operation was aborted, typically due to a concurrency issue
   * like sequencer check failures, transaction aborts, etc.
   * See litmus test above for deciding between FAILED_PRECONDITION,
   * ABORTED, and UNAVAILABLE.
   * HTTP Mapping: 409 Conflict
   * </pre>
   */
  ABORTED(11, 10),
  /**
   * <code>OUT_OF_RANGE = 11;</code>
   *
   * <pre>
   * Operation was attempted past the valid range.  E.g., seeking or
   * reading past end of file.
   * Unlike INVALID_ARGUMENT, this error indicates a problem that may
   * be fixed if the system state changes. For example, a 32-bit file
   * system will generate INVALID_ARGUMENT if asked to read at an
   * offset that is not in the range [0,2^32-1], but it will generate
   * OUT_OF_RANGE if asked to read from an offset past the current
   * file size.
   * There is a fair bit of overlap between FAILED_PRECONDITION and
   * OUT_OF_RANGE.  We recommend using OUT_OF_RANGE (the more specific
   * error) when it applies so that callers who are iterating through
   * a space can easily look for an OUT_OF_RANGE error to detect when
   * they are done.
   * HTTP Mapping: 400 Bad Request
   * </pre>
   */
  OUT_OF_RANGE(12, 11),
  /**
   * <code>UNIMPLEMENTED = 12;</code>
   *
   * <pre>
   * Operation is not implemented or not supported/enabled in this service.
   * HTTP Mapping: 501 Not Implemented
   * </pre>
   */
  UNIMPLEMENTED(13, 12),
  /**
   * <code>INTERNAL = 13;</code>
   *
   * <pre>
   * Internal errors.  Means some invariants expected by underlying
   * system has been broken.  If you see one of these errors,
   * something is very broken.
   * HTTP Mapping: 500 Internal Server Error
   * </pre>
   */
  INTERNAL(14, 13),
  /**
   * <code>UNAVAILABLE = 14;</code>
   *
   * <pre>
   * The service is currently unavailable.  This is a most likely a
   * transient condition and may be corrected by retrying with
   * a backoff.
   * See litmus test above for deciding between FAILED_PRECONDITION,
   * ABORTED, and UNAVAILABLE.
   * HTTP Mapping: 503 Service Unavailable
   * </pre>
   */
  UNAVAILABLE(15, 14),
  /**
   * <code>DATA_LOSS = 15;</code>
   *
   * <pre>
   * Unrecoverable data loss or corruption.
   * HTTP Mapping: 500 Internal Server Error
   * </pre>
   */
  DATA_LOSS(16, 15),
  UNRECOGNIZED(-1, -1),
  ;

  /**
   * <code>OK = 0;</code>
   *
   * <pre>
   * Not an error; returned on success
   * HTTP Mapping: 200 OK
   * </pre>
   */
  public static final int OK_VALUE = 0;
  /**
   * <code>CANCELLED = 1;</code>
   *
   * <pre>
   * The operation was cancelled (typically by the caller).
   * HTTP Mapping: 499 Client Closed Request
   * </pre>
   */
  public static final int CANCELLED_VALUE = 1;
  /**
   * <code>UNKNOWN = 2;</code>
   *
   * <pre>
   * Unknown error.  An example of where this error may be returned is
   * if a Status value received from another address space belongs to
   * an error-space that is not known in this address space.  Also
   * errors raised by APIs that do not return enough error information
   * may be converted to this error.
   * HTTP Mapping: 500 Internal Server Error
   * </pre>
   */
  public static final int UNKNOWN_VALUE = 2;
  /**
   * <code>INVALID_ARGUMENT = 3;</code>
   *
   * <pre>
   * Client specified an invalid argument.  Note that this differs
   * from FAILED_PRECONDITION.  INVALID_ARGUMENT indicates arguments
   * that are problematic regardless of the state of the system
   * (e.g., a malformed file name).
   * HTTP Mapping: 400 Bad Request
   * </pre>
   */
  public static final int INVALID_ARGUMENT_VALUE = 3;
  /**
   * <code>DEADLINE_EXCEEDED = 4;</code>
   *
   * <pre>
   * Deadline expired before operation could complete.  For operations
   * that change the state of the system, this error may be returned
   * even if the operation has completed successfully.  For example, a
   * successful response from a server could have been delayed long
   * enough for the deadline to expire.
   * HTTP Mapping: 504 Gateway Timeout
   * </pre>
   */
  public static final int DEADLINE_EXCEEDED_VALUE = 4;
  /**
   * <code>NOT_FOUND = 5;</code>
   *
   * <pre>
   * Some requested entity (e.g., file or directory) was not found.
   * For privacy reasons, this code *may* be returned when the client
   * does not have the access right to the entity.
   * HTTP Mapping: 404 Not Found
   * </pre>
   */
  public static final int NOT_FOUND_VALUE = 5;
  /**
   * <code>ALREADY_EXISTS = 6;</code>
   *
   * <pre>
   * Some entity that we attempted to create (e.g., file or directory)
   * already exists.
   * HTTP Mapping: 409 Conflict
   * </pre>
   */
  public static final int ALREADY_EXISTS_VALUE = 6;
  /**
   * <code>PERMISSION_DENIED = 7;</code>
   *
   * <pre>
   * The caller does not have permission to execute the specified
   * operation.  PERMISSION_DENIED must not be used for rejections
   * caused by exhausting some resource (use RESOURCE_EXHAUSTED
   * instead for those errors).  PERMISSION_DENIED must not be
   * used if the caller can not be identified (use UNAUTHENTICATED
   * instead for those errors).
   * HTTP Mapping: 403 Forbidden
   * </pre>
   */
  public static final int PERMISSION_DENIED_VALUE = 7;
  /**
   * <code>UNAUTHENTICATED = 16;</code>
   *
   * <pre>
   * The request does not have valid authentication credentials for the
   * operation.
   * HTTP Mapping: 401 Unauthorized
   * </pre>
   */
  public static final int UNAUTHENTICATED_VALUE = 16;
  /**
   * <code>RESOURCE_EXHAUSTED = 8;</code>
   *
   * <pre>
   * Some resource has been exhausted, perhaps a per-user quota, or
   * perhaps the entire file system is out of space.
   * HTTP Mapping: 429 Too Many Requests
   * </pre>
   */
  public static final int RESOURCE_EXHAUSTED_VALUE = 8;
  /**
   * <code>FAILED_PRECONDITION = 9;</code>
   *
   * <pre>
   * Operation was rejected because the system is not in a state
   * required for the operation's execution.  For example, directory
   * to be deleted may be non-empty, an rmdir operation is applied to
   * a non-directory, etc.
   * A litmus test that may help a service implementor in deciding
   * between FAILED_PRECONDITION, ABORTED, and UNAVAILABLE:
   *  (a) Use UNAVAILABLE if the client can retry just the failing call.
   *  (b) Use ABORTED if the client should retry at a higher-level
   *      (e.g., restarting a read-modify-write sequence).
   *  (c) Use FAILED_PRECONDITION if the client should not retry until
   *      the system state has been explicitly fixed.  E.g., if an "rmdir"
   *      fails because the directory is non-empty, FAILED_PRECONDITION
   *      should be returned since the client should not retry unless
   *      they have first fixed up the directory by deleting files from it.
   *  (d) Use FAILED_PRECONDITION if the client performs conditional
   *      REST Get/Update/Delete on a resource and the resource on the
   *      server does not match the condition. E.g., conflicting
   *      read-modify-write on the same resource.
   * HTTP Mapping: 400 Bad Request
   * NOTE: HTTP spec says 412 Precondition Failed should only be used if
   * the request contains Etag related headers. So if the server does see
   * Etag related headers in the request, it may choose to return 412
   * instead of 400 for this error code.
   * </pre>
   */
  public static final int FAILED_PRECONDITION_VALUE = 9;
  /**
   * <code>ABORTED = 10;</code>
   *
   * <pre>
   * The operation was aborted, typically due to a concurrency issue
   * like sequencer check failures, transaction aborts, etc.
   * See litmus test above for deciding between FAILED_PRECONDITION,
   * ABORTED, and UNAVAILABLE.
   * HTTP Mapping: 409 Conflict
   * </pre>
   */
  public static final int ABORTED_VALUE = 10;
  /**
   * <code>OUT_OF_RANGE = 11;</code>
   *
   * <pre>
   * Operation was attempted past the valid range.  E.g., seeking or
   * reading past end of file.
   * Unlike INVALID_ARGUMENT, this error indicates a problem that may
   * be fixed if the system state changes. For example, a 32-bit file
   * system will generate INVALID_ARGUMENT if asked to read at an
   * offset that is not in the range [0,2^32-1], but it will generate
   * OUT_OF_RANGE if asked to read from an offset past the current
   * file size.
   * There is a fair bit of overlap between FAILED_PRECONDITION and
   * OUT_OF_RANGE.  We recommend using OUT_OF_RANGE (the more specific
   * error) when it applies so that callers who are iterating through
   * a space can easily look for an OUT_OF_RANGE error to detect when
   * they are done.
   * HTTP Mapping: 400 Bad Request
   * </pre>
   */
  public static final int OUT_OF_RANGE_VALUE = 11;
  /**
   * <code>UNIMPLEMENTED = 12;</code>
   *
   * <pre>
   * Operation is not implemented or not supported/enabled in this service.
   * HTTP Mapping: 501 Not Implemented
   * </pre>
   */
  public static final int UNIMPLEMENTED_VALUE = 12;
  /**
   * <code>INTERNAL = 13;</code>
   *
   * <pre>
   * Internal errors.  Means some invariants expected by underlying
   * system has been broken.  If you see one of these errors,
   * something is very broken.
   * HTTP Mapping: 500 Internal Server Error
   * </pre>
   */
  public static final int INTERNAL_VALUE = 13;
  /**
   * <code>UNAVAILABLE = 14;</code>
   *
   * <pre>
   * The service is currently unavailable.  This is a most likely a
   * transient condition and may be corrected by retrying with
   * a backoff.
   * See litmus test above for deciding between FAILED_PRECONDITION,
   * ABORTED, and UNAVAILABLE.
   * HTTP Mapping: 503 Service Unavailable
   * </pre>
   */
  public static final int UNAVAILABLE_VALUE = 14;
  /**
   * <code>DATA_LOSS = 15;</code>
   *
   * <pre>
   * Unrecoverable data loss or corruption.
   * HTTP Mapping: 500 Internal Server Error
   * </pre>
   */
  public static final int DATA_LOSS_VALUE = 15;


  public final int getNumber() {
    if (index == -1) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  public static Code valueOf(int value) {
    switch (value) {
      case 0: return OK;
      case 1: return CANCELLED;
      case 2: return UNKNOWN;
      case 3: return INVALID_ARGUMENT;
      case 4: return DEADLINE_EXCEEDED;
      case 5: return NOT_FOUND;
      case 6: return ALREADY_EXISTS;
      case 7: return PERMISSION_DENIED;
      case 16: return UNAUTHENTICATED;
      case 8: return RESOURCE_EXHAUSTED;
      case 9: return FAILED_PRECONDITION;
      case 10: return ABORTED;
      case 11: return OUT_OF_RANGE;
      case 12: return UNIMPLEMENTED;
      case 13: return INTERNAL;
      case 14: return UNAVAILABLE;
      case 15: return DATA_LOSS;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<Code>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static com.google.protobuf.Internal.EnumLiteMap<Code>
      internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<Code>() {
          public Code findValueByNumber(int number) {
            return Code.valueOf(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(index);
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return com.google.rpc.CodeProto.getDescriptor()
        .getEnumTypes().get(0);
  }

  private static final Code[] VALUES = values();

  public static Code valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int index;
  private final int value;

  private Code(int index, int value) {
    this.index = index;
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:google.rpc.Code)
}
