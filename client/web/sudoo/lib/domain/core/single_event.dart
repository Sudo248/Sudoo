class SingleEvent<T> {
  final T _value;
  bool _isHandler = false;

  SingleEvent(this._value);

  T? getValueIfNotHandler() {
    if (_isHandler) return null;
    _isHandler = true;
    return _value;
  }

  T? get value => getValueIfNotHandler();

  T requireValue() => _value;

  bool get isHandler => _isHandler;

  void reset() {
    _isHandler = false;
  }
}
