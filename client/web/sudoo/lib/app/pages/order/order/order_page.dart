import 'dart:async';

import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/dialog/qr_dialog.dart';
import 'package:sudoo/app/pages/order/order/order_bloc.dart';
import 'package:sudoo/app/widgets/online_image.dart';
import 'package:sudoo/domain/model/discovery/order_cart_product.dart';
import 'package:sudoo/domain/model/order/order.dart';
import 'package:sudoo/domain/model/order/order_status.dart';
import 'package:sudoo/domain/model/order/order_supplier.dart';
import 'package:sudoo/extensions/date_time_ext.dart';
import 'package:sudoo/extensions/double_ext.dart';
import 'package:sudoo/extensions/string_ext.dart';

import '../../../../resources/R.dart';

class OrderPage extends BasePage<OrderBloc> {
  final String? orderSupplierId;
  BuildContext? _currentContext;

  OrderPage({super.key, required this.orderSupplierId}) {
    if (orderSupplierId != null) {
      bloc.fetchOrderSupplier(orderSupplierId!);
    }
  }

  @override
  bool get enableStatePage => true;

  final TextStyle style = const TextStyle(fontSize: 16, color: Colors.black);

  @override
  void onInit() {
    bloc.qr.addListener(_showQrDialog);
    super.onInit();
  }

  @override
  void onDispose() {
    bloc.qr.removeListener(_showQrDialog);
    super.onDispose();
  }

