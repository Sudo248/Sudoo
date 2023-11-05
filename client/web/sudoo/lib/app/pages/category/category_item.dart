import 'package:flutter/material.dart';
import 'package:sudoo/app/widgets/online_image.dart';
import 'package:sudoo/domain/model/discovery/category.dart';

import '../../../resources/R.dart';

class CategoryItem extends StatelessWidget {
  final Category category;
  final ValueSetter<Category>? onItemClick;

  const CategoryItem({super.key, required this.category, this.onItemClick});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(15),
      color: Colors.white,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          AspectRatio(
            aspectRatio: 1.0,
            child: OnlineImage(category.image),
          ),
          const SizedBox(
            height: 15,
          ),
          Text(
            category.name,
            maxLines: 2,
            style: R.style.h5.copyWith(
              color: Colors.black,
            ),
          ),
          const SizedBox(
            height: 10,
          ),
          category.countProduct != null
              ? Text(
                  "Total product: ${category.countProduct}",
                  maxLines: 1,
                  style: R.style.h5.copyWith(
                    color: Colors.black,
                  ),
                )
              : const SizedBox.shrink(),
        ],
      ),
    );
  }
}
