import 'dart:js_interop';

extension ListExt<T> on List<T>? {
  bool get isNullOrEmpty {
    return isNull || this!.isEmpty;
  }
}
