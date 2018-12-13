package com.ROCSAFE.maven.WGS84Polygon;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import com.ROCSAFE.maven.gpsutilities.CoordinateBase;
import com.ROCSAFE.maven.gpsutilities.WGS84Coordinate;

/**
 * A class which records a polygon made up of Coordinates
 * Assumption is made that Coordinates are close to each 
 * other which allows Cartesian geometry to be applied.
 * Polygon is assumed to be oriented.
 * @author David Smyth
 */

public abstract class PolygonBase<T extends CoordinateBase> {

	//boundary of the GPSPolygon is just a unique set of WGS84Coordinates
	LinkedHashSet<T> boundary;
	

	public PolygonBase(List<T> boundary) throws Exception {
		//other checks for valid polygon?
		this(new LinkedHashSet<T>(boundary));
	}
	
	public PolygonBase(LinkedHashSet<T> boundary) throws Exception {
		//other checks for valid polygon?
		setBoundary(boundary);
	}
	
	public abstract QuadrilateralBase<T> getBoundingQuadrilateral();
	
	/*
	public Quadrilateral getBoundingQuadrilateral() throws Exception {
		//returns a bounding quadrilateral whose edges run parallel to the x,y axes of 
		//WGS coordinate system
		return new Quadrilateral(new T(getLowestLat(), getLowestLng()),
				new T(getLowestLat(), getHighestLng()),
				new T(getHighestLat(), getHighestLng()), 
				new T(getHighestLat(), getLowestLng()));
	}
	*/
	
	/**
	 * Returns the bounding coordinates of the polygon. 
	 * @return List<WGS84Coordinate>
	 */
	public LinkedHashSet<T> getBoundary() {
		return boundary;
	}
	/**
	 * Replaces the old coordinate with a new coordinate in the boundary set, 
	 * only if the old coordinate can be located in the boundary set.
	 * @param oldCoord
	 * @param newCoord
	 * @return
	 */
	
	public boolean replaceCoordinate(WGS84Coordinate oldCoord, WGS84Coordinate newCoord) {
		return true;
	}
	
	
	/**
	 * Gets largest coordinate in X direction of CoordinateBase
	 * @return
	 */
	protected T getHighestLngCoord() {
		return boundary.stream().max(Comparator.comparing(T::getLng)).get();
	}
	protected T getLowestLngCoord() {
		return boundary.stream().min(Comparator.comparing(T::getLng)).get();
	}
	protected T getHighestLatCoord() {
		return boundary.stream().max(Comparator.comparing(T::getLat)).get();
	}
	protected T getLowestLatCoord() {
		return boundary.stream().min(Comparator.comparing(T::getLat)).get();
	}
	
	protected BigDecimal getHighestLat() {
		return getHighestLatCoord().getLat();
	}
	
	protected BigDecimal getHighestLng() {
		return getHighestLngCoord().getLng();
	}
	
	protected BigDecimal getLowestLng() {
		return getLowestLngCoord().getLng();
	}
	
	protected BigDecimal getLowestLat() {
		return getLowestLatCoord().getLat();
	}
	
	protected void setBoundary(LinkedHashSet<T> boundary2) throws Exception {
		if(boundary2.size() <= 2) {
			throw new Exception("Cannot create a polygon with less than 3 vertices");
		}
		else {
			this.boundary = boundary2;
		}
	}
	
	public String toString() {
		String returnString = "";
		for(CoordinateBase coord: getBoundary()) {
			returnString += coord.toString() + "\n";
		}
		return returnString.substring(0, returnString.length() - 1);
	}
	
	public boolean equals(Object other) {
		if(other == null || other.getClass() != getClass()) {
			return false;
		}
		else {
			PolygonBase<T> otherCast = (PolygonBase<T>) other;
			if(otherCast.getBoundary().equals(getBoundary())) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	public boolean pointInPolygon(T testCoord) {
		try {
			return pointInPolygonPrivate(testCoord);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @param testCoord - a WGS84Coordinate which will be tested for inclusion in this polynomial
	 * For the line defined by each pair of successive points in the boundary, figure out whether 
	 * a horizontal ray extended from the coordinate to be tested crosses it.  
	 * @return true if test is in this polynomial, else false
	 * @throws Exception 
	 */
	private boolean pointInPolygonPrivate(T testCoord) throws Exception {
		if(testCoord.getClass() != boundary.iterator().next().getClass()) {
			throw new Exception("Cannot test if point of type " + testCoord.getClass() + " is in polygon.");
		}
	    
	    //if less than lowest lat, lowest long
	    //or
	    //greater than highest lat, highest long, must be false
		
	    if(testCoord.getLat().compareTo(getLowestLat()) < 0 || 
	       testCoord.getLng().compareTo(getLowestLng()) < 0 ||
	       testCoord.getLng().compareTo(getHighestLng()) > 0 ||
	       testCoord.getLat().compareTo(getHighestLng()) > 0
	       ) {
	    	return false;
	    }
	    
	    boolean result = false;
	    LinkedHashSet<T> boundaryPoints = getBoundary();
	    
	    //calculations broken apart into expressions
	    BigDecimal exp1;
	    BigDecimal exp2;
	    BigDecimal exp3;
	    BigDecimal exp4;
	    BigDecimal exp5;
	    BigDecimal exp6;
	    BigDecimal exp7;
	    
	    Iterator<T> boundaryPointsList = boundaryPoints.iterator();
	    
	    T predecessor = boundaryPointsList.next();
	    T successor = boundaryPointsList.next();
	    
	    do {
	    	exp1 = successor.getLng().subtract(predecessor.getLng());
	    	exp2 = testCoord.getLat().subtract(predecessor.getLat());
	    	exp3 = successor.getLat().subtract(predecessor.getLat());
	    	exp4 = predecessor.getLng();
	    	try {
	    		exp5 = exp2.divide(exp3, MathContext.DECIMAL32);
	    	}
	    	catch(ArithmeticException e) {
	    		System.out.println("Couldn't test if point in boundary because can't divide " + exp2 + " by " + exp3);
	    		e.printStackTrace();
	    		//exp5 = new BigDecimal(exp2.doubleValue() / exp3.doubleValue());
	    		return false;
	    	}
	    	exp6 = exp1.multiply(exp5);
	    	exp7 = exp4.add(exp6);
	    	
	    	if((predecessor.getLat().compareTo(testCoord.getLat())>0) != (successor.getLat().compareTo(testCoord.getLat())>0)
	    	&& (testCoord.getLng().compareTo(exp7) <0)){
	    		result = !result;
	    	}
	    	predecessor = successor;
	    	successor = boundaryPointsList.next();
	    }
	    while(boundaryPointsList.hasNext());
	    return result;
	}
}

