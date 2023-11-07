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
    return GestureDetector(
      onTap: () => onItemClick?.call(category),
      child: Container(
        padding: const EdgeInsets.all(15),
        decoration: BoxDecoration(
          color: Colors.white,
          border: Border.all(color: Colors.grey),
          borderRadius: BorderRadius.circular(15.0),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                SizedBox.square(
                  dimension: 100,
                  child: OnlineImage(category.image),
                )
              ],
            ),
            const SizedBox(
              height: 15,
            ),
            Text(
              category.name,
              maxLines: 2,
              overflow: TextOverflow.ellipsis,
              style: R.style.h5.copyWith(
                color: Colors.black,
                fontWeight: FontWeight.bold,
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
      ),
    );
  }
}
