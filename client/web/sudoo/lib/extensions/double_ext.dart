import '../utils/currency.dart';

extension FormatCurrency on double {
  String formatCurrency({int decimalDigits = 0}) {
    return CurrencyFormatUtils.get().format(this);
  }
}
