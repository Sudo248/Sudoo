import 'package:flutter/material.dart';

import '../../../resources/R.dart';

class SizeWeightBlock extends StatelessWidget {
  final TextEditingController weightController,
      heightController,
      widthController,
      lengthController;
  final TextStyle style;

  const SizeWeightBlock({
    super.key,
    required this.style,
    required this.weightController,
    required this.heightController,
    required this.widthController,
    required this.lengthController,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          children: [
            _buildItem(heightController, R.string.height, "cm"),
            const SizedBox(
              width: 20,
            ),
            _buildItem(widthController, R.string.width, "cm"),
          ],
        ),
        const SizedBox(
          height: 20,
        ),
        Row(
          children: [
            _buildItem(lengthController, R.string.length, "cm"),
            const SizedBox(
              width: 20,
            ),
            _buildItem(weightController, R.string.weight, "g"),
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
        labelStyle: style.copyWith(
          fontSize: 18,
        ),
        contentPadding: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
        constraints: const BoxConstraints(
          maxWidth: 200,
        ),
        suffixText: unit,
        suffixStyle: style.copyWith(fontWeight: FontWeight.bold),
        border: const OutlineInputBorder(),
        counterText: "",
      ),
        );
  }
}
