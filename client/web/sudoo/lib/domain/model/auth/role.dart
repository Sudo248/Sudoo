enum Role {
  STAFF("STAFF"),
  ADMIN("ADMIN"),
  CONSUMER("CONSUMER");

  final String value;

  const Role(this.value);

  factory Role.fromValue(String value) {
    switch(value) {
      case "STAFF":
        return Role.STAFF;
      case "ADMIN":
        return Role.ADMIN;
      default:
        return Role.CONSUMER;
    }
  }
}