
/**
* The Generator class generates coils of rope and customer orders 
* @author  Ivan Mykolenko
* @since   30/10/2017
* @extended by Ivan Mykolenko; SN: 150159874
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
	// The longest possible length of rope that a customer can order
	private final int MAX_ORDER_LENGTH = 100;

	/*
	 * These variables represent the longest and smallest possible coils of rope
	 * that the manufacturers can supply
	 */
	private final int MIN_ROPE_LENGTH = 100;
	private final int MAX_ROPE_LENGTH = 200;
	private List<Integer> orderList, orderListCopy;
	private List<Rope> coilsList, coilsListCopy;
	private Random r = new Random(); // Random number generator

	/**
	 * This method generates a list of orders of random lengths between the min and
	 * max order sizes (1 and 100m)
	 * 
	 * @param numberOfOrders:
	 *            the number of customer orders to generate
	 * @return a list of customer orders
	 */
	public List<Integer> generateMultipleOrders(int numberOfOrders) {
		orderList = new ArrayList<Integer>(); // Initialise new list
		for (int i = 0; i < numberOfOrders; i++) { // For the number of orders
			orderList.add(r.nextInt(MAX_ORDER_LENGTH) + 1);
		}
		return orderList;
	}

	/**
	 * This method generates a list of new coils of rope from the manufacturer of
	 * random lengths between the min and max order sizes (100 and 200m)
	 * 
	 * @param numberOfCoils:the
	 *            number of ropes to generate
	 * @return a list of coils of rope supplied by the manufacturer
	 */
	public List<Rope> orderRopeFromManufacturer(int numberOfCoils) {
		coilsList = new ArrayList<Rope>(); // Initialize new list
		for (int i = 0; i < numberOfCoils; i++) { // For the number of coils
			coilsList.add(new Rope(ThreadLocalRandom.current().nextInt(MIN_ROPE_LENGTH, MAX_ROPE_LENGTH + 1)));
		}
		return coilsList;
	}

	/* Creates a deep copy of the list of orders */
	public List<Integer> getDeepCopyOfOrders() {
		if (orderList != null) {
			orderListCopy = new ArrayList<Integer>(); // Initialise new list
			for (Integer i : orderList) { // Copy all the elements from the original list
				orderListCopy.add(i);
			}

			return orderListCopy;
		}
		return null;
	}

	/* Creates a deep copy of the list of coils */
	public List<Rope> getDeepCopyOfCoils() {
		if (coilsList != null) {
			coilsListCopy = new ArrayList<Rope>(); // Initialise new list
			for (Rope r : coilsList) { // Copy all the elements from the original list
				coilsListCopy.add(new Rope(r));
			}
			return coilsListCopy;
		}
		return null;
	}
}
