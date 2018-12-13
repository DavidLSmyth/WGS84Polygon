/**
 * 
 */
package com.ROCSAFE.maven.WGS84Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ROCSAFE.maven.gpsutilities.WGS84Coordinate;
import com.ROCSAFE.maven.gpsutilities.WGS84CoordinateUtils;

/**
 * @author 13383861
 *
 */
public class WGS84PolygonGrid extends PolygonGridBase<WGS84Coordinate> {

	public WGS84PolygonGrid(WGS84Polygon polygon, double latSpacing, double lngSpacing, double altitude)
			throws Exception {
		super(polygon, latSpacing, lngSpacing, altitude);
		// TODO Auto-generated constructor stub
	}
	
	public WGS84PolygonGrid(WGS84Polygon polygon, double latSpacing, double lngSpacing) throws Exception {
		super(polygon, latSpacing, lngSpacing);
	}

	public WGS84PolygonGrid(List<WGS84Coordinate> boundary, double latSpacing, double lngSpacing) throws Exception {
		// TODO Auto-generated constructor stub
		super(new WGS84Polygon(boundary), latSpacing, lngSpacing);
	}

	/**
	 * Height calculated as latitude difference in metres between highest latitude coordinate and lowest 
	 * latitude coordinate .
	 * @return
	 * @throws Exception
	 */
	public double getHeight() throws Exception {
		return WGS84CoordinateUtils.getDistanceMetresLatToOther(getBoundingQuad().getLowestLatCoord(), 
				getBoundingQuad().getHighestLatCoord());
	}
	
	public double getWidth() throws Exception {
		return WGS84CoordinateUtils.getDistanceMetresLngToOther(getBoundingQuad().getLowestLngCoord(), 
				getBoundingQuad().getHighestLngCoord());
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
	
	/*
	 * Returns the number of lat points in the grid
	 */
	private int getNoLatPoints() throws Exception {		
		return (int) (getHeight() / latSpacing);
	}
	
	/*
	 * Returns the number of lng points in the grid
	 */
	private int getNoLngPoints() throws Exception {
		return (int) (getWidth() / lngSpacing);
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
		QuadrilateralBase<WGS84Coordinate> quad = polygon.getBoundingQuadrilateral();
		//System.out.println("Testing rectangle: " + quad);
		
		/*
		double quadHeight = getHeight();
		double quadWidth = getWidth();
		//System.out.println("quadHeight: " + quadHeight);
		
		int noLatPoints = (int) (quadHeight / latSpacing);
		int noLngPoints = (int) (quadWidth / lngSpacing);
		
		if(noLatPoints == 0 && noLngPoints ==0) {
			throw new Exception("No points generated in bounding rectangle, you need to decrease your grid spacing");
		}
		*/
		int noLatPoints = getNoLatPoints();
		int noLngPoints = getNoLngPoints();
		//Test that these are of 'reasonable' size - memory limitations will come into effect
		
		if(noLatPoints * noLngPoints > 100000) {
			throw new Exception("Cannot hold " + noLatPoints * noLngPoints + " grid points in memory.");
		}
		
		System.out.println(noLatPoints);
		System.out.println(noLngPoints);
		
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
	
	
	public List<WGS84Coordinate> generatePolygonContainedWGS84Coordinates() throws Exception{
			return generateGridPoints().parallelStream().filter(coord->polygon.pointInPolygon(coord)).collect(Collectors.toList());
	}
	
	/**
	 * A method which generates WGS84Coordinates contained in this polygon.
	 * Each grid point in the bounding rectangle is passed to the pointInPolygon
	 * method to determine if it lies in the polygon. If it is not contained, it is 
	 * discarded.
	 * @return
	 * @throws Exception
	 */
	public List<WGS84Coordinate> generateContainedWGS84Coordinates() throws Exception{
		//Maybe should change this to a set of grid points.
		
		if(canReturnCachedGridPoints) {
			return cachedPolygonGridPoints;
		}
		
		//Exception will be thrown from here if there are no coordinates generated in bounding rectangle.
		List<WGS84Coordinate> boundingRectCoords = generateGridPoints();
		
		//for each generated GPS coordinate in the bounding rectangle, check whether it lies inside the polygon
		//List<WGS84Coordinate>gridPoints = boundingRectCoords.stream().filter(coord->polygon.pointInPolygon(coord)).collect(Collectors.toList());

		List<WGS84Coordinate>gridPoints= boundingRectCoords.parallelStream().filter(coord->polygon.pointInPolygon(coord)).collect(Collectors.toList());

		int noPointsInPolygon= gridPoints.size();
		
		/*
		for(WGS84Coordinate coord: boundingRectCoords) {
			//System.out.println("Testing if point " + coord + " is in grid.");
			if(polygon.pointInPolygon(coord)) {
				gridPoints.add(coord);
				pointsInPolygonCounter++;
			}
		}
		*/
		
		setNoGridPointsInBoundingRect(boundingRectCoords.size());
		setNoGridPointsInPolygon(noPointsInPolygon);
		
//uncomment this for debugging output
//		System.out.println("Number of generated points in rect: " + getNoGridPointsInBoundingRect());
//		System.out.println("Number of generated points in poly: " + getNoGridPointsInPolygon());
//		System.out.println("Ratio of generated points in bounding rect vs. generated points in poly: " + Double.toString(getRatioOfPolyToRectGridPoints()));
		cachedPolygonGridPoints = gridPoints;
		canReturnCachedGridPoints = true;
		return gridPoints;
	}
	

	/*
	 * Just for testing...
	 */
	public List<WGS84Coordinate> generateGridPointsTest() throws Exception{
		return generateGridPoints();		
	}

}
