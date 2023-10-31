import 'package:sudoo/data/api/discovery/discovery_api_service.dart';
import 'package:sudoo/data/api/discovery/request/upsert_file_request.dart';
import 'package:sudoo/data/api/discovery/request/upsert_product_request.dart';
import 'package:sudoo/data/api/handle_response.dart';
import 'package:sudoo/data/dto/discovery/category_dto.dart';
import 'package:sudoo/data/dto/discovery/category_product_dto.dart';
import 'package:sudoo/data/dto/discovery/file_dto.dart';
import 'package:sudoo/data/dto/discovery/product_pagination_dto.dart';
import 'package:sudoo/data/dto/discovery/upsert_product_dto.dart';
import 'package:sudoo/domain/model/discovery/category_product.dart';
import 'package:sudoo/domain/model/discovery/file.dart';
import 'package:sudoo/domain/model/discovery/product.dart';
import 'package:sudoo/domain/model/discovery/product_info.dart';
import 'package:sudoo/domain/model/discovery/upsert_file.dart';
import 'package:sudoo/domain/model/discovery/upsert_product.dart';
import 'package:sudoo/domain/repository/product_repository.dart';

import '../../domain/core/data_state.dart';
import '../../domain/model/discovery/category.dart';
import '../../domain/model/discovery/product_pagination.dart';
import '../dto/discovery/product_dto.dart';
import '../dto/discovery/product_info_dto.dart';

class ProductRepositoryImpl with HandleResponse implements ProductRepository {
  final DiscoveryService discoveryService;

  const ProductRepositoryImpl(this.discoveryService);

  @override
  Future<DataState<String, Exception>> deleteProduct(String productId) async {
    final response = await handleResponse(
      () => discoveryService.deleteProduct(productId),
      fromJson: (json) => json as String?
    );
    if (response.isSuccess) {
      return DataState.success(response.get() ?? "");
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<Product, Exception>> getProductDetail(
    String identify,
  ) async {
    final response = await handleResponse(
      () => discoveryService.getProductDetail(identify),
      fromJson: (json) => ProductDto.fromJson(json as Map<String, dynamic>),
    );

    if (response.isSuccess) {
      return DataState.success((response.get() as ProductDto).toProduct());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<ProductPagination<ProductInfo>, Exception>> getProducts(
    int offset,
    int limit,
  ) async {
    final response = await handleResponse(
      () => discoveryService.getProducts(offset, limit),
      fromJson: (json) => ProductPaginationDto.fromJson(
        json as Map<String, dynamic>,
        (json) => ProductInfoDto.fromJson(json as Map<String, dynamic>),
      ),
    );

    if (response.isSuccess) {
      final pagination = response.get() as ProductPaginationDto;
      return DataState.success(
        pagination.toProductPagination(
          converter: (e) => (e as ProductInfoDto).toProductInfo(),
        ),
      );
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<dynamic, Exception>> patchProduct(
    UpsertProduct product,
  ) async {
    final response = await handleResponse(
      () => discoveryService.patchProduct(product.toUpsertProductRequest()),
    );
    if (response.isSuccess) {
      return DataState.success(true);
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<UpsertProduct, Exception>> upsertProduct(
    UpsertProduct product,
  ) async {
    final response = await handleResponse(
      () => discoveryService.upsertProduct(product.toUpsertProductRequest()),
      fromJson: (json) =>
          UpsertProductDto.fromJson(json as Map<String, dynamic>),
    );
    if (response.isSuccess) {
      return DataState.success(
          (response.get() as UpsertProductDto).toUpsertProduct());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<List<Category>, Exception>> getCategories(
      String productId) async {
    final response = await handleResponse(
        () => discoveryService.getCategoriesByProductId(productId),
        fromJson: (json) => (json as List<dynamic>)
            .map(
              (e) => CategoryDto.fromJson(e as Map<String, dynamic>),
            )
            .toList());

    if (response.isSuccess) {
      final categories = response.get() as List<CategoryDto>;
      return DataState.success(categories.map((e) => e.toCategory()).toList());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<CategoryProduct, Exception>> deleteCategoryOfProduct(
      CategoryProduct categoryProduct) async {
    final response = await handleResponse(
        () => discoveryService.deleteCategoryToProduct(
            categoryProduct.productId, categoryProduct.categoryId),
        fromJson: (json) =>
            CategoryProductDto.fromJson(json as Map<String, dynamic>));

    if (response.isSuccess) {
      final categoryProductDto = response.get() as CategoryProductDto;
      return DataState.success(categoryProductDto.toCategoryProduct());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<CategoryProduct, Exception>> upsertCategoryToProduct(
      CategoryProduct categoryProduct) async {
    final response = await handleResponse(
        () => discoveryService.upsertCategoryToProduct(
            categoryProduct.productId, categoryProduct.categoryId),
        fromJson: (json) =>
            CategoryProductDto.fromJson(json as Map<String, dynamic>));

    if (response.isSuccess) {
      final categoryProductDto = response.get() as CategoryProductDto;
      return DataState.success(categoryProductDto.toCategoryProduct());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<File, Exception>> deleteImage(String imageId) async {
    final response = await handleResponse(
      () => discoveryService.deleteImage(imageId),
      fromJson: (json) => FileDto.fromJson(json as Map<String, dynamic>),
    );
    if (response.isSuccess) {
      return DataState.success((response.get() as FileDto).toFile());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<File, Exception>> upsertImage(
    UpsertFile upsertImage,
  ) async {
    final response = await handleResponse(
      () => discoveryService.upsertImage(upsertImage.toUpsertImageRequest()),
      fromJson: (json) => FileDto.fromJson(json as Map<String, dynamic>),
    );
    if (response.isSuccess) {
      return DataState.success((response.get() as FileDto).toFile());
    } else {
      return DataState.error(response.getError());
    }
  }
}
