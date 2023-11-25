import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/app/model/category_callback.dart';
import 'package:sudoo/app/model/product_info_action_callback.dart';
import 'package:sudoo/domain/model/discovery/upsert_product.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';

import '../../../../domain/model/discovery/product_info.dart';
import 'product_item/product_action_cell.dart';
import 'product_item/product_info_cell.dart';
import 'product_item/product_sale_info_cell.dart';
import 'product_item/product_sale_status_cell.dart';
import 'product_list_data_table.dart';

class ProductListDataSource extends DataGridSource {
  final AsyncCallback loadMore;
  final CategoryCallback categoryCallback;
  final ProductInfoActionCallback productActionCallback;
  final Future<bool> Function(UpsertProduct) patchProduct;
  final List<ProductInfo> products = List.empty(growable: true);
  List<DataGridRow> _dataRows = List.empty();

  ProductListDataSource({
    required this.loadMore,
    required this.categoryCallback,
    required this.productActionCallback,
    required this.patchProduct,
  });

  @override
  DataGridRowAdapter? buildRow(DataGridRow row) {
    return DataGridRowAdapter(
      cells: row.getCells().map<Widget>(
        (cell) {
          switch (cell.columnName) {
            case "product":
              return Padding(
                padding: const EdgeInsets.all(10.0),
                child: ProductInfoCell(
                  product: cell.value,
                ),
              );
            case "sale-info":
              return Padding(
                padding: const EdgeInsets.all(10.0),
                child: ProductSaleInfoCell(
                  product: cell.value,
                  categoryCallback: categoryCallback,
                  patchProduct: patchProduct,
                ),
              );
            case "sale-status":
              return Padding(
                padding: const EdgeInsets.all(10.0),
                child: ProductSaleStatusCell(
                  product: cell.value,
                  patchProduct: patchProduct,
                ),
              );
            case "action":
              return ProductActionCell(
                product: cell.value,
                callback: productActionCallback,
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

  DataGridRow _mapFromProduct(ProductInfo product) => DataGridRow(
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
            value: product,
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

  void addProducts(List<ProductInfo> products) {
    this.products.addAll(products);
    notifyDataSetChange();
  }

  void deleteProduct(String productId) {
    products.removeWhere((element) => element.productId == productId);
    notifyDataSetChange();
  }

  void setProducts(List<ProductInfo> products) {
    this.products.clear();
    this.products.addAll(products);
    notifyDataSetChange();
  }

  void clearProducts() {
    products.clear();
  }

  void updateProduct(ProductInfo productInfo) {
    final index =
        products.indexWhere((e) => e.productId == productInfo.productId);
    if (index == -1) return;
    products[index] = productInfo;
    buildDataRows();
    notifyItemChange(index, 0);
    notifyItemChange(index, 1);
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
