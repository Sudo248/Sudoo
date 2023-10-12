import 'package:flutter/material.dart';
import 'package:sudoo/domain/model/discovery/product_info.dart';

import '../../../../../domain/model/discovery/upsert_product.dart';

class ProductSaleStatusCell extends StatefulWidget {
  final ProductInfo product;
  final Future<bool> Function(UpsertProduct) patchProduct;

  const ProductSaleStatusCell({
    super.key,
    required this.product,
    required this.patchProduct,
  });

  @override
  State<ProductSaleStatusCell> createState() => _ProductSaleStatusCellState();
}

class _ProductSaleStatusCellState extends State<ProductSaleStatusCell> {
  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
        Switch(
          value: widget.product.saleable,
          onChanged: (value) async {
            if (value != widget.product.saleable) {
              await widget
                  .patchProduct(
                UpsertProduct(
                  productId: widget.product.productId,
                  saleable: value,
                ),
              )
                  .then((isSuccess) {
                if (isSuccess) {
                  setState(() {
                    widget.product.saleable = value;
                  });
                }
              });
            }
          },
          thumbColor: MaterialStateProperty.all(Colors.white),
          activeTrackColor: Colors.red,
          inactiveTrackColor: Colors.grey,
        )
      ],
    );
  }
}
