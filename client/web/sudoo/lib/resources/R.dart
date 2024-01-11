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
  String get todo => "Công việc cần làm";
  String get type => "Loại";
  String get code => "Mã";
  String get updatedAt => "Thời gian cập nhật";
  String get home => "Trang chủ";
  String get listProduct => "Danh sách sản phẩm";
  String get createProduct => "Tạo mới sản phẩm";
  String get productDetail => "Chi tiết sản phẩm";
  String get email => "Email";
  String get emptyList => "Danh sách trống";
  String get password => "Mật khẩu";
  String get confirmPassword => "Xác nhận mật khẩu";
  String get invalidEmail => "Email không khả dụng";
  String get invalidPassword => "Mật khẩu phải có ít nhất 8 kí tự";
  String get wrongPassword => "Sai mật khẩu";
  String get signIn => "Đăng nhập";
  String get signUp => "Đăng ký";
  String get welcomeBack => "Chào mừng quay trở lại!!!";
  String get welcomeTo => "Chào mừng đến với Sudoo";
  String get dontHaveAccount => "Bạn chưa có tài khoản?";
  String get haveAccount => "Bạn đã có tài khoản?";
  String get sellerSKU => "Sku";
  String get gender => "Giới tính";
  String get amount => "Số lượng";
  String get categories => "Danh mục";
  String get listCategory => "Danh sách danh mục";
  String get promotions => "Mã khuyến mại";
  String get banner => "Banner";
  String get listBanner => "Danh sách banner";
  String get listedPrice => "Giá niêm yết";
  String get quantity => "Số lượng";
  String get qrForOrder => "Mã QR cho đơn hàng";
  String get price => "Giá bán";
  String get product => "Sản phẩm";
  String get saleInfo => "Thông tin bán";
  String get saleStatus => "Trạng thái bán";
  String get action => "Thao tác";
  String get from => "Từ";
  String get to => "đến";
  String get otpAuthentication => "Xác thực OTP";
  String get otpDescription => "Chúng tôi sẽ gửi mã xác thực đến email của bạn";
  String get resendOtp => "Gửi lại OTP";
  String get notReceiveOtp => "Chưa nhận được OTP?";
  String get submit => "Submit";
  String get supplier => "Nhà cung cấp";
  String get images => "Hình ảnh";
  String get name => "Tên";
  String get fullName => "Họ và tên";
  String get brand => "Thương hiệu";
  String get phoneNumber => "Số điện thoại";
  String get contactUrl => "Liên hệ";
  String get description => "Mô tả";
  String get optional => "Tùy chọn";
  String get save => "Lưu";
  String get upload => "Tải lên";
  String get extras => "Bổ sung";
  String get enable3DViewer => "Tính năng 3D";
  String get enableARViewer => "Tính năng AR";
  String get chooseFile => "Chọn file";
  String get chooseCategory => "Chọn thể loại";
  String get weight => "Trọng lượng";
  String get height => "Chiều cao";
  String get width => "Chiều rộng";
  String get length => "Chiều dài";
  String get sizeWeight => "Kích thước và trọng lượng";
  String get info => "Thông tin";
  String get store => "Cửa hàng";
  String get model => "Model";
  String get stores => "Danh sách cửa hàng";
  String get listStore => "Danh sách cửa hàng";
  String get dob => "Ngày sinh";
  String get province => "Tỉnh/ Thành phố";
  String get district => "Quận/ Huyện";
  String get ward => "Phường/ Xã";
  String get address => "Địa chỉ";
  String get totalProduct => "Tổng sản phẩm";
  String get totalAmount => "Tổng số lượng";
  String get createdAt => "Tạo lúc";
  String get customer => "Khách hàng";
  String get total => "Tổng cộng";
  String get totalRevenue => "Tổng doanh thu";
  String get income => "Thu nhập";
  String get paymentType => "Loại thanh toán";
  String get paymentDateTime => "Thời gian thanh toán";
  String get deliveryTo => "Giao đến";
  String get expectedDelivery => "Kỳ vọng giao hàng";
  String get haveNotPayment => "Chưa thanh toán";
  String get edit => "Chỉnh sửa";
  String get detail => "Chi tiết";
  String get orderDetail => "Chi tiết đơn hàng";
  String get listOrder => "Danh sách đơn hàng";
  String get orderInfo => "Thông tin đơn hàng";
  String get paymentInfo => "Thông tin thanh toán";
  String get orderStatus => "Trạng thái đơn hàng";
  String get orders => "Danh sách đơn hàng";
  String get revenue => "Doanh thu";
  String get deliveryInfo => "Thông tin giao hàng";
  String get totalPrincipal => "Tổng tiền";
  String get discount => "Giảm giá";
  String get receiver => "Người nhận";
  String get statistic => "Thống kê";
  String get claim => "Nhận";
  String get claimMoney => "Nhận tiền";
  String get history => "Lịch sử";
  String get numberSerialBank => "Số serial ngân hàng";
  String get transaction => "Giao dịch";
  String get historyTransaction => "Lịch sử giao dịch";
  String get bank => "Ngân hàng";
  String get download => "Tải về";
  String get statisticBy => "Thông kê theo";
  String get cantRevertStatus => "Không thể đảo ngược trạng thái";
  String get orderId => "Mã đơn hàng";
  String get version => "Phiên bản";
  String get evaluate => "Đánh giá";
  String get enable => "Cho phép";
  String get value => "Giá trị";
  String get buildAt => "Thời gian thực hiện";
  String get userSize => "Số lượng khách hàng";
  String get productSize => "Số lượng sản phẩm";
  String get categorySize => "Số lượng thể loại";
  String get reviewSize => "Số lượng đánh giá";
  String get syncData => "Đồng bộ dữ liệu";
  String get listModelVersions => "Danh sách thông tin các phiên bản của model";
  String get syncUserDescription => "Đồng bộ tất cả người dùng";
  String get syncProductDescription => "Đồng bộ tất cả sản phẩm";
  String get syncReviewDescription => "Đồng bộ tất cả đánh giá";
  String get syncAllDescription => "Đồng bộ tất cả dữ liệu";
  String get createCategory => "Tạo mới danh mục";
  String get create => "Tạo mới";
  String get update => "Cập nhật";
  String get updateCategory => "Cập nhật danh mục";
  String get createPromotion => "Tạo mới mã giảm giá";
  String get updatePromotion => "Cập nhật mã giảm giá";
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
