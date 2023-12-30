import 'package:sudoo/domain/model/order/order_config.dart';
import 'package:sudoo/domain/model/order/order_supplier_info.dart';
import 'package:sudoo/domain/type_date_picker.dart';

import '../core/data_state.dart';
import '../model/order/order.dart';
import '../model/order/order_status.dart';

abstract class OrderRepository {
  Future<DataState<OrderConfig, Exception>> getConfig();

  Future<DataState<List<OrderSupplierInfo>, Exception>> getListOrderSupplier();

  Future<DataState<Order, Exception>> getOrderSupplier(String orderSupplierId);

  Future<DataState<bool, Exception>> patchOrderStatus(
      String orderSupplierId, OrderStatus status);

  Future<DataState<Map<String, dynamic>, Exception>> getStatisticRevenue({
    TypeDatePicker condition = TypeDatePicker.day,
    required DateTime from,
    required DateTime to,
  });
}
