import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/user/user_bloc.dart';
import 'package:sudoo/app/widgets/date_time_selector.dart';

import '../../../domain/model/user/gender.dart';
import '../../../resources/R.dart';
import '../../dialog/choose_address_dialog.dart';
import '../../widgets/avatar_image.dart';

class UserPage extends BasePage<UserBloc> {
  @override
  bool get enableStatePage => true;

  final TextStyle style = const TextStyle(
    fontSize: 16,
    color: Colors.black,
  );

  UserPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // appBar: AppBar(
      //   leading: GestureDetector(
      //     onTap: () => context.pop(),
      //     child: const Icon(Icons.arrow_back),
      //   ),
      // ),
      body: SizedBox(
        width: double.infinity,
        child: SingleChildScrollView(
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
                    ..._buildGender(context),
                    ..._buildDob(context),
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
        decoration: const InputDecoration(border: OutlineInputBorder()),
      ),
      const SizedBox(
        height: 15,
      )
    ];
  }

  List<Widget> _buildGender(BuildContext context) {
    return [
      Text(
        R.string.gender,
        style: style.copyWith(fontWeight: FontWeight.bold),
      ),
      const SizedBox(
        height: 5,
      ),
      DropdownMenu<Gender>(
        width: 500,
        controller: bloc.genderController,
        textStyle: style,
        dropdownMenuEntries: Gender.values
            .map<DropdownMenuEntry<Gender>>(
              (e) => DropdownMenuEntry<Gender>(
                value: e,
                label: e.value,
              ),
            )
            .toList(),
      ),
      const SizedBox(
        height: 15,
      ),
    ];
  }

  List<Widget> _buildDob(BuildContext context) {
    return [
      Text(
        R.string.dob,
        style: style.copyWith(fontWeight: FontWeight.bold),
      ),
      const SizedBox(
        height: 5,
      ),
      ValueListenableBuilder(
        valueListenable: bloc.dob,
        builder: (context, value, child) => DateTimeSelector(
          firstDate: DateTime(1870),
          lastDate: DateTime.now(),
          value: value,
          hint: R.string.dob,
          onSelectedDate: (dob) => bloc.dob.value = dob,
          isExpandedIcon: true,
        ),
      ),
      const SizedBox(
        height: 15,
      ),
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
