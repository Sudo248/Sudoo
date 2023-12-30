import 'package:sudoo/domain/core/data_state.dart';
import 'package:sudoo/domain/model/auth/account.dart';
import 'package:sudoo/domain/model/auth/auth_config.dart';
import 'package:sudoo/domain/model/auth/token.dart';
import 'package:sudoo/domain/model/auth/verify_otp.dart';

abstract class AuthRepository {
  Future<DataState<AuthConfig, Exception>> getConfig();

  Future<DataState<Token, Exception>> signIn(Account account);

  Future<DataState<dynamic, Exception>> signUp(Account account);

  Future<DataState<Token, Exception>> refreshToken();

  Future<DataState<Token, Exception>> submitOtp(VerifyOtp verifyOtp);

  Future<DataState<dynamic, Exception>> changePassword(
      String oldPassword, String newPassword);

  Future<DataState<dynamic, Exception>> logout();
}
