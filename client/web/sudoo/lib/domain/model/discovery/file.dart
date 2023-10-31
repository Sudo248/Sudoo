import 'package:sudoo/extensions/string_ext.dart';

class File {
  final String fileId;
  final String ownerId;
  final String url;
  final List<int>? bytes;

  File(this.fileId, this.ownerId, this.url, {this.bytes});

  factory File.fromBytes(List<int> bytes, {String name = ""}) {
    return File("", "", name, bytes: bytes);
  }

  static fromUrl(String? url) {
    if (url.isNullOrEmpty) return null;
    return File("", "", url!);
  }
}
