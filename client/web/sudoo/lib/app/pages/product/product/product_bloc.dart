import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/app/model/category_callback.dart';
import 'package:sudoo/app/model/image_callback.dart';
import 'package:sudoo/app/widgets/loading_view.dart';
import 'package:sudoo/domain/model/discovery/category.dart';
import 'package:sudoo/domain/model/discovery/category_product.dart';
import 'package:sudoo/domain/model/discovery/image.dart' as domain;
import 'package:sudoo/domain/model/discovery/upsert_image.dart';
import 'package:sudoo/domain/repository/product_repository.dart';
import 'package:sudoo/domain/repository/storage_repository.dart';
import 'package:sudoo/extensions/list_ext.dart';
import 'package:sudoo/extensions/string_ext.dart';

import '../../../../domain/model/discovery/product.dart';
import '../../../../domain/model/discovery/upsert_product.dart';
import '../../../../domain/repository/category_repository.dart';

class ProductBloc extends BaseBloc implements CategoryCallback, ImageCallback {
  final LoadingViewController loadingController = LoadingViewController();
  final ProductRepository productRepository;
  final CategoryRepository categoryRepository;
  final StorageRepository storageRepository;
  final ValueNotifier<List<domain.Image>?> images = ValueNotifier(null);
  final ValueNotifier<List<Category>?> categories = ValueNotifier(null);
  final ValueNotifier<DateTime> startDateDiscount =
      ValueNotifier(DateTime.now());
  final ValueNotifier<DateTime> endDateDiscount = ValueNotifier(DateTime.now());
  final ValueNotifier<bool> saleable = ValueNotifier(true);
  final List<Category> allCategories = List.empty(growable: true);
  final TextEditingController nameController = TextEditingController(),
      descriptionController = TextEditingController(),
      skuController = TextEditingController(),
      amountController = TextEditingController(),
      listedPriceController = TextEditingController(),
      priceController = TextEditingController(),
      discountController = TextEditingController(text: "0");

  Timer? debounce;
  String? productId;

  ProductBloc(
    this.productRepository,
    this.categoryRepository,
    this.storageRepository,
  );

  @override
  void onDispose() {}

  @override
  void onInit() {
    getCategories();
  }

  Future<void> fetchProduct(String? productId) async {
    if (productId == null) return;
    this.productId = productId;
    final result = await productRepository.getProductDetail(productId);
    if (result.isSuccess) {
      setProduct(result.get());
    } else {
      showErrorMessage(result.requireError());
    }
  }

  void setProduct(Product product) {
    images.value = product.images;
  }

  @override
  Future<String> uploadImage(List<int> bytes) async {
    final result = await storageRepository.uploadImageBytes(bytes);
    if (result.isSuccess) {
      final url = result.get();
      return Future.value(url);
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  @override
  Future<domain.Image> upsertImage(UpsertImage image) async {
    final result = await productRepository.upsertImage(image);
    if (result.isSuccess) {
      return Future.value(result.get());
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  @override
  Future<domain.Image> deleteImage(domain.Image image) async {
    if (images.value != null && images.value!.length <= 1) {
      final error = Exception("Required at least a image for each product");
      showErrorMessage(error);
      return Future.error(error);
    }
    final result = await productRepository.deleteImage(image.imageId);
    if (result.isSuccess) {
      final currentImages = images.value;
      currentImages?.remove(image);
      images.value = currentImages;
      return Future.value(result.get());
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  Future<bool> patchProduct(UpsertProduct product) async {
    final result = await productRepository.patchProduct(product);
    if (result.isSuccess) {
      return Future.value(result.get());
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  Future<List<Category>> getCategories() async {
    final result = await categoryRepository.getCategories();
    if (result.isSuccess) {
      allCategories.clear();
      allCategories.addAll(result.get());
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
    if (categories.value != null && categories.value!.length <= 1) {
      final error = Exception("Required at least a category for each product");
      showErrorMessage(error);
      return Future.error(error);
    }
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
  Future<List<Category>> getCategoriesOfProduct(String productId) async {
    final result = await productRepository.getCategories(productId);
    return result.getDataOrNull().orEmpty;
  }

  @override
  List<Category> getCategoriesWithout(List<Category> categories) {
    final categoryIds = categories.map((e) => e.categoryId).toList();
    return allCategories
        .where((element) => !categoryIds.contains(element.categoryId))
        .toList();
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

  Future<void> onSave() async {
    if (_validateProduct()) {
      loadingController.showLoading();
      if (productId != null) {
        await updateProduct();
      } else {
        await createProduct();
      }
      loadingController.hideLoading();
    }
  }

  bool _validateProduct() {
    if (categories.value == null || categories.value!.isEmpty) {
      showErrorMessage(
          Exception("Required at least a category for each product"));
      return false;
    }

    if (images.value == null || images.value!.isEmpty) {
      showErrorMessage(Exception("Required at least a image for each product"));
      return false;
    }

    if (nameController.text.isNullOrEmpty) {
      showErrorMessage(Exception("Name must be not empty"));
      return false;
    }

    if (listedPriceController.text.isNullOrEmpty ||
        priceController.text.isNullOrEmpty) {
      showErrorMessage(Exception("Price and listed price must be not empty"));
      return false;
    }

    if (amountController.text.isNullOrEmpty) {
      showErrorMessage(Exception("Amount must be not empty"));
      return false;
    }

    return true;
  }

  Future<void> createProduct() async {
    final images = await uploadImages();
    final upsertProduct = UpsertProduct(
      name: nameController.text,
      sku: skuController.text.isNullOrEmpty ? null : skuController.text,
      description: descriptionController.text,
      listedPrice: double.parse(listedPriceController.text),
      price: double.parse(priceController.text),
      discount: int.parse(discountController.text),
      startDateDiscount: startDateDiscount.value,
      endDateDiscount: endDateDiscount.value,
      amount: int.parse(amountController.text),
      saleable: saleable.value,
      images: images.map((e) => UpsertImage(url: e)).toList(),
      categoryIds: categories.value!.map((e) => e.categoryId).toList(),
    );
    final result = await productRepository.upsertProduct(upsertProduct);
    if (result.isSuccess) {
      return;
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  void clearProduct() {
    nameController.clear();
    skuController.clear();
    descriptionController.clear();
    listedPriceController.clear();
    priceController.clear();
    discountController.text = "0";
    amountController.clear();
    startDateDiscount.value = DateTime.now();
    endDateDiscount.value = DateTime.now();
    images.value?.clear();
    categories.value?.clear();
  }

  Future<List<String>> uploadImages() async {
    return await Future.wait<String>(
        images.value!.map((e) => uploadImage(e.bytes!)));
  }

  Future<void> updateProduct() async {
    final upsertProduct = UpsertProduct(
        name: nameController.text,
        description: descriptionController.text,
        listedPrice: double.parse(listedPriceController.text),
        price: double.parse(priceController.text),
        discount: int.parse(discountController.text),
        startDateDiscount: startDateDiscount.value,
        endDateDiscount: endDateDiscount.value,
        amount: int.parse(amountController.text),
        saleable: saleable.value);

    final result = await productRepository.upsertProduct(upsertProduct);
    if (result.isSuccess) {
      return;
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }
}