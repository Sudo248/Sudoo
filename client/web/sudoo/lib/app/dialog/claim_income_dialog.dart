import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/widgets/confirm_button.dart';
import 'package:sudoo/extensions/date_time_ext.dart';
import 'package:sudoo/extensions/double_ext.dart';

import '../../domain/model/discovery/transaction.dart';
import '../../resources/R.dart';

class ClaimIncomeDialog extends StatelessWidget {
  final AsyncValueSetter<Transaction>? onClaim;
  final TextEditingController fullNameController = TextEditingController(),
      bankController = TextEditingController(),
      amountController = TextEditingController(),
      numberSerialBankController = TextEditingController();
  final TextStyle style = R.style.h5.copyWith(color: Colors.black);

  ClaimIncomeDialog({super.key, this.onClaim});

  @override
  Widget build(BuildContext context) {
    return Dialog(
      child: Container(
        padding: const EdgeInsets.all(15.0),
        constraints: const BoxConstraints(
          minWidth: 300,
          minHeight: 300 * 1.5,
          maxWidth: 350,
          maxHeight: 350 * 1.5,
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Align(
              alignment: Alignment.center,
              child: Text(
                R.string.claimMoney,
                style: R.style.h4_1.copyWith(
                  color: Colors.black,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            ..._buildInputField(
              R.string.bank,
              bankController,
            ),
            ..._buildInputField(
              R.string.numberSerialBank,
              numberSerialBankController,
            ),
            ..._buildInputField(
              R.string.fullName,
              fullNameController,
            ),
            ..._buildInputField(
              R.string.amount,
              amountController,
            ),
            const Expanded(child: SizedBox.shrink()),
            ConfirmButton(
              textPositive: R.string.claim,
              onPositive: () {
                context.pop();
                onClaim?.call(_createClaimTransaction());
              },
              onNegative: () {
                context.pop();
              },
            ),
            const SizedBox(
              height: 30,
            ),
          ],
        ),
      ),
    );
  }

  Transaction _createClaimTransaction() {
    return Transaction.create(
      amount: double.parse(amountController.text),
      description: _getDescription(),
    );
  }

  String _getDescription() {
    return """
    Claim income
    Bank      : ${bankController.text}
    Number    : ${numberSerialBankController.text}
    Full name : ${fullNameController.text}
    Amount    : ${double.parse(amountController.text).formatCurrency()}
    Created at: ${DateTime.now().formatDateTime()}
    """.trim();
  }

  List<Widget> _buildInputField(
    String label,
    TextEditingController? controller, {
    int? maxLines,
    int? maxLength,
    bool readOnly = false,
    bool expands = false,
    TextInputType? keyboardType,
    VoidCallback? onTap,
  }) {
    return [
      Text(
        label,
        style: style.copyWith(fontWeight: FontWeight.bold),
      ),
      const SizedBox(
        height: 5,
      ),
      TextField(
        controller: controller,
        style: style,
        maxLines: maxLines,
        maxLength: maxLength,
        readOnly: readOnly,
        expands: expands,
        onTap: onTap,
        keyboardType: keyboardType,
        decoration: const InputDecoration(
          border: OutlineInputBorder(),
          counterText: "",
        ),
      ),
      const SizedBox(
        height: 15,
      )
    ];
  }
}
