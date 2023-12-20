import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/extensions/date_time_ext.dart';

import '../../../domain/model/model/model.dart';
import '../../../resources/R.dart';

class ModelItem extends StatelessWidget {
  final Model model;
  const ModelItem({super.key,required this.model});

  @override
  Widget build(BuildContext context) {
    final TextStyle style = R.style.h5.copyWith(color: Colors.black);
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        children: [
          ..._buildTextView(R.string.version, model.version, style),
          ..._buildTextView(R.string.evaluate, model.evaluate.toStringAsFixed(3), style),
          ..._buildTextView(R.string.buildAt, model.buildAt.formatDateTime(), style),
          ..._buildTextView(R.string.userSize, model.userSize.toString(), style),
          ..._buildTextView(R.string.productSize, model.productSize.toString(), style),
          ..._buildTextView(R.string.categorySize, model.categorySize.toString(), style),
          ..._buildTextView(R.string.reviewSize, model.reviewSize.toString(), style),
        ],
      ),
    );
  }

  List<Widget> _buildTextView(String label, String value, TextStyle style) {
    return [
      RichText(
        text: TextSpan(
          style: style,
          children: [
            TextSpan(
              style: style.copyWith(fontWeight: FontWeight.bold),
              text: "$label: ",
            ),
            TextSpan(text: value),
          ],
        ),
      ),
      const SizedBox(
        height: 10,
      ),
    ];
  }
}
