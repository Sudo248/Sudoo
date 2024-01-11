import 'dart:typed_data';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/widgets/confirm_button.dart';
import 'package:sudoo/app/widgets/online_image.dart';
import 'package:sudoo/extensions/list_ext.dart';

import '../../domain/model/discovery/category.dart';
import '../../domain/model/discovery/file.dart';
import '../../resources/R.dart';
import '../../utils/logger.dart';

class EditCategoryDialog extends StatelessWidget {
  late final Category category;
  final Future<bool> Function(Category, File?)? onSubmitCategory;
  final ValueNotifier<bool> enable = ValueNotifier(true);
  final ValueNotifier<File?> image = ValueNotifier(null);
  final TextStyle style = R.style.h5.copyWith(color: Colors.black);
  final TextEditingController nameController = TextEditingController();

  EditCategoryDialog({super.key, Category? category, this.onSubmitCategory}) {
    if (category != null) {
      this.category = category;
      image.value = File.fromUrl(category.image);
      enable.value = category.enable;
    } else {
      this.category = Category.empty();
    }
  }

  bool get isCreate => category.categoryId.isEmpty;

  @override
  Widget build(BuildContext context) {
    return Dialog(
      child: Container(
        padding: const EdgeInsets.all(15.0),
        constraints: const BoxConstraints(
          minWidth: 300,
          minHeight: 300 * 1.5,
          maxWidth: 350,
          maxHeight: 350 * 1.5,
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Align(
              alignment: Alignment.center,
              child: Text(
                isCreate ? R.string.createCategory : R.string.updateCategory,
                style: R.style.h4_1.copyWith(
                  color: Colors.black,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            Align(
              alignment: Alignment.center,
              child: GestureDetector(
                onTap: pickImage,
                child: ValueListenableBuilder(
                  valueListenable: image,
                  builder: (context, value, child) {
                    if (isCreate) {
                      return value == null ? child! : _buildImage(value);
                    } else {
                      return _buildImage(value);
                    }
                  },
                  child: Container(
                    width: 150,
                    height: 150,
                    padding: const EdgeInsets.all(10.0),
                    decoration: BoxDecoration(
                      color: Colors.grey,
                      borderRadius: BorderRadius.circular(15.0),
                    ),
                    child: const Column(
                      mainAxisSize: MainAxisSize.min,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Icon(
                          Icons.add,
                          size: 30,
                          color: Colors.blueGrey,
                        ),
                        SizedBox(
                          height: 15,
                        ),
                        Text(
                          "Upload",
                          style: TextStyle(
                            fontSize: 16,
                            color: Colors.blueGrey,
                          ),
                        )
                      ],
                    ),
                  ),
                ),
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            ..._buildInputField(
              "Name",
              nameController..text = category.name,
              maxLines: 1,
              maxLength: 50,
            ),
            !isCreate
                ? Row(
                    children: [
                      Text(
                        "Total product: ",
                        style: style,
                      ),
                      const SizedBox(
                        width: 10,
                      ),
                      Text(
                        "${category.countProduct ?? 0}",
                        style: style,
                      )
                    ],
            )
                : const SizedBox.shrink(),
            const SizedBox(
              height: 10,
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Text(
                  "Enable: ",
                  style: R.style.h5.copyWith(color: Colors.black),
                ),
                const SizedBox(
                  width: 10,
                ),
                ValueListenableBuilder(
                  valueListenable: enable,
                  builder: (context, value, child) =>
                      Switch(
                        value: value,
                        onChanged: (value) async {
                          if (value != category.enable) {
                            category.enable = value;
                          }
                        },
                        thumbColor: MaterialStateProperty.all(Colors.white),
                        activeTrackColor: Colors.red,
                        inactiveTrackColor: Colors.grey,
                      ),
                )
              ],
            ),
            const Expanded(child: SizedBox.shrink()),
            ConfirmButton(
              textPositive: isCreate ? "Create" : "Update",
              onPositive: () {
                category.name = nameController.text;
                context.pop();
                onSubmitCategory?.call(category, image.value);
              },
              onNegative: () {
                context.pop();
              },
            ),
            const SizedBox(
              height: 30,
            ),
          ],
        ),
      ),
    );
  }

  List<Widget> _buildInputField(
    String label,
    TextEditingController? controller, {
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
        height: 15,
      )
    ];
  }

  Widget _buildImage(File? image) {
    return SizedBox.square(
      dimension: 150,
      child: image == null
          ? const Padding(
              padding: EdgeInsets.all(100),
              child: CircularProgressIndicator(),
            )
          : image.bytes.isNullOrEmpty
              ? OnlineImage(
                  image.url,
                  fit: BoxFit.cover,
                )
              : Image.memory(
                  Uint8List.fromList(image.bytes!),
                  fit: BoxFit.cover,
                ),
    );
  }

  Future<void> pickImage() async {
    final image = await _pickImage();
    if (image == null || image.value.isNullOrEmpty) return;
    this.image.value = File.fromBytes(image.value!, name: image.key);
  }

  Future<MapEntry<String, List<int>?>?> _pickImage() async {
    try {
      FilePickerResult? result = await FilePicker.platform.pickFiles(
        type: FileType.image,
        allowMultiple: false,
      );
      if (result == null || result.files.isEmpty) return null;
      return MapEntry(result.files.single.name, result.files.single.bytes);
    } catch (e) {
      Logger.error(message: e.toString());
      return null;
    }
  }
}
