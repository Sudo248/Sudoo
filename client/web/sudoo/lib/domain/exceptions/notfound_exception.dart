import 'package:sudoo/domain/exceptions/base_exception.dart';

class NotFoundException extends BaseException {
  const NotFoundException({String? message = "Not found exception"}) : super(message);
}