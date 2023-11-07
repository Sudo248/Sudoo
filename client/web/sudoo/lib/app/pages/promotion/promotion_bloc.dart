import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/model/promotion/promotion.dart';
import 'package:sudoo/extensions/list_ext.dart';

import '../../../domain/model/discovery/file.dart';
import '../../../domain/repository/product_repository.dart';
import '../../../domain/repository/storage_repository.dart';

class PromotionBloc extends BaseBloc {
  final ProductRepository productRepository;
  final StorageRepository storageRepository;

  final ValueNotifier<List<Promotion>> promotions = ValueNotifier([]);

  PromotionBloc(this.productRepository, this.storageRepository);

  @override
  void onDispose() {
    promotions.dispose();
  }

  @override
  void onInit() {
    getPromotions();
  }

  Future<void> getPromotions() async {
    loadingController.showLoading();
    final result = await productRepository.getPromotions();
    if (result.isSuccess) {
      promotions.value = result.get();
    } else {
      showErrorMessage(result.getError());
    }
    loadingController.hideLoading();
  }

  Future<bool> upsertPromotion(Promotion promotion, [File? image]) async {
    if (promotion.promotionId.isEmpty && image == null) {
      showErrorMessage(Exception("Require image for category"));
      return Future.value(false);
    }
    loadingController.showLoading();
    if (image != null && !image.bytes.isNullOrEmpty) {
      promotion.image = await uploadImage(image.bytes!, imageName: image.url);
    }

    final result = await productRepository.upsertPromotion(promotion);
    if (result.isSuccess) {
      final newCategory = result.get();
      final value = promotions.value.toList(growable: true);
      if (promotion.promotionId.isEmpty) {
        value.add(newCategory);
      } else {
        final index = value.indexWhere((e) => e.promotionId == newCategory.promotionId);
        value[index] = newCategory;
      }
      promotions.value = value;
      loadingController.hideLoading();
      return Future.value(true);
    } else {
      loadingController.hideLoading();
      showErrorMessage(result.requireError());
      return Future.value(false);
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