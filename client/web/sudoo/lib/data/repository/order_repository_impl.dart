import 'package:sudoo/data/api/handle_response.dart';
import 'package:sudoo/data/api/order/order_api_service.dart';
import 'package:sudoo/domain/core/data_state.dart';
import 'package:sudoo/domain/model/order/order_status.dart';
import 'package:sudoo/domain/model/order/order_supplier_info.dart';
import 'package:sudoo/domain/type_date_picker.dart';
import 'package:sudoo/domain/repository/order_repository.dart';

import '../../domain/model/order/order.dart';

class OrderRepositoryImpl with HandleResponse implements OrderRepository {
  final OrderService orderService;

  OrderRepositoryImpl(this.orderService);

  @override
  Future<DataState<List<OrderSupplierInfo>, Exception>>
      getListOrderSupplier() async {
    final response = await handleResponse(
      () => orderService.getListOrderSupplier(),
      fromJson: (json) => (json as List<dynamic>)
          .map(
            (e) => OrderSupplierInfo.fromJson(e as Map<String, dynamic>),
          )
          .toList(),
    );
    if (response.isSuccess) {
      return DataState.success(response.get());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<Order, Exception>> getOrderSupplier(
    String orderSupplierId,
  ) async {
    final response = await handleResponse(
      () => orderService.getOrderSupplierDetail(orderSupplierId),
      fromJson: (json) => Order.fromJson(
        json as Map<String, dynamic>,
      ),
    );
    if (response.isSuccess) {
      return DataState.success(response.get());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<bool, Exception>> patchOrderStatus(
    String orderSupplierId,
    OrderStatus status,
  ) async {
    final response = await handleResponse(
      () => orderService.patchOrderStatus(orderSupplierId, status),
      fromJson: (json) => json as Map<String, dynamic>,
    );
    if (response.isSuccess) {
      return DataState.success(true);
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<Map<String, dynamic>, Exception>> getStatisticRevenue({
    TypeDatePicker condition = TypeDatePicker.day,
    required DateTime from,
    required DateTime to,
  }) async {
    final response = await handleResponse(
      () => orderService.getStatisticRevenue(condition, from, to),
      fromJson: (json) => json as Map<String, dynamic>,
    );
    if (response.isSuccess) {
      return DataState.success(response.get());
    } else {
      return DataState.error(response.getError());
    }
  }
}
