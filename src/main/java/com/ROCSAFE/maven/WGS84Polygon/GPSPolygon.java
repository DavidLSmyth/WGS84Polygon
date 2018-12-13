package com.ROCSAFE.maven.WGS84Polygon;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Stream;

import com.ROCSAFE.maven.gpsutilities.WGS84Coordinate;

/**
 * A class which records a polygon made up of WGS84Coordinates
 * Assumption is made that WGS84Coordinates are close to each 
 * other which allows Cartesian geometry to be applied.
 * Polygon is assumed to be oriented.
 * @author David Smyth
 */

public class GPSPolygon {

	//boundary of the GPSPolygon is just a unique set of WGS84Coordinates
	LinkedHashSet<WGS84Coordinate> boundary;
	

	public GPSPolygon(List<WGS84Coordinate> boundary) throws Exception {
		//other checks for valid polygon?
		this(new LinkedHashSet<WGS84Coordinate>(boundary));
	}
	
	public GPSPolygon(LinkedHashSet<WGS84Coordinate> boundary) throws Exception {
		//other checks for valid polygon?
		setBoundary(boundary);
	}
	
	public GPSQuadrilateral getBoundingQuadrilateral() throws Exception {
		//returns a bounding quadrilateral whose edges run parallel to the x,y axes of 
		//WGS coordinate system
		return new GPSQuadrilateral(new WGS84Coordinate(getLowestLat(), getLowestLng()),
				new WGS84Coordinate(getLowestLat(), getHighestLng()),
				new WGS84Coordinate(getHighestLat(), getHighestLng()), 
				new WGS84Coordinate(getHighestLat(), getLowestLng()));
	}
	
	/**
	 * Returns the bounding coordinates of the polygon. 
	 * @return List<WGS84Coordinate>
	 */
	public LinkedHashSet<WGS84Coordinate> getBoundary() {
		return boundary;
	}
	
	protected BigDecimal getHighestLat() {
		return getHighestLatCoord().getLat();
		/*
		BigDecimal highestLat = WGS84Coordinate.LOWER_LAT_BOUND;
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLat().compareTo(highestLat)>0) {
				highestLat = coord.getLat();
			}
		}
		return highestLat;
		*/
	}
	
	protected BigDecimal getHighestLng() {
		return getHighestLngCoord().getLng();
		/*
		BigDecimal highestLng = WGS84Coordinate.LOWER_LNG_BOUND;
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLng().compareTo(highestLng)>0) {
				highestLng = coord.getLng();
			}
		}
		return highestLng;
		*/
	}
	
	protected BigDecimal getLowestLng() {
		return getLowestLngCoord().getLng();
		/*
		BigDecimal lowestLng = WGS84Coordinate.UPPER_LNG_BOUND;
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLng().compareTo(lowestLng)<0) {
				lowestLng = coord.getLng();
			}
		}
		return lowestLng;
		*/
	}
	
	protected BigDecimal getLowestLat() {
		return getLowestLatCoord().getLat();
		
		/*
		BigDecimal lowestLat = WGS84Coordinate.UPPER_LNG_BOUND;
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLat().compareTo(lowestLat)<0) {
				lowestLat = coord.getLat();
			}
		}
		return lowestLat;
		*/
	}
	
	protected WGS84Coordinate getHighestLatCoord() {
		//Stream<WGS84Coordinate> boundaryIterator = boundary.stream();
		
		//A sequence of elements supporting sequential and parallel aggregateoperations.
		//The following example illustrates an aggregate operation using Stream and IntStream: 
		//int sum = widgets.stream()
        //       .filter(w -> w.getColor() == RED)
        //       .mapToInt(w -> w.getWeight())
        //       .sum();
		
		//map(v->v.getLat())
		//Comparator<WGS84Coordinate> comparator = Comparator.comparing(WGS84Coordinate::getLat);
		//System.out.println(comparator.compare(new WGS84Coordinate(2,2), new WGS84Coordinate(5,2)));
		Optional<WGS84Coordinate> highestLatCoord = boundary.stream().max(Comparator.comparing(WGS84Coordinate::getLat));
				
				//Comparator.comparing(WGS84Coordinate::getLat, (lat1, lat2) -> {
		//return lat1.compareTo(lat2);}));
				//(s1,s2) ->{
		//return s1.compareTo(s2);}));
		/*
		for(WGS84Coordinate coord: boundaryIterator) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLat().compareTo(highestLat.getLat())>0) {
				highestLat = coord;
			}
		}
		*/
		return highestLatCoord.get();
	}
	
