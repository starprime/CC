package ectest;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RequestPiValue rpv = new RequestPiValue("2");
		Double d = rpv.retPiVal();
		System.out.print("pi ki val"+d);
	}
}
