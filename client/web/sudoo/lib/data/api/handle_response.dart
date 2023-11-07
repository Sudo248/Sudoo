import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:sudoo/domain/core/data_state.dart';
import 'package:sudoo/domain/exceptions/api_exception.dart';
import 'package:sudoo/extensions/response_ext.dart';

import 'base_response.dart';

mixin HandleResponse {
  Future<DataState> handleResponse(
    AsyncValueGetter block, {
    TypeParser? fromJson,
  }) async {
    try {
      final response = await block();
      if (response is Response) {
        if (response.isSuccess()) {
          final baseResponse = BaseResponse.fromJson(
            response.data,
            (json) => fromJson?.call(json),
          );
          return DataState.success(baseResponse.data);
        }
        final baseResponse =
            BaseResponse.fromJson(response.data, (json) => null);
        return DataState.error(
          ApiException(
            response.statusCode ?? 0,
            baseResponse.message,
          ),
        );
      } else {
        final baseResponse = BaseResponse.fromJson(
          response.data,
          (json) => fromJson?.call(json),
        );
        if (baseResponse.success) {
          return DataState.success(baseResponse.data);
        } else {
          return DataState.error(
            ApiException(
              baseResponse.success ? 1 : 0,
              baseResponse.message,
            ),
          );
        }
      }
    } on ApiException catch (e) {
      return DataState.error<dynamic, ApiException>(e);
    } catch (e) {
      return DataState.error(Exception("Something wen wrong"));
    }
  }
}
