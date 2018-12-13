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
class GPSQuadrilateralTest {
	
	WGS84Coordinate p1;
	WGS84Coordinate p2;
	WGS84Coordinate p3;
	WGS84Coordinate p4;
	WGS84Coordinate p5;
	
	List<WGS84Coordinate> squareBoundary;
	List<WGS84Coordinate> pentagonBoundary;
	List<WGS84Coordinate> triangleBoundary;
	
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
		
		squareBoundary = new ArrayList<WGS84Coordinate>(Arrays.asList(p1,p2,p3,p4));
		pentagonBoundary = new ArrayList<WGS84Coordinate>(Arrays.asList(p1,p2,p3,p4,p5));
		triangleBoundary = new ArrayList<WGS84Coordinate>(Arrays.asList(p1,p2,p3));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSQuadrilateral#GPSQuadrilateral(java.util.List)}.
	 * @throws Exception 
	 */
	@Test
	void testGPSQuadrilateralListOfWGS84Coordinate() throws Exception {
		assertThrows(Exception.class, ()->new WGS84Quadrilateral(pentagonBoundary));
		assertThrows(Exception.class, ()->new WGS84Quadrilateral(triangleBoundary));
		assertEquals(WGS84Quadrilateral.class, new WGS84Quadrilateral(squareBoundary).getClass());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSQuadrilateral#GPSQuadrilateral(com.ROCSAFE.maven.gpsutilities.WGS84Coordinate, com.ROCSAFE.maven.gpsutilities.WGS84Coordinate, com.ROCSAFE.maven.gpsutilities.WGS84Coordinate, com.ROCSAFE.maven.gpsutilities.WGS84Coordinate)}.
	 
	@Test
	void testGPSQuadrilateralWGS84CoordinateWGS84CoordinateWGS84CoordinateWGS84Coordinate() {
		fail("Not yet implemented");
	}
	 */
}
