import 'package:shared_preferences/shared_preferences.dart';
import 'package:sudoo/data/api/auth/auth_api_service.dart';
import 'package:sudoo/data/api/auth/request/account_request.dart';
import 'package:sudoo/data/api/auth/request/change_password_request.dart';
import 'package:sudoo/data/api/auth/request/verify_otp_request.dart';
import 'package:sudoo/data/api/handle_response.dart';
import 'package:sudoo/data/config/pref_keys.dart';
import 'package:sudoo/data/dto/auth/token_dto.dart';
import 'package:sudoo/domain/core/data_state.dart';
import 'package:sudoo/domain/model/auth/account.dart';
import 'package:sudoo/domain/model/auth/verify_otp.dart';
import 'package:sudoo/domain/repository/auth_repository.dart';

class AuthRepositoryImpl with HandleResponse implements AuthRepository {
  final AuthApiService authApiService;
  final SharedPreferences pref;

  const AuthRepositoryImpl(this.authApiService, {required this.pref});

  @override
  Future<DataState<void, Exception>> changePassword(
    String oldPassword,
    String newPassword,
  ) async {
    final request = ChangePasswordRequest(
      oldPassword: oldPassword,
      newPassword: newPassword,
    );
    final response = await handleResponse(
      () => authApiService.changePassword(request),
    );
    return response;
  }

  @override
  Future<DataState<void, Exception>> logout() async {
    return await handleResponse(() => authApiService.logout());
  }

  @override
  Future<DataState<void, Exception>> signIn(Account account) async {
    final request = AccountRequest(
      emailOrPhoneNumber: account.emailOrPhoneNumber,
      password: account.password,
    );
    final response = await handleResponse(
      () => authApiService.signIn(request),
      fromJson: (json) => TokenDto.fromJson(json as Map<String, dynamic>),
    );
    if (response.isSuccess) {
      final TokenDto token = response.get();
      pref.setString(PrefKeys.token, token.token);
      pref.setString(PrefKeys.refreshToken, token.refreshToken ?? "");
    }
    return response;
  }

  @override
  Future<DataState<void, Exception>> signUp(Account account) async {
    final request = AccountRequest(
      emailOrPhoneNumber: account.emailOrPhoneNumber,
      password: account.password,
    );
    final response = await handleResponse(
      () => authApiService.signUp(request),
    );
    return response;
  }

  @override
  Future<DataState<void, Exception>> submitOtp(VerifyOtp verifyOtp) async {
    final request =
        VerifyOtpRequest(verifyOtp.emailOrPhoneNumber, verifyOtp.otp);
    final response = await handleResponse(
        () => authApiService.verifyOtp(request),
        fromJson: (json) => TokenDto.fromJson(json as Map<String, dynamic>));
    if (response.isSuccess) {
      final TokenDto token = response.get();
      pref.setString(PrefKeys.token, token.token);
      pref.setString(PrefKeys.refreshToken, token.refreshToken ?? "");
    }
    return response;
  }
}
