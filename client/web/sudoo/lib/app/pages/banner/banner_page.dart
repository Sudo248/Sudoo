import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/banner/banner_bloc.dart';
import 'package:sudoo/domain/model/discovery/file.dart';
import 'package:sudoo/extensions/list_ext.dart';
import 'package:sudoo/utils/logger.dart';

import '../../widgets/online_image.dart';

class BannerPage extends BasePage<BannerBloc> {
  @override
  bool get enableStatePage => true;

  BannerPage({super.key});

  @override
  Widget build(BuildContext context) {
    return ValueListenableBuilder(
      valueListenable: bloc.banners,
      builder: (context, images, child) {
        return Container(
          padding: const EdgeInsets.all(20),
          child: _buildListImage(context, images, child!),
        );
      },
      child: _buildAddImageButton(),
    );
  }

  Widget _buildListImage(BuildContext context, List<File> images, Widget addImage) {
    return GridView.builder(
        padding: const EdgeInsets.all(20),
        itemCount: images.length + 1,
        gridDelegate: const SliverGridDelegateWithMaxCrossAxisExtent(
          maxCrossAxisExtent: 400,
          mainAxisSpacing: 50,
          crossAxisSpacing: 50,
          childAspectRatio: 5 / 3,
        ),
        itemBuilder: (context, index) {
          if (index < images.length) {
            return _buildImageItem(images[index]);
          } else {
            return addImage;
          }
        },
      );
  }

  Widget _buildAddImageButton() {
    return GestureDetector(
      onTap: pickImage,
      child: Container(
        padding: const EdgeInsets.all(10.0),
        decoration: BoxDecoration(
          color: Colors.grey,
          borderRadius: BorderRadius.circular(10.0),
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

  Widget _buildImageItem(File image) {
    return DecoratedBox(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10),
        border: Border.all(color: Colors.grey, width: 0.5),
      ),
      child: Stack(
        children: [
          OnlineImage(
            image.url,
          ),
          Positioned(
            right: 3,
            top: 3,
            child: GestureDetector(
              onTap: () {
                bloc.deleteBanner(image);
              },
              child: const Icon(
                Icons.cancel,
                color: Colors.grey,
              ),
            ),
          )
        ],
      ),
    );
  }

  Future<void> pickImage() async {
    final image = await _pickImage();
    if (image == null || image.value.isNullOrEmpty) return;
    await bloc
        .uploadImage(image.value!, imageName: image.key)
        .then((url) async {
      await bloc.upsertBanner(File.fromUrl(url));
    });
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
