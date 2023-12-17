
abstract class DataState<T, E extends Exception> {
  static DataState<T, Exception> idle<T>() => _Idle<T>();
  static DataState<T, Exception> loading<T>() => _Loading<T>();
  static DataState<T, Exception> success<T>(T data) => _Success<T>(data: data);
  static DataState<T, E> error<T, E extends Exception>(E error) => _Error<T, E>(error: error);

  bool get isLoading => this is _Loading;
  bool get isSuccess => this is _Success;
  bool get isError => this is _Error;

  T get() => requireData();

  T requireData() {
    if (!isSuccess) throw TypeError();
    return (this as _Success).data;
  }

  T? getDataOrNull() {
    if (!isSuccess) return null;
    return (this as _Success).data;
  }

  T? getOrNull() => getDataOrNull();

  E requireError() {
    if (!isError) throw TypeError();
    return (this as _Error).error as E;
  }

  E getError() => requireError();
}

class _Idle<T> extends DataState<T, Exception> {}

class _Loading<T> extends DataState<T, Exception> {}

class _Success<T> extends DataState<T, Exception> {
  final T data;
  _Success({required this.data});
}

class _Error<T, E extends Exception> extends DataState<T, E> {
  final E error;
  _Error({required this.error});
}
