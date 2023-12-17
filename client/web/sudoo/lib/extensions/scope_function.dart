

extension ScopeFunction<T> on T {
  T also(void Function(T) block) {
    block(this);
    return this;
  }

  R let<R>(R Function(T) block) {
    return block(this);
  }
}
