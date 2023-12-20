import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/data/api/discovery/product_api_service.dart';
import 'package:sudoo/data/api/user/user_api_service.dart';
import 'package:sudoo/domain/repository/product_repository.dart';
import 'package:sudoo/domain/repository/recommend_repository.dart';
import 'package:sudoo/domain/repository/user_repository.dart';

import '../../../domain/model/model/model.dart';

class ModelBloc extends BaseBloc {
  final RecommendRepository recommendRepository;
  final ProductRepository productRepository;
  final UserRepository userRepository;

  ModelBloc(this.recommendRepository, this.productRepository, this.userRepository);

  final ValueNotifier<List<Model>> models = ValueNotifier([]);

  @override
  void onDispose() {
    models.dispose();
  }

  @override
  void onInit() {
    getAllModel();
  }

  Future getAllModel() async {
    loadingController.showLoading();
    final response = await recommendRepository.getAllModels();
    if (response.isSuccess) {
      models.value = response.get();
    } else {
      showErrorMessage(response.getError());
    }
    loadingController.hideLoading();
  }

  Future syncUserToRecommendService() async {
    loadingController.showLoading();
    final response = await userRepository.syncUserToRecommendService();
    if (response.isSuccess) {
      final total = response.get().total;
      showInfoMessage("Syncing $total users to recommend service");
    } else {
      showErrorMessage(response.getError());
    }
    loadingController.hideLoading();
  }

  Future syncProductRecommendService() async {
    loadingController.showLoading();
    final response = await productRepository.syncProductToRecommendService();
    if (response.isSuccess) {
      final total = response.get().total;
      showInfoMessage("Syncing $total products to recommend service");
    } else {
      showErrorMessage(response.getError());
    }
    loadingController.hideLoading();
  }

  Future syncReviewRecommendService() async {
    loadingController.showLoading();
    final response = await productRepository.syncReviewToRecommendService();
    if (response.isSuccess) {
      final total = response.get().total;
      showInfoMessage("Syncing $total reviews to recommend service");
    } else {
      showErrorMessage(response.getError());
    }
    loadingController.hideLoading();
  }
}