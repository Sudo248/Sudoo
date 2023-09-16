class Pagination<T> {
  final int offset;
  final int limit;
  final List<T> data;

  Pagination(this.offset, this.limit, this.data);

  factory Pagination.first({int limit = 30, List<T> initData = const [] }) {
    return Pagination(0, limit, initData);
  }
}