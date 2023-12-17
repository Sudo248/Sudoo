/// Failure

abstract class BaseException implements Exception {
  ///	[message] description reason for failure
  final dynamic message;
  const BaseException([this.message = ""]);
  @override
  String toString() {
    Object? message = this.message;
    if (message == null) {
      return "\x1B[31m! $runtimeType Exception\x1B[0m";
    } else {
      return "\x1B[31m! $runtimeType Exception => message: $message\x1B[0m";
    }
  }

  List<Object> get props => [message];
}

/// Failure socket: no internet connection.

class NoInternetConnection extends BaseException {
  const NoInternetConnection([String message = "No internet connection"])
      : super(message);
}

/// 

class ApiFormatException extends BaseException {
  const ApiFormatException([String message = "Format Exception"])
      : super(message);
}

///

class UnableToProcess extends BaseException {
  const UnableToProcess([String message = "Unable to process the data"])
      : super(message);
}

///

class DefaultError extends BaseException {
  const DefaultError([String message = "DefaultError"]) : super(message);
}


///

class UnexpectedError extends BaseException {
  const UnexpectedError([String message = "Unexpected error occurred"])
      : super(message);
}
