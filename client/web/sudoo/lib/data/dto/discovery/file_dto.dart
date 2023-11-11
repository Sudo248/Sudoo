import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/api/base_request.dart';
import 'package:sudoo/domain/model/discovery/upsert_file.dart';

import '../../../domain/model/discovery/file.dart';

part 'file_dto.g.dart';

@JsonSerializable()
class FileDto implements BaseRequest{
  @JsonKey(name: "imageId")
  final String fileId;
  final String ownerId;
  final String url;

  FileDto(this.fileId, this.ownerId, this.url);

  factory FileDto.fromJson(Map<String, dynamic> json) =>
      _$FileDtoFromJson(json);

  @override
  Map<String, dynamic> toJson() => _$FileDtoToJson(this);
}

extension Mapper on FileDto {
  File toFile() {
    return File(fileId, ownerId, url);
  }

  UpsertFile toUpsertFIle() {
    return UpsertFile(
        fileId: fileId,
        ownerId: ownerId,
        url: url,
    );
  }
}
