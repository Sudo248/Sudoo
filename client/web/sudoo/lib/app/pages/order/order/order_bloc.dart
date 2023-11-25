import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/model/order/order_status.dart';

import '../../../../domain/model/order/order.dart';
import '../../../../domain/repository/order_repository.dart';

class OrderBloc extends BaseBloc {
  final OrderRepository orderRepository;
  final ValueNotifier<Order?> order = ValueNotifier(null);
  final ValueNotifier<OrderStatus?> orderStatus = ValueNotifier(null);

  OrderBloc(this.orderRepository);

  @override
  void onDispose() {
    // TODO: implement onDispose
  }

  @override
  void onInit() {
    // TODO: implement onInit
  }

  Future<void> fetchOrderSupplier(String orderSupplierId) async {
    loadingController.showLoading();
    final result = await orderRepository.getOrderSupplier(orderSupplierId);
    if (result.isSuccess) {
      order.value = result.get();
      orderStatus.value = result.get().orderSuppliers.first.status;
    } else {
      showErrorMessage(result.getError());
    }
    loadingController.hideLoading();
  }

  Future<void> patchOrderStatus(OrderStatus status) async {
    if (order.value != null && order.value!.orderSuppliers.isNotEmpty && status != order.value!.orderSuppliers.first.status) {
      final result = await orderRepository.patchOrderStatus(order.value!.orderSuppliers.first.orderSupplierId, status);
      if (result.isSuccess) {
        order.value!.orderSuppliers.first.status = status;
        orderStatus.value = status;
      } else {
        showErrorMessage(result.getError());
      }
    }
  }

  void onStatusChanged(OrderStatus? status) {
    if (status == null) return;
    patchOrderStatus(status);
  }
}