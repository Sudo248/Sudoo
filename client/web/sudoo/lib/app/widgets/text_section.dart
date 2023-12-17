import 'package:flutter/material.dart';

class TextSection extends StatelessWidget {
  final String label;
  final TextStyle labelTextStyle;
  final String text;
  final TextStyle style;
  final int maxLines;
  final TextOverflow overflow;

  const TextSection({
    super.key,
    required this.label,
    required this.labelTextStyle,
    required this.text,
    required this.style,
    this.maxLines = 1,
    this.overflow = TextOverflow.ellipsis
  });

  @override
  Widget build(BuildContext context) {
    return RichText(
      maxLines: maxLines,
      overflow: overflow,
      text: TextSpan(
        children: [
          TextSpan(
            text: "$label: ",
            style: labelTextStyle,
          ),
          TextSpan(
            text: text,
            style: style,
          )
        ],
      ),
    );
  }
}
