import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/order/shipment_type.dart';

part 'shipment.g.dart';

@JsonSerializable()
class Shipment {
  final ShipmentType shipmentType;
  final int deliveryTime;
  final DateTime? receivedDateTime;
  final double shipmentPrice;

  Shipment(
    this.shipmentType,
    this.deliveryTime,
    this.receivedDateTime,
    this.shipmentPrice,
  );

  factory Shipment.fromJson(Map<String, dynamic> json) => _$ShipmentFromJson(json);

  Map<String, dynamic> toJson() => _$ShipmentToJson(this);
}
