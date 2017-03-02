package ectest;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RequestPiValue rpv = new RequestPiValue("10");
		ResponsePiValue repv = new ResponsePiValue();
		repv.processReq();
	}
}
