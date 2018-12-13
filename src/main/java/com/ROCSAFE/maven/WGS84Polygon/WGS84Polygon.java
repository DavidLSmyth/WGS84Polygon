/**
 * 
 */
package com.ROCSAFE.maven.WGS84Polygon;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import com.ROCSAFE.maven.gpsutilities.CoordinateBase;
import com.ROCSAFE.maven.gpsutilities.CoordinateInterface;
import com.ROCSAFE.maven.gpsutilities.WGS84Coordinate;

/**
 * @author 13383861
 *
 */
public class WGS84Polygon extends PolygonBase<WGS84Coordinate> {

	public WGS84Polygon(LinkedHashSet<WGS84Coordinate> boundary) throws Exception {
		super(boundary);
		// WGS84CoordinateODO Auto-generated constructor stub
	}

	public WGS84Polygon(List<WGS84Coordinate> boundary) throws Exception {
		// WGS84CoordinateODO Auto-generated constructor stub
		super(boundary);
	}

	/*
	public boolean pointInPolygon(WGS84Coordinate testCoord) {
		try {
			return pointInPolygonPrivate(testCoord);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
*/
	/**
	 * 
	 * @param testCoord - a WGS84Coordinate which will be tested for inclusion in this polynomial
	 * For the line defined by each pair of successive points in the boundary, figure out whether 
	 * a horizontal ray extended from the coordinate to be tested crosses it.  
	 * @return true if test is in this polynomial, else false
	 * @throws Exception 
	 */
	/*
	private boolean pointInPolygonPrivate(WGS84Coordinate testCoord) throws Exception {
		if(testCoord.getClass() != boundary.iterator().next().getClass()) {
			throw new Exception("Cannot test if point of type " + testCoord.getClass() + " is in polygon.");
			//return (Boolean) null;
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
	    
	    CoordinateBase predecessor = boundaryPointsList.next();
	    CoordinateBase successor = boundaryPointsList.next();
	    
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
	    	
	    	if((predecessor.getLat().compareTo(testCoord.getY())>0) != (successor.getLat().compareTo(testCoord.getLat())>0)
	    	&& (testCoord.getX().compareTo(exp7) <0)){
	    		result = !result;
	    	}
	    	predecessor = successor;
	    	successor = boundaryPointsList.next();
	    }
	    while(boundaryPointsList.hasNext());
	    return result;
	}
	*/

	@Override
	public QuadrilateralBase<WGS84Coordinate> getBoundingQuadrilateral() {
		// TODO Auto-generated method stub
		try {
			return new WGS84Quadrilateral(new LinkedHashSet<WGS84Coordinate>(
					Arrays.asList(getLowestLatCoord(),
					getLowestLngCoord(), 
					getHighestLatCoord(), 
					getHighestLngCoord())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


}
