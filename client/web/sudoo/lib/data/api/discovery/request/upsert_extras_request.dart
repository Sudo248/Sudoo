
import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/discovery/extras.dart';

import '../../base_request.dart';

part 'upsert_extras_request.g.dart';

@JsonSerializable()
class UpsertExtrasRequest extends BaseRequest  {
  final bool enable3DViewer;
  final bool enableArViewer;
  final String? source;

  UpsertExtrasRequest(this.enable3DViewer, this.enableArViewer, this.source);

  factory UpsertExtrasRequest.fromJson(Map<String, dynamic> json) => _$UpsertExtrasRequestFromJson(json);

  @override
  Map<String, dynamic> toJson() => _$UpsertExtrasRequestToJson(this);
}

extension Mapper on Extras {
  UpsertExtrasRequest toUpsertProductExtraRequest() {
    return UpsertExtrasRequest(enable3DViewer, enableArViewer, source);
  }
}