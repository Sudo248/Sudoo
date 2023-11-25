enum TypeDatePicker {
  day("day"),
  month("month"),
  year("year");

  final String value;

  const TypeDatePicker(this.value);

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
}