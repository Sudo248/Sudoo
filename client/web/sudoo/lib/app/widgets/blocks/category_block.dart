import 'package:flutter/material.dart';
import 'package:sudoo/extensions/list_ext.dart';

import '../../../domain/common/Constants.dart';
import '../../../domain/model/discovery/category.dart';
import '../../../domain/model/discovery/category_product.dart';
import '../../../resources/R.dart';
import '../../dialog/choose_category_dialog.dart';
import '../../model/category_callback.dart';

class CategoryBlock extends StatelessWidget {
  final String? productId;
  final ValueNotifier<List<Category>?> categories;
  final CategoryCallback callback;

  const CategoryBlock({
    super.key,
    required this.productId,
    required this.categories,
    required this.callback,
  });

  @override
  Widget build(BuildContext context) {
    return ValueListenableBuilder(
        valueListenable: categories,
        builder: (context, categories, child) {
          return Container(
            width: double.infinity,
            constraints: const BoxConstraints(
              maxHeight: 80,
            ),
            padding: const EdgeInsets.all(5),
            decoration: BoxDecoration(
              border: Border.all(color: Colors.grey),
              borderRadius: BorderRadius.circular(5),
            ),
            child: productId != null && categories == null
                ? const Center(
                    child: SizedBox.square(
                      dimension: 35,
                      child: CircularProgressIndicator(),
                    ),
                  )
                : _buildCategories(context, categories),
          );
        });
  }

  Widget _buildCategories(BuildContext context, List<Category>? categories) {
    final List<Widget> categoryWidgets = categories.orEmpty
        .map<Widget>(
          (category) => _buildCategory(category),
        )
        .toList();
    if (categories == null || categories.length < Constants.maxCategoryOfEachProduct) {
      categoryWidgets.add(
        _buildAddCategoryButton(context),
      );
    }
    return Wrap(
      spacing: 8,
      runSpacing: 10,
      children: categoryWidgets,
    );
  }

  Widget _buildAddCategoryButton(BuildContext context) {
    return GestureDetector(
      onTap: () {
        showDialogAddCategory(context);
      },
      child: DecoratedBox(
        decoration: BoxDecoration(
          border: Border.all(
            color: Colors.grey,
          ),
          borderRadius: BorderRadius.circular(5),
        ),
        child: const Icon(
          Icons.add,
          color: Colors.grey,
        ),
      ),
    );
  }

  Widget _buildCategory(Category category) {
    return DecoratedBox(
      decoration: BoxDecoration(
        border: Border.all(color: Colors.grey),
        borderRadius: BorderRadius.circular(5),
      ),
      child: Row(
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          const SizedBox(
            width: 5,
          ),
          Text(
            category.name,
            maxLines: 1,
            overflow: TextOverflow.ellipsis,
            style: R.style.h5.copyWith(
              color: Colors.black,
            ),
          ),
          const SizedBox(
            width: 2,
          ),
          const Icon(
            Icons.list_alt_rounded,
            color: Colors.blue,
          ),
          GestureDetector(
            child: const Icon(
              size: 16,
              Icons.clear_outlined,
              color: Colors.grey,
            ),
            onTap: () async {
              if (productId != null) {
                final categoryProduct = await callback.deleteCategoryToProduct(
                  CategoryProduct(
                    productId: productId!,
                    categoryId: category.categoryId,
                  ),
                );
                if (categoryProduct.categoryProductId != null) {
                  deleteCategory(category);
                }
              } else {
                deleteCategory(category);
              }
            },
          ),
          const SizedBox(
            width: 2,
          ),
        ],
      ),
    );
  }

  void showDialogAddCategory(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => ChooseCategoryDialog(
        categories: callback.getCategoriesWithout(categories.value.orEmpty),
        onPositive: (value) async {
          if (productId != null) {
            final result = await callback.upsertCategoryToProduct(
              CategoryProduct(
                productId: productId!,
                categoryId: value.categoryId,
              ),
            );
            if (result.categoryProductId != null) {
              addCategory(value);
            }
          } else {
            addCategory(value);
          }
        },
      ),
    );
  }

  void addCategory(Category category) {
    final currentCategories = categories.value.orEmpty.toList(growable: true);
    currentCategories.add(category);
    categories.value = currentCategories;
  }

  void deleteCategory(Category category) {
    final currentCategories = categories.value.orEmpty.toList(growable: true);
    currentCategories.remove(category);
    categories.value = currentCategories;
  }
}
