import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/dialog/edit_category_dialog.dart';
import 'package:sudoo/app/pages/category/category_bloc.dart';
import 'package:sudoo/app/pages/category/category_item.dart';
import 'package:sudoo/domain/model/discovery/category.dart';

class CategoryPage extends BasePage<CategoryBloc> {
  CategoryPage({super.key});

  @override
  bool get enableStatePage => true;

  @override
  Widget build(BuildContext context) {
    return _buildCategories(context);
  }

  Widget _buildCategories(BuildContext context) {
    return ValueListenableBuilder(
      valueListenable: bloc.categories,
      builder: (context, value, child) => GridView.builder(
        itemCount: value.length + 1,
        padding: const EdgeInsets.all(20),
        gridDelegate: const SliverGridDelegateWithMaxCrossAxisExtent(
          maxCrossAxisExtent: 210,
          mainAxisSpacing: 30,
          crossAxisSpacing: 30,
          childAspectRatio: 3 / 4.2,
        ),
        itemBuilder: (context, index) {
          if (index < value.length) {
            return CategoryItem(
              category: value[index],
              onItemClick: (category) => _showEditCategoryDialog(
                context,
                category: category,
              ),
              upsertCategory: bloc.upsertCategory,
            );
          } else {
            return child;
          }
        },
      ),
      child: _buildAddCategory(context),
    );
  }

  Widget _buildAddCategory(BuildContext context) {
    return GestureDetector(
      onTap: () => _showEditCategoryDialog(context),
      child: Container(
        padding: const EdgeInsets.all(10.0),
        decoration: BoxDecoration(
          color: Colors.grey,
          borderRadius: BorderRadius.circular(10.0),
        ),
        child: const Center(
          child: Icon(
            Icons.add,
            size: 50,
            color: Colors.blueGrey,
          ),
        ),
      ),
    );
  }

  void _showEditCategoryDialog(BuildContext context, {Category? category}) {
    showDialog(
      context: context,
      builder: (context) => EditCategoryDialog(
        category: category,
        onSubmitCategory: bloc.upsertCategory,
      ),
    );
  }
}
