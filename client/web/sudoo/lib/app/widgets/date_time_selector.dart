import 'package:flutter/material.dart';
import 'package:month_year_picker/month_year_picker.dart';
import 'package:sudoo/domain/type_date_picker.dart';
import 'package:sudoo/extensions/date_time_ext.dart';

import '../../resources/R.dart';
import '../dialog/year_picker_dialog.dart';

class DateTimeSelector extends StatelessWidget {
  final bool isEditable;
  final DateTime? value;
  final String? hint;
  final TextStyle? style;
  final ValueSetter<DateTime>? onSelectedDate;
  final bool isExpandedIcon;
  final DateTime firstDate, lastDate;
  final TypeDatePicker type;

  const DateTimeSelector({
    super.key,
    required this.firstDate,
    required this.lastDate,
    this.value,
    this.onSelectedDate,
    this.hint = "Choose",
    this.isEditable = true,
    this.style,
    this.isExpandedIcon = false,
    this.type = TypeDatePicker.day,
  });

  @override
  Widget build(BuildContext context) {
    final TextStyle style = this.style ?? R.style.h4_1.copyWith(color: Colors.black);
    if (!isEditable) {
      return Padding(
        padding: const EdgeInsets.symmetric(horizontal: 5.0),
        child: Text(
          "${value == null ? hint : value!.formatDate()}",
          style: style,
        ),
      );
    } else {
      return Container(
        padding: const EdgeInsets.symmetric(horizontal: 8.0, vertical: 5.0),
        decoration: BoxDecoration(
          border: Border.all(
            color: Colors.grey,
          ),
          borderRadius: BorderRadius.circular(5),
        ),
        child: Row(
          children: [
            Text(
              "${value == null ? hint : value!.formatDate()}",
              style: style.copyWith(
                color: value == null ? Colors.grey : Colors.black,
              ),
            ),
            isExpandedIcon
                ? const Expanded(child: SizedBox.shrink())
                : const SizedBox(
                    width: 10,
                  ),
            _buildDatePicker(context),
          ],
        ),
      );
    }
  }

  Widget _buildDatePicker(BuildContext context) {
    return GestureDetector(
      onTap: () async {
        final DateTime now = DateTime.now();

        final selectedDate = await showDatePicker(
          context: context,
          initialDate: value ?? now,
          firstDate: firstDate,
          lastDate: lastDate,
        );
        if (selectedDate != null) {
          onSelectedDate?.call(selectedDate);
        }
      },
      child: const Icon(
        Icons.calendar_month_outlined,
        color: Colors.grey,
      ),
    );
  }

  Future<void> selected(BuildContext context) async {
    final DateTime now = DateTime.now();
    if (type == TypeDatePicker.day) {
      final selectedDate = await showDatePicker(
        context: context,
        initialDate: value ?? now,
        firstDate: firstDate,
        lastDate: lastDate,
      );
      if (selectedDate != null) {
        onSelectedDate?.call(selectedDate);
      }
    } else if (type == TypeDatePicker.month) {
      final selectedDate = await showMonthYearPicker(
        context: context,
        initialDate: value ?? now,
        firstDate: firstDate,
        lastDate: lastDate,
      );
      if (selectedDate != null) {
        onSelectedDate?.call(selectedDate);
      }
    } else {
      showYearPicker(
          context: context,
          initialDate: value ?? now,
          firstDate: firstDate,
          lastDate: lastDate,
          onChanged: (value) => onSelectedDate?.call(value));
    }
  }
}
