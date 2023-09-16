extension StringExt on String? {
  bool isNullOrEmpty() => this == null || this!.isEmpty;
  String orEmpty() => this == null ? "" : this!;
}
