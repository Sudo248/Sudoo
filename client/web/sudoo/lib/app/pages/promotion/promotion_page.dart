import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/dialog/edit_promotion_dialog.dart';
import 'package:sudoo/app/pages/promotion/promotion_bloc.dart';
import 'package:sudoo/app/pages/promotion/promotion_item.dart';
import 'package:sudoo/domain/model/promotion/promotion.dart';

import '../../../resources/R.dart';

class PromotionPage extends BasePage<PromotionBloc> {
  PromotionPage({super.key});

  @override
  bool get enableStatePage => true;

  @override
  Widget build(BuildContext context) {
    return _buildPromotions(context);
  }
  Widget _buildPromotions(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
          child: Text(
            R.string.promotions,
            style: R.style.h4_1.copyWith(
              fontWeight: FontWeight.bold,
              color: Colors.black,
            ),
          ),
        ),
        const SizedBox(
          height: 10,
        ),
        Expanded(
          child: ValueListenableBuilder(
            valueListenable: bloc.promotions,
            builder: (context, value, child) => GridView.builder(
              padding: const EdgeInsets.all(20),
              itemCount: value.length + 1,
              gridDelegate: const SliverGridDelegateWithMaxCrossAxisExtent(
                maxCrossAxisExtent: 230,
                mainAxisSpacing: 50,
                crossAxisSpacing: 50,
                childAspectRatio: 3 / 4.2,
              ),
              itemBuilder: (context, index) {
                if (index < value.length) {
                  return PromotionItem(
                    promotion: value[index],
                    onItemClick: (promotion) => _showEditPromotionDialog(
                      context,
                      promotion: promotion,
                    ),
                    upsertPromotion: bloc.upsertPromotion,
                  );
                } else {
                  return child;
                }
              },
            ),
            child: _buildAddPromotion(context),
          ),
        ),
      ],
    );
  }

  Widget _buildAddPromotion(BuildContext context) {
    return GestureDetector(
      onTap: () => _showEditPromotionDialog(context),
      child: Container(
        padding: const EdgeInsets.all(10.0),
        decoration: BoxDecoration(
          color: Colors.grey,
          borderRadius: BorderRadius.circular(10.0),
        ),
        child: const Center(
          child: Icon(
            Icons.add,
            size: 50,
            color: Colors.blueGrey,
          ),
        ),
      ),
    );
  }

  void _showEditPromotionDialog(BuildContext context, {Promotion? promotion}) {
    showDialog(
      context: context,
      builder: (context) => EditPromotionDialog(
        promotion: promotion,
        onSubmitPromotion: bloc.upsertPromotion,
      ),
    );
  }
}