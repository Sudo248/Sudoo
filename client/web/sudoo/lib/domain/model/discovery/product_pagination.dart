import 'package:sudoo/domain/model/pagination.dart';

class ProductPagination<T> {
  final List<T> products;
  final Pagination pagination;

  ProductPagination(this.products, this.pagination);
}