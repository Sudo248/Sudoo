import 'package:flutter/material.dart';
import 'package:sudoo/domain/model/order/order_status.dart';

class OrderStatusCell extends StatelessWidget {
  final OrderStatus status;
  final TextStyle style;

  const OrderStatusCell({super.key, required this.status, required this.style});

  @override
  Widget build(BuildContext context) {
    return Align(
      alignment: Alignment.centerLeft,
      child: DecoratedBox(
        decoration: BoxDecoration(
          color: status.color,
          borderRadius: BorderRadius.circular(3),
        ),
        child: Padding(
          padding: const EdgeInsets.symmetric(
            vertical: 5,
            horizontal: 20,
          ),
          child: Text(
            status.value,
            style: style.copyWith(
              color: Colors.white,
              fontSize: 18,
            ),
          ),
        ),
      )
    );
  }
}
