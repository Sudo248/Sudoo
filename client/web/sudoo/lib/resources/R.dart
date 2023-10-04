import 'package:flutter/material.dart';

abstract class R {
  static AppString string = AppString();
  static AppColor color = AppColor();
  static AppTheme theme = AppTheme();
  static AppDrawable drawable = AppDrawable();
  static AppFont font = AppFont();
  static AppStyle style = AppStyle();
  static AppButtonStyle buttonStyle = AppButtonStyle();
}

class AppString extends R {
  String get appName => "Sudoo";

  String get home => "Home";

  String get email => "Email";

  String get password => "Password";

  String get confirmPassword => "Confirm password";

  String get invalidEmail => "Invalid email";

  String get invalidPassword => "Password must be at least 8 character";

  String get wrongPassword => "Wrong password";

  String get signIn => "Sign in";

  String get signUp => "Sign up";

  String get welcomeBack => "Welcome back!!!";

  String get welcomeTo => "Welcome to Sudoo";

  String get dontHaveAcount => "I don't have an account?";

  String get haveAcount => "I have an account?";

  String get sellerSKU => "Seller SKU";

  String get amount => "Amount";

  String get categories => "Categories";

  String get listedPrice => "Listed price";

  String get price => "Price";

  String get product => "Product";

  String get saleInfo => "Sale information";

  String get saleStatus => "Sale status";

  String get action => "Action";

  String get from => "From";

  String get to => "To";

  String get otpAuthentication => "OTP Authentication";

  String get otpDescription => "We will send the verification code to your email.";

  String get resendOtp => "Resend OTP";

  String get notReceiveOtp => "Don't receive OTP?";

  String get submit => "Submit";
}

class AppColor extends R {
  Color get primaryColor => const Color(0xff3AC5C9);

  Color get primaryColorDark => const Color(0xFF0D9099);

  Color get primaryColorLight => const Color(0xFF9EF5F8);

  Color get backgroundNavColor => const Color(0xFF002B31);
}

class AppFont extends R {
  String get raleWay => "Raleway";
}

class AppTheme extends R {
  ThemeData get appTheme => ThemeData(
        useMaterial3: true,
        primaryColor: R.color.primaryColor,
        primaryColorDark: R.color.primaryColorDark,
        primaryColorLight: R.color.primaryColorLight,
        fontFamily: R.font.raleWay,
      );
}

class AppStyle extends R {
  TextStyle get h1 => const TextStyle(
        fontSize: 109.66,
        color: Colors.white,
      );

  TextStyle get h2 => const TextStyle(
        fontSize: 67.77,
        color: Colors.white,
      );

  TextStyle get h3 => const TextStyle(
        fontSize: 41.89,
        color: Colors.white,
      );

  TextStyle get h4 => const TextStyle(
        fontSize: 25.89,
        color: Colors.white,
      );

  TextStyle get h4_1 => const TextStyle(
        fontSize: 18,
        color: Colors.white,
      );

  TextStyle get h5 => const TextStyle(
        fontSize: 16,
        color: Colors.white,
      );

  TextStyle get h6 => const TextStyle(
        fontSize: 12,
        color: Colors.white,
      );
}

class AppDrawable extends R {
  // static const String _imagePath = "assets/images";
  static const String _iconPath = "assets/icons";

  String get iconLauncher => "$_iconPath/ic_launcher.svg";
}

class AppButtonStyle extends R {
  ButtonStyle filledButtonStyle({
    Color? backgroundColor,
    OutlinedBorder? shape,
    EdgeInsetsGeometry? padding,
  }) {
    return ButtonStyle(
      backgroundColor: MaterialStateProperty.all<Color?>(
        backgroundColor ?? R.color.primaryColor,
      ),
      shape: MaterialStateProperty.all<OutlinedBorder>(
        shape ??
            RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(5.0),
            ),
      ),
      padding: MaterialStateProperty.all<EdgeInsetsGeometry?>(padding),
    );
  }

  ButtonStyle outlinedButtonStyle({
    Color? backgroundColor,
    BorderSide? side,
    OutlinedBorder? shape,
    EdgeInsetsGeometry? padding,
  }) {
    return ButtonStyle(
      backgroundColor: MaterialStateProperty.all<Color>(
        backgroundColor ?? Colors.white,
      ),
      side: MaterialStateProperty.all<BorderSide>(
        side ??
            BorderSide(
              width: 1.5,
              color: R.color.primaryColor,
            ),
      ),
      shape: MaterialStateProperty.all<OutlinedBorder>(
        shape ??
            RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(5.0),
            ),
      ),
      padding: MaterialStateProperty.all<EdgeInsetsGeometry?>(padding),
    );
  }
}
