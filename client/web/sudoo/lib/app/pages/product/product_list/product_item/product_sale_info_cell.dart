import 'package:flutter/material.dart';
import 'package:sudoo/app/widgets/date_time_selector.dart';
import 'package:sudoo/domain/common/Constants.dart';
import 'package:sudoo/extensions/double_ext.dart';

import '../../../../../domain/model/discovery/category.dart';
import '../../../../../domain/model/discovery/product.dart';
import '../../../../../resources/R.dart';

class ProductSaleInfoCell extends StatelessWidget {
  final Product product;
  final TextStyle? textStyle;

  const ProductSaleInfoCell({
    super.key,
    required this.product,
    this.textStyle,
  });

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
                _buildCategories(product.categories),
              ],
            ),
            TableRow(
              children: [
                _buildTitle(R.string.listedPrice, style),
                _buildListedPrice(style),
              ],
            ),
            TableRow(
              children: [
                _buildTitle(R.string.price, style),
                _buildPrice(style),
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

  Widget _buildCategories(List<Category> categories) {
    final List<Widget> categoryWidgets = categories
        .map<Widget>(
          (category) => _buildCategory(category),
        )
        .toList();
    if (categories.length < Constants.maxCategoryOfEachProduct) {
      categoryWidgets.add(
        GestureDetector(
          onTap: () {},
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
      child: Wrap(
        spacing: 8,
        runSpacing: 10,
        children: categoryWidgets,
      ),
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
            onTap: () {},
          ),
          const SizedBox(
            width: 2,
          ),
        ],
      ),
    );
  }

  Widget _buildListedPrice(TextStyle style) {
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
          onTap: () {},
          child: const Icon(
            size: 20,
            Icons.drive_file_rename_outline_outlined,
            color: Colors.red,
          ),
        )
      ],
    );
  }

  Widget _buildPrice(TextStyle style) {
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
              "${product.discount}%",
              style: style,
            ),
            const SizedBox(
              width: 8,
            ),
            GestureDetector(
              onTap: () {},
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
}
