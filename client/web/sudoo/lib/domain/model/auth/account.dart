import 'package:sudoo/domain/model/auth/provider.dart';

class Account {
  final String? userId;
  final String emailOrPhoneNumber;
  final String password;
  final Provider? provider;
  final bool? isValidate;
  final DateTime? createdAt;

  Account({
    this.userId,
    required this.emailOrPhoneNumber,
    required this.password,
    this.provider,
    this.isValidate,
    this.createdAt,
  });
}
