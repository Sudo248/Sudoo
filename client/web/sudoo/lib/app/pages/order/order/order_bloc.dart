import 'dart:typed_data';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:image_downloader_web/image_downloader_web.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/data/api/order/order_api_service.dart';
import 'package:sudoo/domain/model/order/order_status.dart';
import 'package:sudoo/extensions/string_ext.dart';

import '../../../../domain/model/order/order.dart';
import '../../../../domain/repository/order_repository.dart';

class OrderBloc extends BaseBloc {
  final OrderRepository orderRepository;
  final ValueNotifier<Order?> order = ValueNotifier(null);
  final ValueNotifier<OrderStatus?> orderStatus = ValueNotifier(null);
  final ValueNotifier<String?> qr = ValueNotifier(null);

  OrderBloc(this.orderRepository);

  @override
  void onDispose() {
    order.dispose();
    orderStatus.dispose();
    qr.dispose();
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
    if (isSuccess && needGenQr) {
      qr.value = getDetailOrderSupplierUrl();
    }
  }

  String getDetailOrderSupplierUrl() {
    return "${OrderService.orderSupplierUrl}/${order.value!.orderSuppliers.first.orderSupplierId}";
  }

  Future<void> onDownloadQr(GlobalKey qrKey) async {
    try {
      RenderRepaintBoundary boundary =
          qrKey.currentContext!.findRenderObject() as RenderRepaintBoundary;
      var image = await boundary.toImage(pixelRatio: 3.0);
      final whitePaint = Paint()..color = Colors.white;
      final recorder = PictureRecorder();
      final canvas = Canvas(recorder,
          Rect.fromLTWH(0, 0, image.width.toDouble(), image.height.toDouble()));
      canvas.drawRect(
          Rect.fromLTWH(0, 0, image.width.toDouble(), image.height.toDouble()),
          whitePaint);
      canvas.drawImage(image, Offset.zero, Paint());
      final picture = recorder.endRecording();
      final img = await picture.toImage(image.width, image.height);
      ByteData? byteData = await img.toByteData(format: ImageByteFormat.png);
      Uint8List pngBytes = byteData!.buffer.asUint8List();

      String fileName = "Order_#${qr.value.orEmpty}.png";
      await WebImageDownloader.downloadImageFromUInt8List(
          uInt8List: pngBytes, name: fileName, imageType: ImageType.png);
      showInfoMessage("Download success");
    } catch (e) {
      print(e);
    }
  }
}