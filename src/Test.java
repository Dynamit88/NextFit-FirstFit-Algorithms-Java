
/**
* @author  Ivan Mykolenko
* @since   30/10/2017
* @extended by Ivan Mykolenko; SN: 150159874
*/

import java.util.*; //needed for usage of the List interface

public class Test {
	/*Globals*/
	private Algorithms a = new Algorithms();
	private Generator g = new Generator();
	private List<Integer> orders, ordersCopy;
	private List<Rope> coils, coilsCopy;
	private long startTime, endTime, timeUsed;
	private int coilsUsed;
	private int[][] fFitPerfarmance, nFitPerfarmance;
	/*The following variables are used to control output*/
	private int noOfTests = 1;
	private int noOfRep = 1;
	private int noOfOrders = 500;
	private int increment = 10000;

	/* Method to create list of orders for correctness testing */
	private List<Integer> createOrdersForCorrectnessTest() {
		orders.clear();/* Clear the list for the next algorithm */
		orders.add(100);
		orders.add(26);
		orders.add(50);
		orders.add(100);
		orders.add(50);
		return orders;
	}

	/* Method to create list of coils for correctness testing */
	private List<Rope> createCoilsForCorrectnessTest() {
		coils.clear(); /* Clear the list for the next algorithm */
		coils.add(new Rope(130));
		coils.add(new Rope(100));
		coils.add(new Rope(120));
		coils.add(new Rope(130));
		coils.add(new Rope(110));
		coils.add(new Rope(130));
		coils.add(new Rope(130));
		return coils;
	}
	/*Method for displaying a single rope */
	private void displaySingleRope (List<Rope> list, int index) {
		System.out.printf("The length of the rope %d is %d.\n", index+1, list.get(index).getLength());
	}
	/*Method for a list of ropes */
	private void displayListOfRopes (List<Rope> list) {
		for (int i =0; i<list.size();i++) {
			displaySingleRope(list, i);
		}
	}
	
	
	/*
	 * Test to makes sure both algorithms operate as expected.Due to the nature of
	 * the algorithm, First-Fit must consume 1 coil less than Next-Fit.
	 */
	private void corectnessTesting() {
		System.out.println("*********** Correctness testing ***********");
		orders = new ArrayList<Integer>();
		coils = new ArrayList<Rope>();
		displayListOfRopes (createCoilsForCorrectnessTest());
		
		System.out.printf("Next-Fit = Coils used expected:4 actual: %d\n",
				a.nextFit(createOrdersForCorrectnessTest(), createCoilsForCorrectnessTest()));
		System.out.printf("First-Fit = Coils used expected:3 actual: %d\n",
				a.firstFit(createOrdersForCorrectnessTest(), createCoilsForCorrectnessTest()));
	}

	/*
	 * The following method tests the two algorithms using identical input data for
	 * every instance. For every repetition coils and orders are generated randomly.
	 * Execution of every sample run is timed. Time and coils used while for each
	 * execution are stored in the x-FitPerfarmance array.
	 */
	private void performanceTesting(int noOfTests, int noOfRep, int noOfOrders, int increment) {
		System.out.println("*********** Performance analysis ***********");
		if (noOfTests > 0) { /* Input validation */
			if (noOfRep > 0) {/* Input validation */
				if (noOfOrders > 0) {/* Input validation */
					/*
					 * Initialise two-dimensional arrays for storing performance data (time and
					 * coils used)
					 */
					nFitPerfarmance = new int[noOfRep + 1][noOfRep + 1];
					fFitPerfarmance = new int[noOfRep + 1][noOfRep + 1];
					for (int i = 0; i < noOfTests; i++) { // For Each test
						System.out.printf("%13sTest %d testing %d orders.\n", " ", i + 1, noOfOrders);
						for (int r = 0; r < noOfRep; r++) {
							orders = g.generateMultipleOrders(noOfOrders);
							coils = g.orderRopeFromManufacturer(noOfOrders);
							ordersCopy = g.getDeepCopyOfOrders(); // Get deep copy of the list with random data
							coilsCopy = g.getDeepCopyOfCoils();// Get deep copy of the list with random data
							System.out.printf("%10s%d\tAlgorithm\tCoils used\tTime\n ", "Sample ", r + 1);
							startTime = System.currentTimeMillis(); // Start time of the execution
							coilsUsed = a.nextFit(ordersCopy, coilsCopy); // Next-Fit
							endTime = System.currentTimeMillis(); // End time of the execution
							timeUsed = endTime - startTime;
							System.out.printf("%15s%-16s%-16d%d ms\n", " ", "Next-Fit", coilsUsed, timeUsed);
							nFitPerfarmance[0][r] = coilsUsed; // Store coils used by New-Fit for this test
							nFitPerfarmance[1][r] = (int) timeUsed; // Store time used by New-Fit for this test
							startTime = System.currentTimeMillis(); // Start time of the execution
							coilsUsed = a.firstFit(orders, coils); // First-Fit
							endTime = System.currentTimeMillis();// End time of the execution
							timeUsed = endTime - startTime;
							System.out.printf("%16s%-16s%-16d%d ms\n", " ", "First-Fit", coilsUsed, timeUsed);
							fFitPerfarmance[0][r] = coilsUsed; // Store coils used by First-Fit for this test
							fFitPerfarmance[1][r] = (int) timeUsed; // Store time used by First-Fit for this test
							System.out.println(String.join("", Collections.nCopies(55, ".")));
						}
						analyzeResults(); // Launch results analysis
						System.out.println(String.join("", Collections.nCopies(55, "=")));
						noOfOrders += increment; // Proceed to the next number of orders
					}

				} else {
					System.out.println("The number of orders must be greater than 0!");
				}
			} else {
				System.out.println("The number of repetitions must be greater than 0!");
			}
		} else {
			System.out.println("The number of tests must be greater than 0!");
		}
	}

