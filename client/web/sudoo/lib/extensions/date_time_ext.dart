import 'package:intl/intl.dart';

extension DateTimeFormat on DateTime {
  String formatDate({String format = "dd/MM/yyyy"}) {
    return DateFormat(format).format(this);
  }

  String formatDateTime({String format = "d/M/y HH:mm:ss"}) {
    return DateFormat(format).format(this);
  }
}