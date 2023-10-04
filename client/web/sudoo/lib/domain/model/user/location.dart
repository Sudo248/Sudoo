class Location {
  final double longitude;
  final double latitude;

  const Location({this.longitude = 0.0, this.latitude = 0.0});

  factory Location.fromJson(Map<String, dynamic> json) => Location(
    longitude: json["longitude"] as double,
    latitude: json["latitude"] as double,
  );

  Map<String, dynamic> toJson() => {
    "longitude": longitude,
    "latitude": latitude,
  };
}
