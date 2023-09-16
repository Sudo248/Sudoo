

class AccountRequest {
  final String emailOrPhoneNumber;
  final String password;

  AccountRequest({required this.emailOrPhoneNumber, required this.password});

  Map<String, dynamic> toJson() {
    return {
      "emailOrPhoneNumber": emailOrPhoneNumber,
      "password": password,
    };
  }
}
