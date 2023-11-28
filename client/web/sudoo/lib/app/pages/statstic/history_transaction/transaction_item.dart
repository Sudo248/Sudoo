import 'package:flutter/material.dart';
import 'package:sudoo/domain/model/discovery/transaction.dart';
import 'package:sudoo/extensions/double_ext.dart';

import '../../../../resources/R.dart';

class TransactionItem extends StatelessWidget {
  final Transaction transaction;

  const TransactionItem({super.key, required this.transaction});

  @override
  Widget build(BuildContext context) {
    final TextStyle style = R.style.h5.copyWith(color: Colors.black);
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 15),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Expanded(
            flex: 1,
            child: Text(
              "#${transaction.transactionId}",
              style: style.copyWith(color: Colors.blue),
            ),
          ),
          Expanded(
            flex: 1,
            child: Text(
              transaction.amount.formatCurrency(),
              style: style.copyWith(
                  color: transaction.amount.isNegative
                      ? Colors.red
                      : Colors.green),
            ),
          ),
          Expanded(
            flex: 3,
            child: Text(
              transaction.description,
              style: style,
              maxLines: 5,
              overflow: TextOverflow.ellipsis,
            ),
          ),
        ],
      ),
    );
  }

  List<Widget> _buildTextView(String label, String value, TextStyle style) {
    return [
      RichText(
        text: TextSpan(
          style: style,
          children: [
            TextSpan(
              style: style.copyWith(fontWeight: FontWeight.bold),
              text: "$label: ",
            ),
            TextSpan(text: value),
          ],
        ),
      ),
      const SizedBox(
        height: 10,
      ),
    ];
  }
}
