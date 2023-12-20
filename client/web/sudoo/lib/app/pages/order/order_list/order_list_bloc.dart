import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/model/order/order_status.dart';
import 'package:sudoo/domain/model/order/order_supplier_info.dart';
import 'package:sudoo/domain/repository/order_repository.dart';

import '../../../model/order_supplier_info_action_callback.dart';
import 'order_list_data_source.dart';

class OrderListBloc extends BaseBloc
    implements OrderSupplierInfoActionCallback {
  static OrderStatus? updatedOrderStatus = null;

  final OrderRepository orderRepository;
  late final OrderListDataSource orderListDatSource;
  Future Function(String)? onClickDetail;
  final ValueNotifier<int> totalOrders = ValueNotifier(0);

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

  @override
  Future<void> onOpenDetail(OrderSupplierInfo orderSupplierInfo) async {
    await onClickDetail?.call(orderSupplierInfo.orderSupplierId);
    if (updatedOrderStatus != null) {
      orderListDatSource.updateOrderStatus(orderSupplierInfo.orderSupplierId, updatedOrderStatus!);
      updatedOrderStatus = null;
    }
  }


}
