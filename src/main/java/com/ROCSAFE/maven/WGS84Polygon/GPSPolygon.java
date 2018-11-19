package com.ROCSAFE.maven.WGS84Polygon;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import com.ROCSAFE.maven.gpsutilities.WGS84Coordinate;

/**
 * A class which records a polygon made up of WGS84Coordinates
 * Assumption is made that WGS84Coordinates are close to each 
 * other which allows Cartesian geometry to be applied
 * @author David Smyth
 */

public class GPSPolygon {

	List<WGS84Coordinate> boundary;
	
	

	public GPSPolygon(List<WGS84Coordinate> boundary) throws Exception {
		//other checks for valid polygon?
		if(boundary.size() <= 2) {
			throw new Exception("Cannot create a polygon with less than 3 vertices");
		}
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
	public List<WGS84Coordinate> getBoundary() {
		return boundary;
	}
	
	protected BigDecimal getHighestLat() {
		BigDecimal highestLat = WGS84Coordinate.LOWER_LAT_BOUND;
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLat().compareTo(highestLat)>0) {
				highestLat = coord.getLat();
			}
		}
		return highestLat;
	}
	
	protected BigDecimal getHighestLng() {
		BigDecimal highestLng = WGS84Coordinate.LOWER_LNG_BOUND;
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLng().compareTo(highestLng)>0) {
				highestLng = coord.getLng();
			}
		}
		return highestLng;
	}
	
	protected BigDecimal getLowestLng() {
		BigDecimal lowestLng = WGS84Coordinate.UPPER_LNG_BOUND;
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLng().compareTo(lowestLng)<0) {
				lowestLng = coord.getLng();
			}
		}
		return lowestLng;
	}
	
	protected BigDecimal getLowestLat() {
		BigDecimal lowestLat = WGS84Coordinate.UPPER_LNG_BOUND;
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLat().compareTo(lowestLat)<0) {
				lowestLat = coord.getLat();
			}
		}
		return lowestLat;
	}
	
	protected WGS84Coordinate getHighestLatCoord() {
		WGS84Coordinate highestLat = boundary.get(0);
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLat().compareTo(highestLat.getLat())>0) {
				highestLat = coord;
			}
		}
		return highestLat;
	}
	
	protected WGS84Coordinate getHighestLngCoord() {
		WGS84Coordinate highestLng = boundary.get(0);
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLng().compareTo(highestLng.getLng())>0) {
				highestLng = coord;
			}
		}
		return highestLng;
	}
	
	protected WGS84Coordinate getLowestLngCoord() {
		WGS84Coordinate lowestLng = boundary.get(0);
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLng().compareTo(lowestLng.getLng())<0) {
				lowestLng = coord;
			}
		}
		return lowestLng;
	}
	
	protected WGS84Coordinate getLowestLatCoord() {
		WGS84Coordinate lowestLat = boundary.get(0);
		for(WGS84Coordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLat().compareTo(lowestLat.getLat())<0) {
				lowestLat = coord;
			}
		}
		return lowestLat;
	}

	
	protected void setBoundary(List<WGS84Coordinate> boundary) {
		this.boundary = boundary;
	}
	
	public String toString() {
		String returnString = "";
		for(WGS84Coordinate coord: getBoundary()) {
			returnString += coord.toString();
		}
		return returnString;
	}
	
	/**
	 * 
	 * @param test - a WGS84Coordinate which will be tested for inclusion in this polynomial
	 * @return true if test is in this polynomial, else false
	 */
	public boolean pointInPolygon(WGS84Coordinate testCoord) {
		int i;
	    int j;
	    boolean result = false;
	    List<WGS84Coordinate> boundaryPoints = getBoundary();
	    BigDecimal exp1;
	    BigDecimal exp2;
	    BigDecimal exp3;
	    BigDecimal exp4;
	    BigDecimal exp5;
	    BigDecimal exp6;
	    BigDecimal exp7;
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
	}
}

