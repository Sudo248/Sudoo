import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/dialog/choose_address_dialog.dart';
import 'package:sudoo/app/pages/supplier/supplier_bloc.dart';
import 'package:sudoo/app/widgets/avatar_image.dart';

import '../../../resources/R.dart';

class SupplierPage extends BasePage<SupplierBloc> {

  @override
  bool get enableStatePage => true;

  final TextStyle style = const TextStyle(
    fontSize: 16,
    color: Colors.black,
  );

  SupplierPage({super.key});

  @override
  Widget build(BuildContext context) {
    return _buildSupplier(context);
  }

  Widget _buildSupplier(BuildContext context) {
    return SizedBox(
      width: double.infinity,
      child: SingleChildScrollView(
        primary: true,
        padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 15),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            AvatarImage(
              avatar: bloc.avatar,
              height: 150,
              width: 150,
            ),
            const SizedBox(
              height: 20,
            ),
            ConstrainedBox(
              constraints: const BoxConstraints(minWidth: 100, maxWidth: 500),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  ..._buildInputField(
                    R.string.name,
                    bloc.nameController,
                    maxLines: 1,
                  ),
                  ValueListenableBuilder(
                    valueListenable: bloc.showInputPhoneNumber,
                    builder: (context, value, child) =>
                        value ? child! : const SizedBox.shrink(),
                    child: _buildPhoneNumberField(),
                  ),
                  ..._buildInputField(
                    R.string.brand,
                    bloc.brandController,
                    maxLines: 1,
                    maxLength: 10,
                  ),
                  ..._buildInputField(
                    R.string.contactUrl,
                    bloc.contactUrlController,
                    maxLines: 1,
                  ),
                  ..._buildInputField(
                    R.string.province,
                    bloc.provinceController,
                    maxLines: 1,
                    readOnly: true,
                    onTap: () {
                      bloc.onChooseProvince();
                      showDialogChooseAddress(context);
                    },
                  ),
                  ..._buildInputField(
                    R.string.district,
                    bloc.districtController,
                    maxLines: 1,
                    readOnly: true,
                    onTap: () {
                      bloc.onChooseDistrict();
                      showDialogChooseAddress(context);
                    },
                  ),
                  ..._buildInputField(
                    R.string.ward,
                    bloc.wardCodeController,
                    maxLines: 1,
                    readOnly: true,
                    onTap: () {
                      bloc.onChooseWard();
                      showDialogChooseAddress(context);
                    },
                  ),
                  ..._buildInputField(
                    R.string.address,
                    bloc.addressController,
                    maxLines: 5,
                    maxLength: 100,
                    keyboardType: TextInputType.multiline,
                  ),
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
      ),
    );
  }

  Widget _buildPhoneNumberField() {
    return Column(
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: _buildInputField(
        R.string.phoneNumber,
        bloc.phoneNumberController,
        maxLines: 1,
        maxLength: 14,
      ),
    );
  }

  List<Widget> _buildInputField(
    String label,
    TextEditingController controller, {
    int? maxLines,
    int? maxLength,
    bool readOnly = false,
    bool expands = false,
    TextInputType? keyboardType,
    VoidCallback? onTap,
  }) {
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
        keyboardType: keyboardType,
        decoration: const InputDecoration(
          border: OutlineInputBorder(),
          counterText: "",
        ),
      ),
      const SizedBox(
        height: 30,
      )
    ];
  }

  void showDialogChooseAddress(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => ChooseAddressDialog(
        suggestion: bloc.suggestion,
        step: bloc.stepChooseAddress,
        callback: bloc,
      ),
    );
  }
}
