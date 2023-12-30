import 'dart:convert';

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
import 'package:sudoo/domain/model/auth/auth_config.dart';
import 'package:sudoo/domain/model/auth/verify_otp.dart';
import 'package:sudoo/domain/repository/auth_repository.dart';
import 'package:sudoo/extensions/string_ext.dart';

import '../../domain/common/Constants.dart';
import '../../domain/model/auth/token.dart';

class AuthRepositoryImpl with HandleResponse implements AuthRepository {
  final AuthApiService authApiService;
  final SharedPreferences pref;

  const AuthRepositoryImpl(this.authApiService, {required this.pref});

  @override
  Future<DataState<dynamic, Exception>> changePassword(String oldPassword,
      String newPassword,) async {
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
  Future<DataState<dynamic, Exception>> logout() async {
    return await handleResponse(() => authApiService.logout());
  }

  @override
  Future<DataState<Token, Exception>> signIn(Account account) async {
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
      pref.setString(PrefKeys.role, token.role.value);
      return DataState.success(token.toToken());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<dynamic, Exception>> signUp(Account account) async {
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
  Future<DataState<Token, Exception>> submitOtp(VerifyOtp verifyOtp) async {
    final request =
    VerifyOtpRequest(verifyOtp.emailOrPhoneNumber, verifyOtp.otp);
    final response = await handleResponse(
            () => authApiService.verifyOtp(request),
        fromJson: (json) => TokenDto.fromJson(json as Map<String, dynamic>));
    if (response.isSuccess) {
      final TokenDto token = response.get();
      pref.setString(PrefKeys.token, token.token);
      pref.setString(PrefKeys.refreshToken, token.refreshToken ?? "");
      pref.setString(PrefKeys.role, token.role.value);
      return DataState.success(token.toToken());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<Token, Exception>> refreshToken() async {
    final refreshToken = pref.getString(PrefKeys.refreshToken);
    if (refreshToken.isNullOrEmpty) {
      return DataState.error(Exception());
    }
    final response = await handleResponse(
            () => authApiService.refreshToken(refreshToken!),
        fromJson: (json) => TokenDto.fromJson(json as Map<String, dynamic>));
    if (response.isSuccess) {
      final TokenDto token = response.get();
      pref.setString(PrefKeys.token, token.token);
      pref.setString(PrefKeys.refreshToken, token.refreshToken ?? "");
      pref.setString(PrefKeys.role, token.role.value);
      return DataState.success(token.toToken());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<AuthConfig, Exception>> getConfig() async {
    final int counter = pref.getInt(PrefKeys.counter) ?? 0;
    AuthConfig? config = _getLocalConfig();
    if (counter % Constants.timesRefreshConfig == 0 || config == null) {
      final response = await handleResponse(
        () => authApiService.getAuthConfig(),
        fromJson: (json) => AuthConfig.fromJson(json as Map<String, dynamic>),
      );
      if (response.isSuccess) {
        config = response.get();
        _saveAuthConfig(config!);
        return DataState.success(config);
      } else {
        return DataState.success(AuthConfig(false));
      }
    } else {
      return DataState.success(config);
    }
  }

  AuthConfig? _getLocalConfig() {
    final stringConfig = pref.getString(PrefKeys.authConfig);
    if (stringConfig == null) return null;
    final jsonConfig = json.decode(stringConfig);
    return AuthConfig.fromJson(jsonConfig as Map<String, dynamic>);
  }

  void _saveAuthConfig(AuthConfig config) {
    Map<String, dynamic> map = config.toJson();
    final stringConfig = json.encode(map);
    pref.setString(PrefKeys.authConfig, stringConfig);
  }
}
