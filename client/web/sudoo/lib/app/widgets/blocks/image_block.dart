import 'dart:typed_data';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/app/model/image_callback.dart';
import 'package:sudoo/app/widgets/online_image.dart';
import 'package:sudoo/domain/common/Constants.dart';
import 'package:sudoo/domain/model/discovery/file.dart' as domain;
import 'package:sudoo/domain/model/discovery/upsert_file.dart';
import 'package:sudoo/extensions/list_ext.dart';
import 'package:sudoo/utils/logger.dart';

class ImageBlock extends StatelessWidget {
  final String? productId;
  final ValueNotifier<List<domain.File>?> images;
  final ImageCallback callback;

  const ImageBlock({
    super.key,
    required this.productId,
    required this.images,
    required this.callback,
  });

  @override
  Widget build(BuildContext context) {
    return ValueListenableBuilder(
      valueListenable: images,
      builder: (context, images, child) {
        return Container(
          width: double.infinity,
          constraints: const BoxConstraints(
            minHeight: 210,
            maxHeight: 630,
          ),
          padding: const EdgeInsets.all(5),
          decoration: BoxDecoration(
            border: Border.all(color: Colors.grey),
            borderRadius: BorderRadius.circular(5),
          ),
          child: productId != null && images == null
              ? const Center(
                  child: SizedBox.square(
                    dimension: 35,
                    child: CircularProgressIndicator(),
                  ),
                )
              : _buildListImage(context, images),
        );
      },
    );
  }

  Widget _buildAddImageButton() {
    return GestureDetector(
      onTap: pickImage,
      child: Container(
        width: 150,
        height: 200,
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
    );
  }

  Widget _buildListImage(BuildContext context, List<domain.File>? images) {
    final List<Widget> imageWidgets =
        images.orEmpty.map<Widget>((image) => _buildImageItem(image)).toList();
    if (images == null || images.length < Constants.maxImageOfEachProduct) {
      imageWidgets.add(_buildAddImageButton());
    }
    return Wrap(
      spacing: 8,
      runSpacing: 10,
      children: imageWidgets,
    );
  }

  Widget _buildImageItem(domain.File image) {
    return Stack(
      children: [
        image.bytes != null
            ? Image.memory(
                Uint8List.fromList(image.bytes!),
                width: 150,
                height: 200,
                fit: BoxFit.fill,
              )
            : OnlineImage(
                image.url,
                width: 150,
                height: 200,
              ),
        Positioned(
          right: 3,
          top: 3,
          child: GestureDetector(
            onTap: () {
              deleteImage(image);
            },
            child: const Icon(Icons.cancel),
          ),
        )
      ],
    );
  }

  Future<void> deleteImage(domain.File image) async {
    if (productId != null) {
      await callback.deleteImage(image).then((value) {
        final currentImages = images.value?.toList(growable: true);
        currentImages?.remove(image);
        images.value = currentImages;
      });
    } else {
      final currentImages = images.value?.toList(growable: true);
      currentImages?.remove(image);
      images.value = currentImages;
    }
  }

  Future<void> pickImage() async {
    final image = await _pickImage();
    if (image == null || image.value.isNullOrEmpty) return;
    if (productId != null) {
      await callback.uploadImage(image.value!, imageName: image.key).then((url) async {
        await callback
            .upsertImage(UpsertFile(ownerId: productId!, url: url))
            .then((image) {
          final currentImages = images.value?.toList(growable: true);
          currentImages?.add(image);
          images.value = currentImages;
        });
      });
    } else {
      final List<domain.File> currentImages;
      if (images.value != null) {
        currentImages = images.value!.toList(growable: true);
      } else {
        currentImages = [];
      }
      currentImages.add(domain.File.fromBytes(image.value!, name: image.key));
      images.value = currentImages;
    }
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
