
import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/dialog/edit_promotion_dialog.dart';
import 'package:sudoo/app/pages/promotion/promotion_bloc.dart';
import 'package:sudoo/app/pages/promotion/promotion_item.dart';
import 'package:sudoo/domain/model/promotion/promotion.dart';

class PromotionPage extends BasePage<PromotionBloc> {
  PromotionPage({super.key});

  @override
  bool get enableStatePage => true;

  @override
  Widget build(BuildContext context) {
    return _buildPromotions(context);
  }
  Widget _buildPromotions(BuildContext context) {
    return ValueListenableBuilder(
      valueListenable: bloc.promotions,
      builder: (context, value, child) => GridView.builder(
        itemCount: value.length + 1,
        gridDelegate: const SliverGridDelegateWithMaxCrossAxisExtent(
          maxCrossAxisExtent: 230,
          mainAxisSpacing: 10,
          crossAxisSpacing: 10,
          childAspectRatio: 4 / 3,
        ),
        itemBuilder: (context, index) {
          if (index < value.length - 1) {
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
    );
  }

  Widget _buildAddPromotion(BuildContext context) {
    return GestureDetector(
      onTap: () => _showEditPromotionDialog(context),
      child: Container(
        padding: const EdgeInsets.all(10.0),
        decoration: BoxDecoration(
          color: Colors.grey,
          borderRadius: BorderRadius.circular(15.0),
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