import 'package:flutter/material.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_data_source.dart';
import 'package:sudoo/app/widgets/empty_list.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';

import '../../../../resources/R.dart';

enum ColumnName {
  product("product"),
  saleInfo("sale-info"),
  saleStatus("sale-status"),
  action("action");

  final String name;

  const ColumnName(this.name);
}

class ProductListDataTable extends StatelessWidget {
  final ProductListDataSource productDataSource;

  const ProductListDataTable({super.key, required this.productDataSource});

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.sizeOf(context);
    final itemHeight = size.height * 0.23;
    return SfDataGrid(
      headerGridLinesVisibility: GridLinesVisibility.both,
      gridLinesVisibility: GridLinesVisibility.both,
      headerRowHeight: 60,
      rowHeight: itemHeight,
      columns: _columns(size.width),
      loadMoreViewBuilder: (context, loadMoreRows) {
        Future<String> loadRows() async {
          // Call the loadMoreRows function to call the
          // DataGridSource.handleLoadMoreRows method. So, additional
          // rows can be added from handleLoadMoreRows method.
          if (!productDataSource.isLastPage) {
            await loadMoreRows();
          }
          return Future<String>.value('Completed');
        }

        return FutureBuilder<String>(
          initialData: 'loading',
          future: loadRows(),
          builder: (context, snapShot) {
            if (snapShot.data == 'loading') {
              return Container(
                height: 60.0,
                width: double.infinity,
                decoration: const BoxDecoration(
                  color: Colors.white,
                  border: BorderDirectional(
                    top: BorderSide(
                      width: 1.0,
                      color: Color.fromRGBO(0, 0, 0, 0.26),
                    ),
                  ),
                ),
                alignment: Alignment.center,
                child: const CircularProgressIndicator(
                  valueColor: AlwaysStoppedAnimation(Color(0xff3AC5C9)),
                ),
              );
            } else {
              return const SizedBox.shrink();
            }
          },
        );
      },
      source: productDataSource,
    );
  }

  List<GridColumn> _columns(double width) => [
        GridColumn(
          columnName: ColumnName.product.name,
          width: width * 0.18,
          label: Container(
            alignment: Alignment.centerLeft,
            color: Colors.grey.shade300,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text(
              R.string.product,
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
          columnName: ColumnName.saleInfo.name,
          columnWidthMode: ColumnWidthMode.fill,
          label: Container(
            alignment: Alignment.centerLeft,
            color: Colors.grey.shade300,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text(
              R.string.saleInfo,
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
          columnName: ColumnName.saleStatus.name,
          width: 120,
          label: Container(
            alignment: Alignment.center,
            color: Colors.grey.shade300,
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text(
              R.string.saleStatus,
              overflow: TextOverflow.ellipsis,
              maxLines: 2,
              textAlign: TextAlign.center,
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
            alignment: Alignment.center,
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
}
