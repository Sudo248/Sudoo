import 'package:flutter/material.dart';
import 'package:sudoo/extensions/date_time_ext.dart';

import '../../../../../resources/R.dart';

class OrderInfoCell extends StatelessWidget {
  final String orderSupplierId;
  final DateTime createdAt;
  final String? storeName;
  final TextStyle style;

  const OrderInfoCell({
    super.key,
    required this.orderSupplierId,
    required this.createdAt,
    required this.style,
    this.storeName,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Text(
          "#$orderSupplierId",
          style: style.copyWith(
            color: Colors.blue,
            fontWeight: FontWeight.bold,
          ),
        ),
        const SizedBox(
          height: 3,
        ),
        RichText(
          text: TextSpan(
            children: [
              TextSpan(
                text: "${R.string.createdAt}: ",
                style: style.copyWith(
                  color: Colors.black,
                  fontWeight: FontWeight.bold,
                ),
              ),
              TextSpan(text: createdAt.formatDateTime(), style: style)
            ],
          ),
        ),
        storeName != null
            ? Container(
                padding: const EdgeInsets.symmetric(
                  vertical: 5,
                  horizontal: 8,
                ),
                decoration: BoxDecoration(
                  color: Colors.blue,
                  borderRadius: BorderRadius.circular(3),
                ),
                child: Text(
                  storeName!,
                  style: style.copyWith(color: Colors.white),
                ),
              )
            : const SizedBox.shrink(),
        const SizedBox(
          height: 5,
        ),
      ],
    );
  }
}
