import '../utils/currency.dart';

extension FormatCurrency on double {
  String formatCurrency() {
    return CurrencyFormatUtils.get().format(this);
  }

  String formatCurrencyValue() {
    return CurrencyFormatUtils.get().formatCurrencyValue(this);
  }
}
