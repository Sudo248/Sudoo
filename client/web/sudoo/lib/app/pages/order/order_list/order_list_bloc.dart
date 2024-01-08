import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/app/pages/order/order_list/order_list_page.dart';
import 'package:sudoo/domain/model/order/order_status.dart';
import 'package:sudoo/domain/model/order/order_supplier_info.dart';
import 'package:sudoo/domain/repository/order_repository.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';

import '../../../model/order_supplier_info_action_callback.dart';
import 'order_list_data_source.dart';

class OrderListBloc extends BaseBloc
    implements OrderSupplierInfoActionCallback {

  final OrderRepository orderRepository;
  late final OrderListDataSource orderListDatSource;
  Future Function(String)? onClickDetail;
  final ValueNotifier<int> totalOrders = ValueNotifier(0);

  final List<OrderStatus> currentFilterOrderStatus =
      OrderStatus.values.toList(growable: true);

  OrderListBloc(this.orderRepository) {
    orderListDatSource = OrderListDataSource(loadMore, this);
  }

  Future<void> loadMore() async {}

  @override
  void onDispose() {
    // should dispose
    // orderListDatSource.dispose();
  }

  @override
  void onInit() async {
    loadingController.showLoading();
    await getListOrderSupplier();
    loadingController.hideLoading();
  }

  Future<List<OrderSupplierInfo>> getListOrderSupplier() async {
    final result = await orderRepository.getListOrderSupplier();
    if (result.isSuccess) {
      totalOrders.value = result.get().length;
      orderListDatSource.setOrders(result.get());
      return Future.value(result.get());
    } else {
      showErrorMessage(result.getError());
      return Future.value([]);
    }
  }

  void updateOrderStatus(String orderSupplierId, OrderStatus status) {
    orderListDatSource.updateOrderStatus(orderSupplierId, status);
  }

  @override
  Future<void> onOpenDetail(OrderSupplierInfo orderSupplierInfo) async {
    onClickDetail?.call(orderSupplierInfo.orderSupplierId);
  }

  Future<void> filterStatus() async {
    clearFilter(columnName: ColumnName.orderStatus.name);
    if (currentFilterOrderStatus.isNotEmpty) {
      for (var element in currentFilterOrderStatus) {
        orderListDatSource.addFilter(
          ColumnName.orderStatus.name,
          FilterCondition(
            type: FilterType.equals,
            value: element.value,
            filterBehavior: FilterBehavior.stringDataType,
          ),
        );
      }
    }
  }

  void clearFilterStatus() {
    currentFilterOrderStatus.clear();
    currentFilterOrderStatus.addAll(OrderStatus.values);
    clearFilter(
      columnName: ColumnName.orderStatus.name,
    );
  }

  void clearFilter({String? columnName}) {
    orderListDatSource.clearFilters(columnName: columnName);
  }
}
