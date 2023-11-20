#include <iostream>

using namespace std;

constexpr int MAX_SIZE = 100000;

int n, m;
int arr[MAX_SIZE];

bool is_valid(int k) {
	int holding = 0;
	int withdrawal_count = 0;
	for (int i = 0; i < n; ++i) {
		if (holding < arr[i]) {
			holding = k;
			withdrawal_count++;
		}
		holding -= arr[i];
		if (withdrawal_count > m || holding < 0)
			return (false);
	}
	return (true);
}

int main() {
	int left, right;

	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(nullptr);

	cin >> n >> m;
	for (int i = 0; i < n; ++i)
		cin >> arr[i];

	left = INT_MAX;
	right = INT_MIN;
	for (int i = 0; i < n; ++i) {
		left = min(left, arr[i]);
		right = max(right, arr[i]);
	}
	right *= n;

	while (left < right) {
		int mid = (right + left) / 2;
		if (right - left <= 1) {
			if (is_valid(left))
				cout << left << '\n';
			else
				cout << right << '\n';
			return (0);
		}
		if (is_valid(mid))
			right = mid;
		else
			left = mid;
	}
	return (0);
}