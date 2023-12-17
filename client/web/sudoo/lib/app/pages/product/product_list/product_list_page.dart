import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_bloc.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_data_table.dart';

import '../../../../resources/R.dart';

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
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: ValueListenableBuilder(
            valueListenable: bloc.totalProducts,
            builder: (context, value, child) {
              return Align(
                alignment: Alignment.topLeft,
                child: Text(
                  "Total: $value",
                  style: R.style.h5.copyWith(
                    color: Colors.red,
                  ),
                ),
              );
            },
          ),
        ),
        ProductListDataTable(
          productDataSource: bloc.productDataSource,
        )
      ],
    );
  }
}
