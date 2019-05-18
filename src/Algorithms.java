
/**
* @author  Ivan Mykolenko
* @since   30/10/2017
* @extended by Ivan Mykolenko; SN: 150159874
*/

import java.util.*;

public class Algorithms {

	/* First-Fit algorithm */
	public int firstFit(List<Integer> orders, List<Rope> coils) {
		/* Initializy local variables */
		int coilIndex = 0;
		Set<Rope> coilsUsed = new HashSet<Rope>();
		for (int i = 0; i < orders.size(); i++) { // Through every order
			while (coils.get(coilIndex).getLength() < orders.get(i)) { // Find a coil which is long enough
				coilIndex++;
			}
			coils.get(coilIndex).cut(orders.get(i)); // When found, cut the order off
			coilsUsed.add(coils.get(coilIndex)); // Add this coil to the set of used ones
			if (coils.get(coilIndex).getLength() < 5) { // If current coil is less than 5 in length
				coils.remove(coilIndex); // Scrap it
			}
			coilIndex = 0; // Go back to the first coil
		}
		return coilsUsed.size(); // Return the quantity of coils that have been used
	}

	/* Next-Fit algorithm */
	public int nextFit(List<Integer> orders, List<Rope> coils) {
		/* Initializy local variables */
		int coilsUsed = 0;
		int coilIndex = 0;
		int orderIndex = 0;
		while (orderIndex < orders.size()) { // Repeat till all orders are fulfilled
			/* While there is more orders and current coil has enough length */
			while (orderIndex < orders.size() && coils.get(coilIndex).getLength() >= orders.get(orderIndex)) {
				coils.get(coilIndex).cut(orders.get(orderIndex)); // Cut the order off
				orderIndex++; // Proceed to the next order
			}
			if (coils.get(coilIndex).getLength() < 5) {// If current coil is less than 5 in length
				coils.remove(coilIndex); // Scrap it
			} else { // If not
				coilIndex++; // Proceed to the next coil
			}
			coilsUsed++; // Increase used coils counter
		}
		return coilsUsed; // Return used coils counter
	}

	/*
	 * Below are the two methods identical to the methods above. The only difference
	 * is that the following methods contain print statements. I've use the
	 * versions below to track whether algorithms work as expected. These were also
	 * helpful for the correctness testing.
	 */
//
//	 public int firstFit(List<Integer> orders, List<Rope> coils) {
//	 int coilIndex = 0;
//	 Set<Rope> coilsUsed = new HashSet<Rope>();
//	 for (int i = 0; i < orders.size(); i++) {
//	 while (coils.get(coilIndex).getLength() < orders.get(i)) {
//	 coilIndex++;
//	 }
//	 System.out.println("Coil " + coilIndex + " was: " +
//	 coils.get(coilIndex).getLength());
//	 System.out.println("Taking away: " + orders.get(i));
//	 coils.get(coilIndex).cut(orders.get(i));
//	 coilsUsed.add(coils.get(coilIndex));
//	 System.out.println("Coil " + coilIndex + " became: " +
//	 coils.get(coilIndex).getLength());
//	 if (coils.get(coilIndex).getLength() < 5) {
//	 System.out.println("Coil " + coilIndex + " length is less than 5. Scrapit!");
//	 coils.remove(coilIndex);
//	 }
//	 coilIndex = 0;
//	 }
//	 return coilsUsed.size();
//	 }
	//

	//
//
//	 public int nextFit(List<Integer> orders, List<Rope> coils) {
//	 int coilsUsed = 0;
//	 int coilIndex = 0;
//	 int orderIndex =0;
//	 while( orderIndex < orders.size()) {
//	 while (orderIndex < orders.size()&&coils.get(coilIndex).getLength() >=
//	 orders.get(orderIndex)){
//	 System.out.println("Coil " + coilIndex + " was: " +
//	 coils.get(coilIndex).getLength());
//	 System.out.println("Taking away: " + orders.get(orderIndex));
//	 coils.get(coilIndex).cut(orders.get(orderIndex));
//	 System.out.println("Coil " + coilIndex + " became: " +
//	 coils.get(coilIndex).getLength());
//	 orderIndex++;
//	 }
//	 if (coils.get(coilIndex).getLength() < 5) {
//	 System.out.println("Coil " + coilIndex + " length is less than 5. Scrapit!");
//	 coils.remove(coilIndex);
//	 } else{
//	
//	 coilIndex++;
//	 }
//	 coilsUsed++;
//	 }
//	
//	
//	 return coilsUsed;
//	 }
	//
	//
	//

}
