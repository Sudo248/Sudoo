import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/api/base_request.dart';
import 'package:sudoo/domain/model/discovery/upsert_file.dart';

part 'upsert_file_request.g.dart';

@JsonSerializable()
class UpsertFileRequest extends BaseRequest {
  @JsonKey(name: "imageId")
  final String? fileId;
  final String? ownerId;
  final String url;

  UpsertFileRequest(this.fileId, this.ownerId, this.url);

  factory UpsertFileRequest.fromJson(Map<String, dynamic> json) => _$UpsertFileRequestFromJson(json);

  @override
  Map<String, dynamic> toJson() => _$UpsertFileRequestToJson(this);
}

extension Mapper on UpsertFile {
  UpsertFileRequest toUpsertImageRequest() {
    return UpsertFileRequest(fileId, ownerId, url);
  }
}
