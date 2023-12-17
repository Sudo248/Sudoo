import 'package:flutter/material.dart';

import '../../domain/model/discovery/category.dart';
import '../../resources/R.dart';
import '../widgets/confirm_button.dart';

class ChooseCategoryDialog extends StatefulWidget {
  final List<Category> categories;
  final ValueSetter<Category>? onPositive;

  const ChooseCategoryDialog({
    super.key,
    required this.categories,
    this.onPositive,
  });

  @override
  State<ChooseCategoryDialog> createState() => _ChooseCategoryDialogState();
}

class _ChooseCategoryDialogState extends State<ChooseCategoryDialog> {
  Category? category;

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.sizeOf(context);
    return Container(
      height: size.height * 0.6,
      width: size.width * 0.3,
      padding: const EdgeInsets.all(10.0),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10.0),
      ),
      child: Column(
        children: [
          Expanded(
            child: ListView.builder(
              itemCount: widget.categories.length,
              itemBuilder: (context, index) => Padding(
                padding: const EdgeInsets.all(8.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(
                      widget.categories[index].name,
                      style: R.style.h5,
                    ),
                    Checkbox(
                      value: category != null &&
                          category!.categoryId ==
                              widget.categories[index].categoryId,
                      onChanged: (value) {
                        if (value == true) {
                          setState(() {
                            category = widget.categories[index];
                          });
                        }
                      },
                    ),
                  ],
                ),
              ),
            ),
          ),
          const SizedBox(
            height: 10,
          ),
          ConfirmButton(
            onPositive: () {
              if (category == null) Navigator.of(context).pop();
              widget.onPositive?.call(category!);
            },
            onNegative: () {
              Navigator.of(context).pop();
            },
          )
        ],
      ),
    );
  }
}
