import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/order/order_list/order_list_bloc.dart';
import 'package:sudoo/app/routes/app_routes.dart';
import 'package:sudoo/app/widgets/confirm_button.dart';
import 'package:sudoo/app/widgets/empty_list.dart';
import 'package:sudoo/domain/model/order/order_status.dart';
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

  final MenuStyle menuStyle = MenuStyle(
    padding: MaterialStateProperty.all<EdgeInsetsGeometry?>(
      const EdgeInsets.only(top: 15, bottom: 20, right: 10, left: 10),
    ),
    backgroundColor:
        MaterialStateProperty.all<Color?>(R.color.backgroundMenuColor),
    shape: MaterialStateProperty.all<OutlinedBorder>(
      RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(5.0),
      ),
    ),
  );

  final MenuController _menuController = MenuController();

  @override
  Widget build(BuildContext context) {
    bloc.onClickDetail = (orderSupplierId) =>
        context.push("${AppRoutes.orders}/$orderSupplierId");

    final size = MediaQuery.sizeOf(context);
    final itemHeight = size.height * 0.13;
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
          child: Text(
            R.string.listOrder,
            style: R.style.h4_1.copyWith(
              fontWeight: FontWeight.bold,
              color: Colors.black,
            ),
          ),
        ),
        const SizedBox(
          height: 10,
        ),
        Expanded(
          child: ValueListenableBuilder(
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
          ),
        ),
      ],
    );
  }

  List<GridColumn> _columns(double width) => [
        GridColumn(
          columnName: ColumnName.orderInfo.name,
          width: width * 0.15,
          label: Container(
            alignment: Alignment.centerLeft,
            color: Colors.grey.shade300,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text(
              R.string.orderInfo,
              overflow: TextOverflow.ellipsis,
              maxLines: 2,
              style: const TextStyle(
                color: Colors.black,
                fontSize: 16,
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
            color: Colors.grey.shade300,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text(
              R.string.paymentInfo,
              overflow: TextOverflow.ellipsis,
              maxLines: 2,
              style: const TextStyle(
                color: Colors.black,
                fontSize: 16,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ),
        GridColumn(
          columnName: ColumnName.orderStatus.name,
          filterPopupMenuOptions: const FilterPopupMenuOptions(
            filterMode: FilterMode.checkboxFilter,
            canShowSortingOptions: false,
          ),
          width: 200,
          label: Container(
            alignment: Alignment.centerLeft,
            color: Colors.grey.shade300,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              mainAxisSize: MainAxisSize.min,
              children: [
                MenuAnchor(
                  controller: _menuController,
                  style: menuStyle,
                  builder: (context, controller, child) => GestureDetector(
                    onTap: () {
                      if (controller.isOpen) {
                        controller.close();
                      } else {
                        controller.open();
                      }
                    },
                    child: child,
                  ),
                  alignmentOffset: const Offset(-10, 5),
                  menuChildren: <Widget>[
                    MenuItemButton(
                      style: ButtonStyle(
                          padding:
                          MaterialStateProperty.all<EdgeInsetsGeometry?>(
                            const EdgeInsets.only(right: 100, left: 15),
                          )),
                      leadingIcon: const Icon(Icons.filter_alt_off_sharp),
                      child: const Text(
                        "Clear filter",
                        style: TextStyle(fontSize: 16),
                      ),
                      onPressed: () {
                        bloc.clearFilterStatus();
                        _menuController.close();
                      },
                    ),
                    ..._statusCheckbox(),
                    const SizedBox(height: 12,),
                    ConfirmButton(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      onPositive: () {
                        bloc.filterStatus();
                        _menuController.close();
                      },
                      onNegative: () {
                        _menuController.close();
                      },
                    ),
                  ],
                  child: const Icon(Icons.filter_alt_sharp),
                ),
                Text(
                  R.string.orderStatus,
                  overflow: TextOverflow.ellipsis,
                  maxLines: 2,
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    color: Colors.black,
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
          ),
        ),
        GridColumn(
          columnName: ColumnName.deliveryInfo.name,
          columnWidthMode: ColumnWidthMode.fill,
          label: Container(
            alignment: Alignment.centerLeft,
            color: Colors.grey.shade300,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text(
              R.string.deliveryInfo,
              overflow: TextOverflow.ellipsis,
              maxLines: 2,
              style: const TextStyle(
                color: Colors.black,
                fontSize: 16,
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
            color: Colors.grey.shade300,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text(
              R.string.action,
              overflow: TextOverflow.ellipsis,
              maxLines: 2,
              style: const TextStyle(
                color: Colors.black,
                fontSize: 16,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ),
      ];

  List<Widget> _statusCheckbox() => OrderStatus.values
      .map(
        (e) => StatefulBuilder(
          builder: (context, setState) => Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              const SizedBox(
                width: 10,
              ),
              Checkbox(
                activeColor: R.color.primaryColor,
                value: bloc.currentFilterOrderStatus.contains(e),
                onChanged: (bool? value) {
                  setState(() {
                    if (value == true) {
                      bloc.currentFilterOrderStatus.add(e);
                    } else {
                      bloc.currentFilterOrderStatus.remove(e);
                    }
                  });
                },
              ),
              const SizedBox(
                width: 10,
              ),
              Text(e.value,
                  style: const TextStyle(
                      fontSize: 16, fontWeight: FontWeight.bold))
            ],
          ),
        ),
      )
      .toList();
}
