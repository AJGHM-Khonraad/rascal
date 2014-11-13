module lang::rascal::tests::library::analysis::statistics::DescriptiveTests

import List;
import util::Math;
import analysis::statistics::Descriptive;
import lang::rascal::tests::library::analysis::statistics::RangeUtils;

bool eq(num a, num b) {
	error = 1 / pow(10, min(scale(a), scale(b)) - 1);
	return abs(a-b) <= error;
}
bool leq(num a, num b) = a < b ? true : eq(a,b);

test bool geometricLessThanArithmeticMean(list[num] nums) {
	if (nums == []) return true;
	nums = abs(nums);
	nums = assureRange(nums, 0.1,30);
	return leq(geometricMean(nums), mean(nums));
}
test bool meanTimesSizeEqualsSum(list[num] nums) {
	if (nums == []) return true;
	return eq(mean(nums) * size(nums), sum(nums));
}