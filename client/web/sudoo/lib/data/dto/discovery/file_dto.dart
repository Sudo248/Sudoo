import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/discovery/upsert_file.dart';

import '../../../domain/model/discovery/file.dart';

part 'file_dto.g.dart';

@JsonSerializable()
class FileDto {
  final String imageId;
  final String ownerId;
  final String url;

  FileDto(this.imageId, this.ownerId, this.url);

  factory FileDto.fromJson(Map<String, dynamic> json) =>
      _$FileDtoFromJson(json);

  Map<String, dynamic> toJson() => _$FileDtoToJson(this);
}

extension Mapper on FileDto {
  File toFile() {
    return File(imageId, ownerId, url);
  }

  UpsertFile toUpsertFIle() {
    return UpsertFile(
        imageId: imageId,
        ownerId: ownerId,
        url: url,
    );
  }
}
