import 'package:flutter/material.dart';

import '../../resources/R.dart';

class ConfirmButton extends StatelessWidget {
  final String textPositive;
  final String textNegative;
  final VoidCallback onPositive;
  final VoidCallback? onNegative;
  const ConfirmButton({
    super.key,
    this.textPositive = "OK",
    this.textNegative = "Cancel",
    required this.onPositive,
    this.onNegative,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.end,
      children: [
        FilledButton(
          style: R.buttonStyle.filledButtonStyle(),
          onPressed: onPositive,
          child: Text(
            textPositive,
            style: R.style.h5,
          ),
        ),
        const SizedBox(
          width: 20,
        ),
        OutlinedButton(
          style: R.buttonStyle.outlinedButtonStyle(),
          onPressed: onNegative,
          child: Text(
            textNegative,
            style: R.style.h5.copyWith(color: Colors.blueGrey),
          ),
        )
      ],
    );
  }
}
