import 'package:intl/intl.dart';

class DateTimeFormatUtils {
  static DateTimeFormatUtils? _instance;

  static DateTimeFormatUtils get() {
    return DateTimeFormatUtils();
  }

  factory DateTimeFormatUtils() {
    _instance ??= DateTimeFormatUtils._internal();
    return _instance!;
  }

  DateTimeFormatUtils._internal();

  String formatDate(DateTime value, {String format = "dd/MM/yyyy"}) {
    return DateFormat(format).format(value);
  }
}