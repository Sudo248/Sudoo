import 'dart:math';

import 'package:flutter/material.dart';
import 'package:sudoo/data/config/api_config.dart';

class OnlineImage extends StatelessWidget {
  final String src;
  final double? width, height;
  final BoxFit? fit;
  const OnlineImage(this.src, {super.key, this.height, this.width, this.fit});

  @override
  Widget build(BuildContext context) {
    String imageUrl = src;
    if (!src.startsWith("http")) {
      imageUrl = "${ApiConfig.baseUrl}/storage/images/$src";
    }
    return Image.network(
      imageUrl,
      height: height,
      width: width,
      loadingBuilder: (context, child, loadingProgress) {
        if (loadingProgress == null) return child;
        return const Center(
          child: CircularProgressIndicator(),
        );
      },
      errorBuilder: (context, error, stackTrace) => SizedBox(
        height: height,
        width: width,
        child: Icon(
          Icons.error,
          color: Colors.red,
          size: width != null ? width! / 2 : null,
        ),
      ),
      fit: fit ?? BoxFit.fill,
    );
  }
}
