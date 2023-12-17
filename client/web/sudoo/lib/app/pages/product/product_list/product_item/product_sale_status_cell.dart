import 'package:flutter/material.dart';

class ProductSaleStatusCell extends StatelessWidget {
  final bool isSellable;
  final ValueSetter<bool> onChanged;

  const ProductSaleStatusCell({
    super.key,
    required this.isSellable,
    required this.onChanged,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
        Switch(
          value: isSellable,
          onChanged: onChanged,
          thumbColor: MaterialStateProperty.all(Colors.white),
          activeTrackColor: Colors.red,
          inactiveTrackColor: Colors.grey,
        )
      ],
    );
  }
}
