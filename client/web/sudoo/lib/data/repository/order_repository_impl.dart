import 'dart:convert';

import 'package:shared_preferences/shared_preferences.dart';
import 'package:sudoo/data/api/handle_response.dart';
import 'package:sudoo/data/api/order/order_api_service.dart';
import 'package:sudoo/domain/core/data_state.dart';
import 'package:sudoo/domain/model/order/order_config.dart';
import 'package:sudoo/domain/model/order/order_status.dart';
import 'package:sudoo/domain/model/order/order_supplier_info.dart';
import 'package:sudoo/domain/repository/order_repository.dart';
import 'package:sudoo/domain/type_date_picker.dart';

import '../../domain/model/order/order.dart';
import '../config/pref_keys.dart';

class OrderRepositoryImpl with HandleResponse implements OrderRepository {
  final OrderService orderService;
  final SharedPreferences pref;

  OrderRepositoryImpl(this.orderService, this.pref);

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
  Future<DataState<Order, Exception>> getOrderSupplier(String orderSupplierId,) async {
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
  Future<DataState<bool, Exception>> patchOrderStatus(String orderSupplierId,
      OrderStatus status,) async {
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

  @override
  Future<DataState<OrderConfig, Exception>> getConfig() async {
    final int counter = pref.getInt(PrefKeys.counter) ?? 0;
    OrderConfig? config = _getLocalConfig();
    if (counter % 2 == 0 || config == null) {
      final response = await handleResponse(() => orderService.getOrderConfig(),
          fromJson: (json) =>
              OrderConfig.fromJson(json as Map<String, dynamic>));
      if (response.isSuccess) {
        config = response.get();
        _saveOrderConfig(config!);
        return DataState.success(config);
      } else {
        return DataState.success(OrderConfig(false));
      }
    } else {
      return DataState.success(config);
    }
  }

  OrderConfig? _getLocalConfig() {
    final stringConfig = pref.getString(PrefKeys.orderConfig);
    if (stringConfig == null) return null;
    final jsonConfig = json.decode(stringConfig);
    return OrderConfig.fromJson(jsonConfig as Map<String, dynamic>);
  }

  void _saveOrderConfig(OrderConfig config) {
    Map<String, dynamic> map = config.toJson();
    final stringConfig = json.encode(map);
    pref.setString(PrefKeys.orderConfig, stringConfig);
  }
}
