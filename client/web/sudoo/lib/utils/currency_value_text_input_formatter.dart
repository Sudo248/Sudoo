import 'package:flutter/services.dart';
import 'package:sudoo/extensions/double_ext.dart';
import 'package:sudoo/extensions/string_ext.dart';

class CurrencyValueTextInputFormatter extends TextInputFormatter {
  @override
  TextEditingValue formatEditUpdate(TextEditingValue oldValue, TextEditingValue newValue) {
    // return TextEditingValue(
    //   text: double.parse(newValue.text).formatCurrencyValue(),
    //   selection: newValue.selection,
    // );
    if (newValue.text.isEmpty) {
      return newValue.copyWith(text: '');
    } else if (newValue.text.compareTo(oldValue.text) != 0) {
      final int selectionIndexFromTheRight = newValue.text.length - newValue.selection.end;
      final newString = newValue.text.parserCurrencyValue().formatCurrencyValue();
      return TextEditingValue(
        text: newString,
        selection: TextSelection.collapsed(offset: newString.length - selectionIndexFromTheRight),
      );
    } else {
      return newValue;
    }
  }
}
