import 'package:flutter/material.dart';
import 'package:sudoo/app/widgets/confirm_button.dart';
import 'package:sudoo/domain/model/discovery/Price.dart';
import 'package:sudoo/extensions/double_ext.dart';
import 'package:sudoo/extensions/int_extensions.dart';
import 'package:sudoo/extensions/string_ext.dart';

import '../../resources/R.dart';

class EditPriceDialog extends StatelessWidget {
  final Price price;
  final ValueSetter<Price>? onPositive;
  late final TextEditingController listedPriceController,
      priceController,
      discountController;
  final TextStyle style;

  EditPriceDialog({
    super.key,
    required this.price,
    required this.style,
    this.onPositive,
  }) {
    listedPriceController =
        TextEditingController(text: price.listedPrice.formatCurrency());
    priceController = TextEditingController(text: price.price.formatCurrency());
    discountController =
        TextEditingController(text: price.discount.formatPercent());
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(10.0),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10.0),
      ),
      height: 500,
      width: 500,
      child: Column(
        children: [
          Table(
            columnWidths: const {
              0: FlexColumnWidth(1),
              1: FlexColumnWidth(2),
            },
            defaultVerticalAlignment: TableCellVerticalAlignment.middle,
            children: [
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
          const SizedBox(
            height: 8.0,
          ),
          ConfirmButton(
            onPositive: () {
              final Price newPrice = Price(
                price: priceController.text.parserCurrency(),
                listedPrice: priceController.text.parserCurrency(),
                discount: discountController.text.parserPercent(),
              );
              onPositive?.call(newPrice);
            },
            onNegative: () {
              Navigator.of(context).pop();
            },
          ),
        ],
      ),
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

  Widget _buildListedPrice(TextStyle style) {
    return TextField(
      controller: listedPriceController,
      style: style,
      onChanged: (value) {},
    );
  }

  Widget _buildPrice(TextStyle style) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextField(
              controller: priceController,
              style: style,
            ),
            const Icon(
              Icons.remove,
              size: 12,
            ),
            TextField(
              controller: discountController,
              style: style,
            ),
          ],
        ),
      ],
    );
  }
}
