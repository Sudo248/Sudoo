import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/repository/product_repository.dart';
import 'package:sudoo/domain/model/discovery/file.dart' as domain;
import 'package:sudoo/domain/repository/storage_repository.dart';

class BannerBloc extends BaseBloc {

  final ProductRepository productRepository;
  final StorageRepository storageRepository;

  BannerBloc(this.productRepository, this.storageRepository);

  ValueNotifier<List<domain.File>> banners = ValueNotifier([]);

  @override
  void onDispose() {
    
  }

  @override
  void onInit() {
    getBanners();
  }

  Future<List<domain.File>> getBanners() async {
    loadingController.showLoading();
    final result = await productRepository.getBanners();
    if (result.isSuccess) {
      banners.value = result.get();
      loadingController.hideLoading();
      return Future.value(banners.value);
    } else {
      showErrorMessage(result.requireError());
      loadingController.hideLoading();
      return Future.error(result.requireError());
    }
  }

  Future<String> uploadImage(List<int> bytes, {String? imageName}) async {
    loadingController.showLoading();
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

  Future<domain.File> upsertBanner(domain.File banner) async {
    final result = await productRepository.upsertBanner(banner);
    if (result.isSuccess) {
      final currentBanner = banners.value.toList(growable: true);
      currentBanner.add(result.get());
      banners.value = currentBanner;
      showInfoMessage("Success");
      loadingController.hideLoading();
      return Future.value(result.get());
    } else {
      showErrorMessage(result.requireError());
      loadingController.hideLoading();
      return Future.error(result.requireError());
    }
  }

  Future<domain.File> deleteBanner(domain.File banner) async {
    final result = await productRepository.deleteBanner(banner.fileId);
    if (result.isSuccess) {
      final currentImages = banners.value;
      currentImages.remove(banner);
      banners.value = currentImages;
      showInfoMessage("Success");
      return Future.value(result.get());
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }

}