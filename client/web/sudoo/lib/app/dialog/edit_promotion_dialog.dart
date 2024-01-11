import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/domain/model/promotion/promotion.dart';
import 'package:sudoo/extensions/list_ext.dart';

import '../../domain/model/discovery/file.dart';
import '../../resources/R.dart';
import '../../utils/logger.dart';
import '../widgets/confirm_button.dart';
import '../widgets/online_image.dart';

class EditPromotionDialog extends StatelessWidget {
  late final Promotion promotion;
  final Future<bool> Function(Promotion, File?)? onSubmitPromotion;
  final ValueNotifier<bool> enable = ValueNotifier(true);
  final ValueNotifier<File?> image = ValueNotifier(null);
  final TextStyle style = R.style.h5.copyWith(color: Colors.black);
  final TextEditingController nameController = TextEditingController(),
      amountController = TextEditingController(),
      valueController = TextEditingController();

  EditPromotionDialog(
      {super.key, Promotion? promotion, this.onSubmitPromotion}) {
    if (promotion != null) {
      this.promotion = promotion;
      image.value = File.fromUrl(promotion.image);
      enable.value = promotion.enable ?? false;
    } else {
      this.promotion = Promotion.empty();
    }
  }

  bool get isCreate => promotion.promotionId.isEmpty;

  @override
  Widget build(BuildContext context) {
    return Dialog(
      child: Container(
        padding: const EdgeInsets.all(15.0),
        constraints: const BoxConstraints(
          minWidth: 300,
          maxWidth: 450,
          minHeight: 300 * 1.5,
          maxHeight: 450 * 1.5,
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Align(
              alignment: Alignment.center,
              child: Text(
                isCreate ? R.string.createPromotion : R.string.updatePromotion,
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
                    child: Column(
                      mainAxisSize: MainAxisSize.min,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        const Icon(
                          Icons.add,
                          size: 50,
                          color: Colors.blueGrey,
                        ),
                        const SizedBox(
                          height: 30,
                        ),
                        Text(
                          R.string.upload,
                          style: const TextStyle(
                            fontSize: 24,
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
              height: 15,
            ),
            ..._buildInputField(
              R.string.name,
              nameController..text = promotion.name,
              maxLength: 100,
              maxLines: 1,
            ),
            ..._buildInputField(
              R.string.amount,
              amountController..text = promotion.totalAmount.toString(),
              maxLines: 1,
              maxLength: 5,
              keyboardType: TextInputType.number,
            ),
            ..._buildInputField(
              R.string.value,
              valueController..text = promotion.value.toStringAsFixed(1),
              maxLines: 1,
              keyboardType: TextInputType.number,
              suffixText: "đ",
              suffixStyle: style.copyWith(fontWeight: FontWeight.bold),
            ),
            isCreate || promotion.enable != null
                ? Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      Text(
                        "${R.string.enable}: ",
                        style: R.style.h5.copyWith(color: Colors.black),
                      ),
                      const SizedBox(
                        width: 10,
                      ),
                      ValueListenableBuilder(
                        valueListenable: enable,
                        builder: (context, value, child) => Switch(
                          value: value,
                          onChanged: (value) async {
                            if (value != promotion.enable) {
                              promotion.enable = value;
                            }
                          },
                          thumbColor: MaterialStateProperty.all(Colors.white),
                          activeTrackColor: Colors.red,
                          inactiveTrackColor: Colors.grey,
                        ),
                      )
                    ],
                  )
                : const SizedBox.shrink(),
            const Expanded(child: SizedBox.shrink()),
            ConfirmButton(
              textPositive: isCreate ? R.string.create : R.string.update,
              onPositive: () {
                promotion.name = nameController.text;
                promotion.totalAmount =
                    double.parse(amountController.text).toInt();
                promotion.value = double.parse(valueController.text);
                promotion.enable = enable.value;
                context.pop();
                onSubmitPromotion?.call(promotion, image.value);
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
    String? suffixText,
    TextStyle? suffixStyle,
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
        decoration: InputDecoration(
          suffixText: suffixText,
          suffixStyle: suffixStyle,
          border: const OutlineInputBorder(),
          contentPadding: const EdgeInsets.symmetric(vertical: 8, horizontal: 8),
          counterText: "",
        ),
      ),
      const SizedBox(
        height: 20,
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
