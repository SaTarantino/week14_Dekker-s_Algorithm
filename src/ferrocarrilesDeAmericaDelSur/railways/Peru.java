package ferrocarrilesDeAmericaDelSur.railways;

import ferrocarrilesDeAmericaDelSur.errors.RailwaySystemError;
import ferrocarrilesDeAmericaDelSur.errors.SetUpError;
import ferrocarrilesDeAmericaDelSur.tools.Clock;
import ferrocarrilesDeAmericaDelSur.tools.Delay;

/**
 * An implementation of a railway.  The runTrain method, should, in collaboration with Bolivia's runTrain(), guarantee
 * safe joint operation of the railways.
 */
public class Peru extends Railway {
	/**
	 * Change the parameters of the Delay constructor in the call of the superconstructor to
	 * change the behaviour of this railway.
	 * @throws SetUpError if there is an error in setting up the delay.
	 */
	public Peru() throws SetUpError {
		super("Peru",new Delay(0.1,0.3));
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
//    		while (getSharedBasket().hasStone());
//    		getSharedBasket().putStone();
//    		crossPass();
//    		getSharedBasket().takeStone();
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
//			while (getSharedBasket().hasStone()) {
//				siesta();
//			}
//			crossPass();
//			getSharedBasket().putStone();
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
//		while (!clock.timeOut()) {
//			choochoo();
//			getBasket().putStone();
//			while (nextRailway.getBasket().hasStone()) {
//				getBasket().takeStone();
//				siesta();
//				getBasket().putStone();
//			}
//			crossPass();
//			getBasket().takeStone();
//		}
//	}

	/**
	 * Attempt 6.
	 * @throws RailwaySystemError
	 */
	public void runTrain() throws RailwaySystemError {
		Clock clock = getRailwaySystem().getClock();
		Railway nextRailway = getRailwaySystem().getNextRailway(this);
		while (!clock.timeOut()) {
			choochoo();
			getBasket().putStone();
			while (nextRailway.getBasket().hasStone()) {
				if (getSharedBasket().hasStone() == getBasket().hasStone()) {
					getBasket().takeStone();
					while (!getSharedBasket().hasStone()) { // != getBasket().hasStone()
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

//	public void runTrain() throws RailwaySystemError {
//		Clock clock = getRailwaySystem().getClock();
//		Railway nextRailway = getRailwaySystem().getNextRailway(this);
//		while (!clock.timeOut()) {
//			choochoo();
//			getBasket().putStone();
//			while (nextRailway.getBasket().hasStone()) {
//				if (getSharedBasket().hasStone()) {
//					getBasket().takeStone();
//					while (getSharedBasket().hasStone()) { // != getBasket().hasStone()
//						siesta();
//					}
//					getBasket().putStone();
//				}
//			}
//			crossPass();
//			getSharedBasket().putStone();
//			getBasket().takeStone();
//		}
//	}