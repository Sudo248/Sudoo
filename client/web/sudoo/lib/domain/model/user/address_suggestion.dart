
import 'package:json_annotation/json_annotation.dart';

part 'address_suggestion.g.dart';

@JsonSerializable()
class AddressSuggestion {
  final int addressId;
  final String addressName;
  final String addressCode;

  AddressSuggestion(this.addressId, this.addressName, this.addressCode);

  factory AddressSuggestion.fromJson(Map<String, dynamic> json) => _$AddressSuggestionFromJson(json);

  Map<String, dynamic> toJson() => _$AddressSuggestionToJson(this);
}