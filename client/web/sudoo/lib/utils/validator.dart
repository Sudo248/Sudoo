import 'package:sudoo/extensions/string_ext.dart';

abstract class Validator {
  static RegExp emailRegex = RegExp(r"^[\w-\.]+@([\w-]+\.)+[a-z]{2,4}$");

  static bool validateEmail(String? email) {
    return !email.isNullOrEmpty && emailRegex.hasMatch(email!);
  }

  static bool validatePassword(String? password) {
    return !password.isNullOrEmpty && password!.length >= 8;
  }
}
