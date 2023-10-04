

import 'package:sudoo/data/api/base_request.dart';

class AccountRequest extends BaseRequest {
  final String emailOrPhoneNumber;
  final String password;

  AccountRequest({required this.emailOrPhoneNumber, required this.password});

  @override
  Map<String, dynamic> toJson() {
    return {
      "emailOrPhoneNumber": emailOrPhoneNumber,
      "password": password,
    };
  }
}
