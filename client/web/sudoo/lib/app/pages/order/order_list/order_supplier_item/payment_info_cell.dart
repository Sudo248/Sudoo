import 'package:flutter/material.dart';
import 'package:sudoo/extensions/date_time_ext.dart';
import 'package:sudoo/extensions/double_ext.dart';

import '../../../../../resources/R.dart';

class PaymentInfoCell extends StatelessWidget {
  final double amount;
  final String? paymentType;
  final DateTime? paymentDateTime;
  final TextStyle style;

  const PaymentInfoCell({
    super.key,
    required this.amount,
    this.paymentType,
    this.paymentDateTime,
    required this.style,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        RichText(
          text: TextSpan(
            children: [
              TextSpan(
                text: "${R.string.total}: ",
                style: style.copyWith(
                  fontWeight: FontWeight.bold,
                ),
              ),
              TextSpan(text: amount.formatCurrency(), style: style)
            ],
          ),
        ),
        const SizedBox(
          height: 3,
        ),
        ...(paymentType == null || paymentDateTime == null
            ? [
                Text(
                  R.string.haveNotPayment,
                  style: style,
                )
              ]
            : _buildPaymentInfo())
      ],
    );
  }

  List<Widget> _buildPaymentInfo() {
    return [
      RichText(
        text: TextSpan(
          children: [
            TextSpan(
              text: "${R.string.paymentType}: ",
              style: style.copyWith(
                fontWeight: FontWeight.bold,
              ),
            ),
            TextSpan(text: paymentType, style: style)
          ],
        ),
      ),
      const SizedBox(
        height: 3,
      ),
      RichText(
        text: TextSpan(
          children: [
            TextSpan(
              text: "${R.string.paymentDateTime}: ",
              style: style.copyWith(
                fontWeight: FontWeight.bold,
              ),
            ),
            TextSpan(text: paymentDateTime!.formatDateTime(), style: style)
          ],
        ),
      )
    ];
  }
}
