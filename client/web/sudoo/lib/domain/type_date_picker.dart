enum TypeDatePicker {
  day("day", "Ngày"),
  month("month", "Tháng"),
  year("year", "Năm");

  final String value;
  final String displayValue;

  const TypeDatePicker(this.value, this.displayValue);

  factory TypeDatePicker.fromValue(String? value) {
    if (value == null) return TypeDatePicker.day;
    switch(value) {
      case "month":
        return TypeDatePicker.month;
      case "year":
        return TypeDatePicker.year;
      default:
        return TypeDatePicker.day;
    }
  }

  factory TypeDatePicker.fromDisplayValue(String? displayValue) {
    if (displayValue == null) return TypeDatePicker.day;
    switch(displayValue) {
      case "Tháng":
        return TypeDatePicker.month;
      case "Năm":
        return TypeDatePicker.year;
      default:
        return TypeDatePicker.day;
    }
  }
}