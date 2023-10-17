import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import '../../../resources/R.dart';
import '../date_time_selector.dart';

class RangeTimeBlock extends StatelessWidget {
  final ValueNotifier<DateTime?> startDate;
  final ValueNotifier<DateTime?> endDate;
  final TextStyle? style;
  final AsyncValueSetter<DateTime>? onSelectedStartTime, onSelectedEndTime;

  const RangeTimeBlock({
    super.key,
    required this.startDate,
    required this.endDate,
    this.style,
    this.onSelectedStartTime,
    this.onSelectedEndTime,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Text(
          R.string.from,
          style: style?.copyWith(fontWeight: FontWeight.bold),
        ),
        const SizedBox(
          width: 10,
        ),
        ValueListenableBuilder(
          valueListenable: startDate,
          builder: (context, value, child) {
            return DateTimeSelector(
              value: value,
              onSelectedDate: onSelectedStartTime,
            );
          },
        ),
        const SizedBox(
          width: 5,
        ),
        const Icon(
          Icons.remove,
          size: 10,
          color: Colors.black,
        ),
        const SizedBox(
          width: 3,
        ),
        Text(
          R.string.to,
          style: style?.copyWith(fontWeight: FontWeight.bold),
        ),
        const SizedBox(
          width: 10,
        ),
        ValueListenableBuilder(
          valueListenable: endDate,
          builder: (context, value, child) {
            return DateTimeSelector(
              value: value,
              onSelectedDate: onSelectedEndTime,
            );
          },
        ),
      ],
    );
  }
}
