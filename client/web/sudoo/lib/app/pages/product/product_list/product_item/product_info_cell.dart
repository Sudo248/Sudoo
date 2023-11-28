import 'package:flutter/material.dart';

import '../../../../../domain/model/discovery/product_info.dart';
import '../../../../../resources/R.dart';
import '../../../../widgets/text_section.dart';

class ProductInfoCell extends StatelessWidget {
  final ProductInfo product;
  final TextStyle? textStyle;

  const ProductInfoCell({
    super.key,
    required this.product,
    this.textStyle,
  });

  @override
  Widget build(BuildContext context) {
    final TextStyle style = textStyle ?? R.style.h5;
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          product.name,
          style: style.copyWith(
            color: Colors.black,
            fontWeight: FontWeight.bold,
          ),
          maxLines: 2,
          overflow: TextOverflow.ellipsis,
        ),
        const SizedBox(
          height: 15,
        ),
        TextSection(
          label: R.string.sellerSKU,
          labelTextStyle: style.copyWith(
            color: Colors.grey.shade600,
          ),
          text: product.sku,
          style: style.copyWith(
            color: Colors.red,
          ),
        ),
        const SizedBox(
          height: 15,
        ),
        TextSection(
          label: R.string.amount,
          labelTextStyle: style.copyWith(
            color: Colors.grey.shade600,
          ),
          text: "${product.amount}",
          style: style.copyWith(
            color: Colors.blue,
          ),
        )
      ],
    );
  }
}