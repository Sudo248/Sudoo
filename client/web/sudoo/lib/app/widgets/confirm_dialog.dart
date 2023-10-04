import 'package:flutter/material.dart';
import 'package:sudoo/app/widgets/confirm_button.dart';

class ConfirmDialog extends StatelessWidget {
  final String title;
  final String? description;
  final TextStyle? titleStyle, descriptionStyle;
  final VoidCallback? onPositive;
  final VoidCallback? onNegative;

  const ConfirmDialog({
    super.key,
    required this.title,
    this.description,
    this.titleStyle,
    this.descriptionStyle,
    this.onPositive,
    this.onNegative,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(10.0),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10),
      ),
      width: 500,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Text(
            title,
            style: titleStyle ??
                const TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
          ),
          const SizedBox(
            height: 10,
          ),
          description != null
              ? Text(
                  description!,
                  style: descriptionStyle ??
                      const TextStyle(
                        fontSize: 16,
                      ),
                )
              : const SizedBox(),
          const SizedBox(
            height: 10,
          ),
          ConfirmButton(
            onPositive: () {
              if (onPositive != null) {
                onPositive!.call();
              } else {
                Navigator.of(context).pop();
              }
            },
            onNegative: () {
              if (onNegative != null) {
                onNegative!.call();
              } else {
                Navigator.of(context).pop();
              }
            },
          )
        ],
      ),
    );
  }
}
