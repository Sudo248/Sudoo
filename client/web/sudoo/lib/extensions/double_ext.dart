import '../utils/currency.dart';

extension FormatCurrency on double {
  String formatCurrency() {
    return CurrencyFormatUtils.get().format(this);
  }
}