	protected WGS84Coordinate getHighestLngCoord() {
		/*
		WGS84Coordinate highestLng = boundary.get(0);
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLng().compareTo(highestLng.getLng())>0) {
				highestLng = coord;
			}
		}
		return highestLng;
		*/
		Optional<WGS84Coordinate> highestLng = boundary.stream().max(Comparator.comparing(WGS84Coordinate::getLng, (lng1, lng2) -> {
			return lng1.compareTo(lng2);}));
		return highestLng.get();
	}
	
	protected WGS84Coordinate getLowestLngCoord() {
		
		Optional<WGS84Coordinate> lowestLng = boundary.stream().min(Comparator.comparing(WGS84Coordinate::getLng, (lng1, lng2) -> {
			return lng1.compareTo(lng2);}));
		return lowestLng.get();
		
		
		/*
		WGS84Coordinate lowestLng = boundary.get(0);
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLng().compareTo(lowestLng.getLng())<0) {
				lowestLng = coord;
			}
		}
		return lowestLng;
		*/
		
		
	}
	
	protected WGS84Coordinate getLowestLatCoord() {
		
		Optional<WGS84Coordinate> lowestLat = boundary.stream().min(Comparator.comparing(WGS84Coordinate::getLat, (lat1, lat2) -> {
			return lat1.compareTo(lat2);}));
		
		return lowestLat.get();
		/*
		WGS84Coordinate lowestLat = boundary.get(0);
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLat().compareTo(lowestLat.getLat())<0) {
				lowestLat = coord;
			}
		}
		return lowestLat;
		*/
	}

	
	protected void setBoundary(LinkedHashSet<WGS84Coordinate> boundary) throws Exception {
		if(boundary.size() <= 2) {
			throw new Exception("Cannot create a polygon with less than 3 vertices");
		}
		else {
			this.boundary = boundary;
		}
	}
	
	public String toString() {
		String returnString = "";
		for(WGS84Coordinate coord: getBoundary()) {
			returnString += coord.toString() + "\n";
		}
		return returnString.substring(0, returnString.length() - 1);
	}
	
	public boolean equals(Object other) {
		if(other == null || other.getClass() != getClass()) {
			return false;
		}
		else {
			GPSPolygon otherCast = (GPSPolygon) other;
			if(otherCast.getBoundary().equals(getBoundary())) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	/**
	 * 
	 * @param testCoord - a WGS84Coordinate which will be tested for inclusion in this polynomial
	 * For the line defined by each pair of successive points in the boundary, figure out whether 
	 * a horizontal ray extended from the coordinate to be tested crosses it.  
	 * @return true if test is in this polynomial, else false
	 */
	public boolean pointInPolygon(WGS84Coordinate testCoord) {
		int i;
	    int j;
	    
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
	    LinkedHashSet<WGS84Coordinate> boundaryPoints = getBoundary();
	    
	    //calculations broken apart into expressions
	    BigDecimal exp1;
	    BigDecimal exp2;
	    BigDecimal exp3;
	    BigDecimal exp4;
	    BigDecimal exp5;
	    BigDecimal exp6;
	    BigDecimal exp7;
	    
	    Iterator<WGS84Coordinate> boundaryPointsList = boundaryPoints.iterator();
	    
	    WGS84Coordinate predecessor = boundaryPointsList.next();
	    WGS84Coordinate successor = boundaryPointsList.next();
	    
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
	    
	    
	    //j is the number before i
	    //iterate over the line going from point i to i+1 and check if 
	    /*
	    for (i = 0, j = getBoundary().size() - 1; i < getBoundary().size(); j = i++) {
	    	exp1 = boundaryPoints.get(j).getLng().subtract(boundaryPoints.get(i).getLng());
	    	exp2 = testCoord.getLat().subtract(boundaryPoints.get(i).getLat());
	    	exp3 = boundaryPoints.get(j).getLat().subtract(boundaryPoints.get(i).getLat());
	    	exp4 = boundaryPoints.get(i).getLng();
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
	    	
	    	if((boundaryPoints.get(i).getLat().compareTo(testCoord.getLat())>0) != (boundaryPoints.get(j).getLat().compareTo(testCoord.getLat())>0)
	    	&& (testCoord.getLng().compareTo(exp7) <0)){
	    		result = !result;
	    	}
	    }
	    return result;
	    */
	}
}

