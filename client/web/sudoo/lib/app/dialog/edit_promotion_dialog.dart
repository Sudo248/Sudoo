import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/domain/model/promotion/promotion.dart';
import 'package:sudoo/extensions/list_ext.dart';

import '../../domain/model/discovery/file.dart';
import '../../resources/R.dart';
import '../../utils/logger.dart';
import '../widgets/confirm_button.dart';
import '../widgets/online_image.dart';

class EditPromotionDialog extends StatelessWidget {
  late final Promotion promotion;
  final Function(Promotion, File?)? onSubmitPromotion;
  final ValueNotifier<bool> enable = ValueNotifier(false);
  final ValueNotifier<File?> image = ValueNotifier(null);

  EditPromotionDialog({super.key, Promotion? promotion, this.onSubmitPromotion}) {
    if (promotion != null) {
      this.promotion = promotion;
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
          minHeight: 650,
          maxWidth: 350,
          maxHeight: 750,
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Text(
              isCreate ? "Create promotion" : "Edit promotion",
              style: R.style.h4_1.copyWith(
                color: Colors.black,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            ValueListenableBuilder(
              valueListenable: image,
              builder: (context, value, child) {
                if (isCreate) {
                  return value == null ? child! : _buildImage(value);
                } else {
                  return _buildImage(value!);
                }
              },
              child: Container(
                width: 300,
                height: 300,
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
                      size: 50,
                      color: Colors.blueGrey,
                    ),
                    SizedBox(
                      height: 30,
                    ),
                    Text(
                      "Upload",
                      style: TextStyle(
                        fontSize: 24,
                        color: Colors.blueGrey,
                      ),
                    )
                  ],
                ),
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            TextFormField(
              initialValue: promotion.name,
              maxLines: 1,
              maxLength: 50,
              style: R.style.h5,
              decoration: const InputDecoration(hintText: "Name promotion", border: OutlineInputBorder()),
              onChanged: (value) => promotion.name = value,
            ),
            const SizedBox(
              height: 10,
            ),
            isCreate || promotion.enable != null
                ? ValueListenableBuilder(
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
                : const SizedBox.shrink(),
            const SizedBox(
              height: 10,
            ),
            ConfirmButton(
              textPositive: isCreate ? "Create" : "Update",
              onPositive: () {
                onSubmitPromotion?.call(promotion, image.value);
              },
            )
          ],
        ),
      ),
    );
  }

  Widget _buildImage(File image) {
    return SizedBox.square(
      dimension: 300,
      child: image.bytes.isNullOrEmpty
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
