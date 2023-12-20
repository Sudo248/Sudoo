import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/app/model/order_supplier_info_action_callback.dart';
import 'package:sudoo/app/pages/order/order_list/order_list_page.dart';
import 'package:sudoo/app/pages/order/order_list/order_supplier_item/delivery_info_cell.dart';
import 'package:sudoo/app/pages/order/order_list/order_supplier_item/order_info_cell.dart';
import 'package:sudoo/app/pages/order/order_list/order_supplier_item/order_status_cell.dart';
import 'package:sudoo/app/routes/app_router.dart';
import 'package:sudoo/domain/model/order/order_status.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';

import '../../../../domain/model/order/order_supplier_info.dart';
import '../../../../resources/R.dart';
import 'order_supplier_item/payment_info_cell.dart';

class OrderListDataSource extends DataGridSource {
  final AsyncCallback loadMore;
  final OrderSupplierInfoActionCallback actionCallback;
  final List<OrderSupplierInfo> orders = List.empty(growable: true);
  List<DataGridRow> _dataRows = List.empty();
  TextStyle style = const TextStyle(fontSize: 16, color: Colors.black);

  OrderListDataSource(
    this.loadMore,
    this.actionCallback,
  );

  @override
  DataGridRowAdapter? buildRow(DataGridRow row) {
    return DataGridRowAdapter(
        cells: row.getCells().map<Widget>((cell) {
      switch (cell.columnName) {
        case "orderInfo":
          return Padding(
            padding: const EdgeInsets.all(10.0),
            child: OrderInfoCell(
              orderSupplierId: cell.value.orderSupplierId,
              createdAt: cell.value.createdAt,
              storeName: AppRouter.isAdmin ? cell.value.supplierName : null,
              style: style,
            ),
          );
        case "paymentInfo":
          return Padding(
            padding: const EdgeInsets.all(10.0),
            child: PaymentInfoCell(
              amount: cell.value.totalPrice,
              paymentType: cell.value.paymentType,
              paymentDateTime: cell.value.paymentDateTime,
              style: style,
            ),
          );
        case "orderStatus":
          return Padding(
            padding: const EdgeInsets.all(10.0),
            child: OrderStatusCell(
              status: cell.value.status,
              style: style,
            ),
          );
        case "deliveryInfo":
          return Padding(
            padding: const EdgeInsets.all(10.0),
            child: DeliveryInfoCell(
              userFullName: cell.value.userFullName,
              userPhoneNumber: cell.value.userPhoneNumber,
              address: cell.value.address,
              expectedReceiveDateTime: cell.value.expectedReceiveDateTime,
              style: style,
            ),
          );
        case "action":
          return Center(
            child: OutlinedButton(
              style: R.buttonStyle.outlinedButtonStyle(),
              onPressed: () => actionCallback.onOpenDetail(cell.value),
              child: Text(
                R.string.detail,
                style: style,
              ),
            ),
          );
        default:
          return const SizedBox.shrink();
      }
    }).toList());
  }

  @override
  List<DataGridRow> get rows => _dataRows;

  @override
  Future<void> handleLoadMoreRows() => loadMore();

  DataGridRow _mapFromOrderSupplier(OrderSupplierInfo orderSupplierInfo) =>
      DataGridRow(cells: [
        DataGridCell(
            columnName: ColumnName.orderInfo.name, value: orderSupplierInfo),
        DataGridCell(
            columnName: ColumnName.paymentInfo.name, value: orderSupplierInfo),
        DataGridCell(
            columnName: ColumnName.orderStatus.name, value: orderSupplierInfo),
        DataGridCell(
            columnName: ColumnName.deliveryInfo.name, value: orderSupplierInfo),
        DataGridCell(
            columnName: ColumnName.action.name, value: orderSupplierInfo),
      ]);

  void buildDataRows() {
    _dataRows = orders
        .map<DataGridRow>((order) => _mapFromOrderSupplier(order))
        .toList();
  }

  void setOrders(List<OrderSupplierInfo> orders) {
    this.orders.clear();
    this.orders.addAll(orders);
    notifyDataSetChange();
  }

  void updateOrderStatus(String orderSupplierId, OrderStatus status) {
    final index = orders.indexWhere((element) => element.orderSupplierId == orderSupplierId);
    orders[index].status = status;
    notifyItemChange(index, 2);
  }

  void notifyDataSetChange() {
    buildDataRows();
    notifyListeners();
  }

  void notifyItemChange(int rowIndex, int columnIndex) {
    notifyDataSourceListeners(
      rowColumnIndex: RowColumnIndex(rowIndex, columnIndex),
    );
  }
}
