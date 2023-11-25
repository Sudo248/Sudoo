// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'shipment.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Shipment _$ShipmentFromJson(Map<String, dynamic> json) => Shipment(
      $enumDecode(_$ShipmentTypeEnumMap, json['shipmentType']),
      json['deliveryTime'] as int,
      json['receivedDateTime'] == null
          ? null
          : DateTime.parse(json['receivedDateTime'] as String),
      (json['shipmentPrice'] as num).toDouble(),
    );

Map<String, dynamic> _$ShipmentToJson(Shipment instance) => <String, dynamic>{
      'shipmentType': _$ShipmentTypeEnumMap[instance.shipmentType]!,
      'deliveryTime': instance.deliveryTime,
      'receivedDateTime': instance.receivedDateTime?.toIso8601String(),
      'shipmentPrice': instance.shipmentPrice,
    };

const _$ShipmentTypeEnumMap = {
  ShipmentType.nothing: 'NOTHING',
  ShipmentType.express: 'EXPRESS',
  ShipmentType.standard: 'STANDARD',
  ShipmentType.saving: 'SAVING',
};
