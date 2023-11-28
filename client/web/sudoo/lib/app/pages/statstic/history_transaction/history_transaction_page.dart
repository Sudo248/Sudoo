import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/statstic/history_transaction/history_transaction_bloc.dart';
import 'package:sudoo/extensions/double_ext.dart';

import '../../../../resources/R.dart';

class HistoryTransactionPage extends BasePage<HistoryTransactionBloc> {
  HistoryTransactionPage({super.key});

  @override
  bool get enableStatePage => true;

  @override
  Widget build(BuildContext context) {
    final TextStyle style = R.style.h5.copyWith(color: Colors.black);
    return ValueListenableBuilder(
      valueListenable: bloc.transactions,
      builder: (context, value, child) => DataTable(
        columns: [
          DataColumn(
            label: Text(
              R.string.totalRevenue,
              style: style.copyWith(
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
          DataColumn(
            label: Text(
              R.string.amount,
              style: style.copyWith(
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
          DataColumn(
            label: Text(
              R.string.description,
              style: style.copyWith(
                fontWeight: FontWeight.bold,
              ),
            ),
          )
        ],
        rows: value
            .map<DataRow>(
              (transaction) => DataRow(
                cells: [
                  DataCell(
                    ConstrainedBox(
                      constraints: const BoxConstraints(
                        minWidth: 100,
                        maxWidth: 150,
                      ),
                      child: Text(
                        "#${transaction.transactionId}",
                        style: style.copyWith(color: Colors.blue),
                      ),
                    ),
                  ),
                  DataCell(
                    ConstrainedBox(
                      constraints: const BoxConstraints(
                        minWidth: 100,
                        maxWidth: 150,
                      ),
                      child: Text(
                        transaction.amount.formatCurrency(),
                        style: style.copyWith(
                            color: transaction.amount.isNegative
                                ? Colors.red
                                : Colors.green),
                      ),
                    ),
                  ),
                  DataCell(
                    Expanded(
                      child: Text(
                        transaction.description,
                        style: style,
                        maxLines: 5,
                        overflow: TextOverflow.ellipsis,
                      ),
                    ),
                  )
                ],
              ),
            )
            .toList(),
      ),
    );
  }
}
