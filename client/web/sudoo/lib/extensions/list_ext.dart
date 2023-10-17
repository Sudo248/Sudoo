
extension ListExt<T> on List<T>? {
  bool get isNullOrEmpty {
    return this == null || this!.isEmpty;
  }

  List<T> get orEmpty => this == null ? List.empty() : this!;
}
