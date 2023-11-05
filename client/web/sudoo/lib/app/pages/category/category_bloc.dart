import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/model/discovery/category.dart';
import 'package:sudoo/domain/model/discovery/file.dart';
import 'package:sudoo/domain/repository/category_repository.dart';
import 'package:sudoo/domain/repository/storage_repository.dart';
import 'package:sudoo/extensions/list_ext.dart';

class CategoryBloc extends BaseBloc {
  final CategoryRepository categoryRepository;
  final StorageRepository storageRepository;

  final ValueNotifier<List<Category>> categories = ValueNotifier([]);

  CategoryBloc(this.categoryRepository, this.storageRepository);

  @override
  void onDispose() {
    categories.dispose();
  }

  @override
  void onInit() {
    getCategories();
  }

  Future<void> getCategories() async {
      loadingController.showLoading();
      final result = await categoryRepository.getCategories(includeCountProduct: true);
      if (result.isSuccess) {
        categories.value = result.get();
      } else {
        showErrorMessage(result.getError());
      }
      loadingController.hideLoading();
  }

  Future<void> upsertCategory(Category category, File? image) async {
    if (category.categoryId.isEmpty && image == null) {
      showErrorMessage(Exception("Require image for category"));
      return;
    }
    loadingController.showLoading();
    if (image != null && !image.bytes.isNullOrEmpty) {
      category.image = await uploadImage(image.bytes!, imageName: image.url);
    }

    final result = await categoryRepository.upsertCategory(category);
    if (result.isSuccess) {
      final newCategory = result.get();
      final value = categories.value;
      if (category.categoryId.isEmpty) {
        value.add(newCategory);
      } else {
        final index = value.indexWhere((e) => e.categoryId == newCategory.categoryId);
        value[index] = newCategory;
      }
      categories.value = value;
      loadingController.hideLoading();
      return;
    } else {
      loadingController.hideLoading();
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  Future<String> uploadImage(List<int> bytes, {String? imageName}) async {
    final result = await storageRepository.uploadImageBytes(
      bytes,
      imageName: imageName,
    );
    if (result.isSuccess) {
      final url = result.get();
      return Future.value(url);
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }
}
