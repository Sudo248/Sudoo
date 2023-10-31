import 'dart:async';

import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/product/product/product_bloc.dart';
import 'package:sudoo/app/widgets/blocks/category_block.dart';
import 'package:sudoo/app/widgets/blocks/extras_block.dart';
import 'package:sudoo/app/widgets/blocks/image_block.dart';
import 'package:sudoo/app/widgets/blocks/size_weight_block.dart';
import 'package:sudoo/app/widgets/loading_view.dart';

import '../../../../resources/R.dart';
import '../../../../utils/logger.dart';
import '../../../widgets/blocks/range_time_block.dart';

class ProductPage extends BasePage<ProductBloc> {
  final String? productId;
  late final TextStyle style;

  ProductPage({super.key, required this.productId, TextStyle? style}) {
    this.style = style ?? R.style.h4_1.copyWith(color: Colors.black);
    bloc.fetchProduct(productId);
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        bloc.navigator.back();
        return Future.value(true);
      },
      child: Stack(
        children: [
          buildContent(context),
          LoadingView(controller: bloc.loadingController),
        ],
      ),
    );
  }

  Widget buildContent(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            productId != null ? "Product detail" : "Create Product",
            style: R.style.h4.copyWith(
              color: Colors.black,
              fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(
            height: 10,
          ),
          Padding(
            padding: const EdgeInsets.all(10.0),
            child: Table(
              columnWidths: const {
                0: FlexColumnWidth(1),
                1: FlexColumnWidth(5),
              },
              children: [
                TableRow(children: [
                  _buildTitle(R.string.images, style),
                  Padding(
                    padding: const EdgeInsets.symmetric(vertical: 5.0),
                    child: ImageBlock(
                      productId: productId,
                      images: bloc.images,
                      callback: bloc,
                    ),
                  ),
                ]),
                TableRow(children: [
                  _buildTitle(R.string.categories, style),
                  Padding(
                    padding: const EdgeInsets.symmetric(vertical: 5.0),
                    child: CategoryBlock(
                      productId: productId,
                      categories: bloc.categories,
                      callback: bloc,
                    ),
                  ),
                ]),
                TableRow(children: [
                  _buildTitle(R.string.name, style),
                  Padding(
                    padding: const EdgeInsets.symmetric(vertical: 5.0),
                    child: _buildTextFieldBlock(
                      bloc.nameController,
                    ),
                  ),
                ]),
                TableRow(children: [
                  _buildTitle(R.string.sellerSKU, style),
                  Padding(
                    padding: const EdgeInsets.symmetric(vertical: 5.0),
                    child: _buildTextFieldBlock(bloc.skuController,
                        hintText: R.string.optional,
                        maxLines: 1,
                        readOnly: productId != null),
                  ),
                ]),
                TableRow(children: [
                  _buildTitle(R.string.description, style),
                  Padding(
                    padding: const EdgeInsets.symmetric(vertical: 5.0),
                    child: _buildTextFieldBlock(
                      bloc.descriptionController,
                      maxLines: 10,
                      maxLength: 5000,
                    ),
                  ),
                ]),
                TableRow(
                  children: [
                    _buildTitle(R.string.listedPrice, style),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 10.0),
                      child: _buildListedPriceBlock(),
                    ),
                  ],
                ),
                TableRow(
                  children: [
                    _buildTitle(R.string.price, style),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 10.0),
                      child: _buildPriceBlock(),
                    ),
                  ],
                ),
                TableRow(children: [
                  _buildTitle(R.string.amount, style),
                  Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Padding(
                        padding: const EdgeInsets.symmetric(vertical: 10.0),
                        child: _buildTextFieldBlock(
                          bloc.amountController,
                          maxLines: 1,
                          maxLength: 10,
                          keyboardType: TextInputType.number,
                          decoration: const InputDecoration(
                            constraints: BoxConstraints(maxWidth: 150),
                            contentPadding: EdgeInsets.symmetric(
                              horizontal: 10,
                              vertical: 5,
                            ),
                            border: OutlineInputBorder(),
                            counterText: "",
                          ),
                        ),
                      ),
                    ],
                  ),
                ]),
                TableRow(children: [
                  _buildTitle(R.string.sizeWeight, style),
                  Padding(
                    padding: const EdgeInsets.symmetric(vertical: 10.0),
                    child: SizeWeightBlock(
                      style: style,
                      weightController: bloc.weightController,
                      heightController: bloc.heightController,
                      widthController: bloc.widthController,
                      lengthController: bloc.lengthController,
                    ),
                  )
                ]),
                TableRow(children: [
                  _buildTitle(R.string.saleStatus, style),
                  Padding(
                    padding: const EdgeInsets.symmetric(vertical: 10.0),
                    child: ValueListenableBuilder(
                      valueListenable: bloc.saleable,
                      builder: (context, value, child) => Row(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          Switch(
                            value: value,
                            onChanged: (value) =>
                                bloc.onChangeSaleStatus(value),
                            thumbColor: MaterialStateProperty.all(Colors.white),
                            activeTrackColor: Colors.red,
                            inactiveTrackColor: Colors.grey,
                          ),
                        ],
                      ),
                    ),
                  ),
                ]),
                TableRow(children: [
                  _buildTitle(R.string.extras, style),
                  Padding(
                    padding: const EdgeInsets.symmetric(vertical: 10.0),
                    child: ExtrasBlock(
                      enable3DViewer: bloc.enable3DViewer,
                      enableARViewer: bloc.enableArViewer,
                      sourceViewer: bloc.sourceViewer,
                      style: style,
                      onChangeEnableViewer: bloc.onChangeEnableViewer,
                    ),
                  ),
                ])
              ],
            ),
          ),
          const SizedBox(
            height: 30,
          ),
          FilledButton(
            style: R.buttonStyle.filledButtonStyle(),
            onPressed: bloc.onSave,
            child: Text(
              R.string.save,
              style: R.style.h5,
            ),
          ),
          const SizedBox(
            height: 30,
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

  Widget _buildTextFieldBlock(TextEditingController controller,
      {String? hintText,
      int? maxLines,
      int? maxLength,
      TextInputType? keyboardType,
      InputDecoration? decoration,
      bool readOnly = false}) {
    return TextField(
      controller: controller,
      style: style,
      maxLines: maxLines,
      maxLength: maxLength,
      keyboardType: keyboardType,
      readOnly: readOnly,
      decoration: decoration ??
          InputDecoration(
            hintText: hintText,
            border: const OutlineInputBorder(),
          ),
    );
  }

  Widget _buildListedPriceBlock() {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        TextField(
          controller: bloc.listedPriceController,
          decoration: InputDecoration(
            constraints: const BoxConstraints(
              maxWidth: 200,
            ),
            contentPadding:
                const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
            suffixText: "đ",
            suffixStyle: style.copyWith(fontWeight: FontWeight.bold),
            border: const OutlineInputBorder(),
            counterText: "",
          ),
          maxLength: 9,
          style: style,
          keyboardType: TextInputType.number,
          onChanged: onListedPriceChange,
        ),
      ],
    );
  }

  Widget _buildPriceBlock() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisSize: MainAxisSize.min,
      children: [
        Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextField(
              controller: bloc.priceController,
              decoration: InputDecoration(
                constraints: const BoxConstraints(
                  maxWidth: 200,
                ),
                contentPadding:
                    const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
                suffixText: "đ",
                suffixStyle: style.copyWith(fontWeight: FontWeight.bold),
                border: const OutlineInputBorder(),
                counterText: "",
              ),
              maxLength: 10,
              style: style,
              keyboardType: TextInputType.number,
              onChanged: onPriceChange,
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
            TextField(
              controller: bloc.discountController,
              decoration: InputDecoration(
                constraints: const BoxConstraints(
                  maxWidth: 80,
                ),
                contentPadding:
                    const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
                suffixText: "%",
                suffixStyle: style.copyWith(fontWeight: FontWeight.bold),
                border: const OutlineInputBorder(),
                counterText: "",
              ),
              maxLength: 2,
              textAlign: TextAlign.center,
              style: style,
              keyboardType: TextInputType.number,
              onChanged: onDiscountPercentChange,
            ),
          ],
        ),
        const SizedBox(
          height: 10,
        ),
        RangeTimeBlock(
          startDate: bloc.startDateDiscount,
          endDate: bloc.endDateDiscount,
          onSelectedStartTime: (selectedDate) async {
            bloc.startDateDiscount.value = selectedDate;
          },
          onSelectedEndTime: (selectedDate) async {
            bloc.endDateDiscount.value = selectedDate;
          },
        ),
      ],
    );
  }

  Future<void> onListedPriceChange(String value) async {
    if (bloc.debounce?.isActive ?? false) bloc.debounce?.cancel();
    bloc.debounce = Timer(const Duration(milliseconds: 500), () {
      try {
        final listedPrice = double.parse(value);
        bloc.priceController.text = (listedPrice *
                (100 - int.parse(bloc.discountController.text)) /
                100)
            .toString();
      } on Exception catch (e) {
        Logger.error(message: e.toString());
      }
    });
  }

  Future<void> onPriceChange(String value) async {
    if (bloc.debounce?.isActive ?? false) bloc.debounce?.cancel();
    bloc.debounce = Timer(const Duration(milliseconds: 500), () {
      try {
        final price = double.parse(value);
        final listedPrice = double.parse(bloc.listedPriceController.text);
        bloc.discountController.text =
            (100 - (price / listedPrice * 100)).toInt().toString();
      } on Exception catch (e) {
        Logger.error(message: e.toString());
      }
    });
  }

  Future<void> onDiscountPercentChange(String value) async {
    if (bloc.debounce?.isActive ?? false) bloc.debounce?.cancel();
    bloc.debounce = Timer(const Duration(milliseconds: 500), () {
      try {
        final listedPrice = double.parse(bloc.listedPriceController.text);
        bloc.priceController.text =
            (listedPrice * ((100 - int.parse(value)) / 100)).toString();
      } on Exception catch (e) {
        Logger.error(message: e.toString());
      }
    });
  }
}
