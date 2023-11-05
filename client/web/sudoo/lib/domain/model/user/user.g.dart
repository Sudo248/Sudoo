// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

User _$UserFromJson(Map<String, dynamic> json) => User(
      json['userId'] as String,
      json['fullName'] as String,
      json['emailOrPhoneNumber'] as String,
      DateTime.parse(json['dob'] as String),
      json['bio'] as String,
      json['avatar'] as String,
      json['cover'] as String,
      Address.fromJson(json['address'] as Map<String, dynamic>),
      $enumDecode(_$GenderEnumMap, json['gender']),
    );

Map<String, dynamic> _$UserToJson(User instance) => <String, dynamic>{
      'userId': instance.userId,
      'fullName': instance.fullName,
      'emailOrPhoneNumber': instance.emailOrPhoneNumber,
      'dob': instance.dob.toIso8601String(),
      'bio': instance.bio,
      'avatar': instance.avatar,
      'cover': instance.cover,
      'address': instance.address,
      'gender': _$GenderEnumMap[instance.gender]!,
    };

const _$GenderEnumMap = {
  Gender.male: 'MALE',
  Gender.female: 'FEMALE',
  Gender.other: 'OTHER',
};
