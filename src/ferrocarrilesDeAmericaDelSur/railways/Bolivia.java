package ferrocarrilesDeAmericaDelSur.railways;

import ferrocarrilesDeAmericaDelSur.errors.RailwaySystemError;
import ferrocarrilesDeAmericaDelSur.errors.SetUpError;
import ferrocarrilesDeAmericaDelSur.tools.Clock;
import ferrocarrilesDeAmericaDelSur.tools.Delay;

/**
 * An implementation of a railway.  The runTrain method, should, in collaboration with Peru's runTrain(), guarantee
 * safe joint operation of the railways.
 */
public class Bolivia extends Railway {
	/**
     * Change the parameters of the Delay constructor in the call of the superconstructor to
	 * change the behaviour of this railway.
	 * @throws SetUpError if there is an error in setting up the delay.
	 */
	public Bolivia() throws SetUpError {
		super("Bolivia",new Delay(0.1,0.3));
	}

    /**
     * Run the train on the railway.
     * This method currently does not provide any synchronisation to avoid two trains being in the pass at the same time.
	 * @throws RailwaySystemError
	 */
//    public void runTrain() throws RailwaySystemError {
//    	Clock clock = getRailwaySystem().getClock();
//    	while (!clock.timeOut()) {
//    		choochoo();
//			while (getSharedBasket().hasStone());
//			getSharedBasket().putStone();
//			crossPass();
//			getSharedBasket().takeStone();
//    	}
//    }

	/**
	 * Attempt 2. This method do not guarantees mutual exclusion and liveness  (starvation).
	 * @throws RailwaySystemError
	 */
//	public void runTrain() throws RailwaySystemError {
//		Clock clock = getRailwaySystem().getClock();
//		while (!clock.timeOut()) {
//			choochoo();
//			while (!getSharedBasket().hasStone()) {
//				siesta();
//			}
//			crossPass();
//			getSharedBasket().takeStone();
//		}
//	}

	/**
	 * Attempt 3
	 * @throws RailwaySystemError
	 */
//	public void runTrain() throws RailwaySystemError {
//		Clock clock = getRailwaySystem().getClock();
//		Railway nextRailway = getRailwaySystem().getNextRailway(this);
//		while (!clock.timeOut()) {
//			choochoo();
//			while (nextRailway.getBasket().hasStone()) {
//				siesta();
//			}
//			getBasket().putStone();
//			crossPass();
//			getBasket().takeStone();
//		}
//	}

	/**
	 * Attempt 4.
	 * @throws RailwaySystemError
	 */
//	public void runTrain() throws RailwaySystemError {
//		Clock clock = getRailwaySystem().getClock();
//		Railway nextRailway = getRailwaySystem().getNextRailway(this);
//		while (!clock.timeOut()) {
//			choochoo();
//			getBasket().putStone();
//			while (nextRailway.getBasket().hasStone()) {
//				siesta();
//			}
//			crossPass();
//			getBasket().takeStone();
//		}
//	}

	/**
	 * Attempt 5.
	 * @throws RailwaySystemError
	 */
//	public void runTrain() throws RailwaySystemError {
//		Clock clock = getRailwaySystem().getClock();
//		Railway nextRailway = getRailwaySystem().getNextRailway(this);
//		while (!clock.timeOut()) {											//while (true);
//			choochoo();														//non-critical section;
//			getBasket().putStone();											//procReqCS[id] = true;
//			while (nextRailway.getBasket().hasStone()) {					//while (procReqCS[(id+1) % 2]) {
//				getBasket().takeStone();									//	procReqCS[id] = false;
//				siesta();													//	"WAIT"
//				getBasket().putStone();										//	procReqCS[id] = true;
//			}																//}
//			crossPass();													//critical section;
//			getBasket().takeStone();										//procReqCS[id] = false;
//		}
//	}
 
	/**
	 * Attempt 6. Dekker's Algorithm.
	 * @throws RailwaySystemError
	 */
	public void runTrain() throws RailwaySystemError {
		Clock clock = getRailwaySystem().getClock();
		Railway nextRailway = getRailwaySystem().getNextRailway(this);
		while (!clock.timeOut()) {
			choochoo();
			getBasket().putStone();
			while (nextRailway.getBasket().hasStone()) {		// Attempt 5 wait loop
				if (getSharedBasket().hasStone()) {
					getBasket().takeStone();
					while (getSharedBasket().hasStone()){		// Attempt 2 wait loop
						siesta();
					}
					getBasket().putStone();
				}
			}
			crossPass();
			getSharedBasket().putStone();
			getBasket().takeStone();
		}
	}
}
