import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/statstic/history_transaction/history_transaction_bloc.dart';
import 'package:sudoo/app/widgets/empty_list.dart';
import 'package:sudoo/extensions/double_ext.dart';

import '../../../../resources/R.dart';

class HistoryTransactionPage extends BasePage<HistoryTransactionBloc> {
  HistoryTransactionPage({super.key});

  @override
  bool get enableStatePage => true;

  @override
  Widget build(BuildContext context) {
    final TextStyle style = R.style.h5.copyWith(color: Colors.black);
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
          child: Text(
            R.string.historyTransaction,
            style: R.style.h4_1.copyWith(
              fontWeight: FontWeight.bold,
              color: Colors.black,
            ),
          ),
        ),
        const SizedBox(
          height: 10,
        ),
        Expanded(
          child: ValueListenableBuilder(
            valueListenable: bloc.transactions,
            builder: (context, value, child) => value.isEmpty
                ? const EmptyList()
                : Row(
                    children: [
                      Expanded(
                        child: SingleChildScrollView(
                          scrollDirection: Axis.vertical,
                          child: DataTable(
                            dataRowMinHeight: 50,
                            dataRowMaxHeight: 150,
                            headingRowColor: MaterialStateProperty.all<Color?>(
                              Colors.grey.shade300
                            ),
                            columns: [
                              DataColumn(
                                label: Text(
                                  R.string.transaction,
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
                                            maxWidth: 350,
                                          ),
                                          child: Text(
                                            "#${transaction.transactionId}",
                                            style: style.copyWith(
                                              color: Colors.blue,
                                              fontWeight: FontWeight.bold,
                                            ),
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
                                              color:
                                                  transaction.amount.isNegative
                                                      ? Colors.red
                                                      : Colors.green,
                                            ),
                                          ),
                                        ),
                                      ),
                                      DataCell(
                                        Expanded(
                                          child: Text(
                                            transaction.description,
                                            style: style,
                                            maxLines: 10,
                                            overflow: TextOverflow.ellipsis,
                                          ),
                                        ),
                                      )
                                    ],
                                  ),
                                )
                                .toList(),
                          ),
                        ),
                      ),
                    ],
                  ),
          ),
        ),
      ],
    );
  }
}
