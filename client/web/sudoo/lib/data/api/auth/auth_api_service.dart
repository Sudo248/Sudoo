import 'package:sudoo/data/api/api_service.dart';
import 'package:sudoo/data/api/auth/request/account_request.dart';
import 'package:sudoo/data/api/auth/request/change_password_request.dart';
import 'package:sudoo/data/api/auth/request/verify_otp_request.dart';

class AuthApiService {
  static const auth = "/auth";
  static const signInPath = "$auth/sign-in";
  static const signUpPath = "$auth/sign-up";
  static const verifyOtpPath = "$auth/verify-otp";
  static const changePasswordPath = "$auth/change-password";
  static const logoutPath = "$auth/logout";

  final ApiService api;

  const AuthApiService(this.api);

  Future signIn(AccountRequest request) =>
      api.post(signInPath, request: request);

  Future signUp(AccountRequest request) =>
      api.post(signUpPath, request: request);

  Future verifyOtp(VerifyOtpRequest request) =>
      api.post(verifyOtpPath, request: request);

  Future changePassword(ChangePasswordRequest request) =>
      api.post(signUpPath, request: request);

  Future logout() => api.get(logoutPath);
}
