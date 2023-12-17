import 'package:flutter/material.dart';
import 'package:sudoo/domain/core/data_state.dart';

extension DataStatExtention on DataState {
  T? onState<T>({
    required Function() loading,
    required T Function(T data) success,
    required Function(Exception error) error,
  }) {
    if (isLoading) {
      loading();
    } else if (isSuccess) {
      return success(requireData());
    } else if (isError) {
      error(requireError());
    }
    return null;
  }

  Widget onStateWidget({
    Widget Function()? loading,
    required Widget Function(dynamic data) success,
    required Widget Function(Exception error) error,
  }) {
    if (isLoading) {
      return loading?.call() ??
          const Center(
            child: SizedBox.square(
              dimension: 50,
              child: CircularProgressIndicator(),
            ),
          );
    } else if (isSuccess) {
      return success(requireData());
    } else if (isError) {
      return error(requireError());
    }
    return const SizedBox.shrink();
  }
}
