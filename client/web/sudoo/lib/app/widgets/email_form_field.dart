import 'package:flutter/material.dart';
import 'package:sudoo/resources/R.dart';

class EmailFormField extends StatelessWidget {
  final TextEditingController controller;
  final FormFieldValidator<String?> validator;
  final String? value;
  final FocusNode? nextFocusNode;
  final FocusNode? focusNode;

  const EmailFormField({
    super.key,
    required this.controller,
    required this.validator,
    this.value,
    this.nextFocusNode,
    this.focusNode,
  });

  @override
  Widget build(BuildContext context) {
    return TextFormField(
      controller: controller,
      initialValue: value,
      focusNode: focusNode,
      onFieldSubmitted: (_) => nextFocusNode?.requestFocus(),
      keyboardType: TextInputType.emailAddress,
      decoration: InputDecoration(
        labelText: R.string.email,
        border: const OutlineInputBorder(),
      ),
      validator: validator,
    );
  }
}
