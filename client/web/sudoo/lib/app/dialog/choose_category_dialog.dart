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
    final size = MediaQuery.of(context).size;
    return Dialog(
      child: Container(
        padding: const EdgeInsets.all(15.0),
        constraints: BoxConstraints(
          minWidth: size.width * 0.2,
          minHeight: size.height * 0.3,
          maxWidth: size.width * 0.3,
          maxHeight: size.height * 0.5,
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(
              R.string.chooseCategory,
              style: R.style.h4_1.copyWith(
                color: Colors.black,
                fontWeight: FontWeight.bold,
              ),
            ),
            Expanded(
              child: ListView.builder(
                itemCount: widget.categories.length,
                itemBuilder: (context, index) => Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Checkbox(
                        value: category != null && category!.categoryId == widget.categories[index].categoryId,
                        onChanged: (value) {
                          if (value == true) {
                            setState(() {
                              category = widget.categories[index];
                            });
                          }
                        },
                      ),
                      Expanded(
                        child: Text(
                          widget.categories[index].name,
                          style: R.style.h5.copyWith(color: Colors.black),
                          textAlign: TextAlign.start,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            const SizedBox(
              height: 20,
            ),
            ConfirmButton(
              onPositive: () {
                if (category == null) {
                  Navigator.of(context).pop();
                } else {
                  widget.onPositive?.call(category!);
                  Navigator.of(context).pop();
                }
              },
              onNegative: () {
                Navigator.of(context).pop();
              },
            )
          ],
        ),
      ),
    );
  }
}
