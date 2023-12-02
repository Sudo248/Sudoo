import 'package:flutter/material.dart';
import 'package:qr_flutter/qr_flutter.dart';
import 'package:sudoo/app/widgets/online_image.dart';
import 'package:sudoo/domain/common/Constants.dart';

import '../../resources/R.dart';

class QrDialog extends StatelessWidget {
  final String data;
  final GlobalKey _qrKey = GlobalKey();
  final Function(GlobalKey)? onDownload;
  QrDialog({super.key, required this.data, this.onDownload});

  @override
  Widget build(BuildContext context) {
    return Dialog(
      child: Container(
        padding: const EdgeInsets.all(15.0),
        constraints: const BoxConstraints(
          minWidth: 200,
          maxWidth: 250,
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const SizedBox(
              height: 10,
            ),
            Text(
              "QR for order",
              style: R.style.h4_1.copyWith(
                color: Colors.black,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            // RepaintBoundary(
            //   key: _qrKey,
            //   child: QrImageView(
            //     data: data,
            //     size: 200,
            //     version: QrVersions.auto,
            //     errorCorrectionLevel: QrErrorCorrectLevel.H,
            //     gapless: false,
            //     backgroundColor: Colors.white,
            //   ),
            // ),
            SizedBox.square(
              dimension: 200,
              child: OnlineImage(
                "${Constants.createQrUrl}$data",
                width: 200,
                height: 200,
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            onDownload != null ? FilledButton(
              style: R.buttonStyle.filledButtonStyle(),
              onPressed: () {
                Navigator.pop(context);
                onDownload?.call(_qrKey);
              },
              child: Row(
                children: [
                  const Icon(
                    Icons.download,
                    color: Colors.white,
                  ),
                  const SizedBox(
                    width: 3,
                  ),
                  Text(
                    R.string.download,
                    style: R.style.h5,
                  ),
                ],
              ),
            ) : const SizedBox.shrink(),
            const SizedBox(
              height: 10,
            ),
          ],
        ),
      ),
    );
  }
}
