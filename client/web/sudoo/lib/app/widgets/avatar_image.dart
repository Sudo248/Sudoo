import 'dart:typed_data';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/app/widgets/online_image.dart';
import 'package:sudoo/extensions/list_ext.dart';

import '../../domain/model/discovery/file.dart';
import '../../resources/R.dart';
import '../../utils/logger.dart';

class AvatarImage extends StatelessWidget {
  final ValueNotifier<File?> avatar;
  final double height, width;

  const AvatarImage({
    super.key,
    required this.avatar,
    this.height = 100,
    this.width = 100,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      height: height,
      width: width,
      decoration: const BoxDecoration(shape: BoxShape.circle),
      child: Stack(
        children: [
          avatar.value == null
              ? const ColoredBox(color: Colors.white)
              : avatar.value!.bytes != null
                  ? Image.memory(
                      Uint8List.fromList(avatar.value!.bytes!),
                      fit: BoxFit.cover,
                    )
                  : OnlineImage(
                      avatar.value!.url,
                      fit: BoxFit.cover,
                    ),
          Positioned(
              bottom: 0,
              child: Container(
                height: height * 0.2,
                color: R.color.backgroundNavColor,
                child: const Center(
                  child: Icon(Icons.image_outlined),
                ),
              ))
        ],
      ),
    );
  }

  Future<void> pickImage() async {
    final image = await _pickImage();
    if (image == null || image.value.isNullOrEmpty) return;
    avatar.value = File.fromBytes(image.value!, name: image.key);
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
