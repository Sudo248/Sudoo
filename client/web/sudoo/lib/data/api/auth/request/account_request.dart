

import 'package:sudoo/data/api/base_request.dart';

import '../../../../domain/model/auth/role.dart';

class AccountRequest extends BaseRequest {
  final String emailOrPhoneNumber;
  final String password;
  final Role role;

  AccountRequest({required this.emailOrPhoneNumber, required this.password, this.role = Role.STAFF});

  @override
  Map<String, dynamic> toJson() {
    return {
      "emailOrPhoneNumber": emailOrPhoneNumber,
      "password": password,
      "role": role.value,
    };
  }
}
