import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/domain/common/Constants.dart';
import 'package:sudoo/domain/model/discovery/file.dart' as domain;

import '../../../resources/R.dart';
import '../../../utils/logger.dart';
import '../../pages/product/product/viewer.dart';

class ExtrasBlock extends StatelessWidget {
  final Function(Viewer, bool)? onChangeEnableViewer;
  final ValueNotifier<bool> enable3DViewer;
  final ValueNotifier<bool> enableARViewer;
  final ValueNotifier<domain.File?> sourceViewer;

  const ExtrasBlock({
    super.key,
    required this.enable3DViewer,
    required this.enableARViewer,
    required this.sourceViewer,
    this.onChangeEnableViewer,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Column(
          mainAxisSize: MainAxisSize.max,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Row(
              children: [
                Text(R.string.enable3DViewer),
                const SizedBox(
                  width: 5,
                ),
                _buildSwitch(Viewer.viewer3D, enable3DViewer)
              ],
            ),
            const SizedBox(
              height: 10,
            ),
            Row(
              children: [
                Text(R.string.enableARViewer),
                const SizedBox(
                  width: 5,
                ),
                _buildSwitch(Viewer.viewerAR, enableARViewer)
              ],
            )
          ],
        ),
        _buildChooseFile()
      ],
    );
  }

  Widget _buildSwitch(
    Viewer viewer,
    ValueNotifier<bool> value,
  ) {
    return ValueListenableBuilder(
      valueListenable: value,
      builder: (context, value, child) => Switch(
        value: value,
        onChanged: (value) => onChangeEnableViewer?.call(viewer, value),
        thumbColor: MaterialStateProperty.all(Colors.white),
        activeTrackColor: Colors.red,
        inactiveTrackColor: Colors.grey,
      ),
    );
  }

  Widget _buildChooseFile() {
    return Container(
      padding: const EdgeInsets.symmetric(
        vertical: 10,
        horizontal: 5,
      ),
      constraints: const BoxConstraints(minWidth: 80, maxWidth: 200),
      decoration: BoxDecoration(
        color: Colors.grey.shade300,
        borderRadius: BorderRadius.circular(5.0),
      ),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Expanded(
            child: ValueListenableBuilder(
              valueListenable: sourceViewer,
              builder: (context, value, child) => Text(
                _getNameSource(value?.url),
                style: R.style.h5,
              ),
            ),
          ),
          const SizedBox(
            width: 3,
          ),
          TextButton(
            onPressed: pickFile,
            style: R.buttonStyle.outlinedButtonStyle(
              backgroundColor: Colors.grey,
              padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 15),
            ),
            child: Text(
              R.string.chooseFile,
              style: R.style.h5,
            ),
          )
        ],
      ),
    );
  }

  String _getNameSource(String? url) {
    if (url == null) return "";
    if (url.contains("/")) {
      return url.split("/").last;
    } else {
      return url;
    }
  }

  Future<void> pickFile() async {
    final fileBytes = await _pickFile();
    if (fileBytes == null) return;
    sourceViewer.value = domain.File.fromBytes(fileBytes);
  }

  Future<List<int>?> _pickFile() async {
    try {
      FilePickerResult? result = await FilePicker.platform.pickFiles(
        type: FileType.custom,
        allowedExtensions: Constants.supportArFileType,
        allowMultiple: false,
      );
      if (result == null || result.files.isEmpty) return null;
      return result.files.single.bytes;
    } catch (e) {
      Logger.error(message: e.toString());
      return null;
    }
  }
}
