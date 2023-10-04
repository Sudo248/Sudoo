import 'package:flutter/material.dart';
import 'package:sudoo/app/dialog/choose_category_dialog.dart';
import 'package:sudoo/app/dialog/edit_price_dialog.dart';
import 'package:sudoo/app/model/category_callback.dart';
import 'package:sudoo/app/widgets/date_time_selector.dart';
import 'package:sudoo/domain/common/Constants.dart';
import 'package:sudoo/domain/model/discovery/Price.dart';
import 'package:sudoo/domain/model/discovery/category_product.dart';
import 'package:sudoo/domain/model/discovery/product_info.dart';
import 'package:sudoo/extensions/double_ext.dart';
import 'package:sudoo/extensions/int_extensions.dart';
import 'package:sudoo/extensions/list_ext.dart';

import '../../../../../domain/model/discovery/category.dart';
import '../../../../../domain/model/discovery/upsert_product.dart';
import '../../../../../resources/R.dart';

class ProductSaleInfoCell extends StatelessWidget {
  final ProductInfo product;
  final TextStyle? textStyle;
  final CategoryCallback categoryCallback;
  final Future<UpsertProduct> Function(UpsertProduct) patchProduct;
  final ValueNotifier<List<Category>?> categories = ValueNotifier(null);

  ProductSaleInfoCell({
    super.key,
    required this.product,
    required this.categoryCallback,
    required this.patchProduct,
    this.textStyle,
  }) {
    getCategories();
  }

  Future<void> getCategories() async {
    categories.value = null;
    categories.value =
        await categoryCallback.getCategoriesOfProduct(product.productId);
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
                _buildStateCategories(context, product.productId),
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

  Widget _buildStateCategories(BuildContext context, String productId) {
    return ValueListenableBuilder(
        valueListenable: categories,
        builder: (context, categories, child) {
          return Container(
            width: double.infinity,
            constraints: const BoxConstraints(
              maxHeight: 80,
            ),
            padding: const EdgeInsets.all(5),
            decoration: BoxDecoration(
              border: Border.all(color: Colors.grey),
              borderRadius: BorderRadius.circular(5),
            ),
            child: categories == null
                ? const CircularProgressIndicator(
                    strokeWidth: 1,
                  )
                : _buildCategories(context, categories),
          );
        });
  }

  Widget _buildCategories(BuildContext context, List<Category> categories) {
    final List<Widget> categoryWidgets = categories
        .map<Widget>(
          (category) => _buildCategory(category),
        )
        .toList();
    if (categories.length < Constants.maxCategoryOfEachProduct) {
      categoryWidgets.add(
        GestureDetector(
          onTap: () {
            showDialogAddCategory(context);
          },
          child: DecoratedBox(
            decoration: BoxDecoration(
              border: Border.all(
                color: Colors.grey,
              ),
              borderRadius: BorderRadius.circular(5),
            ),
            child: const Icon(
              Icons.add,
              color: Colors.grey,
            ),
          ),
        ),
      );
    }
    return Wrap(
      spacing: 8,
      runSpacing: 10,
      children: categoryWidgets,
    );
  }

  Widget _buildCategory(Category category) {
    return DecoratedBox(
      decoration: BoxDecoration(
        border: Border.all(color: Colors.grey),
        borderRadius: BorderRadius.circular(5),
      ),
      child: Row(
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          const SizedBox(
            width: 3,
          ),
          Text(
            category.name,
            maxLines: 1,
            overflow: TextOverflow.ellipsis,
            style: R.style.h5.copyWith(
              color: Colors.black,
            ),
          ),
          const SizedBox(
            width: 2,
          ),
          const Icon(
            Icons.list_alt_rounded,
            color: Colors.blue,
          ),
          GestureDetector(
            child: const Icon(
              size: 16,
              Icons.clear_outlined,
              color: Colors.grey,
            ),
            onTap: () async {
              final categoryProduct =
                  await categoryCallback.deleteCategoryToProduct(
                CategoryProduct(
                  productId: product.productId,
                  categoryId: category.categoryId,
                ),
              );
              if (categoryProduct.categoryProductId != null) {
                deleteCategory(category);
              }
            },
          ),
          const SizedBox(
            width: 2,
          ),
        ],
      ),
    );
  }

  Widget _buildListedPrice(BuildContext context, TextStyle style) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        Text(
          product.listedPrice.formatCurrency(),
          style: style,
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
            Text(
              product.price.formatCurrency(),
              style: style,
            ),
            const Icon(
              Icons.remove,
              size: 12,
            ),
            Text(
              product.discount.formatPercent(),
              style: style,
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
        _buildRangeTimeDiscount(style),
      ],
    );
  }

  Widget _buildRangeTimeDiscount(TextStyle style) {
    return Row(
      children: [
        Text(
          R.string.from,
          style: style.copyWith(fontWeight: FontWeight.bold),
        ),
        const SizedBox(
          width: 10,
        ),
        DateTimeSelector(
          value: product.startDateDiscount,
          isEditable: false,
          onSelectedDate: (selectedDate) {},
        ),
        const SizedBox(
          width: 5,
        ),
        const Icon(
          Icons.remove,
          size: 10,
          color: Colors.black,
        ),
        const SizedBox(
          width: 3,
        ),
        Text(
          R.string.to,
          style: style.copyWith(fontWeight: FontWeight.bold),
        ),
        const SizedBox(
          width: 10,
        ),
        DateTimeSelector(
          value: product.endDateDiscount,
          onSelectedDate: (selectedDate) {},
        ),
      ],
    );
  }

  void showDialogAddCategory(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => ChooseCategoryDialog(
        categories:
            categoryCallback.getCategoriesWithout(categories.value.orEmpty),
        onPositive: (value) async {
          final result = await categoryCallback.upsertCategoryToProduct(
            CategoryProduct(
                productId: product.productId, categoryId: value.categoryId),
          );
          if (result.categoryProductId != null) {
            addCategory(value);
          }
        },
      ),
    );
  }

  void addCategory(Category category) {
    final currentCategories = categories.value.orEmpty.toList(growable: true);
    currentCategories.add(category);
    categories.value = currentCategories;
  }

  void deleteCategory(Category category) {
    final currentCategories = categories.value.orEmpty.toList(growable: true);
    currentCategories.remove(category);
    categories.value = currentCategories;
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
            listedPrice: value.listedPrice,
            price: value.price,
            discount: value.discount,
          );
          patchProduct(upsertProduct);
        },
      ),
    );
  }
}
