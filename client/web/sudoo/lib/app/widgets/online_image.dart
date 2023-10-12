import 'package:flutter/material.dart';

class OnlineImage extends StatelessWidget {
  final String src;
  final double? width, height;
  final BoxFit? fit;
  const OnlineImage(this.src, {super.key, this.height, this.width, this.fit});

  @override
  Widget build(BuildContext context) {
    return Image.network(
      src,
      height: height,
      width: width,
      loadingBuilder: (context, child, loadingProgress) {
        if (loadingProgress == null) return child;
        return const Center(
          child: CircularProgressIndicator(),
        );
      },
      errorBuilder: (context, error, stackTrace) => const Icon(Icons.image),
      fit: fit ?? BoxFit.fill,
    );
  }
}
