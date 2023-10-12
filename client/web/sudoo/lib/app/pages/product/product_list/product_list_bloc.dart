import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/app/model/category_callback.dart';
import 'package:sudoo/app/model/product_info_action_callback.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_data_source.dart';
import 'package:sudoo/app/widgets/loading_view.dart';
import 'package:sudoo/domain/model/discovery/category.dart';
import 'package:sudoo/domain/model/discovery/upsert_product.dart';
import 'package:sudoo/domain/repository/category_repository.dart';
import 'package:sudoo/domain/repository/product_repository.dart';
import 'package:sudoo/extensions/list_ext.dart';

import '../../../../domain/model/discovery/category_product.dart';
import '../../../routes/app_routes.dart';

class ProductListBloc extends BaseBloc implements CategoryCallback, ProductInfoActionCallback {
  final ProductRepository productRepository;
  final CategoryRepository categoryRepository;
  late ProductListDataSource productDataSource;
  final LoadingViewController loadingController = LoadingViewController();
  final ValueNotifier<int> totalProducts = ValueNotifier(0);
  final List<Category> categories = List.empty(growable: true);

  int _offset = 0;
  final int _limit = 20;

  ProductListBloc(
    this.productRepository,
    this.categoryRepository,
  ) {
    productDataSource = ProductListDataSource(
      loadMore: loadMore,
      categoryCallback: this,
      productActionCallback: this,
      patchProduct: patchProduct,
    );
  }

  @override
  void onDispose() {
    productDataSource.dispose();
  }

  @override
  void onInit() {
    getCategories();
    loadMore();
  }

  Future<void> loadMore() async {
    final result = await productRepository.getProducts(_offset, _limit);
    if (result.isSuccess) {
      final pagination = result.get();
      productDataSource.addProducts(pagination.products);
      _offset = pagination.pagination.offset;
      totalProducts.value = pagination.pagination.total;
    } else {
      showErrorMessage(result.requireError());
    }
  }

  Future<void> navigateToProductDetail(String productId) async {
    await navigator.navigateTo(AppRoutes.product, arguments: productId);
  }

  Future<bool> patchProduct(UpsertProduct product) async {
    loadingController.showLoading();
    final result = await productRepository.patchProduct(product);
    loadingController.hideLoading();
    if (result.isSuccess) {
      return Future.value(result.get());
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  @override
  Future<List<Category>> getCategoriesOfProduct(String productId) async {
    final result = await productRepository.getCategories(productId);
    return result.getDataOrNull().orEmpty;
  }

  Future<List<Category>> getCategories() async {
    final result = await categoryRepository.getCategories();
    if (result.isSuccess) {
      categories.clear();
      categories.addAll(result.get());
      return Future.value(result.get());
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  @override
  Future<CategoryProduct> upsertCategoryToProduct(
    CategoryProduct categoryProduct,
  ) async {
    final result =
        await productRepository.upsertCategoryToProduct(categoryProduct);
    if (result.isSuccess) {
      return Future.value(result.get());
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  @override
  Future<CategoryProduct> deleteCategoryToProduct(
    CategoryProduct categoryProduct,
  ) async {
    final result =
        await productRepository.deleteCategoryOfProduct(categoryProduct);
    if (result.isSuccess) {
      return Future.value(result.get());
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  @override
  List<Category> getCategoriesWithout(List<Category> categories) {
    final categoryIds = categories.map((e) => e.categoryId).toList();
    return this
        .categories
        .where((element) => !categoryIds.contains(element.categoryId))
        .toList();
  }

  @override
  Future<void> deleteProduct(String productId) async {
    final result = await productRepository.deleteProduct(productId);
    if (result.isSuccess) {
      productDataSource.deleteProduct(result.get());
    } else {
      showErrorMessage(result.requireError());
    }
  }

  @override
  void manageImages() {
  }

  @override
  Future<void> viewDetailProduct(String productId) async {
    navigateToProductDetail(productId);
  }
}
