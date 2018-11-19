package com.ROCSAFE.maven.WGS84Polygon;

import java.util.ArrayList;
import java.util.List;

import com.ROCSAFE.maven.gpsutilities.WGS84Coordinate;
import com.ROCSAFE.maven.gpsutilities.WGS84CoordinateUtils;

/**
 * 
 * @author David Smyth
 * A class which creates a grid inside a Polygon formed by WGS84 GPS coordinates
 *
 */
public class GPSPolygonGrid {

	GPSPolygon polygon;
	double latSpacing;
	double lngSpacing;
	//Does it make sense for the grid to have an altitude?
	double altitude;
	
	private int noGridPointsInBoundingRect;
	private int noGridPointsInPolygon;
	private boolean canReturnCachedGridPoints;
	//a cache to store calculated grid points in Rectangle
	private List<WGS84Coordinate> cachedRectangleGridPoints;
	private List<WGS84Coordinate> cachedPolygonGridPoints;
	//The quadrilateral that circumscribes the polygon
	private GPSQuadrilateral boundingQuadrilateral;
	
	/**
	 * @param polygon
	 * @param latSpacing - latitude spacing in metres
	 * @param lngSpacing - longitude spacing in metres
	 * @throws Exception 
	 */
	public GPSPolygonGrid(GPSPolygon polygon, double latSpacing, double lngSpacing) throws Exception {
		setPolygon(polygon);
		setLatSpacing(latSpacing);
		setLngSpacing(lngSpacing);
		canReturnCachedGridPoints = false;
		boundingQuadrilateral = polygon.getBoundingQuadrilateral();
		
	}
	
	public GPSPolygonGrid(GPSPolygon polygon, double latSpacing, double lngSpacing, double altitude) throws Exception {
		this(polygon, latSpacing, lngSpacing);
	}
	
	public GPSPolygonGrid(List<WGS84Coordinate> boundary, double latSpacing, double lngSpacing) throws Exception {
		this(new GPSPolygon(boundary), latSpacing, lngSpacing);
	}
	
	/**
	 * Height calculated as latitude difference in metres between highest latitude coordinate and lowest 
	 * latitude coordinate .
	 * @return
	 * @throws Exception
	 */
	public double getHeight() throws Exception {
		return WGS84CoordinateUtils.getDistanceMetresLatToOther(boundingQuadrilateral.getLowestLatCoord(), 
																boundingQuadrilateral.getHighestLatCoord());
	}
	
	public double getWidth() throws Exception {
		return WGS84CoordinateUtils.getDistanceMetresLngToOther(boundingQuadrilateral.getLowestLngCoord(), 
				boundingQuadrilateral.getHighestLngCoord());
	}
	
	public String toString() {
		try {
			String returnString = "Polygon width: " + getWidth() + ", polygon height: " + getHeight()+ " lat spacing: " + 
		getLatSpacing() + " long spacing: " + getLngSpacing() + " bounding points " + getPolygon();
			return returnString;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Can't get polygon string representation";
		}
	}
	
	/**
	 * A method which generates WGS84Coordinates in the bounding rectangle of this polygon
	 * according to the specified latitude and longitude spacing.
	 * Returned cached grid points if previously generated.
	 * @return List<WGS84Coordinate>
	 * @throws Exception
	 */
	private List<WGS84Coordinate> generateGridPoints() throws Exception{
		if(canReturnCachedGridPoints) {
			return cachedRectangleGridPoints;
		}
		GPSQuadrilateral quad = polygon.getBoundingQuadrilateral();
		//System.out.println("Testing rectangle: " + quad);
		
		double quadHeight = getHeight();
		double quadWidth = getWidth();
		//System.out.println("quadHeight: " + quadHeight);
		
		int noLatPoints = (int) (quadHeight / latSpacing);
		int noLngPoints = (int) (quadWidth / lngSpacing);
		
		if(noLatPoints == 0 && noLngPoints ==0) {
			throw new Exception("No points generated in bounding rectangle, you need to decrease your grid spacing");
		}
		
		WGS84Coordinate bottomLeft = new WGS84Coordinate(quad.getLowestLat(), quad.getLowestLng()); 
		WGS84Coordinate bottomRight = new WGS84Coordinate(quad.getLowestLat(), quad.getHighestLng());
		WGS84Coordinate topRight = new WGS84Coordinate(quad.getHighestLat(), quad.getHighestLng());
		
		WGS84Coordinate latDegreeDifference = topRight.subtract(bottomRight).divide(noLatPoints); 
		WGS84Coordinate lngDegreeDifference = bottomRight.subtract(bottomLeft).divide(noLngPoints); 
		
		List<WGS84Coordinate> gridPoints = new ArrayList<WGS84Coordinate>();
		
		for(int latPointCounter=0; latPointCounter < noLatPoints; latPointCounter++) {
			for(int lngPointCounter = 0; lngPointCounter < noLngPoints; lngPointCounter++) {
				gridPoints.add(bottomLeft.add(latDegreeDifference.multiply(latPointCounter).add(lngDegreeDifference.multiply(lngPointCounter))));
			}
		}
		canReturnCachedGridPoints = true;
		cachedRectangleGridPoints = gridPoints;
		return gridPoints;
	}
	
	
		
