import 'package:intl/intl.dart';

class CurrencyFormatUtils {

  static CurrencyFormatUtils? _instance;

  static CurrencyFormatUtils get() {
    return CurrencyFormatUtils();
  }

  factory CurrencyFormatUtils() {
    _instance ??= CurrencyFormatUtils._internal();
    return _instance!;
  }

  CurrencyFormatUtils._internal();

  final NumberFormat currencyFormat = NumberFormat.simpleCurrency(
    locale: "vi-VN",
    decimalDigits: 0,
  );

  String format(double value) {
    return currencyFormat.format(value);
  }

  String formatWithDecimalDigits(double value, int decimalDigits) {
    return NumberFormat.simpleCurrency(
      locale: "vi-VN",
      decimalDigits: 0,
    ).format(value);
  }
}