	/*
	 * analyzeResults() method displays the average performance (time and coils used)
	 * for every test of the two algorithms. Then results the two methods have
	 * produced are compared against each other.
	 */
	private void analyzeResults() {
		double nFItTotalCoilsUsed = 0, fFItTotalCoilsUsed = 0, nFItTotalTime = 0, fFItTotalTime = 0,
				coilsPercentage = 0, timePercentage = 0; /* Initialising data */
		for (int i = 0; i < fFitPerfarmance[0].length; i++) { // Add up all the coils First-Fit has used in each sample
			fFItTotalCoilsUsed += fFitPerfarmance[0][i];
		}
		for (int i = 0; i < nFitPerfarmance[0].length; i++) {// Add the times it took First-Fit to complete all the
																// orders
			nFItTotalCoilsUsed += nFitPerfarmance[0][i];
		}
		for (int i = 0; i < fFitPerfarmance[1].length; i++) {// Add up all the coils Next-Fit has used in each sample
			fFItTotalTime += fFitPerfarmance[1][i];
		}
		for (int i = 0; i < nFitPerfarmance[1].length; i++) {// Add the times it took Next-Fit to complete all the orders
			nFItTotalTime += nFitPerfarmance[1][i];
		}
		/* Calculate averages */
		double nFitCoilsAver = nFItTotalCoilsUsed / noOfRep;
		double nFitTimeAver = nFItTotalTime / noOfRep;
		double fFitCoilsAver = fFItTotalCoilsUsed / noOfRep;
		double fFitTimeAver = fFItTotalTime / noOfRep;
		/* Print off the data */
		System.out.printf("%16s%-28s%s\n", " ", "Aver. coils used", "Aver. Time");
		System.out.printf("%13s\t%-28.1f%.1f\n", "Next-Fit: ", nFitCoilsAver, nFitTimeAver);
		System.out.printf("%14s\t%-28.1f%.1f\n", "First-Fit: ", fFitCoilsAver, fFitTimeAver);
		/* Decide upon the fastest algorithm */
		String fastestAlgorithm = nFitTimeAver < fFitTimeAver ? "Next-Fit" : "First-Fit";
		timePercentage = fFitTimeAver < nFitTimeAver ? (nFitTimeAver - fFitTimeAver) / fFitTimeAver * 100
				: (fFitTimeAver - nFitTimeAver) / nFitTimeAver * 100;
		/* Decide upon the efficient algorithm */
		String efficientAlgorithm = nFitCoilsAver < fFitCoilsAver ? "Next-Fit" : "First-Fit";
		coilsPercentage = fFitCoilsAver < nFitCoilsAver ? (nFitCoilsAver - fFitCoilsAver) / fFitCoilsAver * 100
				: (fFitCoilsAver - nFitCoilsAver) / nFitCoilsAver * 100;
		System.out.printf("> On average %s was %.2f%% faster.\n", fastestAlgorithm, timePercentage);
		System.out.printf("> On average %s was %.2f%% more coil-efficient.\n", efficientAlgorithm, coilsPercentage);
	}

	/* Main Method */
	public static void main(String[] args) {
		Test t = new Test(); /*Instance of the Test class*/
		t.corectnessTesting(); /*Launch correctness test for the two algorithms*/
		t.performanceTesting(t.noOfTests, t.noOfRep, t.noOfOrders, t.increment); /*Launch performance test for the two algorithms*/
	}
}
