import 'package:json_annotation/json_annotation.dart';

enum PaymentStatus {
  @JsonValue("INIT")
  init("init"),
  @JsonValue("PENDING")
  pending("pending"),
  @JsonValue("SUCCESS")
  success("success"),
  @JsonValue("FAILURE")
  failure("failure");

  final String value;
  const PaymentStatus(this.value);
}