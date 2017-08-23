#include <iostream>
#include <vector>
using std::vector;

long long get_fibonacci_partial_sum(long long n, long long m) {
  vector<int> f(n + 1, 0);
  f.at(0) = 0;
  f.at(1) = 1;
  for (long long i = 2; i <= n; ++i)
    f.at(i) = (f.at(i-1) + f.at(i-2)) % 10;

  int result = 0;
  for (long long i = m; i <= n; ++i)
    result += (result + f.at(i)) % 10;

  return result;
}

int main() {
    long long from, to;
    std::cin >> from >> to;
    std::cout << get_fibonacci_partial_sum(from, to) << '\n';
}
