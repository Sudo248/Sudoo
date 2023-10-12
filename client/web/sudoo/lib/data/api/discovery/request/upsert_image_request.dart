import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/api/base_request.dart';
import 'package:sudoo/domain/model/discovery/upsert_image.dart';

part 'upsert_image_request.g.dart';

@JsonSerializable()
class UpsertImageRequest extends BaseRequest {
  final String? imageId;
  final String? ownerId;
  final String url;

  UpsertImageRequest(this.imageId, this.ownerId, this.url);

  factory UpsertImageRequest.fromJson(Map<String, dynamic> json) => _$UpsertImageRequestFromJson(json);

  @override
  Map<String, dynamic> toJson() => _$UpsertImageRequestToJson(this);
}

extension Mapper on UpsertImage {
  UpsertImageRequest toUpsertImageRequest() {
    return UpsertImageRequest(imageId,ownerId, url);
  }
}
