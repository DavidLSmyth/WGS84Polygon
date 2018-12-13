/**
 * 
 */
package com.ROCSAFE.maven.WGS84Polygon;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ROCSAFE.maven.gpsutilities.WGS84Coordinate;

/**
 * @author David Smyth
 *
 */
class GPSPolygonTest {
	
	WGS84Coordinate p1;
	WGS84Coordinate p2;
	WGS84Coordinate p3;
	WGS84Coordinate p4;
	WGS84Coordinate p5;
	
	List<WGS84Coordinate> squareBoundary;
	GPSPolygon square;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		//lat,long
		p1 = new WGS84Coordinate(0,0);
		p2 = new WGS84Coordinate(0.25,0.25);
		p3 = new WGS84Coordinate(0,0.5);
		p4 = new WGS84Coordinate(-0.25,0.25);
		p5 = new WGS84Coordinate(-0.125, -4);
		
		squareBoundary = new ArrayList<WGS84Coordinate>(Arrays.asList(p1,p2,p3,p4));
		square = new GPSPolygon(squareBoundary);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#GPSPolygon(java.util.List)}.
	 */
	@Test
	void testGPSPolygon() {
		//ensure that constuctor throws correct execption when too few vertices provided
		assertThrows(Exception.class, ()->new GPSPolygon(Arrays.asList(p1)));
		assertThrows(Exception.class, ()->new GPSPolygon(Arrays.asList(p1,p2)));
	}
	
	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#equals(Object other)}.
	 * @throws Exception 
	 */
	@Test
	void testEquals() throws Exception {
		GPSPolygon poly1 = new GPSPolygon(new ArrayList<WGS84Coordinate>(Arrays.asList(p3,p1,p4,p2, p4)));
		GPSPolygon poly2 = new GPSPolygon(new ArrayList<WGS84Coordinate>(Arrays.asList(p3,p1,p4)));
		assertEquals(poly1, square);
		assertNotEquals(poly2, square);
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#getBoundingQuadrilateral()}.
	 * @throws Exception 
	 */
	@Test
	void testGetBoundingQuadrilateral() throws Exception {
		for(WGS84Coordinate c: square.getBoundingQuadrilateral().getBoundary()) {
			System.out.println(c);
		}
		WGS84Coordinate boundingQuadP1 = new WGS84Coordinate(0.25,0.5);
		WGS84Coordinate boundingQuadP2 = new WGS84Coordinate(-0.25,0.5);
		WGS84Coordinate boundingQuadP3 = new WGS84Coordinate(0.25,0);
		WGS84Coordinate boundingQuadP4 = new WGS84Coordinate(-0.25,0);
		GPSQuadrilateral boundary = new GPSQuadrilateral(boundingQuadP1,
				boundingQuadP2,
				boundingQuadP3,
				boundingQuadP4);
		for(WGS84Coordinate c: boundary.getBoundary()) {
			System.out.println(square.getBoundingQuadrilateral().getBoundary().contains(c));
		}
		//assertTrue(boundary.getBoundary().containsAll(square.getBoundingQuadrilateral().getBoundary()));
		//ensure that set of points defining bounding quadrilateral are equal
		assertEquals(new HashSet<WGS84Coordinate>(boundary.getBoundary()), new HashSet<WGS84Coordinate>(square.getBoundingQuadrilateral().getBoundary()));
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#getBoundary()}.
	 * @throws Exception 
	 */
	@Test
	void testGetBoundary() throws Exception {
		assertEquals(new LinkedHashSet<WGS84Coordinate>(squareBoundary), square.getBoundary());
		squareBoundary.add(p5);
		square.setBoundary(new LinkedHashSet<WGS84Coordinate>(squareBoundary));
		assertEquals(new LinkedHashSet<WGS84Coordinate>(squareBoundary), square.getBoundary());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#getHighestLat()}.
	 * @throws Exception 
	 */
	@Test
	void testGetHighestLat() {
		assertEquals(p2.getLat(), square.getHighestLat());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#getHighestLng()}.
	 */
	@Test
	void testGetHighestLng() {
		assertEquals(p3.getLng(), square.getHighestLng());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#getLowestLng()}.
	 */
	@Test
	void testGetLowestLng() {
		assertEquals(p1.getLng(), square.getLowestLng());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#getLowestLat()}.
	 */
	@Test
	void testGetLowestLat() {
		assertEquals(p4.getLat(), square.getLowestLat());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#getHighestLatCoord()}.
	 * @throws Exception 
	 */
	@Test
	void testGetHighestLatCoord() {
		assertEquals(p2, square.getHighestLatCoord());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#getHighestLngCoord()}.
	 */
	@Test
	void testGetHighestLngCoord() {
		assertEquals(p3, square.getHighestLngCoord());		
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#getLowestLngCoord()}.
	 */
	@Test
	void testGetLowestLngCoord() {
		assertEquals(p1, square.getLowestLngCoord());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#getLowestLatCoord()}.
	 */
	@Test
	void testGetLowestLatCoord() {
		assertEquals(p4, square.getLowestLatCoord());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#setBoundary(java.util.List)}.
	 */
	@Test
	void testSetBoundary() {
		assertThrows(Exception.class, ()->square.setBoundary(new LinkedHashSet<WGS84Coordinate>(Arrays.asList(p1, p2))));
		assertThrows(Exception.class, ()->square.setBoundary(new LinkedHashSet<WGS84Coordinate>(Arrays.asList(p1))));
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#toString()}.
	 */
	@Test
	void testToString() {
		assertEquals(p1.toString() + "\n" +
				p2.toString() + "\n" + 
				p3.toString() + "\n" +
				p4.toString(),square.toString());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.GPSPolygon#pointInPolygon(com.ROCSAFE.maven.gpsutilities.WGS84Coordinate)}.
	 * @throws Exception 
	 */
	@Test
	void testPointInPolygon() throws Exception {
		assertTrue(square.pointInPolygon(new WGS84Coordinate(0,0.1)));
		assertFalse(square.pointInPolygon(new WGS84Coordinate(5,0.1)));
		assertFalse(square.pointInPolygon(new WGS84Coordinate(-5,0.1)));
		assertFalse(square.pointInPolygon(new WGS84Coordinate(0.25,3)));
		assertFalse(square.pointInPolygon(new WGS84Coordinate(0.25,-33)));
	}

}
