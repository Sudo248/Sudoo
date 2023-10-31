import 'package:sudoo/domain/model/discovery/upsert_file.dart';

import '../../domain/model/discovery/file.dart';

abstract class ImageCallback {
  Future<String> uploadImage(List<int> bytes, {String? imageName});
  Future<File> upsertImage(UpsertFile image);
  Future<File> deleteImage(File image);
}