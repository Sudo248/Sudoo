import 'package:sudoo/domain/type_date_picker.dart';
import 'package:sudoo/extensions/date_time_ext.dart';

import '../../../domain/model/order/order_status.dart';
import '../api_service.dart';

class OrderService {
  static const orders = "/orders";
  static const orderSupplier = "$orders/order-supplier";
  static const statistic = "$orders/statistic";
  static const statisticRevenue = "$statistic/revenue";

  final ApiService api;

  const OrderService(this.api);

  Future getListOrderSupplier() => api.get(orderSupplier);

  Future getOrderSupplierDetail(String orderSupplierId) =>
      api.get("$orderSupplier/$orderSupplierId");

  Future patchOrderStatus(String orderSupplierId, OrderStatus status) =>
      api.patch(
        "$orderSupplier/$orderSupplierId",
        body: {
          "status": status.getSerialization(),
        },
      );

  Future getStatisticRevenue(
    TypeDatePicker condition,
    DateTime from,
    DateTime to,
  ) =>
      api.get(statisticRevenue, queryParameters: {
        "key": condition.value,
        "from": from.formatDate(),
        "to": to.formatDate(),
      });
}
