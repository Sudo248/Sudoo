import 'package:sudoo/data/api/base_request.dart';

class VerifyOtpRequest extends BaseRequest {
  final String emailOrPhoneNumber;
  final String otp;

  VerifyOtpRequest(this.emailOrPhoneNumber, this.otp);

  @override
  Map<String, dynamic> toJson() {
    return {
      "emailOrPhoneNumber": emailOrPhoneNumber,
      "otp": otp,
    };
  }
}