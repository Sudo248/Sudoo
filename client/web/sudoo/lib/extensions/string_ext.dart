import 'package:sudoo/utils/currency.dart';

extension StringExt on String? {
  bool get isNullOrEmpty => this == null || this!.isEmpty;

  String get orEmpty => this == null ? "" : this!;

  double parserCurrency() {
    return isNullOrEmpty
        ? 0.0
        : CurrencyFormatUtils.get().parser(this!) as double;
  }

  int parserPercent() {
    if (isNullOrEmpty) return 0;
    return int.parse(this!.replaceAll("%", ""));
  }

  double parserCurrencyValue() {
    return isNullOrEmpty
        ? 0.0
        : CurrencyFormatUtils.get().parserCurrencyValue(this!) as double;
  }
}
