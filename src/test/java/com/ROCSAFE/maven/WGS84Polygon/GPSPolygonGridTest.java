/**
 * 
 */
package com.ROCSAFE.maven.WGS84Polygon;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ROCSAFE.maven.gpsutilities.WGS84Coordinate;

/**
 * @author 13383861
 *
 */
class GPSPolygonGridTest {
	
	WGS84Coordinate p1;
	WGS84Coordinate p2;
	WGS84Coordinate p3;
	WGS84Coordinate p4;
	WGS84Coordinate p5;
	
	double latSpacing;
	double lngSpacing;
	
	List<WGS84Coordinate> squareBoundary;
	WGS84Polygon square;
	WGS84PolygonGrid squareGrid;
	WGS84PolygonGrid testGrid;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		p1 = new WGS84Coordinate(0,0);
		p2 = new WGS84Coordinate(0.25,0.25);
		p3 = new WGS84Coordinate(0,0.5);
		p4 = new WGS84Coordinate(-0.25,0.25);
		p5 = new WGS84Coordinate(-0.125, -4);
		
		latSpacing = 10000;
		lngSpacing = 12000;
		
		squareBoundary = new ArrayList<WGS84Coordinate>(Arrays.asList(p1,p2,p3,p4));
		square = new WGS84Polygon(squareBoundary);
		squareGrid = new WGS84PolygonGrid(square,latSpacing, lngSpacing);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#GPSPolygonGrid(com.ROCSAFE.maven.WGS84Polygon.GPSPolygon, double, double)}.
	 * @throws Exception 
	 */
	@Test
	void testGPSPolygonGridGPSPolygonDoubleDouble() throws Exception {
		testGrid = new WGS84PolygonGrid(square,latSpacing, lngSpacing);
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#GPSPolygonGrid(com.ROCSAFE.maven.WGS84Polygon.GPSPolygon, double, double, double)}.
	 * @throws Exception 
	 */
	@Test
	void testGPSPolygonGridGPSPolygonDoubleDoubleDouble() throws Exception {
		testGrid = new WGS84PolygonGrid(square,latSpacing, lngSpacing,0);
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#GPSPolygonGrid(java.util.List, double, double)}.
	 * @throws Exception 
	 */
	@Test
	void testGPSPolygonGridListOfWGS84CoordinateDoubleDouble() throws Exception {
		testGrid = new WGS84PolygonGrid(squareBoundary,latSpacing, lngSpacing);
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#getHeight()}.
	 * @throws Exception 
	 */
	@Test
	void testGetHeight() throws Exception {
		//fail("Not yet implemented");
		assertEquals(p4.getMetresToOther(p2),squareGrid.getHeight(), 20);
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#getWidth()}.
	 * @throws Exception 
	 */
	@Test
	void testGetWidth() throws Exception {
		//fail("Not yet implemented");
		assertEquals(p1.getMetresToOther(p3),squareGrid.getWidth(), 20);
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#toString()}.
	 */
	@Test
	void testToString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#generateContainedWGS84Coordinates()}.
	 * @throws Exception 
	 */
	@Test
	void testGenerateContainedWGS84Coordinates() throws Exception {
		squareGrid.generateContainedWGS84Coordinates();
	}
	
	@Test
	void testGenerateGridPointsTest() throws Exception {
		//squareGrid.generateGridPointsTest();
		
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#getRatioOfPolyToRectGridPoints()}.
	 */
	@Test
	void testGetRatioOfPolyToRectGridPoints() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#getNoGridPointsInBoundingRect()}.
	 */
	@Test
	void testGetNoGridPointsInBoundingRect() {
		assertEquals(20, squareGrid.getNoGridPointsInBoundingRect());
	}


	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#getNoGridPointsInPolygon()}.
	 * @throws Exception 
	 */
	@Test
	void testGetNoGridPointsInPolygon() throws Exception {
		squareGrid.generateContainedWGS84Coordinates();
		assertEquals(20, squareGrid.getNoGridPointsInPolygon());
	}
	
	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#getLatSpacing()}.
	 */
	@Test
	void testGetLatSpacing() {
		assertEquals(10000, squareGrid.getLatSpacing());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#setLatSpacing(double)}.
	 */
	@Test
	void testSetLatSpacing() {
		squareGrid.setLatSpacing(200);
		assertEquals(200, squareGrid.getLatSpacing());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#getLngSpacing()}.
	 */
	@Test
	void testGetLngSpacing() {
		assertEquals(12000, squareGrid.getLngSpacing());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygonGrid#setLngSpacing(double)}.
	 */
	@Test
	void testSetLngSpacing() {
		squareGrid.setLngSpacing(200);
		assertEquals(200, squareGrid.getLngSpacing());

	}

}
