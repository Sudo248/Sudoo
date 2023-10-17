import 'package:flutter/material.dart';
import 'package:sudoo/extensions/date_time_ext.dart';

import '../../resources/R.dart';

class DateTimeSelector extends StatefulWidget {
  final bool isEditable;
  final DateTime? value;
  final String? hint;
  final TextStyle? style;
  final ValueSetter<DateTime>? onSelectedDate;

  const DateTimeSelector({
    super.key,
    this.value,
    this.onSelectedDate,
    this.hint,
    this.isEditable = true,
    this.style,
  });

  @override
  State<DateTimeSelector> createState() => _DateTimeSelectorState();
}

class _DateTimeSelectorState extends State<DateTimeSelector> {
  @override
  Widget build(BuildContext context) {
    final TextStyle style =
        widget.style ?? R.style.h4_1.copyWith(color: Colors.black);
    if (!widget.isEditable) {
      return Container(
        padding: const EdgeInsets.symmetric(horizontal: 5.0),
        child: Text(
          "${widget.value == null ? widget.hint : widget.value!.formatDate()}",
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
              "${widget.value == null ? widget.hint : widget.value!.formatDate()}",
              style: style.copyWith(
                color: widget.value == null ? Colors.grey : Colors.black,
              ),
            ),
            const SizedBox(
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
          initialDate: widget.value ?? now,
          firstDate: DateTime(now.year),
          lastDate: DateTime(now.year + 1),
        );
        if (selectedDate != null) {
          widget.onSelectedDate?.call(selectedDate);
        }
      },
      child: const Icon(
        Icons.calendar_month_outlined,
        color: Colors.grey,
      ),
    );
  }
}

