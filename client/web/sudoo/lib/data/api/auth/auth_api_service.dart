import 'package:sudoo/data/api/api_service.dart';
import 'package:sudoo/data/api/auth/request/account_request.dart';
import 'package:sudoo/data/api/auth/request/change_password_request.dart';

class AuthApiService {
  static const auth = "/auth";
  static const signInPath = "$auth/sign-in";
  static const signUpPath = "$auth/sign-up";
  static const changePasswordPath = "$auth/change-password";
  static const logoutPath = "$auth/logout";

  final ApiService api;

  const AuthApiService(this.api);

  Future signIn(AccountRequest request) =>
      api.post(signInPath, body: request.toJson());

  Future signUp(AccountRequest request) =>
      api.post(signUpPath, body: request.toJson());

  Future changePassword(ChangePasswordRequest request) =>
      api.post(signUpPath, body: request.toJson());

  Future logout() => api.get(logoutPath);
}