  @override
  Widget build(BuildContext context) {
    _currentContext = context;
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
          child: Text(
            R.string.orderDetail,
            style: style.copyWith(
              fontWeight: FontWeight.bold,
              fontSize: 20,
            ),
          ),
        ),
        Expanded(
          child: orderSupplierId == null
              ? const Center(
                  child: Text(
                    "404 Not found",
                    style: TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                      color: Colors.red,
                    ),
                  ),
                )
              : Padding(
                  padding: const EdgeInsets.all(10.0),
                  child: ValueListenableBuilder(
                    valueListenable: bloc.order,
                    builder: (context, value, child) {
                      if (value == null) {
                        return const SizedBox.shrink();
                      } else {
                        return SingleChildScrollView(
                          child: _buildOrder(context, value),
                        );
                      }
                    },
                  ),
                ),
        ),
      ],
    );
  }

  Widget _buildOrder(BuildContext context, Order order) {
    final OrderSupplier orderSupplier = order.orderSuppliers.first;
    return Column(
      children: [
        Row(
          children: [
            RichText(
              text: TextSpan(
                children: [
                  TextSpan(
                    text: "${R.string.orderId}: ",
                    style: style.copyWith(
                      fontWeight: FontWeight.bold,
                      fontSize: 18,
                      color: Colors.black,
                    ),
                  ),
                  TextSpan(
                    text: "#${orderSupplier.orderSupplierId}",
                    style: style.copyWith(
                      fontWeight: FontWeight.bold,
                      fontSize: 18,
                      color: Colors.blue,
                    ),
                  )
                ]
              ),
            ),
            const SizedBox(
              width: 30,
            ),
            ValueListenableBuilder(
              valueListenable: bloc.orderStatus,
              builder: (context, value, child) =>
              value == null
                  ? const Center(
                      child: SizedBox.square(
                        dimension: 30,
                        child: CircularProgressIndicator(),
                      ),
                    )
                  : DecoratedBox(
                      decoration: BoxDecoration(
                        color: value.color,
                        borderRadius: BorderRadius.circular(3),
                      ),
                      child: DropdownButton<String>(
                        underline: const SizedBox.shrink(),
                        iconEnabledColor: Colors.white,
                        value: value.value,
                        selectedItemBuilder: (context) => OrderStatus.values
                            .map(
                              (e) => Padding(
                                padding: const EdgeInsets.symmetric(
                                    horizontal: 25, vertical: 5),
                                child: Center(
                                  child: Text(
                                    e.value,
                                    style: style.copyWith(
                                      color: Colors.white,
                                      fontSize: 18,
                                    ),
                                  ),
                                ),
                              ),
                            )
                            .toList(),
                        items: OrderStatus.values
                            .map<DropdownMenuItem<String>>(
                              (e) =>
                                  bloc.enableStatus.contains(e)
                                      ? DropdownMenuItem<String>(
                                          enabled: value.index < e.index,
                                          value: e.value,
                                          child: _buildOrderStatusItem(e),
                                        )
                                      : DropdownMenuItem(
                                          value: e.value,
                                          enabled: false,
                                          child: const SizedBox.shrink(),
                                        ),
                            )
                            .toList(),
                        onChanged: (value) {
                          bloc.onStatusChanged(
                            OrderStatus.fromValue(value),
                          );
                        },
                      ),
                    ),
            ),
          ],
        ),
        const SizedBox(
          height: 20,
        ),
        _divider(),
        Table(
          columnWidths: const {
            0: FlexColumnWidth(5),
            1: FlexColumnWidth(1),
            2: FlexColumnWidth(1.5),
            3: FlexColumnWidth(1.5),
          },
          // border: TableBorder.all(
          //   color: Colors.grey,
          // ),
          defaultVerticalAlignment: TableCellVerticalAlignment.middle,
          children: [
            _buildTableTitle(),
            ...orderSupplier.orderCartProducts
                .map((e) => _buildProductItem(e))
                .toList()
          ],
        ),
        _divider(),
        ColoredBox(
          color: Colors.white,
          child: Padding(
            padding: const EdgeInsets.all(10),
            child: Row(
              children: [
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Text(
                        R.string.orderInfo,
                        style: style.copyWith(
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(
                        height: 20,
                      ),
                      Table(
                        columnWidths: const {
                          0: FlexColumnWidth(1),
                          1: FlexColumnWidth(1),
                        },
                        defaultVerticalAlignment:
                            TableCellVerticalAlignment.top,
                        children: [
                          _buildSimpleRow(
                            R.string.createdAt,
                            order.createdAt.formatDateTime(),
                          ),
                          _buildSimpleRow(
                            R.string.totalPrincipal,
                            order.totalPrice.formatCurrency(),
                          ),
                          _buildSimpleRow(
                            R.string.discount,
                            0.0.formatCurrency(),
                          ),
                          _buildSimpleRow(
                            R.string.total,
                            order.finalPrice.formatCurrency(),
                          ),
                          _buildSimpleRow(
                            R.string.paymentType,
                            order.payment.paymentType,
                          ),
                        ],
                      )
                    ],
                  ),
                ),
                const VerticalDivider(
                  width: 5,
                  color: Colors.grey,
                ),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Text(
                        R.string.deliveryInfo,
                        style: style.copyWith(
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(
                        height: 20,
                      ),
                      Table(
                        columnWidths: const {
                          0: FlexColumnWidth(1),
                          1: FlexColumnWidth(1),
                        },
                        defaultVerticalAlignment:
                            TableCellVerticalAlignment.top,
                        children: [
                          _buildSimpleRow(
                            R.string.expectedDelivery,
                            orderSupplier.expectedReceiveDateTime
                                .formatDateTime(),
                          ),
                          _buildSimpleRow(
                            R.string.receiver,
                            order.user.fullName,
                          ),
                          _buildSimpleRow(
                            R.string.phoneNumber,
                            order.user.emailOrPhoneNumber,
                          ),
                          _buildSimpleRow(
                            R.string.address,
                            order.address,
                          ),
                        ],
                      )
                    ],
                  ),
                )
              ],
            ),
          ),
        ),
        _divider()
      ],
    );
  }

  Widget _divider() {
    return const Divider(
      height: 20,
      color: Colors.grey,
    );
  }

  TableRow _buildTableTitle() {
    return TableRow(
      children: [
        R.string.product,
        R.string.quantity,
        R.string.price,
        R.string.total,
      ]
          .map(
            (e) => Padding(
              padding: const EdgeInsets.symmetric(vertical: 10),
              child: Align(
                alignment: Alignment.center,
                child: Text(
                  e,
                  style: style.copyWith(
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
          )
          .toList(),
    );
  }

  TableRow _buildProductItem(
    OrderCartProduct orderCartProduct, {
    double rowHeight = 80,
  }) {
    return TableRow(
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 8.0),
          child: SizedBox(
            height: rowHeight,
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                OnlineImage(
                  orderCartProduct.product.images.first,
                  width: rowHeight * 0.9,
                  height: rowHeight * 0.9,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(3),
                    border: Border.all(color: Colors.grey, width: 0.3),
                  ),
                  fit: BoxFit.contain,
                ),
                const SizedBox(
                  width: 10,
                ),
                Expanded(
                  child: Text(
                    "[${orderCartProduct.product.sku}] ${orderCartProduct.product.name}",
                    style: style,
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                  ),
                ),
              ],
            ),
          ),
        ),
        SizedBox(
          height: rowHeight,
          child: Align(
            alignment: Alignment.center,
            child: Text(
              orderCartProduct.quantity.toString(),
              style: style,
              maxLines: 1,
            ),
          ),
        ),
        SizedBox(
          height: rowHeight,
          child: Align(
            alignment: Alignment.center,
            child: Text(
              orderCartProduct.product.price.formatCurrency(),
              style: style,
              maxLines: 1,
            ),
          ),
        ),
        SizedBox(
          height: rowHeight,
          child: Align(
            alignment: Alignment.center,
            child: Text(
              orderCartProduct.totalPrice.formatCurrency(),
              style: style,
              maxLines: 1,
            ),
          ),
        ),
      ],
    );
  }

  TableRow _buildSimpleRow(String label, String value) {
    return TableRow(
      children: [
        Text(
          label,
          style: style,
        ),
        Text(
          value,
          style: style,
        ),
      ],
    );
  }

  Widget _buildOrderStatusItem(OrderStatus status) {
    return DecoratedBox(
      decoration: BoxDecoration(
        color: status.color,
        borderRadius: BorderRadius.circular(3),
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 25, vertical: 5),
        child: Text(
          status.value,
          style: style.copyWith(
            color: Colors.white,
            fontSize: 18,
          ),
        ),
      ),
    );
  }

  Future<void> _showQrDialog() async {
    if (_currentContext != null && !bloc.qr.value.isNullOrEmpty) {
      showDialog(
        context: _currentContext!,
        builder: (context) => QrDialog(
          data: bloc.qr.value!,
          onDownload: bloc.onDownloadQr,
        ),
      );
    }
  }
}
