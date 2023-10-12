import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/discovery/upsert_image.dart';

import '../../../domain/model/discovery/image.dart';

part 'image_dto.g.dart';

@JsonSerializable()
class ImageDto {
  final String imageId;
  final String ownerId;
  final String url;

  ImageDto(this.imageId, this.ownerId, this.url);

  factory ImageDto.fromJson(Map<String, dynamic> json) =>
      _$ImageDtoFromJson(json);

  Map<String, dynamic> toJson() => _$ImageDtoToJson(this);
}

extension Mapper on ImageDto {
  Image toImage() {
    return Image(imageId, ownerId, url);
  }

  UpsertImage toUpsertImage() {
    return UpsertImage(
        imageId: imageId,
        ownerId: ownerId,
        url: url,
    );
  }
}
