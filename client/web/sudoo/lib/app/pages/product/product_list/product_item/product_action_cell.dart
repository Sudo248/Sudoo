import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/model/product_info_action_callback.dart';
import 'package:sudoo/app/routes/app_routes.dart';
import 'package:sudoo/app/dialog/confirm_dialog.dart';
import 'package:sudoo/domain/model/discovery/product_info.dart';

import '../../../../../resources/R.dart';

enum ProductAction {
  viewDetail("View detail"),
  manageImages("Manage images"),
  delete("Delete");

  final String value;

  const ProductAction(this.value);
}

class ProductActionCell extends StatelessWidget {
  final ProductInfo product;
  final ProductInfoActionCallback callback;
  final TextStyle? textStyle;

  const ProductActionCell({
    super.key,
    required this.product,
    required this.callback,
    this.textStyle,
  });

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
              value: ProductAction.viewDetail,
              child: Text(
                ProductAction.viewDetail.value,
              ),
            ),
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
          onChanged: (value) {
            switch (value) {
              case ProductAction.viewDetail:
                // callback.viewDetailProduct(product.productId);
                pushToProductDetail(context);
                break;
              case ProductAction.manageImages:
                callback.manageImages();
                break;
              case ProductAction.delete:
                showDialog(
                  context: context,
                  builder: (context) => ConfirmDialog(
                    title: "Delete this product?",
                    onPositive: () {
                      callback.deleteProduct(product.productId);
                    },
                  ),
                );
              default:
            }
          },
        ),
      ],
    );
  }

  Future<void> pushToProductDetail(BuildContext context) async {
    final needUpdate = await context.push("${AppRoutes.products}/${product.productId}");
    if (needUpdate != null && needUpdate is bool && needUpdate) {
      callback.updateItemProduct(product.productId);
    }
  }
}
