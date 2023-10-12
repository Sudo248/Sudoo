import 'package:sudoo/domain/model/discovery/upsert_image.dart';

import '../../domain/model/discovery/image.dart';

abstract class ImageCallback {
  Future<String> uploadImage(List<int> bytes);
  Future<Image> upsertImage(UpsertImage image);
  Future<Image> deleteImage(Image image);
}