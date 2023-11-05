import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/app/model/category_callback.dart';
import 'package:sudoo/app/model/image_callback.dart';
import 'package:sudoo/app/pages/product/product/viewer.dart';
import 'package:sudoo/domain/model/discovery/category.dart';
import 'package:sudoo/domain/model/discovery/category_product.dart';
import 'package:sudoo/domain/model/discovery/extras.dart';
import 'package:sudoo/domain/model/discovery/file.dart' as domain;
import 'package:sudoo/domain/model/discovery/upsert_file.dart';
import 'package:sudoo/domain/repository/product_repository.dart';
import 'package:sudoo/domain/repository/storage_repository.dart';
import 'package:sudoo/extensions/list_ext.dart';
import 'package:sudoo/extensions/string_ext.dart';

import '../../../../domain/model/discovery/product.dart';
import '../../../../domain/model/discovery/upsert_product.dart';
import '../../../../domain/repository/category_repository.dart';

class ProductBloc extends BaseBloc implements CategoryCallback, ImageCallback {
  final ProductRepository productRepository;
  final CategoryRepository categoryRepository;
  final StorageRepository storageRepository;
  final List<Category> allCategories = List.empty(growable: true);

  final ValueNotifier<List<domain.File>?> images = ValueNotifier(null);
  final ValueNotifier<List<Category>?> categories = ValueNotifier(null);

  final TextEditingController nameController = TextEditingController(),
      descriptionController = TextEditingController(),
      skuController = TextEditingController(),
      amountController = TextEditingController(),
      listedPriceController = TextEditingController(),
      priceController = TextEditingController(),
      discountController = TextEditingController(text: "0"),
      weightController = TextEditingController(),
      heightController = TextEditingController(),
      widthController = TextEditingController(),
      lengthController = TextEditingController();

  final ValueNotifier<DateTime?> startDateDiscount = ValueNotifier(null);
  final ValueNotifier<DateTime?> endDateDiscount = ValueNotifier(null);
  final ValueNotifier<bool> saleable = ValueNotifier(true);

  // extras
  final ValueNotifier<bool> enable3DViewer = ValueNotifier(false);
  final ValueNotifier<bool> enableArViewer = ValueNotifier(false);
  final ValueNotifier<domain.File?> sourceViewer = ValueNotifier(null);

  bool isSaved = false;

  Timer? debounce;
  String? productId;

  ProductBloc(
    this.productRepository,
    this.categoryRepository,
    this.storageRepository,
  );

  @override
  void onDispose() {
    loadingController.dispose();
    images.dispose();
    categories.dispose();
    startDateDiscount.dispose();
    endDateDiscount.dispose();
    saleable.dispose();
    nameController.dispose();
    descriptionController.dispose();
    skuController.dispose();
    amountController.dispose();
    listedPriceController.dispose();
    priceController.dispose();
    discountController.dispose();
    enable3DViewer.dispose();
    enableArViewer.dispose();
    sourceViewer.dispose();
    weightController.dispose();
    heightController.dispose();
    widthController.dispose();
    lengthController.dispose();
  }

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
    categories.value = product.categories;
    nameController.text = product.name;
    skuController.text = product.sku;
    descriptionController.text = product.description;
    priceController.text = product.price.toStringAsFixed(1);
    listedPriceController.text = product.listedPrice.toStringAsFixed(1);
    discountController.text = product.discount.toString();
    startDateDiscount.value = product.startDateDiscount;
    endDateDiscount.value = product.endDateDiscount;
    amountController.text = product.amount.toString();
    weightController.text = product.weight.toString();
    heightController.text = product.height.toString();
    widthController.text = product.width.toString();
    lengthController.text = product.length.toString();
    enable3DViewer.value = product.extras?.enable3DViewer ?? false;
    enableArViewer.value = product.extras?.enableArViewer ?? false;
    sourceViewer.value = domain.File.fromUrl(product.extras?.source);
    saleable.value = product.saleable;
  }

  @override
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

  @override
  Future<domain.File> upsertImage(UpsertFile image) async {
    final result = await productRepository.upsertImage(image);
    if (result.isSuccess) {
      return Future.value(result.get());
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  @override
  Future<domain.File> deleteImage(domain.File image) async {
    if (images.value != null && images.value!.length <= 1) {
      final error = Exception("Required at least a image for each product");
      showErrorMessage(error);
      return Future.error(error);
    }
    final result = await productRepository.deleteImage(image.fileId);
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
      isSaved = true;
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
    String? extraSource = sourceViewer.value?.url;
    if (sourceViewer.value != null &&
        !sourceViewer.value!.bytes.isNullOrEmpty) {
      extraSource =
          await uploadFile(sourceViewer.value!.bytes!, sourceViewer.value?.url);
    }
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
      weight: double.parse(weightController.text).toInt(),
      height: double.parse(heightController.text).toInt(),
      width: double.parse(widthController.text).toInt(),
      length: double.parse(lengthController.text).toInt(),
      images: images.map((e) => UpsertFile(url: e)).toList(),
      categoryIds: categories.value!.map((e) => e.categoryId).toList(),
      extras: Extras(
        enable3DViewer: enable3DViewer.value,
        enableArViewer: enableArViewer.value,
        source: extraSource,
      ),
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
    startDateDiscount.value = null;
    endDateDiscount.value = null;
    images.value?.clear();
    categories.value?.clear();
    enable3DViewer.value = false;
    enableArViewer.value = false;
    sourceViewer.value = null;
    weightController.clear();
    heightController.clear();
    widthController.clear();
    lengthController.clear();
  }

  Future<List<String>> uploadImages() async {
    return await Future.wait<String>(
        images.value!.map((e) => uploadImage(e.bytes!, imageName: e.url)));
  }

  Future<String> uploadFile(List<int> bytes, String? fileName) async {
    final result =
        await storageRepository.uploadFileBytes(bytes, fileName: fileName);
    if (result.isSuccess) {
      final url = result.get();
      return Future.value(url);
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  Future<void> updateProduct() async {
    String? extraSource = sourceViewer.value?.url;
    if (sourceViewer.value != null &&
        !sourceViewer.value!.bytes.isNullOrEmpty) {
      extraSource =
          await uploadFile(sourceViewer.value!.bytes!, sourceViewer.value?.url);
    }
    final upsertProduct = UpsertProduct(
      productId: productId,
      name: nameController.text,
      description: descriptionController.text,
      listedPrice: double.parse(listedPriceController.text),
      price: double.parse(priceController.text),
      discount: int.parse(discountController.text),
      startDateDiscount: startDateDiscount.value,
      endDateDiscount: endDateDiscount.value,
      amount: int.parse(amountController.text),
      saleable: saleable.value,
      weight: int.parse(weightController.text),
      height: int.parse(heightController.text),
      width: int.parse(widthController.text),
      length: int.parse(lengthController.text),
      extras: Extras(
        enable3DViewer: enable3DViewer.value,
        enableArViewer: enableArViewer.value,
        source: extraSource,
      ),
    );

    final result = await productRepository.upsertProduct(upsertProduct);
    if (result.isSuccess) {
      return;
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

  void onChangeEnableViewer(Viewer viewer, bool value) {
    if (viewer == Viewer.viewer3D) {
      enable3DViewer.value = value;
    } else {
      enableArViewer.value = value;
    }
  }

  void onChangeSaleStatus(bool saleable) {
    this.saleable.value = saleable;
  }
}
