import 'package:flutter/material.dart';

import '../../../resources/R.dart';

class SizeBlock extends StatelessWidget {
  final TextEditingController weightController,
      heightController,
      widthController,
      lengthController;
  final TextStyle style;
  const SizeBlock({
    super.key,
    required this.style,
    required this.weightController,
    required this.heightController,
    required this.widthController,
    required this.lengthController,
  });

  @override
  Widget build(BuildContext context) {
    return Table(
      children: [
        TableRow(
          children: [
            _buildItem(heightController,R.string.height, "cm"),
            _buildItem(weightController,R.string.weight, "cm"),
          ],
        ),
        TableRow(
          children: [
            _buildItem(weightController,R.string.weight, "g"),
            _buildItem(lengthController,R.string.length, "cm"),
          ],
        )
      ],
    );
  }

  Widget _buildItem(
      TextEditingController controller, String label, String unit) {
    return TextField(
          controller: controller,
          maxLines: 1,
          style: style,
          keyboardType: TextInputType.number,
          decoration: InputDecoration(
            labelText: label,
            labelStyle: style,
            constraints: const BoxConstraints(
              maxWidth: 200,
            ),
            suffixIcon: Text(
              unit,
              style: style.copyWith(fontWeight: FontWeight.bold),
            ),
            border: const OutlineInputBorder(),
            counterText: "",
          ),
        );
  }
}
