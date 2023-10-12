import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/home/home_bloc.dart';
import 'package:sudoo/app/widgets/blocks/image_block.dart';

class HomePage extends BasePage<HomeBloc> {

  HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Center(child: Column(
      children: [
        ImageBlock(productId: null, images: bloc.images, callback: bloc),
        TextButton(onPressed: () {
          bloc.upload();
        }, child: Text("upload"))
      ],
    ));
  }
}