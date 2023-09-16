import 'package:flutter/material.dart';
import 'package:sudoo/domain/model/discovery/product.dart';

import '../../../../../resources/R.dart';

enum ProductAction {
  manageImages("Manage images"),
  delete("Delete");

  final String value;

  const ProductAction(this.value);
}

class ProductActionCell extends StatelessWidget {
  final Product product;
  final TextStyle? textStyle;
  const ProductActionCell({super.key, required this.product, this.textStyle});

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
        DropdownButton(
          isExpanded: true,
          icon: const Icon(Icons.arrow_drop_down),
          style: textStyle ??
              R.style.h5.copyWith(
                color: Colors.black,
              ),
          hint: Text(R.string.action),
          borderRadius: BorderRadius.circular(10),
          dropdownColor: Colors.grey.shade100,
          underline: const SizedBox.shrink(),
          padding: const EdgeInsets.only(left: 10),
          items: [
            DropdownMenuItem(
              value: ProductAction.manageImages,
              child: Text(
                ProductAction.manageImages.value,
              ),
            ),
            DropdownMenuItem(
              value: ProductAction.delete,
              child: Text(
                ProductAction.delete.value,
              ),
            ),
          ],
          onChanged: (value) {},
        ),
      ],
    );
  }
}
