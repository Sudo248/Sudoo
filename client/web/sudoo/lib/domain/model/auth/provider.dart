enum Provider {
  authService("Auth-service"),
  google("Google"),
  facbook("Facebook"),
  phone("phone");

  final String value;
  const Provider(this.value);
}
