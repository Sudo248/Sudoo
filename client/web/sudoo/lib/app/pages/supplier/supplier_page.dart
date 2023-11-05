import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/supplier/supplier_bloc.dart';
import 'package:sudoo/app/widgets/avatar_image.dart';

import '../../../resources/R.dart';

class SupplierPage extends BasePage<SupplierBloc> {

  @override
  bool get enableStatePage => true;

  final TextStyle style = const TextStyle(
        fontSize: 16,
        color: Colors.white,
      );

  SupplierPage({super.key});

  @override
  Widget build(BuildContext context) {
    return _buildSupplier(context);
  }

  Widget _buildSupplier(BuildContext context) {
    return SingleChildScrollView(
      padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 15),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          AvatarImage(avatar: bloc.avatar,),
          const SizedBox(
            height: 20,
          ),
          ConstrainedBox(
            constraints: const BoxConstraints(
              minWidth: 100,
              maxWidth: 500
            ),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                ..._buildInputField(R.string.name, bloc.nameController, maxLines: 1),
                ..._buildInputField(R.string.brand, bloc.brandController, maxLines: 1, maxLength: 10),
                ..._buildInputField(R.string.contactUrl, bloc.contactUrlController, maxLines: 1),
              ],
            ),
          ),
          const SizedBox(
            height: 30,
          ),
          FilledButton(
            style: R.buttonStyle.filledButtonStyle(),
            onPressed: bloc.onSave,
            child: Text(
              R.string.save,
              style: R.style.h5,
            ),
          ),
          const SizedBox(
            height: 30,
          ),

        ],
      ),
    );
  }

  List<Widget> _buildInputField(String label, TextEditingController controller,
      {int? maxLines, int? maxLength, bool readOnly = false, bool expands = false, VoidCallback? onTap}) {
    return [
      Text(
        label,
        style: style.copyWith(fontWeight: FontWeight.bold),
      ),
      const SizedBox(
        height: 5,
      ),
      TextField(
        controller: controller,
        style: style,
        maxLines: maxLines,
        maxLength: maxLength,
        readOnly: readOnly,
        expands: expands,
        onTap: onTap,
        decoration: const InputDecoration(border: OutlineInputBorder()),
      ),
      const SizedBox(
        height: 15,
      )
    ];
  }
}
