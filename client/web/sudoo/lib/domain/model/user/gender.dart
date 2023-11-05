import 'package:json_annotation/json_annotation.dart';

enum Gender {
  @JsonValue("MALE")
  male("Male"),
  @JsonValue("FEMALE")
  female("Female"),
  @JsonValue("OTHER")
  other("Other");

  final String value;

  const Gender(this.value);

  factory Gender.fromValue(String value) {
    switch(value) {
      case "Male":
        return male;
      case "Female":
        return female;
      default:
        return other;
    }
  }
}