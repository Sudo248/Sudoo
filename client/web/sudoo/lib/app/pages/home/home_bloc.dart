
import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/app/model/image_callback.dart';
import 'package:sudoo/domain/model/discovery/file.dart' as domain;
import 'package:sudoo/domain/model/discovery/upsert_file.dart';

import '../../../domain/repository/storage_repository.dart';

class HomeBloc extends BaseBloc {
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

}