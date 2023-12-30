import 'package:flutter/material.dart';
import 'package:image_downloader_web/image_downloader_web.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/app/pages/order/order_list/order_list_bloc.dart';
import 'package:sudoo/data/api/order/order_api_service.dart';
import 'package:sudoo/domain/common/Constants.dart';
import 'package:sudoo/domain/model/order/order_status.dart';

import '../../../../domain/model/order/order.dart';
import '../../../../domain/repository/order_repository.dart';

class OrderBloc extends BaseBloc {
  final OrderRepository orderRepository;
  final ValueNotifier<Order?> order = ValueNotifier(null);
  final ValueNotifier<OrderStatus?> orderStatus = ValueNotifier(null);
  final ValueNotifier<String?> qr = ValueNotifier(null);
  final List<OrderStatus> enableStatus = List.empty(growable: true);
  final OrderListBloc _orderListBloc;

  OrderBloc(this.orderRepository, this._orderListBloc);

  @override
  void onDispose() {
    order.dispose();
    orderStatus.dispose();
    qr.dispose();
  }

  @override
  void onInit() {
    getEnableListStatus();
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

  Future<bool> patchOrderStatus(OrderStatus status) async {
    if (order.value != null &&
        order.value!.orderSuppliers.isNotEmpty &&
        status != order.value!.orderSuppliers.first.status) {
      final result = await orderRepository.patchOrderStatus(
          order.value!.orderSuppliers.first.orderSupplierId, status);
      if (result.isSuccess) {
        order.value!.orderSuppliers.first.status = status;
        orderStatus.value = status;
        return Future.value(true);
      } else {
        showErrorMessage(result.getError());
        return Future.value(false);
      }
    } else {
      return Future.value(false);
    }
  }

  Future<void> onStatusChanged(OrderStatus? status) async {
    if (status == null) return;
    final needGenQr = order.value != null &&
        order.value!.orderSuppliers.isNotEmpty &&
        order.value!.orderSuppliers.first.status == OrderStatus.prepare &&
        status == OrderStatus.ready;

    final isSuccess = await patchOrderStatus(status);
    if (isSuccess) {
      _orderListBloc.updateOrderStatus(order.value!.orderSuppliers.first.orderSupplierId, status);
      if (needGenQr) {
        qr.value = getDetailOrderSupplierUrl();
      }
    }
  }

  String getDetailOrderSupplierUrl() {
    return "${OrderService.orderSupplierUrl}/${order.value!.orderSuppliers.first.orderSupplierId}";
  }

  Future<void> onDownloadQr(GlobalKey qrKey) async {
    try {
      await WebImageDownloader.downloadImageFromWeb(
        "${Constants.createQrUrl}${qr.value}",
        name: "Order_#${order.value!.orderSuppliers.first.orderSupplierId}.png",
        imageType: ImageType.png,
      );
      showInfoMessage("Download success");
    } catch (e) {
      print(e);
    }
  }

  Future<void> getEnableListStatus() async {
    final enableAllStatus = (await orderRepository.getConfig()).get().enableAllStatus;
    if (enableAllStatus) {
      enableStatus.addAll(OrderStatus.values);
    } else {
      enableStatus.addAll(OrderStatus.getEnableStaffStatus());
    }
  }
}