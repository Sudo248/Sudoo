import 'package:flutter/material.dart';

class OnlineImage extends StatelessWidget {
  final String src;
  const OnlineImage(this.src, {super.key});

  @override
  Widget build(BuildContext context) {
    return Image.network(
      src,
      loadingBuilder: (context, child, loadingProgress) {
        if (loadingProgress == null) return child;
        return const Center(
          child: CircularProgressIndicator(),
        );
      },
      errorBuilder: (context, error, stackTrace) => const Icon(Icons.image),
      fit: BoxFit.fill,
    );
  }
}
