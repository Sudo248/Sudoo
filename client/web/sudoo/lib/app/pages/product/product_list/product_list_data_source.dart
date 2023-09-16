import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'product_item/product_action_cell.dart';
import 'product_item/product_info_cell.dart';
import 'product_item/product_sale_info_cell.dart';
import 'product_item/product_sale_status_cell.dart';
import 'product_list_data_table.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';

import '../../../../domain/model/discovery/product.dart';

class ProductListDataSource extends DataGridSource {
  final AsyncCallback loadMore;
  final List<Product> products = List.empty(growable: true);
  List<DataGridRow> _dataRows = List.empty();

  ProductListDataSource({required this.loadMore});

  @override
  DataGridRowAdapter? buildRow(DataGridRow row) {
    return DataGridRowAdapter(
      cells: row.getCells().map<Widget>(
            (cell) {
          switch (cell.columnName) {
            case "product":
              return Padding(
                padding: const EdgeInsets.all(10.0),
                child: ProductInfoCell(product: cell.value),
              );
            case "sale-info":
              return Padding(
                padding: const EdgeInsets.all(10.0),
                child: ProductSaleInfoCell(product: cell.value),
              );
            case "sale-status":
              return Padding(
                padding: const EdgeInsets.all(10.0),
                child: ProductSaleStatusCell(
                  isSellable: cell.value,
                  onChanged: (isSellable) {},
                ),
              );
            case "action":
              return Padding(
                padding: const EdgeInsets.all(10.0),
                child: ProductActionCell(
                  product: cell.value,
                ),
              );
            default:
              return const SizedBox.shrink();
          }
        },
      ).toList(),
    );
  }

  @override
  List<DataGridRow> get rows => _dataRows;

  @override
  Future<void> handleLoadMoreRows() => loadMore();

  DataGridRow _mapFromProduct(Product product) => DataGridRow(
    cells: [
      DataGridCell(
        columnName: ColumnName.product.name,
        value: product,
      ),
      DataGridCell(
        columnName: ColumnName.saleInfo.name,
        value: product,
      ),
      DataGridCell(
        columnName: ColumnName.saleStatus.name,
        value: product.saleable,
      ),
      DataGridCell(
        columnName: ColumnName.action.name,
        value: product,
      )
    ],
  );

  void buildDataRows() {
    _dataRows = products
        .map<DataGridRow>((product) => _mapFromProduct(product))
        .toList();
  }

  void addProducts(List<Product> products) {
    products.addAll(products);
    notifyDataSetChange();
  }

  void setProducts(List<Product> products) {
    this.products.clear();
    this.products.addAll(products);
    notifyDataSetChange();
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