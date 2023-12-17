import 'package:sudoo/data/api/base_request.dart';

class ChangePasswordRequest extends BaseRequest{
  final String oldPassword;
  final String newPassword;

  ChangePasswordRequest({required this.oldPassword, required this.newPassword});

  @override
  Map<String, dynamic> toJson() {
    return {
      "oldPassword": oldPassword,
      "newPassword": newPassword,
    };
  }
}
