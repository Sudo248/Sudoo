import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_data_source.dart';
import 'package:sudoo/app/widgets/loading_view.dart';
import 'package:sudoo/domain/model/discovery/category.dart';
import 'package:sudoo/domain/model/discovery/product.dart';
import 'package:sudoo/domain/model/discovery/supplier.dart';
import 'package:sudoo/domain/model/pagination.dart';

class ProductListBloc extends BaseBloc {
  late Pagination<Product> products;
  late ProductListDataSource productDataSource;
  final LoadingViewController loadingController = LoadingViewController();

  ProductListBloc() {
    products = Pagination.first(initData: mockData());
    productDataSource = ProductListDataSource(loadMore: loadMore);
    productDataSource.setProducts(mockData());
  }

  @override
  void onDispose() {
    productDataSource.dispose();
  }

  @override
  void onInit() {

  }

  Future<void> loadMore() async {

  }

  List<Product> mockData() {
    return List.generate(
      10,
      (index) => Product(
        "productId $index",
        "product $index",
        "description $index",
        "sku $index",
        [
          "https://www.dungplus.com/wp-content/uploads/2019/12/girl-xinh-1-480x600.jpg"
        ],
        [
          Category(
            "categoryId $index",
            "category name$index",
            "https://www.dungplus.com/wp-content/uploads/2019/12/girl-xinh-1-480x600.jpg",
          ),
          Category(
            "categoryId $index",
            "category name$index",
            "https://www.dungplus.com/wp-content/uploads/2019/12/girl-xinh-1-480x600.jpg",
          ),
          Category(
            "categoryId $index",
            "category name$index",
            "https://www.dungplus.com/wp-content/uploads/2019/12/girl-xinh-1-480x600.jpg",
          ),

        ],
        Supplier(
          name: "Supplier name $index",
          avatar:
              "https://www.dungplus.com/wp-content/uploads/2019/12/girl-xinh-1-480x600.jpg",
        ),
        10000,
        10,
        20,
        4.6,
        10000,
        20,
        DateTime.now(),
        DateTime.now(),
        index % 2 == 0
      ),
    );
  }
}
