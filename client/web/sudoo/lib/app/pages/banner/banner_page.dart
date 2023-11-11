import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/banner/banner_bloc.dart';
import 'package:sudoo/domain/model/discovery/file.dart';
import 'package:sudoo/extensions/list_ext.dart';
import 'package:sudoo/utils/logger.dart';

import '../../../domain/common/Constants.dart';
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
          width: double.infinity,
          constraints: const BoxConstraints(
            minHeight: 210,
            maxHeight: 630,
          ),
          padding: const EdgeInsets.all(20),
          child: images.isEmpty
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

  Widget _buildListImage(BuildContext context, List<File>? images) {
    final List<Widget> imageWidgets =
        images.orEmpty.map<Widget>((image) => _buildImageItem(image)).toList();
    if (images == null || images.length < Constants.maxBanner) {
      imageWidgets.add(_buildAddImageButton());
    }
    return Wrap(
      spacing: 8,
      runSpacing: 10,
      children: imageWidgets,
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

  Widget _buildImageItem(File image) {
    return Stack(
      children: [
        OnlineImage(
          image.url,
          width: 150,
          height: 200,
        ),
        Positioned(
          right: 3,
          top: 3,
          child: GestureDetector(
            onTap: () {
              bloc.deleteBanner(image);
            },
            child: const Icon(Icons.cancel),
          ),
        )
      ],
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
