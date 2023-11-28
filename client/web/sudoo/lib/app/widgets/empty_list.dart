import 'package:flutter/material.dart';

import '../../resources/R.dart';

class EmptyList extends StatelessWidget {
  const EmptyList({super.key});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Image.asset(
        R.drawable.imgEmptyList,
        width: 400,
        height: 300,
      ),
    );
  }
}
