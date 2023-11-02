import 'dart:async';

import 'package:flutter/material.dart';
import 'package:sudoo/app/widgets/confirm_button.dart';
import 'package:sudoo/domain/model/discovery/price.dart';

import '../../resources/R.dart';
import '../../utils/logger.dart';

// ignore: must_be_immutable
class EditPriceDialog extends StatelessWidget {
  final Price price;
  final ValueSetter<Price>? onPositive;
  late final TextEditingController listedPriceController,
      priceController,
      discountController;
  final TextStyle style;
  Timer? debounce;

  EditPriceDialog({
    super.key,
    required this.price,
    required this.style,
    this.onPositive,
  }) {
    listedPriceController =
        TextEditingController(text: price.listedPrice.toString());
    priceController = TextEditingController(text: price.price.toString());
    discountController = TextEditingController(text: price.discount.toString());
  }

  @override
  Widget build(BuildContext context) {
    return Dialog(
      backgroundColor: Colors.white,
      child: Container(
        padding: const EdgeInsets.all(30.0),
        constraints: const BoxConstraints(
          maxWidth: 500,
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.start,
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
              height: 20.0,
            ),
            ConfirmButton(
              onPositive: () {
                final Price newPrice = Price(
                  price: double.parse(priceController.text),
                  listedPrice: double.parse(listedPriceController.text),
                  discount: int.parse(discountController.text),
                );
                onPositive?.call(newPrice);
                Navigator.of(context).pop();
              },
              onNegative: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        ),
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
    return SizedBox(
      width: 200,
      child: TextField(
        controller: listedPriceController,
        decoration: InputDecoration(
          suffix: Text(
            "đ",
            style: style,
          ),
        ),
        style: style,
        keyboardType: TextInputType.number,
        onChanged: onListedPriceChange,
      ),
    );
  }

  Widget _buildPrice(TextStyle style) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisSize: MainAxisSize.min,
      children: [
        Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            SizedBox(
              width: 150,
              child: TextField(
                controller: priceController,
                decoration: InputDecoration(
                  suffix: Text(
                    "đ",
                    style: style,
                  ),
                ),
                style: style,
                keyboardType: TextInputType.number,
                onChanged: onPriceChange,
              ),
            ),
            const SizedBox(
              width: 5,
            ),
            const Icon(
              Icons.remove,
              size: 12,
            ),
            const SizedBox(
              width: 5,
            ),
            SizedBox(
              width: 50,
              child: TextField(
                controller: discountController,
                decoration: InputDecoration(
                  suffix: Text(
                    "%",
                    style: style,
                  ),
                ),
                textAlign: TextAlign.center,
                style: style,
                keyboardType: TextInputType.number,
                onChanged: onDiscountPercentChange,
              ),
            ),
          ],
        ),
      ],
    );
  }

  Future<void> onListedPriceChange(String value) async {
    if (debounce?.isActive ?? false) debounce?.cancel();
    debounce = Timer(const Duration(milliseconds: 500), () {
      try {
        final listedPrice = double.parse(value);
        priceController.text =
            (listedPrice * (100 - int.parse(discountController.text)) / 100)
                .toString();
      } on Exception catch (e) {
        Logger.error(message: e.toString());
      }
    });
  }

  Future<void> onPriceChange(String value) async {
    if (debounce?.isActive ?? false) debounce?.cancel();
    debounce = Timer(const Duration(milliseconds: 500), () {
      try {
        final price = double.parse(value);
        final listedPrice = double.parse(listedPriceController.text);
        discountController.text =
            (price / listedPrice * 100).toInt().toString();
      } on Exception catch (e) {
        Logger.error(message: e.toString());
      }
    });
  }

  Future<void> onDiscountPercentChange(String value) async {
    if (debounce?.isActive ?? false) debounce?.cancel();
    debounce = Timer(const Duration(milliseconds: 500), () {
      try {
        final listedPrice = double.parse(listedPriceController.text);
        priceController.text =
            (listedPrice * ((100 - int.parse(value)) / 100)).toString();
      } on Exception catch (e) {
        Logger.error(message: e.toString());
      }
    });
  }
}
