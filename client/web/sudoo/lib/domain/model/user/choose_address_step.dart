enum ChooseAddressStep {
  province("Choose province"),
  district("Choose district"),
  ward("Choose ward"),
  finish("finish");

  final String title;

  const ChooseAddressStep(this.title);

  ChooseAddressStep nextStep() {
    switch(this) {
      case province:
        return district;
      case district:
        return ward;
      case ward:
        return finish;
      default:
        return province;
    }
  }
}