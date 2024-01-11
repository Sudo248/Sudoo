import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/recommend/model_bloc.dart';
import 'package:sudoo/app/pages/recommend/model_item.dart';
import 'package:sudoo/app/widgets/empty_list.dart';

import '../../../resources/R.dart';

class ModelPage extends BasePage<ModelBloc> {
  ModelPage({super.key});

  @override
  bool get enableStatePage => true;
  final TextStyle style = R.style.h5.copyWith(color: Colors.black);

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisSize: MainAxisSize.min,
      children: [
        Align(
          alignment: Alignment.centerLeft,
          child: Padding(
            padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
            child: Text(
              R.string.syncData,
              style: R.style.h4_1.copyWith(
                fontWeight: FontWeight.bold,
                color: Colors.black,
              ),
            ),
          ),
        ),
        const SizedBox(
          height: 10,
        ),
        _buildSyncItem(R.string.syncUserDescription, style, () {
          bloc.syncUserToRecommendService();
        }),
        const SizedBox(
          height: 10,
        ),
        _buildSyncItem(R.string.syncProductDescription, style, () {
          bloc.syncProductRecommendService();
        }),
        const SizedBox(
          height: 10,
        ),
        _buildSyncItem(R.string.syncReviewDescription, style, () {
          bloc.syncReviewRecommendService();
        }),
        const SizedBox(
          height: 10,
        ),
        _buildSyncItem(R.string.syncAllDescription, style, () {
          bloc.syncUserToRecommendService();
          bloc.syncProductRecommendService();
          bloc.syncReviewRecommendService();
        }),
        const SizedBox(
          height: 15,
        ),
        Align(
          alignment: Alignment.centerLeft,
          child: Padding(
            padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
            child: Text(
              R.string.listModelVersions,
              style: R.style.h4_1.copyWith(
                fontWeight: FontWeight.bold,
                color: Colors.black,
              ),
            ),
          ),
        ),
        const SizedBox(
          height: 10,
        ),
        Expanded(
          child: ValueListenableBuilder(
            valueListenable: bloc.models,
            builder: (context, value, child) {
              if (value.isEmpty) {
                return const EmptyList();
              } else {
                return ListView.separated(
                  itemBuilder: (context, index) => ModelItem(model: value[index]),
                  separatorBuilder: (context, index) => const Divider(),
                  itemCount: value.length,
                );
              }
            },
          ),
        )
      ],
    );
  }

  Widget _buildSyncItem(
      String description, TextStyle style, VoidCallback onSync) {
    return Padding(
      padding: const EdgeInsets.only(left: 10.0, top: 8.0, bottom: 8.0, right: 30.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Text(
            description,
            style: style,
          ),
          IconButton.filled(
            style: R.buttonStyle.filledButtonStyle(
              backgroundColor: R.color.primaryColor,
            ),
            padding: const EdgeInsets.all(5.0),
            onPressed: onSync,
            icon: const Icon(
              Icons.sync,
              color: Colors.white,
            ),
          )
        ],
      ),
    );
  }
}
