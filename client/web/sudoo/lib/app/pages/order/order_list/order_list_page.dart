import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/order/order_list/order_list_bloc.dart';
import 'package:sudoo/app/routes/app_routes.dart';
import 'package:sudoo/app/widgets/empty_list.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';

import '../../../../resources/R.dart';

enum ColumnName {
  orderInfo("orderInfo"),
  paymentInfo("paymentInfo"),
  orderStatus("orderStatus"),
  deliveryInfo("deliveryInfo"),
  action("action");

  final String name;

  const ColumnName(this.name);
}

class OrderListPage extends BasePage<OrderListBloc> {
  OrderListPage({super.key});

  @override
  bool get enableStatePage => true;

  @override
  bool get wantKeepAlive => true;

  @override
  Widget build(BuildContext context) {
    bloc.onClickDetail = (orderSupplierId) =>
        context.push("${AppRoutes.orders}/$orderSupplierId");

    final size = MediaQuery.sizeOf(context);
    final itemHeight = size.height * 0.13;
    return ValueListenableBuilder(
      valueListenable: bloc.totalOrders,
      builder: (context, value, child) =>
          value <= 0 ? const EmptyList() : child!,
      child: SfDataGrid(
        headerGridLinesVisibility: GridLinesVisibility.both,
        gridLinesVisibility: GridLinesVisibility.both,
        headerRowHeight: 60,
        rowHeight: itemHeight,
        source: bloc.orderListDatSource,
        columns: _columns(size.width),
      ),
    );
  }

  List<GridColumn> _columns(double width) => [
        GridColumn(
          columnName: ColumnName.orderInfo.name,
          width: width * 0.15,
          label: Container(
            alignment: Alignment.centerLeft,
            color: Colors.grey.shade100,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text(
              R.string.orderInfo,
              overflow: TextOverflow.ellipsis,
              maxLines: 2,
              style: const TextStyle(
                color: Colors.black,
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ),
        GridColumn(
          columnName: ColumnName.paymentInfo.name,
          width: width * 0.2,
          label: Container(
            alignment: Alignment.centerLeft,
            color: Colors.grey.shade100,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text(
              R.string.paymentInfo,
              overflow: TextOverflow.ellipsis,
              maxLines: 2,
              style: const TextStyle(
                color: Colors.black,
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ),
        GridColumn(
          columnName: ColumnName.orderStatus.name,
          width: 200,
          label: Container(
            alignment: Alignment.centerLeft,
            color: Colors.grey.shade100,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text(
              R.string.orderStatus,
              overflow: TextOverflow.ellipsis,
              maxLines: 2,
              textAlign: TextAlign.center,
              style: const TextStyle(
                color: Colors.black,
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ),
        GridColumn(
          columnName: ColumnName.deliveryInfo.name,
          columnWidthMode: ColumnWidthMode.fill,
          label: Container(
            alignment: Alignment.centerLeft,
            color: Colors.grey.shade100,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text(
              R.string.deliveryInfo,
              overflow: TextOverflow.ellipsis,
              maxLines: 2,
              style: const TextStyle(
                color: Colors.black,
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ),
        GridColumn(
          columnName: ColumnName.action.name,
          width: 150,
          label: Container(
            alignment: Alignment.centerLeft,
            color: Colors.grey.shade100,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text(
              R.string.action,
              overflow: TextOverflow.ellipsis,
              maxLines: 2,
              style: const TextStyle(
                color: Colors.black,
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ),
      ];
}
