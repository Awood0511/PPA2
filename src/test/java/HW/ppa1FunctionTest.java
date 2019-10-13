import org.junit.Test;
import static org.junit.Assert.*;


class ppa1FunctionTest {

	ppa1Function test = new ppa1Function();

	/* TESTS FOR splitTheTip */

	@Test
	public void EvenSplitTest() {
		double[] testResult1 = { 5.75, 0.0 }; // expected value for test 1
		double[] testResult2 = { 4.6, 0.0 }; // expected value for test 2
		assertArrayEquals(testResult1, test.splitTheTip(10, 2));
		assertArrayEquals(testResult2, test.splitTheTip(12, 3));
	}

	@Test
	public void RoundTheTotalAfterTipTest() {
		double[] testResult1 = { 6.60, 0.0 };
		double[] testResult2 = { 4.62, 0.0 };
		assertArrayEquals(testResult1, test.splitTheTip(11.48, 2));
		assertArrayEquals(testResult2, test.splitTheTip(12.05, 3));
	}

	@Test
	public void UnevenSplitTest() {
		double[] testResult1 = { 3.73, 0.03 };
		double[] testResult2 = { 12.77, 0.07 };
		assertArrayEquals(testResult1, test.splitTheTip(13.00, 4));
		assertArrayEquals(testResult2, test.splitTheTip(100, 9));
	}

	@Test
	public void ZeroInputTest() {
		double[] testResult1 = { -1, -1 };
		double[] testResult2 = { 0, 0 };
		assertArrayEquals(testResult1, test.splitTheTip(15, 0));
		assertArrayEquals(testResult2, test.splitTheTip(0.0, 2));
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
		assertEquals(testResult1, test.shortestDistance(0.0, 0.0, 1.0, 0.0));
		assertEquals(testResult2, test.shortestDistance(1.0, 1.0, 1.0, 3.0));
	}

	@Test
	public void decimalTest() {
		double testResult1 = 2.7080;
		double testResult2 = 60.9073;
		assertEquals(testResult1, test.shortestDistance(3.12, 4.67, 1.5, 2.5));
		assertEquals(testResult2, test.shortestDistance(4.23, 5.14, 6.63, 66.0));
	}
	
	@Test
	public void SamePointTest() {
		double testResult = 0;
		assertEquals(testResult, test.shortestDistance(1.7586, 2.456, 1.7586, 2.456));
		assertEquals(testResult, test.shortestDistance(4.1234, 1.1111, 4.1234, 1.1111));
	}
	
	@Test
	public void NegativePointsTest() {
		double testResult1 = 30.016;
		double testResult2 = 6.3246;
		assertEquals(testResult1, test.shortestDistance(-1.34, 12.0, -14.14, -15.15));
		assertEquals(testResult2, test.shortestDistance(-1.0, -2.0, -3.0, 4.0));
	}
	/* Test for Retirement Plan */
	@Test
	public void BadInputForRetirementTest() {	
		assertEquals(-1,test.retirementAge(0, 15, 10, 20));
		assertEquals(-2,test.retirementAge(17, 0, 10, 20));
		assertEquals(-3,test.retirementAge(18, 17, 0, 20));
		assertEquals(-4,test.retirementAge(18, 21, 15, -1));
		assertEquals(19,test.retirementAge(19, 15, 15, 0));
		
	}
	@Test
	public void BasicRetirementTest() {

		assertEquals(45,test.retirementAge(40, 20000, 1, 1300));
		assertEquals(55,test.retirementAge(35, 45000, 2, 24000));
		assertEquals(100,test.retirementAge(70, 50000, 3, 60000));
	}
	@Test
	public void DeathRetirementTest() {
		assertEquals(101,test.retirementAge(35, 45000, 2, 240000));
		assertEquals(101,test.retirementAge(16, 15000, 0.1, 20000));
		assertEquals(101,test.retirementAge(25, 20000, 0.5, 100000));
	}
}
