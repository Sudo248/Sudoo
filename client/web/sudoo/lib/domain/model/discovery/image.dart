class Image {
  final String imageId;
  final String ownerId;
  final String url;
  final List<int>? bytes;

  Image(this.imageId, this.ownerId, this.url, {this.bytes});

  factory Image.fromBytes(List<int> bytes) {
    return Image("", "", "", bytes: bytes);
  }
}
