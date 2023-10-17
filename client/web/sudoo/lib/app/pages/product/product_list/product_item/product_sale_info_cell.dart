import 'package:flutter/material.dart';
import 'package:sudoo/app/dialog/edit_price_dialog.dart';
import 'package:sudoo/app/model/category_callback.dart';
import 'package:sudoo/app/widgets/blocks/category_block.dart';
import 'package:sudoo/app/widgets/blocks/range_time_block.dart';
import 'package:sudoo/domain/model/discovery/price.dart';
import 'package:sudoo/domain/model/discovery/product_info.dart';
import 'package:sudoo/extensions/double_ext.dart';
import 'package:sudoo/extensions/int_extensions.dart';

import '../../../../../domain/model/discovery/category.dart';
import '../../../../../domain/model/discovery/upsert_product.dart';
import '../../../../../resources/R.dart';

class ProductSaleInfoCell extends StatelessWidget {
  final ProductInfo product;
  final TextStyle? textStyle;
  final CategoryCallback categoryCallback;
  final Future<bool> Function(UpsertProduct) patchProduct;
  final ValueNotifier<List<Category>?> categories = ValueNotifier(null);
  final ValueNotifier<double> listedPrice = ValueNotifier(0.0);
  final ValueNotifier<double> price = ValueNotifier(0.0);
  final ValueNotifier<int> discount = ValueNotifier(0);
  final ValueNotifier<DateTime> endDateDiscount = ValueNotifier(DateTime.now());
  final ValueNotifier<DateTime> startDateDiscount =
      ValueNotifier(DateTime.now());

  ProductSaleInfoCell({
    super.key,
    required this.product,
    required this.categoryCallback,
    required this.patchProduct,
    this.textStyle,
  }) {
    getCategories();
    _setPriceData();
    _setEndDateDiscount();
    _setStartDateDiscount();
  }

  Future<void> getCategories() async {
    categories.value = null;
    categories.value =
        await categoryCallback.getCategoriesOfProduct(product.productId);
  }

  void _setPriceData() {
    listedPrice.value = product.listedPrice;
    price.value = product.price;
    discount.value = product.discount;
  }

  void _setEndDateDiscount() {
    endDateDiscount.value = product.endDateDiscount ?? DateTime.now();
  }

  void _setStartDateDiscount() {
    startDateDiscount.value = product.startDateDiscount ?? DateTime.now();
  }

  @override
  Widget build(BuildContext context) {
    final TextStyle style =
        textStyle ?? R.style.h4_1.copyWith(color: Colors.black);
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
        Table(
          columnWidths: const {
            0: FlexColumnWidth(1),
            1: FlexColumnWidth(5),
          },
          defaultVerticalAlignment: TableCellVerticalAlignment.middle,
          children: [
            TableRow(
              children: [
                _buildTitle(R.string.categories, style),
                CategoryBlock(
                  productId: product.productId,
                  categories: categories,
                  callback: categoryCallback,
                ),
              ],
            ),
            TableRow(
              children: [
                _buildTitle(R.string.listedPrice, style),
                _buildListedPrice(context, style),
              ],
            ),
            TableRow(
              children: [
                _buildTitle(R.string.price, style),
                _buildPrice(context, style),
              ],
            )
          ],
        ),
      ],
    );
  }

  Widget _buildTitle(String text, TextStyle style) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 15),
      child: Text(
        text,
        style: style.copyWith(
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }

  Widget _buildListedPrice(BuildContext context, TextStyle style) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        ValueListenableBuilder(
          valueListenable: listedPrice,
          builder: (context, value, child) => Text(
            value.formatCurrency(),
            style: style,
          ),
        ),
        const SizedBox(
          width: 8,
        ),
        GestureDetector(
          onTap: () {
            showEditPriceDialog(context, style);
          },
          child: const Icon(
            size: 20,
            Icons.drive_file_rename_outline_outlined,
            color: Colors.red,
          ),
        )
      ],
    );
  }

  Widget _buildPrice(BuildContext context, TextStyle style) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            ValueListenableBuilder(
              valueListenable: price,
              builder: (context, value, child) => Text(
                value.formatCurrency(),
                style: style,
              ),
            ),
            const Icon(
              Icons.remove,
              size: 12,
            ),
            ValueListenableBuilder(
              valueListenable: discount,
              builder: (context, value, child) => Text(
                value.formatPercent(),
                style: style,
              ),
            ),
            const SizedBox(
              width: 8,
            ),
            GestureDetector(
              onTap: () {
                showEditPriceDialog(context, style);
              },
              child: const Icon(
                size: 20,
                Icons.drive_file_rename_outline_outlined,
                color: Colors.red,
              ),
            )
          ],
        ),
        const SizedBox(
          height: 10,
        ),
        RangeTimeBlock(
          startDate: startDateDiscount,
          endDate: endDateDiscount,
          onSelectedStartTime: (selectedDate) async {
            await patchProduct(
              UpsertProduct(
                productId: product.productId,
                startDateDiscount: selectedDate,
              ),
            ).then((isSuccess) {
              if (isSuccess) {
                product.startDateDiscount = selectedDate;
                startDateDiscount.value = selectedDate;
              }
            });
          },
          onSelectedEndTime: (selectedDate) async {
            await patchProduct(
              UpsertProduct(
                productId: product.productId,
                endDateDiscount: selectedDate,
              ),
            ).then((isSuccess) {
              if (isSuccess) {
                product.endDateDiscount = selectedDate;
                endDateDiscount.value = selectedDate;
              }
            });
          },
        ),
      ],
    );
  }

  void showEditPriceDialog(BuildContext context, TextStyle style) {
    final price = Price(
      price: product.price,
      listedPrice: product.listedPrice,
      discount: product.discount,
    );
    showDialog(
      context: context,
      builder: (context) => EditPriceDialog(
        price: price,
        style: style,
        onPositive: (value) async {
          final UpsertProduct upsertProduct = UpsertProduct(
            productId: product.productId,
            listedPrice: value.listedPrice,
            price: value.price,
            discount: value.discount,
          );
          await patchProduct(upsertProduct).then((isSuccess) {
            if (isSuccess) {
              product.listedPrice = value.listedPrice;
              product.price = value.price;
              product.discount = value.discount;
              _setPriceData();
            }
          });
        },
      ),
    );
  }
}
