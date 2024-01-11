import 'package:flutter/material.dart';

void showYearPicker({
  required BuildContext context,
  required DateTime firstDate,
  required DateTime lastDate,
  required DateTime initialDate,
  required ValueChanged<DateTime> onChanged,
}) {
  showDialog(
    context: context,
    builder: (context) {
      final Size size = MediaQuery.of(context).size;
      return AlertDialog(
        title: const Text('Chọn năm'),
        // Changing default contentPadding to make the content looks better
        contentPadding: const EdgeInsets.all(10),
        content: YearPicker(
          firstDate: firstDate,
          lastDate: lastDate,
          selectedDate: initialDate,
          onChanged: onChanged,
        ),
      );
    },
  );
}
