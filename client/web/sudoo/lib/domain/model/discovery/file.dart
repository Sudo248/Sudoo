class File {
  final String fileId;
  final String ownerId;
  final String url;
  final List<int>? bytes;

  File(this.fileId, this.ownerId, this.url, {this.bytes});

  factory File.fromBytes(List<int> bytes) {
    return File("", "", "", bytes: bytes);
  }
}
