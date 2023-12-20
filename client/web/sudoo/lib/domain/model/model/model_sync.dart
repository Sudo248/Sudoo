
import 'package:json_annotation/json_annotation.dart';

part 'model_sync.g.dart';

@JsonSerializable()
class ModelSync {
  final int total;
  final String message;

  ModelSync(this.total, this.message);

  factory ModelSync.fromJson(Map<String, dynamic> json) => _$ModelSyncFromJson(json);

  Map<String, dynamic> toJson() => _$ModelSyncToJson(this);
}