	/**
	 * A method which generates WGS84Coordinates contained in this polynomial
	 * @return
	 * @throws Exception
	 */
	public List<WGS84Coordinate> generateContainedWGS84Coordinates() throws Exception{
		if(canReturnCachedGridPoints) {
			return cachedPolygonGridPoints;
		}
		//for each generated GPS coordinate in the bounding rectangle, check whether it lies inside the polygon
		List<WGS84Coordinate> gridPoints = new ArrayList<WGS84Coordinate>();
		
		//Exception will be thrown from here if there are no coordinates generated in bounding rectangle.
		List<WGS84Coordinate> boundingRectCoords = generateGridPoints();

		int pointsInPolygonCounter = 0;
		
		for(WGS84Coordinate coord: boundingRectCoords) {
			//System.out.println("Testing if point " + coord + " is in grid.");
			if(polygon.pointInPolygon(coord)) {
				gridPoints.add(coord);
				pointsInPolygonCounter++;
			}
		}
		
		setNoGridPointsInBoundingRect(boundingRectCoords.size());
		setNoGridPointsInPolygon(pointsInPolygonCounter);
		
//uncomment this
//		System.out.println("Number of generated points in rect: " + getNoGridPointsInBoundingRect());
//		System.out.println("Number of generated points in poly: " + getNoGridPointsInPolygon());
//		System.out.println("Ratio of generated points in bounding rect vs. generated points in poly: " + Double.toString(getRatioOfPolyToRectGridPoints()));
		cachedPolygonGridPoints = gridPoints;
		canReturnCachedGridPoints = true;
		return gridPoints;
	}
	
	/**
	 * Gives the ratio of how many grid points had to be generated in the bounding rectangle to the polygon
	 * @return
	 */
	public double getRatioOfPolyToRectGridPoints() {
		if (getNoGridPointsInBoundingRect() != 0) { 
			return ((double) getNoGridPointsInPolygon()) / ((double)getNoGridPointsInBoundingRect());
		}
		else {
//			System.out.println("Warning: no points found in bounding rect - perhaps change the grid spacing.");
			return -1;
		}
	}
	
	public int getNoGridPointsInBoundingRect() {
		return noGridPointsInBoundingRect;
	}

	protected void setNoGridPointsInBoundingRect(int noGridPointsInBoundingRect) {
		this.noGridPointsInBoundingRect = noGridPointsInBoundingRect;
	}

	public int getNoGridPointsInPolygon() {
		return noGridPointsInPolygon;
	}

	protected void setNoGridPointsInPolygon(int noGridPointsInPolygon) {
		canReturnCachedGridPoints = false;
		this.noGridPointsInPolygon = noGridPointsInPolygon;
	}
	
	public GPSPolygon getPolygon() {
		return polygon;
	}


	public void setPolygon(GPSPolygon polygon) {
		canReturnCachedGridPoints = false;
		this.polygon = polygon;
	}


	public double getLatSpacing() {
		return latSpacing;
	}

	public void setLatSpacing(double latSpacing) {
		canReturnCachedGridPoints = false;
		this.latSpacing = latSpacing;
	}


	public double getLngSpacing() {
		return lngSpacing;
	}


	public void setLngSpacing(double lngSpacing) {
		canReturnCachedGridPoints = false;
		this.lngSpacing = lngSpacing;
	}
}
