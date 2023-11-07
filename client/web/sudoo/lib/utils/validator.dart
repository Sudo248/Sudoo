import 'package:sudoo/extensions/string_ext.dart';

abstract class Validator {
  static List<String> admins = List.of(["admin"]);
  static RegExp emailRegex = RegExp(r"^[\w-\.]+@([\w-]+\.)+[a-z]{2,4}$");

  static bool isAdmin(String email) {
    return admins.contains(email);
  }

  static bool validateEmail(String? email) {
    return !email.isNullOrEmpty && emailRegex.hasMatch(email!);
  }

  static bool validatePassword(String? password) {
    return !password.isNullOrEmpty && password!.length >= 8;
  }
}
