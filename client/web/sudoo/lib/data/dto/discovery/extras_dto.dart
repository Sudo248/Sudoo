import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/dto/discovery/file_dto.dart';
import 'package:sudoo/domain/model/discovery/file.dart';
import 'package:sudoo/extensions/string_ext.dart';

import '../../../domain/model/discovery/extras.dart';

part 'extras_dto.g.dart';

@JsonSerializable()
class ExtrasDto {
  final bool enable3DViewer;
  final bool enableArViewer;
  final String? source;

  ExtrasDto(
      {this.enable3DViewer = false, this.enableArViewer = false, this.source});

  factory ExtrasDto.fromJson(Map<String, dynamic> json) =>
      _$ExtrasDtoFromJson(json);

  Map<String, dynamic> toJson() => _$ExtrasDtoToJson(this);
}

extension Mapper on ExtrasDto {
  Extras toExtras() {
    return Extras(
      enable3DViewer: enable3DViewer,
      enableArViewer: enableArViewer,
      source: source,
    );
  }
}
