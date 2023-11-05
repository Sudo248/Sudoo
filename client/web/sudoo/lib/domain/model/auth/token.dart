import 'package:sudoo/domain/model/auth/role.dart';

class Token{
  final String token;
  final String? refreshToken;
  final Role role;

  Token(this.token, this.refreshToken, this.role);
}