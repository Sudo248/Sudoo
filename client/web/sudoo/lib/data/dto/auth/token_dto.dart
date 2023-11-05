import 'package:sudoo/domain/model/auth/token.dart';

import '../../../domain/model/auth/role.dart';

class TokenDto {
  final String token;
  final String? refreshToken;
  final String? userId;
  final Role role;

  TokenDto({
    required this.token,
    required this.role,
    this.refreshToken,
    this.userId,
  });

  factory TokenDto.fromJson(Map<String, dynamic> json) {
    return TokenDto(
        token: json["token"],
        role: Role.fromValue(json["role"]),
        refreshToken: json["refreshToken"],
        userId: json["userId"]);
  }
}

extension Mapper on TokenDto {
  Token toToken() {
    return Token(token, refreshToken, role);
  }
}
