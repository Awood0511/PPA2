package HW;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ppa1FunctionTest {

	ppa1Function test = new ppa1Function();

	/* TESTS FOR splitTheTip */

	@Test
	public void EvenSplitTest() {
		double[] testResult1 = { 5.75, 0.0 }; // expected value for test 1
		double[] testResult2 = { 4.6, 0.0 }; // expected value for test 2
		assertArrayEquals(testResult1, test.splitTheTip(10, 2),
				"10 + 1.50 tip divided by 2 people should be 5.75 per guest");
		assertArrayEquals(testResult2, test.splitTheTip(12, 3),
				"12 + 1.8 tip divided by 3 people should be 4.6 per guest");
	}

	@Test
	public void RoundTheTotalAfterTipTest() {
		double[] testResult1 = { 6.60, 0.0 };
		double[] testResult2 = { 4.62, 0.0 };
		assertArrayEquals(testResult1, test.splitTheTip(11.48, 2),
				"11.48 + 1.722 tip divided by 2 people should be 6.6 per guest when rounded");
		assertArrayEquals(testResult2, test.splitTheTip(12.05, 3),
				"12.05 + 1.8075  tip divided by 3 people should be 4.62 per guest when rounded");
	}

	@Test
	public void UnevenSplitTest() {
		double[] testResult1 = { 3.73, 0.03 };
		double[] testResult2 = { 12.77, 0.07 };
		assertArrayEquals(testResult1, test.splitTheTip(13.00, 4),
				"13.00 + 1.95 tip divided by 4 people should be 3.73 per guest with 0.03 remainder");
		assertArrayEquals(testResult2, test.splitTheTip(100, 9),
				"100.00 + 15.00  tip divided by 9 people should be 12.77 per guest with 0.07 remainder");
	}

	@Test
	public void ZeroInputTest() {
		double[] testResult1 = { -1, -1 };
		double[] testResult2 = { 0, 0 };
		assertArrayEquals(testResult1, test.splitTheTip(15, 0),
				"it should return -1 so it shows that there is no guests. It prevents an error of dividing by 0");
		assertArrayEquals(testResult2, test.splitTheTip(0.0, 2),
				"0 + 0  tip divided by 2 people should be 0 per guest ");
	}

	@Test
	public void TipTypeTest() {
		double[] t;
		t = test.splitTheTip(10, 2);
		assertTrue(t instanceof double[], "The function should return a double array");
		assertTrue(t.length == 2, "The function should return an array with length 2");
		t = test.splitTheTip(10, 0);
		assertTrue(t.length == 2, "The function should return an array with length 2");
	}

	/* TESTS FOR shortestDistance */

	@Test
	public void nonDecimalTest() {
		double testResult1 = 1.0;
		double testResult2 = 2.0;
		assertEquals(testResult1, test.shortestDistance(0.0, 0.0, 1.0, 0.0),
				"The distance from the origin to 1,0 is 1");
		assertEquals(testResult2, test.shortestDistance(1.0, 1.0, 1.0, 3.0), "The distance from 1,1 to 1,3 is 2");
	}

	@Test
	public void decimalTest() {
		double testResult1 = 2.7080;
		double testResult2 = 60.9073;
		assertEquals(testResult1, test.shortestDistance(3.12, 4.67, 1.5, 2.5),
				"The distance should be 2.7080 with 4 dp of precision");
		assertEquals(testResult2, test.shortestDistance(4.23, 5.14, 6.63, 66.0),
				"The distance should be 60.9073 with 4 dp of precision");
	}

	@Test
	public void SamePointTest() {
		double testResult = 0;
		assertEquals(testResult, test.shortestDistance(1.7586, 2.456, 1.7586, 2.456), "The distance should be 0");
		assertEquals(testResult, test.shortestDistance(4.1234, 1.1111, 4.1234, 1.1111), "The distance should be 0");
	}

	@Test
	public void NegativePointsTest() {
		double testResult1 = 30.016;
		double testResult2 = 6.3246;
		assertEquals(testResult1, test.shortestDistance(-1.34, 12.0, -14.14, -15.15),
				"The distance should be 30.0160 with 4 dp of precision");
		assertEquals(testResult2, test.shortestDistance(-1.0, -2.0, -3.0, 4.0),
				"The distance should be 6.3246 with 4 dp of precision");
	}

	/* TESTS FOR BMI */

	@Test
	public void BMIBadInputTest() {
		assertEquals("weightless", test.bodymass(4,4,25),
				"The weight is must be above 30 or else they are wightless");
		assertEquals("heightless", test.bodymass(1,11,140),
				"The height is must be above 24 inches or else they are wightless");
	}

	@Test
	public void BMITestAllWeightCategories() {
		assertEquals("Normal Weight|22.68", test.bodymass(5,3,125),
				"5 ft 3 at 125 lbs is normal weight with BMI 22.68");
		assertEquals("Underweight|15.62", test.bodymass(4,0,50),
				"4 ft 0 at 50 lbs is under weight with BMI 15.63");
		assertEquals("Overweight|29.93", test.bodymass(5,3,165),
				"5 ft 3 at 165 lbs is overweight with BMI 29.93");
		assertEquals("Obese|52.48", test.bodymass(4,7,220.5),
				"4 ft 7 at 220.5 lbs is Obese with BMI 52.48");
	}

	/* TESTS FOR Retirement Plan */
	@Test
	public void BadInputForRetirementTest() {
		assertEquals(-1, test.retirementAge(0, 15, 10, 20), "The retirement age is suppose to be bigger than 16");
		assertEquals(-2, test.retirementAge(17, 0, 10, 20), "The Annual Salary has to be greater than 0");
		assertEquals(-3, test.retirementAge(18, 17, 0, 20), "The percent saved is suppose to be bigger than 0");
		assertEquals(-4, test.retirementAge(18, 21, 15, -1), "The goal cannot be lower than 0 dollars");
		assertEquals(19, test.retirementAge(19, 15, 15, 0),
				"If the goal is 0 is already met so we return the currrent Age");

	}

	@Test
	public void BasicRetirementTest() {
		assertEquals(45, test.retirementAge(40, 20000, 1, 1300),
				"It should take 5 years to retire when saving 1 percent of 20000 with a 1300 goal with 35% company matching");
		assertEquals(55, test.retirementAge(35, 45000, 2, 24000),
				"It should take 20 years to retire when saving 2 percent of 45000 with a 24000 goal with 35% company matching");
		assertEquals(100, test.retirementAge(70, 50000, 3, 60000),
				"It should take 30 years to retire when saving 3 percent of 50000 with a 60000 goal with 35% company matching");
	}

	@Test
	public void DeathRetirementTest() {
		assertEquals(101, test.retirementAge(35, 45000, 2, 240000),
				"At this age, salary, saving rate, and goal they should never retire (age of 101)");
		assertEquals(101, test.retirementAge(16, 15000, 0.1, 20000),
				"At this age, salary, saving rate, and goal they should never retire (age of 101)");
		assertEquals(101, test.retirementAge(25, 20000, 0.5, 100000),
				"At this age, salary, saving rate, and goal they should never retire (age of 101)");
	}
}
