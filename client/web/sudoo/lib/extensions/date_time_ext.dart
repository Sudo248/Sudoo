import 'package:intl/intl.dart';

extension DateTimeFormat on DateTime {
  String formatDate({String format = "d/M/y"}) {
    return DateFormat(format).format(this);
  }
}