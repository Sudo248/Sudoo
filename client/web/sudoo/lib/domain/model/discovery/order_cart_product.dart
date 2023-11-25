import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/discovery/order_product_info.dart';

part 'order_cart_product.g.dart';

@JsonSerializable(explicitToJson: true)
class OrderCartProduct {
  final String cartProductId;
  final String cartId;
  final int quantity;
  final double totalPrice;
  final OrderProductInfo product;

  OrderCartProduct(
    this.cartProductId,
    this.cartId,
    this.quantity,
    this.totalPrice,
    this.product,
  );

  factory OrderCartProduct.fromJson(Map<String, dynamic> json) => _$OrderCartProductFromJson(json);

  Map<String, dynamic> toJson() => _$OrderCartProductToJson(this);
}
