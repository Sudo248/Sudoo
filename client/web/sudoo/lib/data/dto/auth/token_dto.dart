class TokenDto {
  final String token;
  final String? refreshToken;
  final String? userId;

  TokenDto({
    required this.token,
    this.refreshToken,
    this.userId,
  });

  factory TokenDto.fromJson(Map<String, dynamic> json) {
    return TokenDto(
      token: json["token"],
      refreshToken: json["refreshToken"],
      userId: json["userId"]
    );
  }
}
