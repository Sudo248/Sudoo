import 'package:flutter/material.dart';
import 'package:sudoo/resources/R.dart';

class EmailFormField extends StatelessWidget {
  final TextEditingController controller;
  final FormFieldValidator<String?> validator;
  final String? value;
  final FocusNode? nextForcusNode;
  final FocusNode? focusNode;

  const EmailFormField({
    super.key,
    required this.controller,
    required this.validator,
    this.value,
    this.nextForcusNode,
    this.focusNode,
  });

  @override
  Widget build(BuildContext context) {
    return TextFormField(
      controller: controller,
      initialValue: value,
      focusNode: focusNode,
      onFieldSubmitted: (_) => nextForcusNode?.requestFocus(),
      keyboardType: TextInputType.emailAddress,
      decoration: InputDecoration(
        labelText: R.string.email,
        border: const OutlineInputBorder(),
      ),
      validator: validator,
    );
  }
}
