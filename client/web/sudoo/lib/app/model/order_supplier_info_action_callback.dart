import 'package:sudoo/domain/model/order/order_supplier_info.dart';

abstract class OrderSupplierInfoActionCallback {
  Future<void> onOpenDetail(OrderSupplierInfo orderSupplierInfo);
}