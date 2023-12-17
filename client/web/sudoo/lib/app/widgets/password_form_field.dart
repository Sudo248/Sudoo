import 'package:flutter/material.dart';

import '../../resources/R.dart';

class PasswordFormField extends StatefulWidget {
  final TextEditingController controller;
  final FormFieldValidator<String?> validator;
  final String? value;
  final String? label;
  final FocusNode? nextForcusNode;
  final FocusNode? focusNode;

  const PasswordFormField({
    super.key,
    required this.controller,
    required this.validator,
    this.value,
    this.label,
    this.nextForcusNode,
    this.focusNode,
  });

  @override
  State<PasswordFormField> createState() => _PasswordFormFieldState();
}

class _PasswordFormFieldState extends State<PasswordFormField> {
  bool _isObscurePassword = true;

  @override
  Widget build(BuildContext context) {
    return TextFormField(
      controller: widget.controller,
      initialValue: widget.value,
      focusNode: widget.focusNode,
      onFieldSubmitted: (_) => widget.nextForcusNode?.requestFocus(),
      keyboardType: TextInputType.visiblePassword,
      obscureText: _isObscurePassword,
      decoration: InputDecoration(
        labelText: widget.label ?? R.string.password,
        border: const OutlineInputBorder(),
        suffixIcon: IconButton(
          icon: Icon(
            _isObscurePassword ? Icons.visibility : Icons.visibility_off,
            color: R.color.primaryColor,
          ),
          onPressed: () => setState(() {
            _isObscurePassword = !_isObscurePassword;
          }),
        ),
      ),
      validator: widget.validator,
    );
  }
}
