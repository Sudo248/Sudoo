
import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/app/model/image_callback.dart';
import 'package:sudoo/domain/model/discovery/file.dart' as domain;
import 'package:sudoo/domain/model/discovery/upsert_file.dart';

import '../../../domain/repository/storage_repository.dart';

class HomeBloc extends BaseBloc implements ImageCallback{
  final ValueNotifier<List<domain.File>?> images = ValueNotifier(null);
  final StorageRepository storageRepository;

  HomeBloc(this.storageRepository);

  @override
  void onDispose() {
    // TODO: implement onDispose
  }

  @override
  void onInit() {
    // TODO: implement onInit
  }

  Future<void> upload() async {
    final image = images.value!.first;
    final result = await uploadImage(image.bytes!);
    print(result);
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
  Future<domain.File> deleteImage(domain.File image) {
    // TODO: implement deleteImage
    throw UnimplementedError();
  }

  @override
  Future<domain.File> upsertImage(UpsertFile image) {
    // TODO: implement upsertImage
    throw UnimplementedError();
  }


}