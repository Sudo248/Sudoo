import 'package:json_annotation/json_annotation.dart';

enum ShipmentType {
  @JsonValue("NOTHING")
  nothing("nothing"),
  @JsonValue("EXPRESS")
  express("express"),
  @JsonValue("STANDARD")
  standard("standard"),
  @JsonValue("SAVING")
  saving("saving");

  final String value;
  const ShipmentType(this.value);
}