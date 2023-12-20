import 'package:flutter/material.dart';

abstract class R {
  static AppString string = AppString();
  static AppColor color = AppColor();
  static AppTheme theme = AppTheme();
  static AppDrawable drawable = AppDrawable();
  static AppStyle style = AppStyle();
  static AppButtonStyle buttonStyle = AppButtonStyle();
}

class AppString extends R {
  String get appName => "Sudoo";

  String get home => "Home";

  String get listProduct => "List product";

  String get createProduct => "Create product";

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

  String get dontHaveAccount => "I don't have an account?";

  String get haveAccount => "I have an account?";

  String get sellerSKU => "Seller SKU";

  String get amount => "Amount";

  String get categories => "Categories";

  String get promotions => "Promotions";

  String get banner => "Banner";

  String get listedPrice => "Listed price";

  String get quantity => "Quantity";

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

  String get images => "Images";

  String get name => "Name";
  String get fullName => "Full name";

  String get brand => "Brand";

  String get phoneNumber => "Phone number";

  String get contactUrl => "Contact URL";

  String get description => "Description";

  String get optional => "Optional";

  String get save => "Save";

  String get upload => "Upload";

  String get extras => "Extras";

  String get enable3DViewer => "Enable 3D viewer";

  String get enableARViewer => "Enable AR viewer";

  String get chooseFile => "Choose file";

  String get weight => "Weight";
  String get height => "Height";
  String get width => "Width";
  String get length => "Length";
  String get sizeWeight => "Size and Weight";
  String get info => "Info";
  String get store => "Store";
  String get model => "Model";
  String get stores => "Stores";
  String get dob => "Date of birth";
  String get province => "Province";
  String get district => "District";
  String get ward => "Ward";
  String get address => "Address";
  String get totalProduct => "Total Product";
  String get createdAt => "Created at";
  String get customer => "Customer";
  String get total => "Total";
  String get totalRevenue => "Total revenue";
  String get income => "Income";
  String get paymentType => "Payment type";
  String get paymentDateTime => "Payment date";
  String get deliveryTo => "Delivery to";
  String get expectedDelivery => "Expected delivery";
  String get haveNotPayment => "Have not yet payment";
  String get edit => "Edit";
  String get detail => "Detail";
  String get orderDetail => "Order detail";
  String get orderInfo => "Order information";
  String get paymentInfo => "Payment information";
  String get orderStatus => "Order status";
  String get orders => "Orders";
  String get revenue => "Revenue";
  String get deliveryInfo => "Delivery information";
  String get totalPrincipal => "Total principal";
  String get discount => "Discount";
  String get receiver => "Receiver";
  String get statistic => "Statistic";
  String get claim => "Claim";
  String get claimMoney => "Claim money";
  String get history => "History";
  String get numberSerialBank => "Number serial bank";
  String get transaction => "Transaction";
  String get bank => "Bank";
  String get download => "Download";
  String get statisticBy => "Statistic by";
  String get cantRevertStatus => "Statistic by";
  String get orderId => "Order code";
  String get version => "Version";
  String get evaluate => "Evaluate";
  String get buildAt => "Build at";
  String get userSize => "User size";
  String get productSize => "Product size";
  String get categorySize => "Category size";
  String get reviewSize => "Review size";
  String get syncUserDescription => "Sync all current users to recommend service";
  String get syncProductDescription => "Sync all current products to recommend service";
  String get syncReviewDescription => "Sync all current reviews to recommend service";
  String get syncAllDescription => "Sync all current data to recommend service";
}

class AppColor extends R {
  Color get primaryColor => const Color(0xff3AC5C9);

  Color get primaryColorDark => const Color(0xFF0D9099);

  Color get primaryColorLight => const Color(0xFF9EF5F8);

  // Color get backgroundNavColor => const Color(0xFF002B31);
  Color get backgroundNavColor => const Color(0xFF033E48);

  Color get backgroundAvatarColor => const Color(0x80595959);

  Color get backgroundMenuColor => const Color(0xFFE4EAF3);
}

class AppTheme extends R {
  ThemeData get appTheme => ThemeData(
        useMaterial3: true,
        primaryColor: R.color.primaryColor,
        primaryColorDark: R.color.primaryColorDark,
        primaryColorLight: R.color.primaryColorLight,
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
  static const String _imagePath = "assets/images";
  static const String _iconPath = "assets/icons";

  String imgEmptyList = "$_imagePath/img_empty_list.png";
  String imgLauncher = "$_imagePath/img_launcher.png";
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
    Size? size,
  }) {
    return ButtonStyle(
      backgroundColor: MaterialStateProperty.all<Color>(
        backgroundColor ?? Colors.white,
      ),
      side: MaterialStateProperty.all<BorderSide>(
        side ??
            const BorderSide(
              width: 1.5,
              color: Colors.blueGrey,
            ),
      ),
      shape: MaterialStateProperty.all<OutlinedBorder>(
        shape ??
            RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(5.0),
            ),
      ),
      fixedSize:  MaterialStateProperty.all<Size?>(size),
      padding: MaterialStateProperty.all<EdgeInsetsGeometry?>(padding),
    );
  }
}
