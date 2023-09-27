import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_bloc.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_data_table.dart';


class ProductListPage extends BasePage<ProductListBloc> {
  ProductListPage({super.key});

  @override
  Widget build(BuildContext context) {
    // final itemHeight = MediaQuery.sizeOf(context).height * 0.25;
    // return StreamBuilder(
    //   stream: bloc.products.stream,
    //   builder: (context, snapshot) {
    //     if (snapshot.data == null || snapshot.data!.length <= 0)
    //       return SizedBox.shrink();
    //
    //     return ListView.separated(
    //       itemBuilder: (context, index) => ProductListItem(
    //         product: snapshot.data![index],
    //         height: itemHeight,
    //       ),
    //       separatorBuilder: (context, index) => Divider(
    //         thickness: 1,
    //         height: 5,
    //       ),
    //       itemCount: snapshot.data?.length ?? 0,
    //     );
    //   },
    // );
    return ProductListDataTable(productDataSource: bloc.productDataSource);
  }
}
