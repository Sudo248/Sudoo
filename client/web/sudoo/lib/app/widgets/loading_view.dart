import 'package:flutter/material.dart';

class LoadingView extends StatelessWidget {
  final Color backgroundColor;
  final Color indicatorColor;
  final LoadingViewController controller;
  const LoadingView({
    super.key,
    required this.controller,
    this.backgroundColor = const Color.fromARGB(126, 150, 150, 150),
    this.indicatorColor = Colors.white,
  });

  @override
  Widget build(BuildContext context) {
    return ValueListenableBuilder(
      valueListenable: controller.isShow,
      builder: (context, value, child) {
        return Visibility(
          visible: value,
          child: child!,
        );
      },
      child: SizedBox.expand(
        child: ColoredBox(
          color: backgroundColor,
          child: Center(
            child: Container(
              height: 100,
              width: 100,
              padding: const EdgeInsets.all(30),
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(32),
                color: const Color.fromARGB(255, 50, 50, 50),
              ),
              child: const CircularProgressIndicator(
                color: Colors.white,
              ),
            ),
          ),
        ),
      ),
    );
  }
}

class LoadingViewController {
  final ValueNotifier<bool> isShow = ValueNotifier(false);

  void showLoading() {
    if (isShow.value == false) {
      isShow.value = true;
    }
  }

  void hideLoading() {
    if (isShow.value == true) {
      isShow.value = false;
    }
  }

  void dispose() {
    isShow.dispose();
  }
}
