import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/user/address.dart';

import 'gender.dart';

part 'user.g.dart';

@JsonSerializable()
class User {
  final String userId;
  String fullName;
  String emailOrPhoneNumber;
  DateTime dob;
  String bio;
  String avatar;
  String cover;
  Address address;
  Gender gender;

  User(
    this.userId,
    this.fullName,
    this.emailOrPhoneNumber,
    this.dob,
    this.bio,
    this.avatar,
    this.cover,
    this.address,
      this.gender,
  );

  factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);

  Map<String, dynamic> toJson() => _$UserToJson(this);
}
